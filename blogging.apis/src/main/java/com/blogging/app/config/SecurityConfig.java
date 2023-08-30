package com.blogging.app.config;

import com.blogging.app.security.CustomUserDetailService;
import com.blogging.app.security.JwtAuthenticationEntryPoint;
import com.blogging.app.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
        http.
                csrf().
                disable().
                authorizeHttpRequests().antMatchers("/api/v1/auth/login").permitAll().
//                antMatchers("/api/users/**").hasAnyRole("ADMIN","USER").
                anyRequest().
                authenticated().
                and().
                exceptionHandling().
                authenticationEntryPoint(this.jwtAuthenticationEntryPoint).
                and().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS);
               // httpBasic();

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   auth.userDetailsService(this.customUserDetailService).passwordEncoder(getPasswordEncoder());
//        auth.inMemoryAuthentication().
//                withUser("Aman").
//                password("Aman@123").
//                roles("USER").and().withUser("foo").password("foo").roles("ADMIN");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

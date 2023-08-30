package com.blogging.app.controller;

import com.blogging.app.exceptions.PasswordInvalidException;
import com.blogging.app.payload.JwtAuthRequest;
import com.blogging.app.payload.JwtAuthResponse;
import com.blogging.app.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    private ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest)
    throws PasswordInvalidException{
     this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        UserDetails userDetail = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token=this.jwtTokenHelper.generateToken(userDetail);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws PasswordInvalidException {
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
        try {
            this.authenticationManager.authenticate(token);
        }
        catch(BadCredentialsException ex){
            System.out.println();
            throw new PasswordInvalidException("Password Invalid !!");
        }


    }
}

package com.blogging.app.security;

import com.blogging.app.entity.User;
import com.blogging.app.exceptions.UsernameNotFoundException;
import com.blogging.app.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load username from database
        User user=this.userRepo.findByEmailId(username).orElseThrow(()->new UsernameNotFoundException(username));
        return user;
    }

}

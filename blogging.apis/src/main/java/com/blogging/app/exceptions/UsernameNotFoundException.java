package com.blogging.app.exceptions;

public class UsernameNotFoundException extends RuntimeException{
    private String username;

    public UsernameNotFoundException(String username) {
        super(String.format("Username not found : %s",username));
        this.username=username;
    }
}

package com.blogging.app.exceptions;

public class PasswordInvalidException extends RuntimeException{

    public PasswordInvalidException(String message) {
        super(message);
    }
}

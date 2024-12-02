package com.ztech.UserService.Exception;

public class BadCredentialsException extends RuntimeException {

    // Constructor that takes a custom message
    public BadCredentialsException(String message) {
        super(message);
    }
}

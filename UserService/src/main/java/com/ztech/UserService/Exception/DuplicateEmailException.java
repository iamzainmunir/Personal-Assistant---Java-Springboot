package com.ztech.UserService.Exception;

public class DuplicateEmailException extends RuntimeException {

    // Constructor that takes a custom message
    public DuplicateEmailException(String message) {
        super(message);
    }
}

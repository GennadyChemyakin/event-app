package com.epam.eventapp.service.exceptions;

/**
 * exception thrown when we can not find user
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
    }
}

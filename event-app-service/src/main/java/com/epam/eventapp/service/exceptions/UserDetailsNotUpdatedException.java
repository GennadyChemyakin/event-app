package com.epam.eventapp.service.exceptions;

/**
 * Exception is thrown when user details are not updated.
 */
public class UserDetailsNotUpdatedException extends RuntimeException {

    public UserDetailsNotUpdatedException(String message) {
        super(message);
    }
}

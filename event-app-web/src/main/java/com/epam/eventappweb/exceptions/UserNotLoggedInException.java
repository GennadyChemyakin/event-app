package com.epam.eventappweb.exceptions;

/**
 * class for representing exceptions that is thrown when user is not logged in case where it is necessary
 */
public class UserNotLoggedInException extends RuntimeException {

    public UserNotLoggedInException() {
    }

    public UserNotLoggedInException(String message) {
        super(message);
    }
}

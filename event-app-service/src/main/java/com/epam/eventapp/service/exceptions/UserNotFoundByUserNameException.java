package com.epam.eventapp.service.exceptions;

/**
 * Exception is thrown when it is failed to get user from database
 */
public class UserNotFoundByUserNameException extends RuntimeException{

    public UserNotFoundByUserNameException(String message) {
        super(message);
    }
}

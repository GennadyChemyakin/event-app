package com.epam.eventapp.service.exceptions;

/**
 * Exception which throwed in case user is not created
 */
public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(String message) {
        super(message);
    }
}

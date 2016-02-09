package com.epam.eventapp.service.exceptions;


/**
 * Exception which throwed in case user is not created
 */
public class UserNameAlreadyExistsException extends RuntimeException{
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}

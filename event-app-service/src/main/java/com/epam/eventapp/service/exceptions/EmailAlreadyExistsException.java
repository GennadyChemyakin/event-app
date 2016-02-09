package com.epam.eventapp.service.exceptions;

/**
 * Exception which throwed in case user is not created
 */
public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}

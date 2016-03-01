package com.epam.eventapp.service.exceptions;

/**
 * Exception which throwed in case user is not created
 */
public class InvalidIdentifierException extends RuntimeException{
    public InvalidIdentifierException(String message) {
        super(message);
    }
}

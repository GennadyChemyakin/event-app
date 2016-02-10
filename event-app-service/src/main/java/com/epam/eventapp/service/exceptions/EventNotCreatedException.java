package com.epam.eventapp.service.exceptions;

/**
 * Exception which is thrown in case event happened to be not created
 */
public class EventNotCreatedException extends RuntimeException {

    public EventNotCreatedException(String message) {
        super(message);
    }
    
}

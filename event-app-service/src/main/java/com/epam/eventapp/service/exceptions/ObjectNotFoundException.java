package com.epam.eventapp.service.exceptions;

/**
 * exception thrown when we can not find some object
 */
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException() {
    }
}

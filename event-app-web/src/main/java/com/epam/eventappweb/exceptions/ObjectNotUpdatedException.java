package com.epam.eventappweb.exceptions;


/**
 * class for representing exceptions that is thrown when event is not updated
 */
public class ObjectNotUpdatedException extends RuntimeException {

    public ObjectNotUpdatedException() {
    }

    public ObjectNotUpdatedException(String message) {
        super(message);
    }
}

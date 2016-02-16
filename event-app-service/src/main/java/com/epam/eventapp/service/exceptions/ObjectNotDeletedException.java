package com.epam.eventapp.service.exceptions;

/**
 * class for representing exceptions when some object not deleted from datasource
 */
public class ObjectNotDeletedException extends RuntimeException {

    public ObjectNotDeletedException() {
    }

    public ObjectNotDeletedException(String message) {
        super(message);
    }
}

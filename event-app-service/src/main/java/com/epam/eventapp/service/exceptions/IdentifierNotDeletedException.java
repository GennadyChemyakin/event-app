package com.epam.eventapp.service.exceptions;

/**
 * class for representing exceptions when some object not deleted from datasource
 */
public class IdentifierNotDeletedException extends RuntimeException {

    public IdentifierNotDeletedException() {
    }

    public IdentifierNotDeletedException(String message) {
        super(message);
    }
}

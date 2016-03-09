package com.epam.eventapp.service.exceptions;

/**
 * Exception which throws in case object is not created
 */
public class ObjectNotCreatedException extends RuntimeException{

    public ObjectNotCreatedException(String message) {
        super(message);
    }
}

package com.epam.eventappweb.exceptions;


/**
 * class for representing exceptions that is thrown when event is not updated
 */
public class EventNotUpdatedException extends RuntimeException {

    public EventNotUpdatedException() {
        super();
    }

    public EventNotUpdatedException(String message) {
        super(message);
    }
}

package com.epam.eventappweb.exceptions;


/**
 * class for representing exceptions that is thrown when event is not found by id
 */
public class EventNotFoundException extends RuntimeException {


    public EventNotFoundException(){
        super();
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}

package com.epam.eventappweb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class for representing exceptions that is thrown when event is not found by id
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event not found by id")
public class EventNotFoundException extends RuntimeException {


    public EventNotFoundException(){
        super();
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}

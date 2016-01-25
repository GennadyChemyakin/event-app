package com.epam.eventappweb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class for representing exceptions that is thrown when event is not updated
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event not updated")
public class EventNotUpdatedException extends RuntimeException {

    public EventNotUpdatedException() {
        super();
    }

    public EventNotUpdatedException(String message) {
        super(message);
    }
}

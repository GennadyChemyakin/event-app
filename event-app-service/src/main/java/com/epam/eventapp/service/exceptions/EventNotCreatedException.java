package com.epam.eventapp.service.exceptions;

/**
 * Created by Denys_Iakibchuk on 2/1/2016.
 */
public class EventNotCreatedException extends RuntimeException {

    public EventNotCreatedException(String message) {
        super(message);
    }
    
}

package com.epam.eventapp.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which throwed in case user is not created
 */
public class EmailExistsInTheDatabaseException extends RuntimeException{
    public EmailExistsInTheDatabaseException(String message) {
        super(message);
    }
}

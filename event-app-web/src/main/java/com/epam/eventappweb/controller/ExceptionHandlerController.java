package com.epam.eventappweb.controller;

import com.epam.eventapp.service.exceptions.InvalidIdentifierException;
import com.epam.eventapp.service.exceptions.ObjectNotCreatedException;
import com.epam.eventapp.service.exceptions.ObjectNotDeletedException;
import com.epam.eventapp.service.exceptions.ObjectNotFoundException;
import com.epam.eventappweb.exceptions.ObjectNotUpdatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * controller to handle app wide exceptions
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not updated")
    @ExceptionHandler(ObjectNotUpdatedException.class)
    public void handleObjectNotUpdated(ObjectNotUpdatedException exception) {
        LOGGER.error("handleObjectNotUpdated handle exception.", exception);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not deleted")
    @ExceptionHandler(ObjectNotDeletedException.class)
    public void handleObjectNotDeleted(ObjectNotDeletedException exception) {
        LOGGER.error("handleObjectNotDeleted handle exception.", exception);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found")
    @ExceptionHandler(ObjectNotFoundException.class)
    public void handleObjectNotFound(ObjectNotFoundException exception) {
        LOGGER.error("handleObjectNotFound handle exception.", exception);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found")
    @ExceptionHandler(ObjectNotCreatedException.class)
    public void handleObjectNotCreated(ObjectNotCreatedException exception) {
        LOGGER.error("handleObjectNotCreated handle exception.", exception);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden action")
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDenied(AccessDeniedException exception) {
        LOGGER.error("handleAccessDenied handle exception.", exception);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Invalid Identifier")
    @ExceptionHandler(InvalidIdentifierException.class)
    public void handleInvalidIdentifierException(InvalidIdentifierException exception) {
        LOGGER.error("handleInvalidIdentifierException handle exception.", exception);
    }
}

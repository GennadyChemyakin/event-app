package com.epam.eventappweb.controller;

import com.epam.eventapp.service.exceptions.ObjectNotDeletedException;
import com.epam.eventappweb.exceptions.EventNotFoundException;
import com.epam.eventappweb.exceptions.EventNotUpdatedException;
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

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event not found by id")
    @ExceptionHandler(EventNotFoundException.class)
    public void handleEventNotFound(EventNotFoundException exception) {
        LOGGER.error("handleEventNotFound handle exception. Exception: message = {}", exception.getMessage(), exception);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event not updated")
    @ExceptionHandler(EventNotUpdatedException.class)
    public void handleEventNotUpdated(EventNotUpdatedException exception) {
        LOGGER.error("handleEventNotUpdated handle exception. Exception: message = {}", exception.getMessage(), exception);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not deleted")
    @ExceptionHandler(ObjectNotDeletedException.class)
    public void handleObjectNotDeleted(ObjectNotDeletedException exception) {
        LOGGER.error("handleObjectNotDeleted handle exception. Exception: message = {}", exception.getMessage(), exception);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Forbidden action")
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDenied(AccessDeniedException exception) {
        LOGGER.error("handleAccessDenied handle exception. Exception: message = {}", exception.getMessage(), exception);
    }
}

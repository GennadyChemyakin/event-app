package com.epam.eventappweb.controller;

import com.epam.eventappweb.exceptions.EventNotFoundException;
import com.epam.eventappweb.exceptions.EventNotUpdatedException;
import com.epam.eventappweb.exceptions.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
        LOGGER.info("handleEventNotFound handle exception. Exception: message = {}", exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event not updated")
    @ExceptionHandler(EventNotUpdatedException.class)
    public void handleEventNotUpdated(EventNotUpdatedException exception) {
        LOGGER.info("handleEventNotUpdated handle exception. Exception: message = {}", exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Event not updated")
    @ExceptionHandler(UserNotLoggedInException.class)
    public void handleUserNotLoggedIn(UserNotLoggedInException exception) {
        LOGGER.info("handleUserNotLoggedIn handle exception. Exception: message = {}", exception.getMessage());
    }
}

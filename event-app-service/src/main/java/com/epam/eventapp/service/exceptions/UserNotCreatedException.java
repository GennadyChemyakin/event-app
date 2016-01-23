package com.epam.eventapp.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which throwed in case user is not created
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND,
        reason="User is not created")
public class UserNotCreatedException extends RuntimeException{

}

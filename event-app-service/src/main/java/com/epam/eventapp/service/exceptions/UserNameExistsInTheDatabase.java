package com.epam.eventapp.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception which throwed in case user is not created
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST,
        reason="UserName is already in the database")
public class UserNameExistsInTheDatabase extends RuntimeException{

}

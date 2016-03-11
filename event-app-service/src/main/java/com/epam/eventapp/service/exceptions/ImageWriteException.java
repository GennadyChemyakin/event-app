package com.epam.eventapp.service.exceptions;

/**
 * Exception in case an error while saving to disk
 */
public class ImageWriteException extends RuntimeException {


    public ImageWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}

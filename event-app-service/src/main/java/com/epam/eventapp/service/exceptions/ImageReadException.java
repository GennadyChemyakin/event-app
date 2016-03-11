package com.epam.eventapp.service.exceptions;

/**
 * Exception in case an error while saving to disk
 */
public class ImageReadException extends RuntimeException {

    public ImageReadException(String message, Throwable cause) {
        super(message, cause);
    }
}

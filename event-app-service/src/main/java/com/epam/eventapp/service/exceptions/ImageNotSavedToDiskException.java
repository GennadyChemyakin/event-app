package com.epam.eventapp.service.exceptions;

/**
 * Exception in case an error while saving to disk
 */
public class ImageNotSavedToDiskException extends RuntimeException {

    public ImageNotSavedToDiskException(String message) {
        super(message);
    }
}

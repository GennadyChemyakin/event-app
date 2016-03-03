package com.epam.eventapp.service.exceptions;

/**
 * Exception in case an error while saving to disk
 */
public class ImageNotReadFromDiskException extends RuntimeException {

    public ImageNotReadFromDiskException(String message) {
        super(message);
    }
}

package com.epam.eventapp.service.exceptions;

/**
 * class for representing exceptions when commentary not created
 */
public class CommentaryNotAddedException extends RuntimeException {

    public CommentaryNotAddedException() {
    }

    public CommentaryNotAddedException(String message) {
        super(message);
    }
}

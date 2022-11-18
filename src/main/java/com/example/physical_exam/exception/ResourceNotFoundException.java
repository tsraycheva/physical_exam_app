package com.example.physical_exam.exception;

/**
 * Class that represents an exception thrown in Runtime if the searched object is not found
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

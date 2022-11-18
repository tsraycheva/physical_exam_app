package com.example.physical_exam.exception;

/**
 * Class that represents an exception thrown in Runtime if there is bad request
 */
public class CanNotPerformOperationException extends RuntimeException {

    public CanNotPerformOperationException(String errorMessage) {
        super(errorMessage);
    }
}

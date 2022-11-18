package com.example.physical_exam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Class that manages unhandled exceptions at runtime
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), new Date(), HttpStatus.NOT_FOUND);

        return new ResponseEntity(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CanNotPerformOperationException.class)
    public ResponseEntity<?> handleBadRequestException(CanNotPerformOperationException ex, WebRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage(), new Date(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity(errorInfo, HttpStatus.BAD_REQUEST);
    }
}

package com.example.physical_exam.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Class that holds info about the message, timeStamp and the status code of the operation
 */
public class ErrorInfo {
    public String message;
    public Date timeStamp;
    public HttpStatus code;

    public ErrorInfo(String message, Date timeStamp, HttpStatus code) {
        super();
        this.message = message;
        this.timeStamp = timeStamp;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}


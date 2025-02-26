package com.example.docvisitorregister.exception;

public class TimeslotIsInvalidException extends RuntimeException {
    public TimeslotIsInvalidException(String message) {
        super(message);
    }
}

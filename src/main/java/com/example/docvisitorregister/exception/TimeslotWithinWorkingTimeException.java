package com.example.docvisitorregister.exception;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeslotWithinWorkingTimeException extends RuntimeException {
    public TimeslotWithinWorkingTimeException(String s, LocalTime startTime, LocalTime endTime) {
        super(s);
    }
}

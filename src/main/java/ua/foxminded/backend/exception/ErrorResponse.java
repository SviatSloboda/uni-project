package ua.foxminded.backend.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorResponse(
        String message,
        HttpStatus httpStatus,
        ZonedDateTime timeStamp
) {
}

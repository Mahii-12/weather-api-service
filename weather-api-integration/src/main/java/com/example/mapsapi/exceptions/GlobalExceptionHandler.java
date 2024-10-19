package com.example.mapsapi.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<GenericErrorResponse> handleUserNotFoundException(InvalidDateFormatException ex) {
        return createResponseEntity(ex.getErrorCodes(), ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private static ResponseEntity<GenericErrorResponse> createResponseEntity(ErrorCodes errorCodes, String message, HttpStatus status) {
        log.error("Timestamp: {}, Error handling: ErrorCode: {}, ErrorMessage: {}, HttpStatus: {}",
                LocalDateTime.now(), errorCodes.getCode(), message, status);
        GenericErrorResponse errorResponse = new GenericErrorResponse(errorCodes.getCode(), message);
        return new ResponseEntity<>(errorResponse, status);
    }

}

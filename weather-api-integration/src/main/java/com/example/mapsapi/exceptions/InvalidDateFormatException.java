package com.example.mapsapi.exceptions;

import lombok.Getter;

@Getter
public class InvalidDateFormatException extends RuntimeException {
    private final ErrorCodes errorCodes;

    public InvalidDateFormatException(ErrorCodes errorCodes, String message) {
        super(message);
        this.errorCodes = errorCodes;
    }
}

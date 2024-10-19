package com.example.mapsapi.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCodes {

    INVALID_DATE_FORMAT(1);

    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }
}

package com.pi.event.exceptions;

public enum ErrorCodes {
    EVENT_NOT_FOUND(1000),
    EVENT_NOT_VALID(1001),
    EVENT_ALREADY_IN_USE(1002),
    RESERVATION_NOT_FOUND(1000),
    RESERVATION_NOT_VALID(1001),
    RESERVATION_ALREADY_IN_USE(1002),
    ;

    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

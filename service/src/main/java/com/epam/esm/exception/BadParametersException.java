package com.epam.esm.exception;

public class BadParametersException extends ServiceAbstractException {
    public static final String ERROR_CODE = "25";

    public BadParametersException() {
        super(ERROR_CODE);
    }

    public BadParametersException(String message) {
        super(message, ERROR_CODE);
    }

    public BadParametersException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

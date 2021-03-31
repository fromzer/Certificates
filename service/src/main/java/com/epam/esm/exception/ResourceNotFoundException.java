package com.epam.esm.exception;

public class ResourceNotFoundException extends ServiceAbstractException {
    public static final String ERROR_CODE = "23";

    public ResourceNotFoundException() {
        super(ERROR_CODE);
    }

    public ResourceNotFoundException(String message) {
        super(message, ERROR_CODE);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

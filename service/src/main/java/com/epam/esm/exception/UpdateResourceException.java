package com.epam.esm.exception;

public class UpdateResourceException extends ServiceAbstractException {
    public static final String ERROR_CODE = "24";

    public UpdateResourceException() {
        super(ERROR_CODE);
    }

    public UpdateResourceException(String message) {
        super(message, ERROR_CODE);
    }

    public UpdateResourceException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

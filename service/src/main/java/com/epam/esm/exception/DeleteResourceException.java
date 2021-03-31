package com.epam.esm.exception;

public class DeleteResourceException extends ServiceAbstractException {
    public static final String ERROR_CODE = "22";

    public DeleteResourceException() {
        super(ERROR_CODE);
    }

    public DeleteResourceException(String message) {
        super(message, ERROR_CODE);
    }

    public DeleteResourceException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

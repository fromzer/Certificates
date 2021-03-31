package com.epam.esm.exception;

public class CreateResourceException extends ServiceAbstractException {
    public static final String ERROR_CODE = "21";

    public CreateResourceException() {
        super(ERROR_CODE);
    }

    public CreateResourceException(String message) {
        super(message, ERROR_CODE);
    }

    public CreateResourceException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

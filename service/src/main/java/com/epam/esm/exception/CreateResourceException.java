package com.epam.esm.exception;

public class CreateResourceException extends Exception {
    public CreateResourceException() {
        super();
    }

    public CreateResourceException(String message) {
        super(message);
    }

    public CreateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateResourceException(Throwable cause) {
        super(cause);
    }

    protected CreateResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

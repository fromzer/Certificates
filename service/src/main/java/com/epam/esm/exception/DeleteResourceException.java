package com.epam.esm.exception;

public class DeleteResourceException extends Exception {
    public DeleteResourceException() {
        super();
    }

    public DeleteResourceException(String message) {
        super(message);
    }

    public DeleteResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteResourceException(Throwable cause) {
        super(cause);
    }

    protected DeleteResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

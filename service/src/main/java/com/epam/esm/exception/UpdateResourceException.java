package com.epam.esm.exception;

public class UpdateResourceException extends Exception{
    public UpdateResourceException() {
        super();
    }

    public UpdateResourceException(String message) {
        super(message);
    }

    public UpdateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateResourceException(Throwable cause) {
        super(cause);
    }

    protected UpdateResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

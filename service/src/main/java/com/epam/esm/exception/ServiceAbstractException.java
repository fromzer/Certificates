package com.epam.esm.exception;

public abstract class ServiceAbstractException extends Exception {
    private final String errorCode;

    public ServiceAbstractException(String errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceAbstractException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceAbstractException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

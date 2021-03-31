package com.epam.esm.exception;

public abstract class AbstractDAOException extends Exception {
    private final String errorCode;

    public AbstractDAOException(String errorCode) {
        this.errorCode = errorCode;
    }

    public AbstractDAOException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AbstractDAOException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

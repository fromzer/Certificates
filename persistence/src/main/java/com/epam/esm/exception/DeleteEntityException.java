package com.epam.esm.exception;

public class DeleteEntityException extends AbstractDAOException {
    public static final String ERROR_CODE = "12";

    public DeleteEntityException() {
        super(ERROR_CODE);
    }

    public DeleteEntityException(String message) {
        super(message, ERROR_CODE);
    }

    public DeleteEntityException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

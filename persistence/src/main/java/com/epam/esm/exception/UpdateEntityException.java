package com.epam.esm.exception;

public class UpdateEntityException extends AbstractDAOException {
    public static final String ERROR_CODE = "14";

    public UpdateEntityException() {
        super(ERROR_CODE);
    }

    public UpdateEntityException(String message) {
        super(message, ERROR_CODE);
    }

    public UpdateEntityException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

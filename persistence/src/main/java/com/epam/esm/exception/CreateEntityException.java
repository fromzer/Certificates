package com.epam.esm.exception;

public class CreateEntityException extends AbstractDAOException{
    public static final String ERROR_CODE = "11";

    public CreateEntityException() {
        super(ERROR_CODE);
    }

    public CreateEntityException(String message) {
        super(message, ERROR_CODE);
    }

    public CreateEntityException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

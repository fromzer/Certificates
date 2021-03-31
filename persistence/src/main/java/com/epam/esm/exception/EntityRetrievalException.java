package com.epam.esm.exception;

public class EntityRetrievalException extends AbstractDAOException {
    public static final String ERROR_CODE = "13";

    public EntityRetrievalException() {
        super(ERROR_CODE);
    }

    public EntityRetrievalException(String message) {
        super(message, ERROR_CODE);
    }

    public EntityRetrievalException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE);
    }
}

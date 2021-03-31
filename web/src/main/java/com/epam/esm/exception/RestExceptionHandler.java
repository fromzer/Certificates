package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String BAD_PARAMETERS_MESSAGE = "Exception.badParameters";
    private static final String CREATE_RESOURCE_MESSAGE = "Exception.createResource";
    private static final String DELETE_RESOURCE_MESSAGE = "Exception.deleteResource";
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "Exception.resourceNotFound";
    private static final String UPDATE_RESOURCE_MESSAGE = "Exception.updateResource";
    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = UpdateResourceException.class)
    protected ResponseEntity<Object> handleUpdateResourceException(UpdateResourceException ex, Locale locale) {
        String message = messageSource.getMessage(UPDATE_RESOURCE_MESSAGE, null, locale);
        String code = HttpStatus.NOT_MODIFIED.value() + ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_MODIFIED, message, code);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, Locale locale) {
        //String message = messageSource.getMessage(RESOURCE_NOT_FOUND_MESSAGE, null, locale);
        String code = HttpStatus.NOT_FOUND.value() + ex.getErrorCode();
        String message = ex.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, message, code);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(value = DeleteResourceException.class)
    protected ResponseEntity<Object> handleDeleteResourceException(DeleteResourceException ex, Locale locale) {
        String message = messageSource.getMessage(DELETE_RESOURCE_MESSAGE, null, locale);
        String code = HttpStatus.NOT_MODIFIED.value() + ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_MODIFIED, message, code);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(value = CreateResourceException.class)
    protected ResponseEntity<Object> handleCreateResourceException(CreateResourceException ex, Locale locale) {
        String message = messageSource.getMessage(CREATE_RESOURCE_MESSAGE, null, locale);
        String code = HttpStatus.BAD_REQUEST.value() + ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, message, code);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    @ExceptionHandler(value = BadParametersException.class)
    protected ResponseEntity<Object> handleBadParametersException(BadParametersException ex, Locale locale) {
        String message = messageSource.getMessage(BAD_PARAMETERS_MESSAGE, null, locale);
        String code = HttpStatus.BAD_REQUEST.value() + ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, message, code);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }


}

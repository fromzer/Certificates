package com.epam.esm.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CREATE_RESOURCE_MESSAGE = "exception.createResource";
    private static final String DELETE_RESOURCE_MESSAGE = "exception.deleteResource";
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "exception.resourceNotFound";
    private static final String UPDATE_RESOURCE_MESSAGE = "exception.updateResource";
    private static final String TYPE_MISMATCH_MESSAGE = "exception.typeMismatch";
    private final MessageSource messageSource;


    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(value = UpdateResourceException.class)
    protected ResponseEntity<ErrorMessage> handleUpdateResourceException(Locale locale) {
        String msg = messageSource.getMessage(UPDATE_RESOURCE_MESSAGE, null, locale);
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_MODIFIED.value(), msg, "30414");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DeleteResourceException.class)
    protected ResponseEntity<ErrorMessage> handleDeleteResourceException(Locale locale) {
        String msg = messageSource.getMessage(DELETE_RESOURCE_MESSAGE, null, locale);
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_MODIFIED.value(), msg, "30417");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleResourceNotFoundException(Locale locale) {
        String msg = messageSource.getMessage(RESOURCE_NOT_FOUND_MESSAGE, null, locale);
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), msg, "40411");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CreateResourceException.class)
    protected ResponseEntity<ErrorMessage> handleCreateResourceException(Locale locale) {
        String msg = messageSource.getMessage(CREATE_RESOURCE_MESSAGE, null, locale);
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), msg, "40010");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Locale locale = Locale.getDefault();
        String msg = messageSource.getMessage(TYPE_MISMATCH_MESSAGE, null, locale);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("errorMessage", msg);
        body.put("errorCode", status.value() + "" + (status.value() / 10 + 11));
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("HTTP Status", status.value());
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errorMessage", errors);
        body.put("errorCode", status.value() + "" + (status.value() / 10));
        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Locale locale = Locale.getDefault();
        String msg = messageSource.getMessage(TYPE_MISMATCH_MESSAGE, null, locale);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("errorMessage", msg);
        body.put("errorCode", status.value() + "" + (status.value() / 10 + 14));
        return new ResponseEntity<>(body, headers, status);
    }
}

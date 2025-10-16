package com.roshogolla.bookstoreapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Resource Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String allErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(","));

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Validation Error",
                allErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralFound(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Internal Server Error",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}



/*
 * ex.getBindingResult():
 *   Retrieves the BindingResult from MethodArgumentNotValidException,
 *   which contains details about validation failures.

 * getFieldErrors():
 *   Extracts a list of field-specific validation errors (e.g., title, price).

 * .stream():
 *   Converts the list into a stream for easy processing.

 * .map(error -> error.getField() + ": " + error.getDefaultMessage()):
 *   Transforms each error into a readable string like:
 *     "title: must not be empty"
 *     "price: must be greater than 0"

 * .collect(Collectors.joining(", ")):
 *   Joins all error messages into a single comma-separated string:
 *     "title: must not be empty, price: must be greater than 0"
 */

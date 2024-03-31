package com.loyalty.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleYourCustomException(BadRequestException ex, WebRequest request) {
        // Construct the response body or any error details
        String errorDetails = ex.getMessage();
        // You can create a custom response object to return if needed

        // Return the response entity with the desired status code
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Define a generic handler for all other exceptions that are not defined explicitly
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        // Construct the response body or any error details
        String errorDetails = ex.getMessage();
        // You can create a custom response object to return if needed

        // Return the response entity with the desired status code
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

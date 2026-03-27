package com.phonebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handles @Valid failures (e.g., @Pattern for phone numbers)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Get the first error message defined in your DTO
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        errors.put("message", errorMessage);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 2. Handles Database Unique Constraint failures (e.g., Duplicate Email)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        String rootMsg = ex.getMostSpecificCause().getMessage();

        if (rootMsg.contains("email")) {
            errors.put("message", "This email address is already in use.");
        } else if (rootMsg.contains("phone_number")) {
            errors.put("message", "This phone number is already in use.");
        } else {
            errors.put("message", "A duplicate entry was detected in the database.");
        }
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
}
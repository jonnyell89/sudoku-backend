package com.sudoku.sudoku_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice // Global exception handler for all controllers.
public class GlobalExceptionHandler {

    @ExceptionHandler // Bad ID -> NoSuchElementException -> 500 Internal Server Error -> 404 Not Found
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        return new ResponseEntity<>("Error: " + noSuchElementException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler // Bad guess -> IllegalArgumentException -> 500 Internal Server Error -> 400 Bad Request
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return new ResponseEntity<>("Error: " + illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

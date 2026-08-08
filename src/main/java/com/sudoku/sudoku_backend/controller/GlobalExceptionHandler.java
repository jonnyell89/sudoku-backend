package com.sudoku.sudoku_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice // Global exception handler for all controllers.
public class GlobalExceptionHandler {

//    @ExceptionHandler
//    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException noSuchElementException) {
//        return new ResponseEntity<>("Error: " + noSuchElementException.getMessage(), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(NoSuchElementException.class) // Puzzle Not Found -> NoSuchElementException -> 500 Internal Server Error -> 404 Not Found
    public ProblemDetail handleNoSuchElementException(NoSuchElementException noSuchElementException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, noSuchElementException.getMessage());
        problemDetail.setTitle("Puzzle Not Found");
        return problemDetail;
    }

//    @ExceptionHandler
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
//        return new ResponseEntity<>("Error: " + illegalArgumentException.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(IllegalArgumentException.class) // Invalid Guess -> IllegalArgumentException -> 500 Internal Server Error -> 400 Bad Request
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
        problemDetail.setTitle("Invalid Guess");
        return problemDetail;
    }
}

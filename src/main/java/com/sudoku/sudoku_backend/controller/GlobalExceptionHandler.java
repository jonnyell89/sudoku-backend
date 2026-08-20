package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.exception.InvalidGuessException;
import com.sudoku.sudoku_backend.exception.PuzzleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice // Global exception handler for all controllers.
public class GlobalExceptionHandler {

    @ExceptionHandler(PuzzleNotFoundException.class) // Puzzle Not Found -> PuzzleNotFoundException -> 500 Internal Server Error -> 404 Not Found
    public ProblemDetail handlePuzzleNotFoundException(PuzzleNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Puzzle Not Found");
        return problemDetail;
    }

    @ExceptionHandler(InvalidGuessException.class) // Invalid Guess -> InvalidGuessException -> 500 Internal Server Error -> 400 Bad Request
    public ProblemDetail handleInvalidGuessException(InvalidGuessException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Invalid Guess");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle(String.format("Invalid value for parameter '%s'", exception.getName()));
        return problemDetail;
    }
}

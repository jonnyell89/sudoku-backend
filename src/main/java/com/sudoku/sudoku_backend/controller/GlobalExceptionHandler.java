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
    public ProblemDetail handlePuzzleNotFoundException(PuzzleNotFoundException puzzleNotFoundException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, puzzleNotFoundException.getMessage());
        problemDetail.setTitle("Puzzle Not Found");
        return problemDetail;
    }

    @ExceptionHandler(InvalidGuessException.class) // Invalid Guess -> InvalidGuessException -> 500 Internal Server Error -> 400 Bad Request
    public ProblemDetail handleInvalidGuessException(InvalidGuessException invalidGuessException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, invalidGuessException.getMessage());
        problemDetail.setTitle("Invalid Guess");
        return problemDetail;
    }

//    @ExceptionHandler(IllegalArgumentException.class) // Invalid Guess -> IllegalArgumentException -> 500 Internal Server Error -> 400 Bad Request
//    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
//        problemDetail.setTitle("Invalid Request");
//        return problemDetail;
//    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, methodArgumentTypeMismatchException.getMessage());
        problemDetail.setTitle(String.format("Invalid value for parameter '%s'", methodArgumentTypeMismatchException.getName()));
        return problemDetail;
    }
}

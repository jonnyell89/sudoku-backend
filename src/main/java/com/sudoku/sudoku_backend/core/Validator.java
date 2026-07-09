package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

public class Validator {

    public static void validateIndex(String unit, int index) {
        if (index < SudokuConstants.MIN_INDEX || index > SudokuConstants.MAX_INDEX) {
            throw new IllegalArgumentException(String.format("%s must be between %d and %d", unit, SudokuConstants.MIN_INDEX, SudokuConstants.MAX_INDEX));
        }
    }

    public static void validateValue(int value) {
        if (value < SudokuConstants.EMPTY_CELL || value > SudokuConstants.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("value must be between %d and %d.", SudokuConstants.EMPTY_CELL, SudokuConstants.MAX_VALUE));
        }
    }
}

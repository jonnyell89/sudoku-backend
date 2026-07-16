package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.model.Cell;

import java.util.Objects;

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

    public static void validateCells(int[][] cells) {
        Objects.requireNonNull(cells, "cells must not be null.");
        if (cells.length != SudokuConstants.GRID_SIZE) {
            throw new IllegalArgumentException(String.format("cells must contain %d rows.", SudokuConstants.GRID_SIZE));
        }
        for (int row = 0; row < cells.length; row++) {
            Objects.requireNonNull(cells[row], String.format("row %d must not be null.", row));
            if (cells[row].length != SudokuConstants.GRID_SIZE) {
                throw new IllegalArgumentException(String.format("row %d must contain %d cells.", row, SudokuConstants.GRID_SIZE));
            }
            for (int col = 0; col < cells[row].length; col++) {
                Validator.validateValue(cells[row][col]);
            }
        }
    }

    public static void validateCells(Cell[][] cells) {
        Objects.requireNonNull(cells, "cells must not be null.");
        if (cells.length != SudokuConstants.GRID_SIZE) {
            throw new IllegalArgumentException(String.format("cells must contain %d rows.", SudokuConstants.GRID_SIZE));
        }
        for (int row = 0; row < cells.length; row++) {
            Objects.requireNonNull(cells[row], String.format("row %d must not be null.", row));
            if (cells[row].length != SudokuConstants.GRID_SIZE) {
                throw new IllegalArgumentException(String.format("row %d must contain %d cells.", row, SudokuConstants.GRID_SIZE));
            }
            for (int col = 0; col < cells[row].length; col++) {
                Objects.requireNonNull(cells[row][col], String.format("cells[%d][%d] must not be null.", row, col));
                Validator.validateValue(cells[row][col].getValue());
            }
        }
    }
}

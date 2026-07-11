package com.sudoku.sudoku_backend.model;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Validator;

public class Cell {

    private final int row;
    private final int col;
    private int value;
    private final boolean given;

    private Cell(int row, int col, int value, boolean given) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        Validator.validateValue(value);
        this.row = row;
        this.col = col;
        this.value = value;
        this.given = given;
    }

    public static Cell empty(int row, int col) {
        return new Cell(row, col, SudokuConstants.EMPTY_CELL, false);
    }

    public static Cell given(int row, int col, int value) {
        return new Cell(row, col, value, true);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (given) {
            throw new IllegalStateException("given cells cannot be modified.");
        }
        Validator.validateValue(value);
        this.value = value;
    }

    public void clearValue() {
        if (given) {
            throw new IllegalStateException("given cells cannot be modified.");
        }
        this.value = SudokuConstants.EMPTY_CELL;
    }

    public boolean isGiven() {
        return given;
    }

    public boolean isEditable() {
        return !given;
    }

    public boolean isEmpty() {
        return value == SudokuConstants.EMPTY_CELL;
    }
}

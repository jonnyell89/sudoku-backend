package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

public class Cell {

    private final int row;
    private final int col;
    private final boolean given;
    private int guess;

    public Cell(int row, int col, boolean given) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        this.row = row;
        this.col = col;
        this.given = given;
        this.guess = SudokuConstants.EMPTY_CELL;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isGiven() {
        return given;
    }

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        Validator.validateValue(guess);
        if (given) {
            throw new IllegalStateException("given cells cannot be modified.");
        }
        this.guess = guess;
    }

    public boolean isEmpty() {
        return guess == SudokuConstants.EMPTY_CELL;
    }
}

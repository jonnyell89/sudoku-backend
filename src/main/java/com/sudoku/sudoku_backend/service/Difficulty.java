package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.SudokuConstants;

public enum Difficulty {

    EASY(46),
    MEDIUM(51),
    HARD(56);

    private final int target;

    Difficulty(int target) {
        if (target < SudokuConstants.MIN_TARGET | target > SudokuConstants.MAX_TARGET) {
            throw new IllegalArgumentException(String.format("target must be between %d and %d.", SudokuConstants.MIN_TARGET, SudokuConstants.MAX_TARGET));
        }
        this.target = target;
    }

    public int getTarget() {
        return target;
    }
}

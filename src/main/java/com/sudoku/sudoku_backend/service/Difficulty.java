package com.sudoku.sudoku_backend.service;

public enum Difficulty {

    EASY(46),
    MEDIUM(51),
    HARD(56);

    private final int target;

    Difficulty(int target) {
        if (target < 45 | target > 57) {
            throw new IllegalArgumentException("Target must be between 45 and 57.");
        }
        this.target = target;
    }

    public int getTarget() {
        return target;
    }
}

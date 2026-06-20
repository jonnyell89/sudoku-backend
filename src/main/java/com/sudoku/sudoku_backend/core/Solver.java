package com.sudoku.sudoku_backend.core;

public class Solver {

//    public boolean solve(Grid grid) {}
//
//    public int countSolutions(Grid grid) {}
//
//    private boolean backtrack(Grid grid) {}
//
//    private int[] findEmptyCell(Grid grid) {}

    private void validateGrid(Grid grid) {

        if (grid == null) {
            throw new IllegalArgumentException("grid should not be null.");
        }
        if (!grid.isValid()) {
            throw new IllegalArgumentException("grid must not contain duplicate values in any row, col, or box.");
        }
    }
}

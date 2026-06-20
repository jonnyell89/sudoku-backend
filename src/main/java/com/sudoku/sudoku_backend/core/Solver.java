package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

public class Solver {

    private record Coordinate(int rowIndex, int colIndex) {
//        private Coordinate {
//            if (rowIndex < SudokuConstants.MIN_INDEX || rowIndex > SudokuConstants.MAX_INDEX) {
//                throw new IllegalArgumentException(String.format("rowIndex must be between %d and %d.", SudokuConstants.MIN_INDEX, SudokuConstants.MAX_INDEX));
//            }
//            if (colIndex < SudokuConstants.MIN_INDEX || colIndex > SudokuConstants.MAX_INDEX) {
//                throw new IllegalArgumentException(String.format("colIndex must be between %d and %d.", SudokuConstants.MIN_INDEX, SudokuConstants.MAX_INDEX));
//            }
//        }
    }

//    public boolean solve(Grid grid) {}
//
//    public int countSolutions(Grid grid) {}
//
//    private boolean backtrack(Grid grid) {}

    private Coordinate findEmptyCell1(Grid grid) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                if (grid.getValue(i, j) == SudokuConstants.EMPTY_CELL) {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    private void validateGrid(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("grid should not be null.");
        }
        if (!grid.isValid()) {
            throw new IllegalArgumentException("grid must not contain duplicate values in any row, col, or box.");
        }
    }
}

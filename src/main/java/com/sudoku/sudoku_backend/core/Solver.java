package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

import java.util.Random;

public class Solver {

    private final Random random = new Random();

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

    public boolean solve(Grid grid) {
        validateGrid(grid);
        return backtrack(grid);
    }

//    public int countSolutions(Grid grid) {}

    private boolean backtrack(Grid grid) {
        // Base case:
        Coordinate coordinate = findEmptyCell(grid);
        if (coordinate == null) return true;
        // Recursive step:
        int rowIndex = coordinate.rowIndex();
        int colIndex = coordinate.colIndex();
        int[] candidates = fisherYatesShuffle(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        for (int candidate : candidates) {
            if (grid.isLegal(rowIndex, colIndex, candidate)) {
                grid.setValue(rowIndex, colIndex, candidate);
                if (backtrack(grid)) return true;
                grid.clearValue(rowIndex, colIndex);
            }
        }
        return false;
    }

    private Coordinate findEmptyCell(Grid grid) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                if (grid.getValue(i, j) == SudokuConstants.EMPTY_CELL) {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    private int[] fisherYatesShuffle(int[] array) {
        for (int i = array.length-1; i > 0; i--) {
            int j = random.nextInt(i+1);

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
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

package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

import java.util.Random;

public class Solver {

    private final Random random;

    private static class Counter {
        private int count = 0;
        private void increment() { count++; }
    }

    public Solver(Random random) {
        this.random = random;
    }

    public boolean solve(Grid grid) {
        validateGrid(grid);
        return backtrack(grid);
    }

    public int countSolutions(Grid grid) {
        validateGrid(grid);
        Grid gridCopy = grid.copy();
        Counter counter = new Counter();
        enumerate(gridCopy, counter);
        return counter.count;
    }

    private boolean backtrack(Grid grid) {
        // Base case:
        Coordinate coordinate = findEmptyCell(grid);
        if (coordinate == null) return true;
        // Recursive step:
        int rowIndex = coordinate.rowIndex();
        int colIndex = coordinate.colIndex();
        int[] shuffledCandidates = shuffledCandidates();
        for (int candidate : shuffledCandidates) {
            if (grid.isLegal(rowIndex, colIndex, candidate)) {
                grid.setValue(rowIndex, colIndex, candidate);
                if (backtrack(grid)) return true;
                grid.clearValue(rowIndex, colIndex);
            }
        }
        return false;
    }

    private void enumerate(Grid grid, Counter counter) {
        // Terminate:
        if (counter.count >= 2) return;
        // Base case:
        Coordinate coordinate = findEmptyCell(grid);
        if (coordinate == null) {
            counter.increment();
            return;
        }
        // Recursive step:
        int rowIndex = coordinate.rowIndex();
        int colIndex = coordinate.colIndex();
        for (int candidate : candidates()) {
            if (grid.isLegal(rowIndex, colIndex, candidate)) {
                grid.setValue(rowIndex, colIndex, candidate);
                enumerate(grid, counter);
                grid.clearValue(rowIndex, colIndex);
            }
        }
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

    private int[] candidates() {
        int[] candidates = new int[SudokuConstants.GRID_SIZE];
        for (int i = 0; i < candidates.length; i++) {
            candidates[i] = i + 1;
        }
        return candidates;
    }

    private int[] shuffledCandidates() {
        int[] candidates = candidates();
        Shuffle.fisherYatesShuffle(candidates, random);
        return candidates;
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

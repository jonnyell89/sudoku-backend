package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

import java.util.Objects;
import java.util.Random;

public class Solver {

    private final Random random;

    private static class Counter {
        private int count = 0;
        private void increment() { count++; }
    }

    public Solver(Random random) { this.random = random; }

    public boolean solve(Grid grid) {
        Objects.requireNonNull(grid, "grid must not be null.");
        grid.validate();
        return backtrack(grid);
    }

    public int countSolutions(Grid grid) {
        Objects.requireNonNull(grid, "grid must not be null.");
        grid.validate();
        Grid copy = grid.copy();
        Counter counter = new Counter();
        enumerate(copy, counter);
        return counter.count;
    }

    private boolean backtrack(Grid grid) {
        // Base case:
        Coordinate coordinate = findEmptyCell(grid);
        if (coordinate == null) return true;
        // Recursive step:
        int row = coordinate.row();
        int col = coordinate.col();
        for (int candidate : shuffledCandidates()) {
            if (grid.isLegal(row, col, candidate)) {
                grid.setValue(row, col, candidate);
                if (backtrack(grid)) return true;
                grid.clearValue(row, col);
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
        int row = coordinate.row();
        int col = coordinate.col();
        for (int candidate : candidates()) {
            if (grid.isLegal(row, col, candidate)) {
                grid.setValue(row, col, candidate);
                enumerate(grid, counter);
                grid.clearValue(row, col);
            }
        }
    }

    private Coordinate findEmptyCell(Grid grid) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (grid.getValue(row, col) == SudokuConstants.EMPTY_CELL) {
                    return new Coordinate(row, col);
                }
            }
        }
        return null;
    }

    private int[] candidates() {
        int[] candidates = new int[SudokuConstants.GRID_SIZE];
        for (int index = 0; index < candidates.length; index++) {
            candidates[index] = index + 1;
        }
        return candidates;
    }

    private int[] shuffledCandidates() {
        int[] candidates = candidates();
        Shuffle.fisherYates(candidates, random);
        return candidates;
    }
}

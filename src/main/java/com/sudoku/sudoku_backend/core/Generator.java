package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

import java.util.Objects;
import java.util.Random;

public class Generator {

    private final Random random;
    private final Solver solver;

    public Generator(Random random) {
        this.random = random;
        this.solver = new Solver(random);
    }

    public Grid generateGrid() {
        Grid grid = new Grid();
        solver.solve(grid);
        return grid;
    }

    public Puzzle createPuzzle(Grid grid, int target) {
        Objects.requireNonNull(grid, "grid must not be null.");
        grid.validate();
        if (target < SudokuConstants.MIN_TARGET || target > SudokuConstants.MAX_TARGET) {
            throw new IllegalArgumentException(String.format("target must be between %d and %d.", SudokuConstants.MIN_TARGET, SudokuConstants.MAX_TARGET));
        }

        Grid copy = grid.copy();
        int removals = 0;
        for (Coordinate coordinate : shuffledCoordinates()) {
            int row = coordinate.row();
            int col = coordinate.col();
            int value = copy.getValue(row, col);
            copy.clearValue(row, col);
            if (solver.countSolutions(copy) == 1) {
                removals++;
                if (removals >= target) break;
            } else {
                copy.setValue(row, col, value);
            }
        }
        return new Puzzle(grid, copy);
    }

    private Coordinate[] coordinates() {
        Coordinate[] coordinates = new Coordinate[SudokuConstants.GRID_CELLS];
        int index = 0;
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                coordinates[index++] = new Coordinate(row, col);
            }
        }
        return coordinates;
    }

    private Coordinate[] shuffledCoordinates() {
        Coordinate[] coordinates = coordinates();
        Shuffle.fisherYates(coordinates, random);
        return coordinates;
    }
}

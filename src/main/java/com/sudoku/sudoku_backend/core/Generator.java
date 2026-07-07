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
        Objects.requireNonNull(grid, "grid should not be null.");
        grid.validate();
        if (target < 45 || target > 57) {
            throw new IllegalArgumentException("Target must be between 45 and 57.");
        }

        Grid workingGrid = grid.copy();
        Coordinate[] coordinates = shuffledCoordinates();

        int removals = 0;
        for (Coordinate coordinate : coordinates) {
            int rowIndex = coordinate.rowIndex();
            int colIndex = coordinate.colIndex();
            int value = workingGrid.getValue(rowIndex, colIndex);

            workingGrid.clearValue(rowIndex, colIndex);

            if (solver.countSolutions(workingGrid) == 1) {
                removals++;
                if (removals >= target) break;
            } else {
                workingGrid.setValue(rowIndex, colIndex, value);
            }
        }
        return new Puzzle(grid, workingGrid);
    }

    private Coordinate[] coordinates() {
        Coordinate[] coordinates = new Coordinate[SudokuConstants.GRID_CELLS];
        int index = 0;
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                coordinates[index++] = new Coordinate(i, j);
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

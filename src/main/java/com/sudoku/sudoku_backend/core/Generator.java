package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;

import java.util.Random;

public class Generator {

    private final Random random;

    public Generator(Random random) { this.random = random; }

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

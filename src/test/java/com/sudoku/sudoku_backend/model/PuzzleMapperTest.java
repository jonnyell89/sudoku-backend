package com.sudoku.sudoku_backend.model;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;

public class PuzzleMapperTest {

    static IntStream seeds() {
        return IntStream.range(0, SEED_COUNT);
    }

    @ParameterizedTest
    @MethodSource("seeds")
    void shouldMapCarvedPuzzleToTwoDimensionalArrayOfCells(int seed) {
        Random seededRandom = new Random(seed);
        Generator generator = new Generator(seededRandom);
        Grid grid = generator.generateGrid();
        Puzzle puzzle = generator.createPuzzle(grid, TARGET);
        Grid carved = puzzle.carved();
        Cell[][] cells = PuzzleMapper.map(puzzle);
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                Cell cell = cells[row][col];
                assertEquals(row, cell.getRow());
                assertEquals(col, cell.getCol());
                assertEquals(carved.getValue(row, col), cell.getValue());
                boolean isGiven = carved.getValue(row, col) != 0;
                assertEquals(isGiven, cell.isGiven());
                assertEquals(!isGiven, cell.isEditable());
            }
        }
    }
}

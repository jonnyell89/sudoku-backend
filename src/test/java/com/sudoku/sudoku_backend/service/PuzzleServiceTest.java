package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import com.sudoku.sudoku_backend.model.Cell;
import com.sudoku.sudoku_backend.model.CellGrid;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;

public class PuzzleServiceTest {

    static IntStream seeds() {
        return IntStream.range(0, SEED_COUNT);
    }

    @Nested
    class ConstructorTests {

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.service.PuzzleServiceTest#seeds")
        void shouldNotNull(int seed) {

        }
    }

    @Nested
    class NewPuzzleTests {

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.service.PuzzleServiceTest#seeds")
        void shouldReturnMappedPuzzleAsCellGrid(int seed) {
            Random expectedRandom = new Random(seed);
            Random actualRandom = new Random(seed);

            Generator expectedGenerator = new Generator(expectedRandom);
            Generator actualGenerator = new Generator(actualRandom);

            int target = Difficulty.MEDIUM.getTarget();

            Grid grid = expectedGenerator.generateGrid();
            Puzzle puzzle = expectedGenerator.createPuzzle(grid, target);
            Grid expectedCarved = puzzle.carved();

            PuzzleService puzzleService = new PuzzleService(actualGenerator);
            CellGrid cellGrid = puzzleService.newPuzzle(Difficulty.MEDIUM);

            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    Cell cell = cellGrid.getCell(row, col);
                    assertEquals(row, cell.getRow());
                    assertEquals(col, cell.getCol());
                    int expectedValue = expectedCarved.getValue(row, col);
                    assertEquals(expectedValue, cell.getValue());
                    boolean isGiven = expectedValue != 0;
                    assertEquals(isGiven, cell.isGiven());
                }
            }
        }
    }
}

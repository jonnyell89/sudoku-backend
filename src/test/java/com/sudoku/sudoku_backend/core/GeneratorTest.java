package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {

    private static final int SEED_COUNT = 24;
    private static final int TARGET = 50;
    private static final int TARGET_INVALID_LOWER_BOUND = 44;
    private static final int TARGET_INVALID_UPPER_BOUND = 61;

    static IntStream seeds() {
        return IntStream.range(0, SEED_COUNT);
    }

    @Nested
    class GenerateGridTests {

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#seeds")
        void shouldReturnFullGrid(int seed) {
            Random seededRandom = new Random(seed);
            Generator generator = new Generator(seededRandom);

            Grid grid = generator.generateGrid();
            assertTrue(grid.isFull(), String.format("Test failed for seed %d.", seed));
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#seeds")
        void shouldReturnValidGrid(int seed) {
            Random seededRandom = new Random(seed);
            Generator generator = new Generator(seededRandom);

            Grid grid = generator.generateGrid();
            assertTrue(grid.isValid(), String.format("Test failed for seed %d.", seed));
        }
    }

    @Nested
    class CreatePuzzleTests {

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#seeds")
        void shouldCreatePuzzleWithOneUniqueSolution(int seed) {
            Random seededRandom = new Random(seed);
            Generator generator = new Generator(seededRandom);
            Solver solver = new Solver(seededRandom);

            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, TARGET);
            assertEquals(1, solver.countSolutions(puzzle.carved()), String.format("Test failed for seed %d.", seed));
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#seeds")
        void shouldSolveToOriginalCompleteGrid(int seed) {
            Random seededRandom = new Random(seed);
            Generator generator = new Generator(seededRandom);
            Solver solver = new Solver(seededRandom);

            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, TARGET);
            solver.solve(puzzle.carved());
            assertEquals(puzzle.complete(), puzzle.carved(), String.format("Test failed for seed %d.", seed));
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#seeds")
        void shouldNotMutateOriginalGrid(int seed) {
            Random seededRandom = new Random(seed);
            Generator generator = new Generator(seededRandom);

            Grid grid = generator.generateGrid();
            Grid gridCopy = grid.copy();
            generator.createPuzzle(grid, TARGET);
            assertEquals(gridCopy, grid, String.format("Test failed for seed %d.", seed));
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#seeds")
        void shouldPreservePreFilledCellsFromCompleteGrid(int seed) {
            Random seededRandom = new Random(seed);
            Generator generator = new Generator(seededRandom);

            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, TARGET);
            Grid carved = puzzle.carved();
            Grid complete = puzzle.complete();

            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                    int cell = carved.getValue(i, j);
                    if (cell != SudokuConstants.EMPTY_CELL) {
                        assertEquals(complete.getValue(i, j), cell);
                    }
                }
            }
        }

        @Test
        void shouldThrowWhenTargetIsBelowLowerBound() {
            Random random = new Random();
            Generator generator = new Generator(random);

            Grid grid = generator.generateGrid();
            assertThrows(IllegalArgumentException.class, () -> generator.createPuzzle(grid, TARGET_INVALID_LOWER_BOUND));
        }

        @Test
        void shouldThrowWhenTargetIsAboveUpperBound() {
            Random random = new Random();
            Generator generator = new Generator(random);

            Grid grid = generator.generateGrid();
            assertThrows(IllegalArgumentException.class, () -> generator.createPuzzle(grid, TARGET_INVALID_UPPER_BOUND));
        }
    }
}

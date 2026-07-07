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
    private static final int MIN_TARGET = 45;
    private static final int MAX_TARGET = 57;
    private static final int BELOW_MIN_TARGET = MIN_TARGET - 1;
    private static final int ABOVE_MAX_TARGET = MAX_TARGET + 1;

    static IntStream seeds() {
        return IntStream.range(0, SEED_COUNT);
    }

    static IntStream targets() {
        return IntStream.rangeClosed(MIN_TARGET, MAX_TARGET);
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

        @Test
        void shouldCreateTheSameGridWhenUsingTheSameSeed() {
            Random expectedRandom = new Random(24L);
            Random actualRandom = new Random(24L);
            Generator expectedGenerator = new Generator(expectedRandom);
            Generator actualGenerator = new Generator(actualRandom);
            Grid expectedGrid = expectedGenerator.generateGrid();
            Grid actualGrid = actualGenerator.generateGrid();
            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                    int expectedCell = expectedGrid.getValue(i, j);
                    int actualCell = actualGrid.getValue(i, j);
                    assertEquals(expectedCell, actualCell);
                }
            }
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

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#seeds")
        void shouldCreatePuzzleWithEmptyCells(int seed) {
            Random seededRandom = new Random(seed);
            Generator generator = new Generator(seededRandom);

            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, TARGET);
            assertFalse(puzzle.carved().isFull());
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.GeneratorTest#targets")
        void shouldCreatePuzzleWithEmptyCellsUpToTarget(int target) {
            Random random = new Random();
            Generator generator = new Generator(random);

            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, target);
            Grid carved = puzzle.carved();
            int emptyCells = 0;
            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                    int cell = carved.getValue(i, j);
                    if (cell == SudokuConstants.EMPTY_CELL) {
                        emptyCells++;
                    }
                }
            }
            assertTrue(emptyCells <= target);
        }

        @Test
        void shouldCreateTheSamePuzzleWhenUsingTheSameSeed() {
            Random expectedRandom = new Random(24L);
            Random actualRandom = new Random(24L);
            Generator expectedGenerator = new Generator(expectedRandom);
            Generator actualGenerator = new Generator(actualRandom);

            Grid expectedGrid = expectedGenerator.generateGrid();
            Grid actualGrid = actualGenerator.generateGrid();

            Puzzle puzzleOne = expectedGenerator.createPuzzle(expectedGrid, TARGET);
            Puzzle puzzleTwo = actualGenerator.createPuzzle(actualGrid, TARGET);

            Grid carvedGridOne = puzzleOne.carved();
            Grid carvedGridTwo = puzzleTwo.carved();

            for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
                for (int j = 0; j < SudokuConstants.GRID_SIZE; j++) {
                    int expectedCell = carvedGridOne.getValue(i, j);
                    int actualCell = carvedGridTwo.getValue(i, j);
                    assertEquals(expectedCell, actualCell);
                }
            }
        }

        @Test
        void shouldThrowWhenTargetIsBelowLowerBound() {
            Random random = new Random();
            Generator generator = new Generator(random);

            Grid grid = generator.generateGrid();
            assertThrows(IllegalArgumentException.class, () -> generator.createPuzzle(grid, BELOW_MIN_TARGET));
        }

        @Test
        void shouldThrowWhenTargetIsAboveUpperBound() {
            Random random = new Random();
            Generator generator = new Generator(random);

            Grid grid = generator.generateGrid();
            assertThrows(IllegalArgumentException.class, () -> generator.createPuzzle(grid, ABOVE_MAX_TARGET));
        }
    }
}

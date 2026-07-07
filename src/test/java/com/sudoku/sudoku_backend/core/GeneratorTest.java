package com.sudoku.sudoku_backend.core;

import com.sudoku.sudoku_backend.SudokuConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GeneratorTest {

    private final Random random = new Random();

    private Generator generator;
    private Solver solver;

    @BeforeEach
    void init() {
        generator = new Generator(random);
        solver = new Solver(random);
    }

    @Nested
    class GenerateGridTests {

        @Test
        void shouldReturnFullGrid() {
            Grid grid = generator.generateGrid();
            assertTrue(grid.isFull());
        }

        @Test
        void shouldReturnValidGrid() {
            Grid grid = generator.generateGrid();
            assertTrue(grid.isValid());
        }
    }

    @Nested
    class CreatePuzzleTests {

        @Test
        void shouldCreatePuzzleWithOneUniqueSolution() {
            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, 50);
            assertEquals(1, solver.countSolutions(puzzle.puzzle()));
        }

        @Test
        void shouldSolveToOriginalCompleteGrid() {
            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, 50);
            solver.solve(puzzle.puzzle());
            assertEquals(puzzle.grid(), puzzle.puzzle());
        }

        @Test
        void shouldNotMutateOriginalGrid() {
            Grid grid = generator.generateGrid();
            Grid gridCopy = grid.copy();
            generator.createPuzzle(grid, 50);
            assertEquals(gridCopy, grid);
        }

        @Test
        void shouldPreservePreFilledCellsFromCompleteGrid() {
            Grid grid = generator.generateGrid();
            Puzzle puzzle = generator.createPuzzle(grid, 50);
            Grid carved = puzzle.puzzle();
            Grid complete = puzzle.grid();
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
            Grid grid = generator.generateGrid();
            assertThrows(IllegalArgumentException.class, () -> generator.createPuzzle(grid, 44));
        }

        @Test
        void shouldThrowWhenTargetIsAboveUpperBound() {
            Grid grid = generator.generateGrid();
            assertThrows(IllegalArgumentException.class, () -> generator.createPuzzle(grid, 61));
        }
    }
}

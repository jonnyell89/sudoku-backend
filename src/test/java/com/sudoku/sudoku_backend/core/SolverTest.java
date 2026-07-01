package com.sudoku.sudoku_backend.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    private Grid grid;
    private Solver solver;

    private static final int MAX_SOLUTIONS = 2;

    private static final int[][] easy = new int[][]{
            {4, 1, 0, 0, 6, 0, 0, 7, 0},
            {0, 0, 3, 0, 8, 5, 0, 0, 9},
            {0, 2, 0, 3, 7, 0, 5, 0, 1},
            {0, 3, 0, 6, 0, 9, 2, 5, 0},
            {6, 0, 0, 5, 0, 1, 0, 0, 0},
            {0, 0, 9, 0, 2, 0, 0, 0, 3},
            {0, 0, 6, 2, 0, 0, 7, 4, 5},
            {0, 0, 0, 4, 0, 6, 8, 0, 0},
            {2, 8, 4, 0, 0, 0, 1, 9, 6},
    };

    private static final int[][] medium = new int[][]{
            {3, 0, 1, 0, 8, 0, 5, 0, 0},
            {0, 0, 0, 0, 0, 7, 0, 2, 0},
            {9, 2, 0, 0, 0, 0, 8, 6, 0},
            {0, 0, 0, 1, 2, 0, 0, 5, 0},
            {0, 1, 0, 0, 0, 0, 3, 0, 0},
            {6, 0, 2, 7, 0, 5, 0, 0, 0},
            {4, 6, 3, 2, 9, 0, 0, 8, 5},
            {2, 5, 0, 8, 7, 3, 0, 4, 1},
            {0, 0, 0, 0, 0, 6, 0, 3, 9},
    };

    private static final int[][] hard = new int[][]{
            {2, 0, 1, 9, 7, 5, 0, 6, 4},
            {8, 7, 6, 4, 0, 1, 0, 5, 3},
            {0, 9, 0, 0, 0, 0, 0, 7, 2},
            {0, 8, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 1, 0, 2, 6, 9, 0},
            {6, 0, 0, 0, 3, 7, 4, 0, 0},
            {1, 0, 3, 0, 0, 0, 0, 4, 0},
            {0, 4, 8, 0, 1, 9, 0, 0, 0},
            {0, 0, 0, 3, 0, 0, 7, 0, 0},
    };

    private static final int[][] expert = new int[][]{
            {4, 3, 0, 0, 0, 7, 0, 0, 0},
            {7, 0, 2, 0, 0, 0, 5, 0, 3},
            {0, 0, 0, 1, 0, 3, 0, 7, 2},
            {9, 0, 0, 4, 0, 0, 1, 0, 7},
            {0, 7, 3, 0, 0, 6, 9, 8, 0},
            {0, 0, 0, 0, 0, 9, 0, 0, 5},
            {0, 0, 4, 9, 6, 0, 0, 0, 0},
            {1, 2, 7, 0, 0, 0, 6, 4, 9},
            {0, 0, 9, 0, 0, 0, 0, 0, 0},
    };

    private static final int[][] master = new int[][]{
            {0, 0, 6, 0, 0, 5, 0, 0, 0},
            {8, 0, 0, 0, 0, 0, 0, 4, 9},
            {0, 7, 3, 0, 9, 0, 2, 0, 0},
            {5, 6, 0, 0, 0, 3, 0, 0, 0},
            {0, 0, 2, 6, 0, 0, 0, 0, 0},
            {1, 9, 7, 0, 0, 0, 6, 0, 3},
            {0, 0, 0, 0, 6, 0, 0, 9, 0},
            {6, 0, 5, 0, 0, 9, 1, 0, 0},
            {0, 0, 0, 5, 3, 0, 0, 0, 4},
    };

    private static final int[][] extreme = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 6, 0},
            {0, 2, 0, 3, 0, 0, 1, 0, 0},
            {5, 0, 0, 8, 0, 9, 0, 0, 0},
            {8, 0, 0, 0, 5, 0, 0, 9, 6},
            {0, 4, 0, 6, 0, 0, 0, 0, 0},
            {0, 0, 0, 4, 0, 0, 0, 0, 3},
            {9, 0, 0, 0, 7, 0, 5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 4, 0},
            {0, 1, 0, 9, 0, 0, 8, 0, 0},
    };

    private static final int[][] duplicates = new int[][]{
            {1, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    private static final int[][] unsolvable = new int[][] {
            {1, 2, 3, 4, 5, 6, 7, 8, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 9, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 9},
    };

    @Nested
    class SolveTests {

        @BeforeEach
        void init() {
            solver = new Solver();
        }

        @Test
        void shouldSolveEmptyGrid() {
            grid = new Grid();
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveEasyGrid() {
            grid = new Grid(easy);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveMediumGrid() {
            grid = new Grid(medium);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveHardGrid() {
            grid = new Grid(hard);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveExpertGrid() {
            grid = new Grid(expert);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveMasterGrid() {
            grid = new Grid(master);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveExtremeGrid() {
            grid = new Grid(extreme);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            assertThrows(IllegalArgumentException.class, () -> solver.solve(null));
        }

        @Test
        void shouldThrowWhenGridContainsDuplicates() {
            grid = new Grid(duplicates);
            assertThrows(IllegalArgumentException.class, () -> solver.solve(grid));
        }

        @Test
        void shouldReturnFalseWhenGridIsUnsolvable() {
            grid = new Grid(unsolvable);
            assertFalse(solver.solve(grid));
        }

        @Test
        void shouldPreservePreFilledValuesWhenSolvingPartiallyFilledFrid() {
            grid = new Grid(extreme);
            solver.solve(grid);
            assertEquals(6, grid.getValue(0, 7));
            assertEquals(2, grid.getValue(1, 1));
            assertEquals(3, grid.getValue(1, 3));
            assertEquals(1, grid.getValue(1, 6));
            assertEquals(5, grid.getValue(2, 0));
            assertEquals(8, grid.getValue(2, 3));
            assertEquals(9, grid.getValue(2, 5));
            assertEquals(8, grid.getValue(3, 0));
            assertEquals(5, grid.getValue(3, 4));
            assertEquals(9, grid.getValue(3, 7));
            assertEquals(6, grid.getValue(3, 8));
            assertEquals(4, grid.getValue(4, 1));
            assertEquals(6, grid.getValue(4, 3));
            assertEquals(4, grid.getValue(5, 3));
            assertEquals(3, grid.getValue(5, 8));
            assertEquals(9, grid.getValue(6, 0));
            assertEquals(7, grid.getValue(6, 4));
            assertEquals(5, grid.getValue(6, 6));
            assertEquals(4, grid.getValue(7, 7));
            assertEquals(1, grid.getValue(8, 1));
            assertEquals(9, grid.getValue(8, 3));
            assertEquals(8, grid.getValue(8, 6));
        }
    }

    @Nested
    class CountSolutionsTests {

        @BeforeEach
        void init() {
            solver = new Solver();
        }

        @Test
        void shouldReturnMaxSolutionsWhenGridIsEmpty() {
            grid = new Grid();
            assertEquals(MAX_SOLUTIONS, solver.countSolutions(grid));
        }
    }
}

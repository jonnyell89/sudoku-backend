package com.sudoku.sudoku_backend.core;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.sudoku.sudoku_backend.core.TestGrids.*;
import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    private final Random random = new Random();

    @Nested
    class SolveTests {

        @Test
        void shouldSolveEmptyGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid();
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveEasyGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(easy);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveMediumGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(medium);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveHardGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(hard);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveExpertGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(expert);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveMasterGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(master);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldSolveExtremeGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(extreme);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            Solver solver = new Solver(random);
            assertThrows(IllegalArgumentException.class, () -> solver.solve(null));
        }

        @Test
        void shouldThrowWhenGridContainsDuplicates() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(duplicates);
            assertThrows(IllegalArgumentException.class, () -> solver.solve(grid));
        }

        @Test
        void shouldReturnFalseWhenGridIsUnsolvable() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(unsolvable);
            assertFalse(solver.solve(grid));
        }

        @Test
        void shouldPreservePreFilledValuesWhenSolvingPartiallyFilledGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(extreme);
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

        @Test
        void shouldReturnTwoOnEmptyGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid();
            assertEquals(2, solver.countSolutions(grid));
        }

        @Test
        void shouldReturnOneOnEasyGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(easy);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldReturnOneOnMediumGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(medium);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldReturnOneOnHardGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(hard);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldReturnOneOnExpertGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(expert);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldReturnOneOnMasterGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(master);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldReturnOneOnExtremeGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(extreme);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            Solver solver = new Solver(random);
            assertThrows(IllegalArgumentException.class, () -> solver.countSolutions(null));
        }

        @Test
        void shouldThrowWhenGridContainsDuplicates() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(duplicates);
            assertThrows(IllegalArgumentException.class, () -> solver.countSolutions(grid));
        }

        @Test
        void shouldReturnZeroWhenGridIsUnsolvable() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(unsolvable);
            assertEquals(0, solver.countSolutions(grid));
        }

        @Test
        void shouldNotMutateOriginalGrid() {
            Solver solver = new Solver(random);
            Grid grid = new Grid(extreme);
            solver.countSolutions(grid);
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
}

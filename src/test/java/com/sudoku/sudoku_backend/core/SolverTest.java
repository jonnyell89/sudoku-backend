package com.sudoku.sudoku_backend.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static com.sudoku.sudoku_backend.core.TestGrids.*;
import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    private final Random random = new Random();

    private Solver solver;

    @BeforeEach
    void init() {
        solver = new Solver(random);
    }

    static Stream<int[][]> solvableGrids() {
        return Stream.of(easy, medium, hard, expert, master, extreme);
    }

    private static void assertPreFilledValuesPreserved(Grid grid, int[][] source) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                if (source[i][j] != 0) {
                    assertEquals(source[i][j], grid.getValue(i, j));
                }
            }
        }
    }

    @Nested
    class SolveTests {

        @Test
        void shouldSolveEmptyGrid() {
            Grid grid = new Grid();
            assertTrue(solver.solve(grid));
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.SolverTest#solvableGrids")
        void shouldSolveGrid(int[][] data) {
            Grid grid = new Grid(data);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            assertThrows(IllegalArgumentException.class, () -> solver.solve(null));
        }

        @Test
        void shouldThrowWhenGridContainsDuplicates() {
            Grid grid = new Grid(duplicates);
            assertThrows(IllegalArgumentException.class, () -> solver.solve(grid));
        }

        @Test
        void shouldReturnFalseWhenGridIsUnsolvable() {
            Grid grid = new Grid(unsolvable);
            assertFalse(solver.solve(grid));
        }

        @Test
        void shouldPreservePreFilledValuesWhenSolvingPartiallyFilledGrid() {
            Grid grid = new Grid(extreme);
            solver.solve(grid);
            assertPreFilledValuesPreserved(grid, extreme);
        }
    }

    @Nested
    class CountSolutionsTests {

        @Test
        void shouldReturnTwoSolutionsOnEmptyGrid() {
            Grid grid = new Grid();
            assertEquals(2, solver.countSolutions(grid));
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.SolverTest#solvableGrids")
        void shouldReturnOneUniqueSolution(int[][] data) {
            Grid grid = new Grid(data);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            assertThrows(IllegalArgumentException.class, () -> solver.countSolutions(null));
        }

        @Test
        void shouldThrowWhenGridContainsDuplicates() {
            Grid grid = new Grid(duplicates);
            assertThrows(IllegalArgumentException.class, () -> solver.countSolutions(grid));
        }

        @Test
        void shouldReturnZeroSolutionsWhenGridIsUnsolvable() {
            Grid grid = new Grid(unsolvable);
            assertEquals(0, solver.countSolutions(grid));
        }

        @Test
        void shouldNotMutateOriginalGrid() {
            Grid grid = new Grid(extreme);
            solver.countSolutions(grid);
            assertPreFilledValuesPreserved(grid, extreme);
        }
    }
}

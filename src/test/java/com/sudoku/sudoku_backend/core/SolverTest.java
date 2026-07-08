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

    static Stream<int[][]> testGrids() {
        return Stream.of(easy, medium, hard, expert, master, extreme);
    }

    private static void assertValuesPreserved(Grid grid, int[][] source) {
        for (int row = 0; row < source.length; row++) {
            for (int col = 0; col < source[row].length; col++) {
                if (source[row][col] != 0) {
                    assertEquals(source[row][col], grid.getValue(row, col));
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
        @MethodSource("com.sudoku.sudoku_backend.core.SolverTest#testGrids")
        void shouldSolveTestGrid(int[][] data) {
            Grid grid = new Grid(data);
            assertTrue(solver.solve(grid));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            assertThrows(NullPointerException.class, () -> solver.solve(null));
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

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.SolverTest#testGrids")
        void shouldPreserveValuesWhenSolvingTestGrid(int[][] data) {
            Grid grid = new Grid(data);
            solver.solve(grid);
            assertValuesPreserved(grid, data);
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
        @MethodSource("com.sudoku.sudoku_backend.core.SolverTest#testGrids")
        void shouldReturnOneUniqueSolution(int[][] data) {
            Grid grid = new Grid(data);
            assertEquals(1, solver.countSolutions(grid));
        }

        @Test
        void shouldThrowWhenGridIsNull() {
            assertThrows(NullPointerException.class, () -> solver.countSolutions(null));
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

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.core.SolverTest#testGrids")
        void shouldNotMutateOriginalGrid(int[][] data) {
            Grid grid = new Grid(data);
            solver.countSolutions(grid);
            assertValuesPreserved(grid, data);
        }
    }
}

package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import com.sudoku.sudoku_backend.model.Cell;
import com.sudoku.sudoku_backend.persistence.GameRepository;
import com.sudoku.sudoku_backend.persistence.NewGame;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PuzzleServiceTest {

    private static IntStream seeds() {
        return IntStream.range(0, SEED_COUNT);
    }

    private static GameRepository mockGameRepository() {
        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        return gameRepository;
    }

    @Nested
    class ConstructorTests {

        @Test
        void shouldThrowWhenGeneratorAndGameRepositoryIsNull() {
            assertThrows(NullPointerException.class, () -> new PuzzleService(null, null));
        }
    }

    @Nested
    class NewPuzzleTests {

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.service.PuzzleServiceTest#seeds")
        void shouldReturnMappedPuzzleAsCellGridWithDifficultyEasy(int seed) {
            Random expectedRandom = new Random(seed);
            Random actualRandom = new Random(seed);

            Generator expectedGenerator = new Generator(expectedRandom);
            Generator actualGenerator = new Generator(actualRandom);

            int target = Difficulty.EASY.getTarget();

            Grid grid = expectedGenerator.generateGrid();
            Puzzle puzzle = expectedGenerator.createPuzzle(grid, target);
            Grid expectedCarved = puzzle.carved();

            GameRepository gameRepository = mockGameRepository();

            PuzzleService puzzleService = new PuzzleService(actualGenerator, gameRepository);
            NewGame newGame = puzzleService.newPuzzle(Difficulty.EASY);

            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    Cell cell = newGame.cellGrid().getCell(row, col);
                    assertEquals(row, cell.getRow());
                    assertEquals(col, cell.getCol());
                    int expectedValue = expectedCarved.getValue(row, col);
                    assertEquals(expectedValue, cell.getValue());
                    boolean isGiven = expectedValue != 0;
                    assertEquals(isGiven, cell.isGiven());
                }
            }
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.service.PuzzleServiceTest#seeds")
        void shouldReturnMappedPuzzleAsCellGridWithDifficultyMedium(int seed) {
            Random expectedRandom = new Random(seed);
            Random actualRandom = new Random(seed);

            Generator expectedGenerator = new Generator(expectedRandom);
            Generator actualGenerator = new Generator(actualRandom);

            int target = Difficulty.MEDIUM.getTarget();

            Grid grid = expectedGenerator.generateGrid();
            Puzzle puzzle = expectedGenerator.createPuzzle(grid, target);
            Grid expectedCarved = puzzle.carved();

            GameRepository gameRepository = mockGameRepository();

            PuzzleService puzzleService = new PuzzleService(actualGenerator, gameRepository);
            NewGame newGame = puzzleService.newPuzzle(Difficulty.MEDIUM);

            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    Cell cell = newGame.cellGrid().getCell(row, col);
                    assertEquals(row, cell.getRow());
                    assertEquals(col, cell.getCol());
                    int expectedValue = expectedCarved.getValue(row, col);
                    assertEquals(expectedValue, cell.getValue());
                    boolean isGiven = expectedValue != 0;
                    assertEquals(isGiven, cell.isGiven());
                }
            }
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.service.PuzzleServiceTest#seeds")
        void shouldReturnMappedPuzzleAsCellGridWithDifficultyHard(int seed) {
            Random expectedRandom = new Random(seed);
            Random actualRandom = new Random(seed);

            Generator expectedGenerator = new Generator(expectedRandom);
            Generator actualGenerator = new Generator(actualRandom);

            int target = Difficulty.HARD.getTarget();

            Grid grid = expectedGenerator.generateGrid();
            Puzzle puzzle = expectedGenerator.createPuzzle(grid, target);
            Grid expectedCarved = puzzle.carved();

            GameRepository gameRepository = mockGameRepository();

            PuzzleService puzzleService = new PuzzleService(actualGenerator, gameRepository);
            NewGame newGame = puzzleService.newPuzzle(Difficulty.HARD);

            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    Cell cell = newGame.cellGrid().getCell(row, col);
                    assertEquals(row, cell.getRow());
                    assertEquals(col, cell.getCol());
                    int expectedValue = expectedCarved.getValue(row, col);
                    assertEquals(expectedValue, cell.getValue());
                    boolean isGiven = expectedValue != 0;
                    assertEquals(isGiven, cell.isGiven());
                }
            }
        }

        @Test
        void shouldThrowWhenDifficultyIsNull() {
            Random random = new Random();
            Generator generator = new Generator(random);
            GameRepository gameRepository = mockGameRepository();
            PuzzleService puzzleService = new PuzzleService(generator, gameRepository);
            assertThrows(NullPointerException.class, () -> puzzleService.newPuzzle(null));
        }
    }
}

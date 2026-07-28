package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import com.sudoku.sudoku_backend.core.TestGrids;
import com.sudoku.sudoku_backend.model.Cell;
import com.sudoku.sudoku_backend.persistence.GameEntity;
import com.sudoku.sudoku_backend.persistence.GameRepository;
import com.sudoku.sudoku_backend.persistence.GridSerializer;
import com.sudoku.sudoku_backend.persistence.NewGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PuzzleServiceTest {

    private static final int[][] BLANK = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private static final int[][] SOLVED = TestGrids.solved;

    private GameRepository gameRepository;
    private PuzzleService puzzleService;

    private static IntStream seeds() {
        return IntStream.range(0, SEED_COUNT);
    }

    private static GameRepository mockGameRepository() {
        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        return gameRepository;
    }

    private static Generator mockGenerator() {
        return mock(Generator.class);
    }

    private void initPuzzleService() {
        gameRepository = mockGameRepository();
        puzzleService = new PuzzleService(mockGenerator(), gameRepository);
    }

    private static int[][] carve(int[][] solved, int keepRow, int keepCol) {
        int[][] carved = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        carved[keepRow][keepCol] = solved[keepRow][keepCol];
        return carved;
    }

    private void stubGame(int[][] complete, int[][] progress) {
        String serialized = GridSerializer.serialize(new Grid(progress));
        GameEntity gameEntity = new GameEntity(
                GridSerializer.serialize(new Grid(complete)),
                serialized,
                serialized
        );
        when(gameRepository.findById(1L)).thenReturn(Optional.of(gameEntity));
    }

    @Nested
    class ConstructorTests {

        @Test
        void shouldThrowWhenGeneratorIsNull() {
            GameRepository gameRepository = mock(GameRepository.class);
            assertThrows(NullPointerException.class, () -> new PuzzleService(null, gameRepository));
        }

        @Test
        void shouldThrowWhenGameRepositoryIsNull() {
            Random random = new Random();
            Generator generator = new Generator(random);
            assertThrows(NullPointerException.class, () -> new PuzzleService(generator, null));
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

    @Nested
    class CheckGuessTests {

        @BeforeEach
        void init() {
            initPuzzleService();
        }

        @Test
        void shouldReturnTrueWhenGuessIsCorrect() {
            stubGame(SOLVED, BLANK);
            int guess = SOLVED[ROW][COL];
            assertTrue(puzzleService.checkGuess(1L, ROW, COL, guess));
        }

        @Test
        void shouldReturnFalseWhenGuessIsGiven() {
            stubGame(SOLVED, carve(SOLVED, ROW, COL));
            int clue = SOLVED[ROW][COL];
            assertFalse(puzzleService.checkGuess(1L, ROW, COL, clue));
        }

        @Test
        void shouldReturnFalseWhenGuessIsIncorrect() {
            stubGame(SOLVED, BLANK);
            int correct = SOLVED[ROW][COL];
            int incorrect = (correct % SudokuConstants.MAX_VALUE) + 1;
            assertFalse(puzzleService.checkGuess(1L, ROW, COL, incorrect));
        }

        @Test
        void shouldThrowWhenIdIsInvalid() {
            assertThrows(NoSuchElementException.class, () -> puzzleService.checkGuess(1L, ROW, COL, VALUE));
        }

        @Test
        void shouldThrowWhenRowIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> puzzleService.checkGuess(1L, ROW_BELOW_MIN, COL, VALUE));
        }

        @Test
        void shouldThrowWhenRowIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> puzzleService.checkGuess(1L, ROW_ABOVE_MAX, COL, VALUE));
        }

        @Test
        void shouldThrowWhenColIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> puzzleService.checkGuess(1L, ROW, COL_BELOW_MIN, VALUE));
        }

        @Test
        void shouldThrowWhenColIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> puzzleService.checkGuess(1L, ROW, COL_ABOVE_MAX, VALUE));
        }

        @Test
        void shouldThrowWhenValueIsBelowMin() {
            assertThrows(IllegalArgumentException.class, () -> puzzleService.checkGuess(1L, ROW, COL, VALUE_BELOW_MIN));
        }

        @Test
        void shouldThrowWhenValueIsAboveMax() {
            assertThrows(IllegalArgumentException.class, () -> puzzleService.checkGuess(1L, ROW, COL, VALUE_ABOVE_MAX));
        }
    }

    @Nested
    class IsSolvedTests {

        @BeforeEach
        void init() {
            initPuzzleService();
        }

        @Test
        void shouldReturnTrueWhenCompleteAndCurrentMatch() {
            stubGame(SOLVED, SOLVED);
            assertTrue(puzzleService.isSolved(1L));
        }

        @Test
        void shouldReturnFalseWhenCompleteAndCurrentDoNotMatch() {
            stubGame(SOLVED, BLANK);
            assertFalse(puzzleService.isSolved(1L));
        }

        @Test
        void shouldThrowWhenIdIsInvalid() {
            assertThrows(NoSuchElementException.class, () -> puzzleService.isSolved(1L));
        }
    }
}

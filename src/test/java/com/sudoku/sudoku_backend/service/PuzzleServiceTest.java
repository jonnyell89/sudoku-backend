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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static com.sudoku.sudoku_backend.SudokuTestConstants.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PuzzleServiceTest {

    private static final int[][] BLANK = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private static final int[][] SOLVED = TestGrids.solved;

    private PuzzleService puzzleService;
    private GameRepository gameRepository;

    private static Stream<Arguments> seedsAndDifficulties() {
        return IntStream.range(0, SEED_COUNT)
                .boxed()
                .flatMap(seed -> Stream.of(Difficulty.values())
                        .map(difficulty -> arguments(seed, difficulty)));
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
            assertThrows(NullPointerException.class, () -> new PuzzleService(null, mockGameRepository()));
        }

        @Test
        void shouldThrowWhenGameRepositoryIsNull() {
            assertThrows(NullPointerException.class, () -> new PuzzleService(mockGenerator(), null));
        }
    }

    @Nested
    class NewPuzzleTests {

        @BeforeEach
        void init() {
            initPuzzleService();
        }

        @ParameterizedTest
        @MethodSource("com.sudoku.sudoku_backend.service.PuzzleServiceTest#seedsAndDifficulties")
        void shouldReturnMappedPuzzleAsCellGrid(int seed, Difficulty difficulty) {
            Generator expected = new Generator(new Random(seed));
            Generator actual = new Generator(new Random(seed));

            Grid grid = expected.generateGrid();
            Puzzle puzzle = expected.createPuzzle(grid, difficulty.getTarget());
            Grid expectedCarved = puzzle.carved();

            PuzzleService puzzleService = new PuzzleService(actual, mockGameRepository());
            NewGame newGame = puzzleService.newPuzzle(difficulty);

            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    Cell cell = newGame.cellGrid().getCell(row, col);
                    assertEquals(row, cell.getRow());
                    assertEquals(col, cell.getCol());
                    int expectedValue = expectedCarved.getValue(row, col);
                    assertEquals(expectedValue, cell.getValue());
                    assertEquals(expectedValue != 0, cell.isGiven());
                }
            }
        }

        @Test
        void shouldThrowWhenDifficultyIsNull() {
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

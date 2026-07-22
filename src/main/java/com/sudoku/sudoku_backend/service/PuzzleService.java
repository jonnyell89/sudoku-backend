package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import com.sudoku.sudoku_backend.core.Validator;
import com.sudoku.sudoku_backend.model.PuzzleMapper;
import com.sudoku.sudoku_backend.persistence.GameEntity;
import com.sudoku.sudoku_backend.persistence.GameRepository;
import com.sudoku.sudoku_backend.persistence.GridSerializer;
import com.sudoku.sudoku_backend.persistence.NewGame;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class PuzzleService {

    private final Generator generator;
    private final GameRepository gameRepository;

    public PuzzleService(Generator generator, GameRepository gameRepository) {
        Objects.requireNonNull(generator, "generator must not be null.");
        Objects.requireNonNull(gameRepository, "gameRepository must not be null.");
        this.generator = generator;
        this.gameRepository = gameRepository;
    }

    public NewGame newPuzzle(Difficulty difficulty) {
        Objects.requireNonNull(difficulty, "difficulty must not be null.");
        Grid grid = generator.generateGrid();
        Puzzle puzzle = generator.createPuzzle(grid, difficulty.getTarget());
        String carved = GridSerializer.serialize(puzzle.carved());
        GameEntity gameEntity = new GameEntity(
                GridSerializer.serialize(puzzle.complete()),
                carved,
                carved // current field starts as carved
        );
        GameEntity saved = gameRepository.save(gameEntity);
        return new NewGame(saved.getId(), PuzzleMapper.map(puzzle));
    }

    public boolean checkGuess(long id, int row, int col, int value) {
        Validator.validateIndex("row", row);
        Validator.validateIndex("col", col);
        Validator.validateValue(value);
        GameEntity gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("gameEntity was not found with id: %d", id)));

        Grid carved = GridSerializer.deserialize(gameEntity.getCarved());
        if (carved.getValue(row, col) == value) {
            return false; // rejects guesses on clues
        }

        Grid complete = GridSerializer.deserialize(gameEntity.getComplete());
        if (complete.getValue(row, col) != value) {
            return false; // rejects incorrect guesses
        }

        Grid current = GridSerializer.deserialize(gameEntity.getCurrent());
        current.setValue(row, col, value);
        gameEntity.setCurrent(GridSerializer.serialize(current));
        gameRepository.save(gameEntity);
        return true;
    }

    public boolean isSolved(long id) {
        GameEntity gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("gameEntity was not found with id: %d", id)));
        Grid complete = GridSerializer.deserialize(gameEntity.getComplete());
        Grid current = GridSerializer.deserialize(gameEntity.getCurrent());
        return complete.equals(current);
    }
}

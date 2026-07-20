package com.sudoku.sudoku_backend.service;

import com.sudoku.sudoku_backend.core.Generator;
import com.sudoku.sudoku_backend.core.Grid;
import com.sudoku.sudoku_backend.core.Puzzle;
import com.sudoku.sudoku_backend.model.PuzzleMapper;
import com.sudoku.sudoku_backend.persistence.GameEntity;
import com.sudoku.sudoku_backend.persistence.GameRepository;
import com.sudoku.sudoku_backend.persistence.GridSerializer;
import com.sudoku.sudoku_backend.persistence.NewGame;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PuzzleService {

    private final Generator generator;
    private final GameRepository gameRepository;

    public PuzzleService(Generator generator, GameRepository gameRepository) {
        Objects.requireNonNull(generator, "generator must not be null.");
        this.generator = generator;
        this.gameRepository = gameRepository;
    }

    public NewGame newPuzzle(Difficulty difficulty) {
        Objects.requireNonNull(difficulty, "difficulty must not be null.");
        Grid grid = generator.generateGrid();
        Puzzle puzzle = generator.createPuzzle(grid, difficulty.getTarget());
        GameEntity gameEntity = new GameEntity(
                GridSerializer.serialize(puzzle.complete()),
                GridSerializer.serialize(puzzle.carved()),
                GridSerializer.serialize(puzzle.carved()) // Current field starts as puzzle.carved().
        );
        GameEntity saved = gameRepository.save(gameEntity);
        return new NewGame(saved.getId(), PuzzleMapper.map(puzzle));
    }
}

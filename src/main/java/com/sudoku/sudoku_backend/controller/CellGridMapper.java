package com.sudoku.sudoku_backend.controller;

import com.sudoku.sudoku_backend.SudokuConstants;
import com.sudoku.sudoku_backend.model.Cell;
import com.sudoku.sudoku_backend.model.CellGrid;

public class CellGridMapper {

    public static CellResponse[][] map(CellGrid cellGrid) {
        CellResponse[][] cells = new CellResponse[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                Cell cell = cellGrid.getCell(row, col);
                cells[row][col] = new CellResponse(cell.getValue(), cell.isGiven());
            }
        }
        return cells;
    }
}

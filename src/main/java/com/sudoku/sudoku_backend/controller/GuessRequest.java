package com.sudoku.sudoku_backend.controller;

public record GuessRequest(int row, int col, int value) {}

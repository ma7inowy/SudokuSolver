package com.progkomp.sudoku;

public class SudokuBoardDaoFactory {

    private String fileName;

    public Dao<SudokuBoard> getFileDao(final String fileName) {
        return new FileSudokuBoardDao(fileName); //zwraca obiekt
    }

    public static Dao<SudokuBoard> getJdbcDao(final String boardName) {
        return new JdbcSudokuBoardDao(boardName);
    }

}

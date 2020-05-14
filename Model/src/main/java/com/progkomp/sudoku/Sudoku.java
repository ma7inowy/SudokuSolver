package com.progkomp.sudoku;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Sudoku {

    public static void main(final String[] args) throws SQLException {
        SudokuBoard sb = new SudokuBoard();
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();
        BSS.solve(sb);

        FileSudokuBoardDao fsbd = new FileSudokuBoardDao("testy.txt");
        fsbd.write(sb);
        SudokuBoard sb2 = new SudokuBoard(fsbd.read());

        System.out.println(sb.toString());
        System.out.println(sb2.toString());

        JdbcSudokuBoardDao xdxd = new JdbcSudokuBoardDao("1");
        
        xdxd.write(sb2);
        SudokuBoard sb3 = new SudokuBoard();
        sb3 = xdxd.read();
        System.out.println(sb3.toString());
        //System.out.println(authors.getContents()[0][1]);

    }

}

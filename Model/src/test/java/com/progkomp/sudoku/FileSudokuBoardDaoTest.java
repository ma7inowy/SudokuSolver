package com.progkomp.sudoku;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class FileSudokuBoardDaoTest {

    @Test
    public void test() {
        FileSudokuBoardDao f = new FileSudokuBoardDao("testy.txt");
        SudokuBoard sb = new SudokuBoard();
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();

        bss.solve(sb);

        System.out.println(sb.toString());
        System.out.println("...");
        f.write(sb); //zapisz do pliku sudoku
        SudokuBoard h;
        h = f.read(); //zapis do obiektu h;
        assertTrue(sb.toString().equals(h.toString()));

        System.out.println(h.toString());
    }
}

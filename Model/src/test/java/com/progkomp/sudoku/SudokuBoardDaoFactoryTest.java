package com.progkomp.sudoku;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SudokuBoardDaoFactoryTest {

    @Test
    public void test() {

        SudokuBoardDaoFactory sF = new SudokuBoardDaoFactory();
        assertTrue(sF.getFileDao("123.txt") != null);

    }
}

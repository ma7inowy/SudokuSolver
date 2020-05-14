package com.progkomp.sudoku;

import static com.progkomp.sudoku.SudokuLevel.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SudokuLevelTest {

    @Test
    public void test1() {
        SudokuLevel sl = null;
        assertTrue(sl.P1.getLiczbaPolDoUsuniecia() == 10);
        assertTrue(sl.P2.getLiczbaPolDoUsuniecia() == 20);
        assertTrue(sl.P3.getLiczbaPolDoUsuniecia() == 30);
        //sl.P1.usunPola(sl, new SudokuBoard());
        getP1();
        getP2();
        getP3();

    }
}

package com.progkomp.sudoku;

import java.util.ArrayList;

public class SudokuColumn extends SudokuElement {

    public SudokuColumn() {
        super();
    }

    public ArrayList<SudokuField> getColumn() {
        return getTab();
    }

}

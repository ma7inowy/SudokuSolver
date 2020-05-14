package com.progkomp.sudoku;

import java.util.ArrayList;

public class SudokuRow extends SudokuElement {

    public SudokuRow() {
        super();
    }

    public ArrayList<SudokuField> getRow() {
        return getTab();
    }

}

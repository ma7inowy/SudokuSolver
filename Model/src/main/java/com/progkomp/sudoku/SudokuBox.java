package com.progkomp.sudoku;

import java.util.ArrayList;

public class SudokuBox extends SudokuElement {

    public SudokuBox() {
        super();
    }

    public ArrayList<SudokuField> getBox() {
        return getTab();
    }

}

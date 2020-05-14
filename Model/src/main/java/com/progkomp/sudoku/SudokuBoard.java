package com.progkomp.sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
//import java.util.Objects;

public class SudokuBoard implements Cloneable, Serializable {

    private ArrayList<ArrayList<SudokuField>> board;

    //konstruktory------------------------------------------------------------------------------------------------------
    public SudokuBoard() { //konstruktor wypelnia sudoku zerami
        board = new ArrayList<ArrayList<SudokuField>>(9);
        for (int i = 0; i < 9; i++) {
            board.add(i, new ArrayList<SudokuField>(9));
            for (int j = 0; j < 9; j++) {
                board.get(i).add(j, new SudokuField());
            }
        }
    }

    public SudokuBoard(final SudokuBoard sb) {

        board = new ArrayList<ArrayList<SudokuField>>(9);
        for (int i = 0; i < 9; i++) {
            board.add(i, new ArrayList<SudokuField>(9));
            for (int j = 0; j < 9; j++) {
                board.get(i).add(j, new SudokuField());
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.get(i).get(j).setFieldValue(sb.get(i, j));
            }
        }
    }

    //settery i gettery, toString, equals, hashCode------------------------------------------------------------------
    public void setForTests(int n, int x, int y) {
        board.get(x).get(y).setFieldValue(n);
    }

    public boolean set(int n, int x, int y) { // ustawia wartosc n  na polu (x,y)
        if (checkPos(n, x, y)) {
            board.get(x).get(y).setFieldValue(n);
            return true;
        } else {
            board.get(x).get(y).setFieldValue(0);
            return false;
        }
    }

    public int get(int x, int y) { // pobiera wartosc pola (x,y)
        return board.get(x).get(y).getFieldValue();
    }

    public SudokuRow getRow(int y) {
        SudokuRow row = new SudokuRow();
        int wartosc = 0;
        int miejsce = 0;
        for (int i = 0; i < 9; i++) {
            wartosc = board.get(y).get(i).getFieldValue();
            row.set(wartosc, miejsce);
            miejsce++;
        }
        return row;
    }

    public SudokuColumn getColumn(int y) {
        SudokuColumn column = new SudokuColumn();
        int wartosc = 0;
        int miejsce = 0;
        for (int i = 0; i < 9; i++) {
            wartosc = board.get(i).get(y).getFieldValue();
            column.set(wartosc, miejsce);
            miejsce++;
        }
        return column;
    }

    public SudokuBox getBox(int x, int y) {
        SudokuBox box = new SudokuBox();
        int wartosc = 0;
        int miejsce = 0;

        int pom1 = x - x % 3;
        int pom2 = y - y % 3;

        for (int i = pom1; i < pom1 + 3; i++) {
            for (int j = pom2; j < pom2 + 3; j++) {
                wartosc = board.get(i).get(j).getFieldValue();
                box.set(wartosc, miejsce);
                miejsce++;
            }
        }
        return box;
    }

    @Override
    public String toString() {
        String xd = "";
        for (int i = 0; i < 9; i++) {
            xd += MoreObjects.toStringHelper(this).
                    addValue(this.get(i, 0)).
                    addValue(this.get(i, 1)).
                    addValue(this.get(i, 2)).
                    addValue(this.get(i, 3)).
                    addValue(this.get(i, 4)).
                    addValue(this.get(i, 5)).
                    addValue(this.get(i, 6)).
                    addValue(this.get(i, 7)).
                    addValue(this.get(i, 8)).
                    toString();
            xd += "\r\n";
        }
        return xd;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.board);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SudokuBoard other = (SudokuBoard) obj;
        return Objects.equal(this.board, other.board);

    }

    //pozostale metody--------------------------------------------------------------------------------------------------
    public boolean checkPos(int n, int x, int y) { // sprawdza czy mozna wstawic wartosc n na pozycji (x,y)
        for (int i = 0; i < 9; i++) {
            //rzedy
            if (board.get(x).get(i).getFieldValue() == n && y != i) { //jesli w rzedzie wartosc rowna n i "y" rozny od "i" zwroc flasz
                return false; //falsz zwraca gdy nie mozna wstawic
            }
            //kolumny
            if (board.get(i).get(y).getFieldValue() == n && x != i) {
                return false; //zwraca falsz jesli nie mozna wstawic w dane miejsce
            }

        }
        //3x3
        int pom1 = x - x % 3;
        int pom2 = y - y % 3;

        for (int i = pom1; i < pom1 + 3; i++) {
            for (int j = pom2; j < pom2 + 3; j++) {
                if (board.get(i).get(j).getFieldValue() == n && i != x && j != y) {
                    return false;  //ZWRACA FALSZ JESLI W KWADRACIKU 3X3 SIE POWTARZA COS
                }
            }
        }

        return true;

    }

    public void zeruj() { // zeruje cala tablice
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.get(i).get(j).setFieldValue(0);
            }
        }
    }

    private boolean checkBoard() {  //zwraca prawde jesli udalo sie poprawnie wypelnic sudoku
        for (int i = 0; i < 9; i++) {
            if (!this.getRow(i).verify()) {
                return false;
            }

            if (!this.getColumn(i).verify()) {
                return false;
            }

            for (int j = 0; j < 9; j++) {
                if (!this.getBox(i, j).verify()) {
                    return false;
                }

            }
        }
        return true;
    }

    public boolean callCheckBoard() {
        return checkBoard();
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {

        //inicjalizacja tablicy
        SudokuBoard temp;
        temp = (SudokuBoard) super.clone();

        temp.board = new ArrayList<ArrayList<SudokuField>>(9);
        for (int i = 0; i < 9; i++) {
            temp.board.add(i, new ArrayList<SudokuField>(9));
            for (int j = 0; j < 9; j++) {
                temp.board.get(i).add(j, new SudokuField());
            }
        }

        //wpisanie danych do tablicy
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp.board.get(i).get(j).setFieldValue(board.get(i).get(j).getFieldValue());
            }
        }

        return temp;
    }

}

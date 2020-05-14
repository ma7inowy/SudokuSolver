package com.progkomp.sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.ArrayList;

public class SudokuElement implements Cloneable, Serializable {

    private ArrayList<SudokuField> tab;

    public SudokuElement() {
        tab = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            tab.add(i, new SudokuField());
        }
    }

    public void set(int wartosc, int miejsce) {
        tab.get(miejsce).setFieldValue(wartosc);
    }

    public ArrayList<SudokuField> getTab() {
        return tab;
    }

    public boolean verify() { // sprawdza czy w elemencie nie powtarza sie zadna cyfra
        int check = 0;
        for (int j = 0; j < 8; j++) {
            for (int i = j + 1; i < 9; i++) {  //8 bo pozniej jest k=i+1
                if (tab.get(j).getFieldValue() == tab.get(i).getFieldValue()) {
                    check++;  //jesli dana wartosc sie powtorzy to ++
                }
            }
        }

        if (check == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void show() {
        for (int i = 0; i < 9; i++) {
            System.out.println("Miejsce " + i + " " + tab.get(i).getFieldValue());
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                addValue(tab.get(0).getFieldValue()).
                addValue(tab.get(1).getFieldValue()).
                addValue(tab.get(2).getFieldValue()).
                addValue(tab.get(3).getFieldValue()).
                addValue(tab.get(4).getFieldValue()).
                addValue(tab.get(5).getFieldValue()).
                addValue(tab.get(6).getFieldValue()).
                addValue(tab.get(7).getFieldValue()).
                addValue(tab.get(8).getFieldValue()).
                toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.tab);
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
        final SudokuElement other = (SudokuElement) obj;
        return Objects.equal(this.tab, other.tab);
    }

    @Override
    public SudokuElement clone() throws CloneNotSupportedException {
        SudokuElement temp;
        temp = (SudokuElement) super.clone();
        temp.tab = new ArrayList<>(9);

        for (int i = 0; i < 9; i++) {
            temp.tab.add(i, new SudokuField());
        }

        for (int i = 0; i < tab.size(); i++) {
            temp.tab.set(i, (SudokuField) this.tab.get(i).clone());
        }

        return temp;
    }
}

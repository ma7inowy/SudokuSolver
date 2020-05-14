package com.progkomp.sudoku;

import com.google.common.base.MoreObjects;
import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public boolean solve(final SudokuBoard sb) {
        int r = -1;
        int k = -1;
        boolean filled = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {       // przejscie po wszystkich miejscach w tablicy
                if (sb.get(i, j) == 0) {
                    r = i;                      // zapisanie miejsca gdzie jeszcze nie ma cyfry (jest tam 0)
                    k = j;                      // zapisanie miejsca gdzie jeszcze nie ma cyfry (jest tam 0)
                    filled = false;
                    break; //wychodzi z petli wewnetrznej, + flaga filled na false
                }
            }
            if (!filled) {
                break;     //wychodzi z petli zewnetrznej, bo flaga false
            }
        }

        if (filled) {     //jesli nie znajdzie miejsca to wychodzi z funkcji i konczy program bo nie ma miejsc zerowych
            return true;
        }

        if (checkIfEmptyArray(sb)) {             //losowanie przy pierwszym wywolaniu
            sb.set(random(), 0, 1);
            sb.set(random(), 3, 2);
            sb.set(random(), 0, 7);
            sb.set(random(), 8, 7);
            sb.set(random(), 1, 6);
        }

        for (int n = 1; n <= 9; n++) {
            if (sb.set(n, r, k)) {
                if (solve(sb)) {
                    return true;
                } else {
                    sb.set(0, r, k);
                }
            }
        }
        return false;
    }

    public boolean checkIfEmptyArray(final SudokuBoard sb) { //sprawdz czy tablica jest cala wypelniona 0-ami
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sb.get(i, j) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int random() {
        Random generator = new Random();
        return generator.nextInt(8) + 1;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).toString();
    }
}

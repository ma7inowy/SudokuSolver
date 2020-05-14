package com.progkomp.sudoku;

import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BacktrackingSudokuSolverTest {

    @Test
    public void BacktrackingSudokuSolverTests() {
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();
        SudokuBoard sb1 = new SudokuBoard();
        SudokuBoard sb2 = new SudokuBoard();

        assertTrue(BSS.solve(sb1));
        assertTrue(BSS.solve(sb2));
        assertTrue(!sb1.toString().equals(sb2.toString()));

        System.out.println(sb1.toString()); //wyswietl tablice1
        System.out.println("");
        System.out.println(sb2.toString()); //wyswietl tablice2

// sprawdzanie czy jakas wartosc powtarza sie w rzedzie
        int[] sprawdz_rzedy = new int[9];
        for (int i = 0; i < 9; i++) {
            sprawdz_rzedy[i] = 0;
            for (int j = 0; j < 8; j++) { //8 bo pozniej jest k=j+1
                for (int k = j + 1; k < 9; k++) {
                    if (sb1.get(i, j) == sb1.get(i, k)) {
                        sprawdz_rzedy[i]++;  //jesli dana wartosc sie powtorzy to ++
                    }
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            Assert.assertEquals(0, sprawdz_rzedy[i]); //test do rzędow
        }

// sprawdzanie czy jakas wartosc powtarza sie w kolumnie
        int[] sprawdz_kolumny = new int[9];
        for (int j = 0; j < 9; j++) {
            sprawdz_kolumny[j] = 0;
            for (int i = 0; i < 8; i++) {  //8 bo pozniej jest k=i+1
                for (int k = i + 1; k < 9; k++) {
                    if (sb1.get(i, j) == sb1.get(k, j)) {
                        sprawdz_kolumny[j]++;  //jesli dana wartosc sie powtorzy to ++
                    }
                }
            }
        }

        for (int i = 0; i < 9; i++) {
            Assert.assertEquals(0, sprawdz_kolumny[i]); //test do kolumn
        }

    }

}

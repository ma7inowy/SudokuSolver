package com.progkomp.sudoku;

import java.util.Random;

public enum SudokuLevel {
    P1(10),
    P2(20),
    P3(30);

    private final int liczbaPolDoUsuniecia;

    SudokuLevel(int liczbaPolDoUsuniecia) {
        this.liczbaPolDoUsuniecia = liczbaPolDoUsuniecia;
    }

    public void usunPola(final SudokuLevel poziom, final SudokuBoard sb) {

        Random generator = new Random();
        int a, b;
        for (int i = 0; i < poziom.liczbaPolDoUsuniecia; i++) {
            a = generator.nextInt(8);
            b = generator.nextInt(8);
            sb.set(0, a, b);
        }
    }

    public static SudokuLevel getP1() {
        return P1;
    }

    public static SudokuLevel getP2() {
        return P2;
    }

    public static SudokuLevel getP3() {
        return P3;
    }

    public int getLiczbaPolDoUsuniecia() {
        return liczbaPolDoUsuniecia;
    }

}

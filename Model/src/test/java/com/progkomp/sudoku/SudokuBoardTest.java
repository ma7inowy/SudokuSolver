package com.progkomp.sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SudokuBoardTest {

    @Test
    public void zerujTest() {
        int licznik = 0;
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();
        SudokuBoard sb1 = new SudokuBoard();

        sb1.zeruj();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sb1.get(i, j) != 0) {
                    licznik++;
                }
            }
        }
        Assert.assertEquals(0, licznik);
    }

    @Test
    public void setForTest() {
        SudokuBoard sb1 = new SudokuBoard();
        sb1.setForTests(5, 2, 2);
        Assert.assertEquals(5, sb1.get(2, 2));
    }

    @Test
    public void checkBoardTest() {
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();
        SudokuBoard sb2 = new SudokuBoard();
        BSS.solve(sb2);
        assertTrue(sb2.callCheckBoard());
        sb2.setForTests(7, 1, 1); //"psujemy" sudoku przez zmiane jakiejs wartosci
        sb2.getColumn(1).show();
        System.out.println(sb2.toString());
        assertTrue(!sb2.callCheckBoard());
    }

    @Test
    public void getRowTest() {
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();
        SudokuBoard sb1 = new SudokuBoard();
        BSS.solve(sb1);
        int[] tab = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tab[i] = sb1.get(j, i);
                assertTrue(sb1.getRow(j).getTab().get(i).getFieldValue() == tab[i]);
            }
        }
    }

    @Test
    public void getColumnTest() {
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();
        SudokuBoard sb1 = new SudokuBoard();
        BSS.solve(sb1);
        sb1.getColumn(5).getColumn();
        int[] tab = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tab[i] = sb1.get(i, j);
                assertTrue(sb1.getColumn(j).getTab().get(i).getFieldValue() == tab[i]);
            }
        }
    }

    @Test
    public void getBoxTest() {
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
        SudokuBoard sb1 = new SudokuBoard();
        bss.solve(sb1);
        sb1.getBox(5, 3).getBox();
        int[] tab = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tab[i] = sb1.get(i, j);
                assertTrue(sb1.getBox(i, j).getTab().get(i * 3 + j).getFieldValue() == tab[i]);
            }
        }
        for (int i = 0; i < 8; i++) {
            assertTrue(sb1.getBox(0, 0).getTab().get(i).getFieldValue() != tab[i + 1]);
        }
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard sb1 = new SudokuBoard();
        SudokuBoard sb2 = new SudokuBoard();
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();

        BSS.solve(sb1);
        BSS.solve(sb2);

        assertTrue(sb1.hashCode() != sb2.hashCode());
        assertTrue(sb1.hashCode() == sb1.hashCode());
    }

    @Test
    public void equalsTest() {

        SudokuBoard sb1 = new SudokuBoard();
        SudokuBoard sb2 = new SudokuBoard();
        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();

        BSS.solve(sb1);
        BSS.solve(sb2);

        assertFalse(sb1.equals(null));
        assertTrue(sb1.equals(sb1));
        assertFalse(sb1.equals(BSS));
        assertFalse(sb1.equals(sb2));
    }
    
        @Test
    public void cloneableTest() throws CloneNotSupportedException {
        SudokuBoard sb1 = new SudokuBoard();
        SudokuBoard sb2 = new SudokuBoard();

        assertTrue(sb1.clone() != sb1);

        BacktrackingSudokuSolver BSS = new BacktrackingSudokuSolver();
        BSS.solve(sb1);
        sb2 = sb1.clone();

        assertTrue(sb1.equals(sb2));

        System.out.println("SB1 sprawdzam clone czy dziala:");
        System.out.println(sb1.toString());

        System.out.println("SB2 sprawdzam clone czy dziala:");
        System.out.println(sb2.toString());

        sb1.setForTests(7, 1, 1);
        System.out.println("PO TESTACH:");
        System.out.println("SB1 sprawdzam clone czy dziala:");
        System.out.println(sb1.toString());

        System.out.println("SB2 sprawdzam clone czy dziala:");
        System.out.println(sb2.toString());
    }
    
    @Test
    public void serializableTest() throws FileNotFoundException, IOException, ClassNotFoundException {
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
        SudokuBoard sb = new SudokuBoard();
        bss.solve(sb);
        
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"))) {
            outputStream.writeObject(sb);
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"))) {

           // assertEquals(sb, (SudokuBoard) inputStream.readObject());
           SudokuBoard se=(SudokuBoard) inputStream.readObject();
           
           assertEquals(sb,se);
           
            System.out.println("SB ZOBAA: ");
            System.out.println(sb.toString());
            
            System.out.println("SE ZOBAA: ");
            System.out.println(se.toString());

        }
    }
    
}

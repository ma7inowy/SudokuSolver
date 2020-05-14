package com.progkomp.sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SudokuElementTest {

    @Test
    public void verifyTest() {
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
        SudokuBoard sb = new SudokuBoard();
        bss.solve(sb);
        SudokuElement se = new SudokuElement();
        assertTrue(sb.getBox(1, 1).verify()); //getBox
    }

    @Test
    public void hashCodeAndToStringTest() {
        SudokuElement se1 = new SudokuElement();
        se1.set(3, 1);
        SudokuElement se2 = new SudokuElement();
        se2.set(3, 1);
        assertTrue(se2.hashCode() == se1.hashCode());
        se2.set(2, 1);
        assertTrue(se2.hashCode() != se1.hashCode());
        se1.toString();
    }

    @Test
    public void equalsTest() {
        SudokuElement se1 = new SudokuElement();
        SudokuElement se2 = new SudokuElement();
        SudokuBoard sb = new SudokuBoard();
        se1.set(3, 1);
        se2.set(2, 1);

        assertFalse(se1.equals(null));
        assertTrue(se1.equals(se1));
        assertFalse(se1.equals(sb));
        assertFalse(se1.equals(se2));
    }

    @Test
    public void gettersTest() {
        SudokuRow se = new SudokuRow();
        se.getRow().toString();

        SudokuColumn sc = new SudokuColumn();
        sc.getColumn().toString();

        SudokuBox sb = new SudokuBox();
        sb.getBox().toString();
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
        SudokuBoard sb = new SudokuBoard();
        bss.solve(sb);
        SudokuElement se = new SudokuElement();

        //klonujemy dany objekt do se
        se = sb.getColumn(5).clone();

        assertTrue(se.equals(sb.getColumn(5)));

        //wyswietl sobie ze dziala
//        System.out.println("ORYGINAL;");
//        System.out.println(sb.getColumn(5).toString());
//        System.out.println("nie oryginal");
//        System.out.println(se.getTab().toString());
    }

    @Test
    public void serializableTest() throws FileNotFoundException, IOException, ClassNotFoundException {
        BacktrackingSudokuSolver bss = new BacktrackingSudokuSolver();
        SudokuBoard sb = new SudokuBoard();
        bss.solve(sb);
        SudokuElement se = new SudokuElement();
        assertTrue(sb.getBox(1, 1).verify());
        
        se = sb.getColumn(5);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"))) {
            outputStream.writeObject(se);
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"))) {

            assertEquals(se, (SudokuElement) inputStream.readObject());
            

        }
    }
}

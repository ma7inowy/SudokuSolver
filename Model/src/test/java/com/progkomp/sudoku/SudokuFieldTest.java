package com.progkomp.sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

public class SudokuFieldTest {

    @Test
    public void hashCodeAndToStringTest() {
        SudokuField sf1 = new SudokuField(2);
        SudokuField sf2 = new SudokuField(2);
        assertTrue(sf1.hashCode() == sf2.hashCode());
        sf2.setFieldValue(7);
        assertTrue(sf1.hashCode() != sf2.hashCode());
        assertTrue(sf1.toString() != sf2.toString());
    }

    @Test
    public void equalsTest() {
        SudokuField sf1 = new SudokuField(2);
        SudokuField sf2 = new SudokuField(2);
        SudokuBoard sb = new SudokuBoard();

        sf2.setFieldValue(7);
        assertFalse(sf1.equals(sf2));
        assertFalse(sf1.equals(null));
        assertTrue(sf1.equals(sf1));
        assertFalse(sf1.equals(sb));

    }

    @Test
    public void cloneableTest() throws CloneNotSupportedException {
        SudokuField sf1 = new SudokuField(2);
        SudokuField sf2 = sf1.clone();

        assertTrue(sf1.equals(sf2));
    }

    @Test
    public void serializableTest() throws FileNotFoundException, IOException, ClassNotFoundException {
        SudokuField sf1 = new SudokuField(2);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"))) {
            outputStream.writeObject(sf1);
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"))) {

            assertEquals(sf1, (SudokuField) inputStream.readObject());
        }

    }

}

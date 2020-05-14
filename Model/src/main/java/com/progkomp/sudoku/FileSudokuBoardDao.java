package com.progkomp.sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private String nazwa;
    private SudokuBoard sb;

    public FileSudokuBoardDao(final String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public SudokuBoard read() {
        sb = new SudokuBoard();
        BufferedReader fileReader = null;

        try (FileInputStream fileInputStream = new FileInputStream(nazwa)) {
            int b;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    b = fileInputStream.read();
                    sb.setForTests(b, i, j);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSudokuBoardDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSudokuBoardDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileSudokuBoardDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return sb;
    }

    @Override
    public boolean write(final SudokuBoard obj) {
        try {
            File file = new File(nazwa);
            try (FileWriter writer = new FileWriter(file)) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        writer.write(obj.get(i, j));
                    }

                }
            }
            return true;
        } catch (IOException e) {
        }
        return false;

    }

    public void setNazwa(final String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void close() throws Exception {
        System.out.println("Zamykanie pliku");
    }
}

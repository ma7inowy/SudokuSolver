package com.progkomp.sudoku;

public interface Dao<T> {

    T read();

    boolean write(T obj);
}

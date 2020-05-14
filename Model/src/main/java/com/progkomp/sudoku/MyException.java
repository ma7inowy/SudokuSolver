package com.progkomp.sudoku;

import java.io.IOException;

public class MyException extends IOException {

    public MyException(final String str) {
        super(str);
    }

    public MyException(final String str, final NullPointerException e) {
        super(str);
    }
}

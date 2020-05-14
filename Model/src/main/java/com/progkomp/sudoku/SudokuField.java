package com.progkomp.sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import static java.lang.Integer.compare;

public class SudokuField implements Comparable<SudokuField>, Cloneable, Serializable {

    private int value;

    public SudokuField() {
        this.value = 0;
    }

    public SudokuField(int n) {
        this.value = n;
    }

    public int getFieldValue() {
        return this.value;
    }

    public void setFieldValue(int x) {
        this.value = x;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Value", this.getFieldValue()).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);

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
        final SudokuField other = (SudokuField) obj;
        return Objects.equal(this.value, other.value);
    }
    
     @Override
    public int compareTo(final SudokuField t) {
        return compare(value, t.value);
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
    
    

   

   
}

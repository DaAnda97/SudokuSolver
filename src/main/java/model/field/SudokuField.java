package model.field;

import java.util.Objects;

public class SudokuField {
    private Values value;
    private final int row;
    private final int column;
    private final int section;


    public SudokuField(int value, int row, int column, int section) {
        this.value = Values.get(value);
        this.row = row;
        this.column = column;
        this.section = section;
    }

    public boolean isSet(){
        return !value.isEmpty();
    }

    public boolean isUnset(){
        return value.isEmpty();
    }

    public boolean isInRow(int row){
        return this.row == row;
    }

    public boolean isInColumn(int column){
        return this.column == column;
    }

    public boolean isInSection(int section){
        return this.section == section;
    }

    public Values getValue(){
        return value;
    }

    public void setValue(Values value) {
        this.value = value;
    }

    public void setEmpty(){
        setValue(Values.EMPTY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuField field = (SudokuField) o;
        return row == field.row &&
                column == field.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "[row=" + row +
                ", column=" + column +
                "] = " + value;
    }
}

package guess;

import model.field.SudokuField;
import model.field.Values;

import java.util.Objects;

public class Guess {
    private final SudokuField field;
    private final Values value;

    public Guess(SudokuField field, Values value) {
        this.field = field;
        this.value = value;
    }

    public SudokuField getField() {
        return field;
    }

    public Values getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guess guess = (Guess) o;
        return Objects.equals(field, guess.field) &&
                value == guess.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, value);
    }

    @Override
    public String toString() {
        return "Guess{"
                + field +
                ", guessValue=" + value +
                '}';
    }
}

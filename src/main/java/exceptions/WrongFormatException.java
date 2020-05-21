package exceptions;

public class WrongFormatException extends RuntimeException {

    public WrongFormatException() {
        super("A Sudoku has to have 9 x 9 fields.");
    }

}

package exceptions;

public class NoValidValueException extends RuntimeException {
    public NoValidValueException(int value) {
        super( value + " is not a valid value! You can set [1,2,3,4,5,6,7,8,9]. ");
    }
}

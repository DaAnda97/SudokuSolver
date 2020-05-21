package model.field;

import java.util.Arrays;
import java.util.HashSet;

public enum Values {

    EMPTY(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9);

    private int intValue;

    Values(int value) {
        this.intValue = value;
    }

    public static HashSet<Values> getAllNotEmptyValues() {
        HashSet<Values> allNotEmptyValues = new HashSet<>(Arrays.asList(ONE,TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE));
        return allNotEmptyValues;
    }

    public int toInt(){
        return this.intValue;
    }

    public boolean isEmpty(){
        return this.equals(Values.EMPTY);
    }

    public static Values get(int value){
        for(Values val: Values.values()) {
            if(val.intValue == value) {
                return val;
            }
        }
        throw new RuntimeException("There is no Value for " + value);
    }
    
}

package model.group;

import model.field.SudokuField;
import model.field.Values;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SudokuGroup {

    private final int index;
    private ArrayList<SudokuField> related;

    public SudokuGroup(int index, ArrayList<SudokuField> allFields) {
        this.index = index;
        this.related = findRelatedFields(allFields);
    }


    public Optional<HashSet<Values>> getSetValuesIfRelated(SudokuField field){
        if (related.contains(field)){
            HashSet<Values> availableValues = related.stream()
                    .filter(SudokuField::isSet)
                    .map(SudokuField::getValue)
                    .collect(Collectors.toCollection(HashSet::new));

            return Optional.of(availableValues);
        } else {
            return Optional.empty();
        }
    }

    public boolean isValid(){
        ArrayList<Values> setValues = related.stream()
                .filter(SudokuField::isSet)
                .map(SudokuField::getValue)
                .collect(Collectors.toCollection(ArrayList::new));

        HashSet<Values> distinctSetValues = new HashSet<>(setValues);

        return setValues.size() == distinctSetValues.size();
    }

    public abstract ArrayList<SudokuField> findRelatedFields(ArrayList<SudokuField> allFields);

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuGroup that = (SudokuGroup) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + index;
    }
}

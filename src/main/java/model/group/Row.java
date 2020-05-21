package model.group;

import model.field.SudokuField;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Row extends SudokuGroup {


    public Row(int index, ArrayList<SudokuField> allFields) {
        super(index, allFields);
    }

    @Override
    public ArrayList<SudokuField> findRelatedFields(ArrayList<SudokuField> allFields) {
        ArrayList<SudokuField> relatedFields = allFields.stream()
                .filter(field -> field.isInRow(this.getIndex()))
                .collect(Collectors.toCollection(ArrayList::new));

        if(relatedFields.size() != 9){
            throw new RuntimeException("Error in Field-Initialisation Row " + this.getIndex());
        }

        return relatedFields;
    }

}

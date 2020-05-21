package model.group;

import model.field.SudokuField;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Section extends SudokuGroup{


    public Section(int index, ArrayList<SudokuField> allFields) {
        super(index, allFields);
    }

    @Override
    public ArrayList<SudokuField> findRelatedFields(ArrayList<SudokuField> allFields) {
        ArrayList<SudokuField> relatedFields = allFields.stream()
                .filter(field -> field.isInSection(this.getIndex()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (relatedFields.size() != 9) {
            throw new RuntimeException("Error in Field-Initialisation Section " + this.getIndex());
        }

        return relatedFields;
    }
}

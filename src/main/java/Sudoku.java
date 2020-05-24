import guess.Guesser;
import model.AllFields;
import guess.Guess;
import model.field.SudokuField;
import model.field.Values;

import java.util.*;

public class Sudoku {

    private AllFields allFields;
    private Guesser guesser = null;


    public Sudoku(int[][] sudoku) {
        this.allFields = new AllFields(sudoku);
    }


    public int[][] solve() {

        while (!isSolved()) {
            Set<SudokuField> changedFields = setIfOnlyOnePossibility();

            if (changedFields.size() == 0) {

                if (guesser == null) {
                    guesser = new Guesser(allFields);
                }

                Guess guess = guesser.getNextGuess(allFields);
                allFields.setGuess(guess);
            }
        }

        if (guesser != null) {
            guesser.print();
        }

        return allFields.asMatrix();

    }


    private Set<SudokuField> setIfOnlyOnePossibility() {

        Set<SudokuField> changedFields = new HashSet<>();
        for (SudokuField field : allFields.getAll()) {

            List<Values> available = allFields.getAvailableValuesFor(field);
            if (available.size() == 1) {
                field.setValue(available.get(0));
                changedFields.add(field);
            }

        }

        // System.out.println("Solved fields: " + Arrays.toString(changedFields.toArray()));

        return changedFields;
    }

    private boolean isSolved() {
        return allFields.isEachFieldSet() && isValid();
    }

    private boolean isValid() {
        return allFields.isUniqueInAllGroups();
    }

}

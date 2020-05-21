package model;

import guess.Guess;
import model.field.SudokuField;
import model.field.Values;
import model.group.Column;
import model.group.Row;
import model.group.Section;
import model.group.SudokuGroup;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AllFields {

    private ArrayList<SudokuField> allFields;

    private ArrayList<Column> allColumns;
    private ArrayList<Row> allRows;
    private ArrayList<Section> allSections;

    public AllFields(int[][] sudoku) {
        this.allFields = initFields(sudoku);

        this.allColumns = initAllColumns();
        this.allRows = initAllRows();
        this.allSections = initAllSections();
    }


    public ArrayList<Guess> getGuessesOfBestField() {
        Optional<SudokuField> optionalField = allFields.stream()
                .filter(SudokuField::isUnset)
                .min(Comparator.comparing(field -> getAvailableValuesFor(field).size()));

        ArrayList<Guess> guessesOfField = new ArrayList<>();
        optionalField.ifPresent(
                sudokuField -> getAvailableValuesFor(sudokuField)
                        .forEach(value -> guessesOfField.add(new Guess(sudokuField, value)))
        );

        return guessesOfField;
    }


    public ArrayList<Guess> getBestGuesses() {
        Map<SudokuField, List<Values>> unsetFieldWithItsAvailableValues = new HashMap<>();
        allFields.stream()
                .filter(SudokuField::isUnset)
                .forEach(field -> unsetFieldWithItsAvailableValues.put(field, getAvailableValuesFor(field)));

        ArrayList<SudokuField> fieldsSortedByAvailableValuesSize = unsetFieldWithItsAvailableValues
                .keySet()
                .stream()
                .sorted(Comparator.comparing(field -> unsetFieldWithItsAvailableValues.get(field).size()))
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Guess> bestGuesses = new ArrayList<>();
        for (SudokuField field : fieldsSortedByAvailableValuesSize) {
            for (Values value : getAvailableValuesFor(field)) {
                Guess currentGuess = new Guess(field, value);
                bestGuesses.add(currentGuess);
            }
        }

        return bestGuesses;
    }


    public List<Values> getAvailableValuesFor(SudokuField field) {

        if (field.isSet()) {
            return new ArrayList<Values>();
        }

        Set<Values> setInRow = allRows.stream()
                .map(row -> row.getSetValuesIfRelated(field))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSingleton());

        Set<Values> setInColumn = allColumns.stream()
                .map(row -> row.getSetValuesIfRelated(field))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSingleton());

        Set<Values> setInSection = allSections.stream()
                .map(row -> row.getSetValuesIfRelated(field))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toSingleton());

        List<Values> availableValues = Values.getAllNotEmptyValues().stream()
                .filter(value -> !setInRow.contains(value))
                .filter(value -> !setInColumn.contains(value))
                .filter(value -> !setInSection.contains(value))
                .collect(Collectors.toCollection(ArrayList::new));

        return availableValues;
    }

    public boolean isEachFieldSet() {
        return allFields.stream().filter(SudokuField::isSet).count() == 81;
    }

    public boolean isUniqueInAllGroups() {
        boolean allRowsAreValid = allRows.stream().allMatch(SudokuGroup::isValid);
        boolean allColumnsAreValid = allColumns.stream().allMatch(SudokuGroup::isValid);
        boolean allSectionsAreValid = allSections.stream().allMatch(SudokuGroup::isValid);

        return allRowsAreValid & allColumnsAreValid & allSectionsAreValid;
    }

    public boolean isSolveable(){
        return getAllEmptyFields()
                .stream()
                .allMatch(sudokuField -> getAvailableValuesFor(sudokuField).size() > 0);
    }

    public ArrayList<SudokuField> getAll() {
        return this.allFields;
    }

    public Set<SudokuField> getAllEmptyFields() {
        return allFields.stream()
                .filter(SudokuField::isUnset)
                .collect(Collectors.toSet());
    }

    public void setGuess(Guess guess) {
        allFields.stream()
                .filter(field -> field.equals(guess.getField()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("The given field is not available in allFields"))
                .setValue(guess.getValue());
    }

    public int[][] asMatrix() {
        int[][] matrix = new int[9][9];
        for (int i = 0; i < 9; i++) {

            matrix[i] = new int[]{
                    allFields.get(i * 9).getValue().toInt(),
                    allFields.get(i * 9 + 1).getValue().toInt(),
                    allFields.get(i * 9 + 2).getValue().toInt(),
                    allFields.get(i * 9 + 3).getValue().toInt(),
                    allFields.get(i * 9 + 4).getValue().toInt(),
                    allFields.get(i * 9 + 5).getValue().toInt(),
                    allFields.get(i * 9 + 6).getValue().toInt(),
                    allFields.get(i * 9 + 7).getValue().toInt(),
                    allFields.get(i * 9 + 8).getValue().toInt()};
        }
        return matrix;
    }


    private ArrayList<SudokuField> initFields(int[][] sudoku) {

        ArrayList<SudokuField> fields = new ArrayList<>();

        for (int row = 0; row < sudoku.length; row++) {
            for (int column = 0; column < sudoku[row].length; column++) {
                fields.add(new SudokuField(
                        sudoku[row][column],
                        row,
                        column,
                        getSectionIndex(row, column)));
            }
        }

        return fields;
    }

    private ArrayList<Section> initAllSections() {

        ArrayList<Section> sections = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            sections.add(new Section(i, allFields));
        }
        return sections;

    }

    private ArrayList<Row> initAllRows() {
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            rows.add(new Row(i, allFields));
        }
        return rows;
    }

    private ArrayList<Column> initAllColumns() {

        ArrayList<Column> columns = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            columns.add(new Column(i, allFields));
        }
        return columns;
    }

    private int getSectionIndex(int row, int col) {

        if (row <= 2) {
            if (col <= 2) {
                return 0;
            } else if (col <= 5) {
                return 1;
            } else {
                return 2;
            }
        } else if (row <= 5) {
            if (col <= 2) {
                return 3;
            } else if (col <= 5) {
                return 4;
            } else {
                return 5;
            }
        } else {
            if (col <= 2) {
                return 6;
            } else if (col <= 5) {
                return 7;
            } else {
                return 8;
            }
        }
    }

    private static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("A field was initialized in two Groups");
                    }
                    return list.get(0);
                }
        );
    }

}

import guess.Guess;
import model.AllFields;

import model.field.SudokuField;
import model.field.Values;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

public class AllFieldsTest {

    public static Stream<AllFields> allSudokus() {
        return Sudokus.allSudokus();
    }

    @Test
    void shouldInitCorrectly(){

        //arrange
        int[][] sudoku = Sudokus.SUDOKU_2;
        AllFields allFields = new AllFields(sudoku);

        //act
        int[][] initializedFields = allFields.asMatrix();

        //assert
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                assertEquals(sudoku[row][col], initializedFields[row][col]);
            }
        }
    }

    @Test
    void shouldSetValue(){
        //arrange
        AllFields allFields = new AllFields(Sudokus.SUDOKU_4);
        Guess guess = new Guess(new SudokuField(0, 1,1,0), Values.THREE);

        //act
        allFields.setGuess(guess);

        //assert
        assertThat(allFields.getAll(), hasItem(new SudokuField(3, 1, 1, 0)));

    }

    @ParameterizedTest(name = "Sudoku{index} is valid")
    @MethodSource("allSudokus")
    void shouldBeValid(AllFields allFields){

        //act
        boolean isValid = allFields.isUniqueInAllGroups();

        //assert
        assertTrue(isValid);

    }


    @ParameterizedTest(name = "Sudoku{index} has unset fields")
    @MethodSource("allSudokus")
    public void shouldNotBeSolved(AllFields allFields){

        //act
        boolean isSolved = allFields.isEachFieldSet();

        //assert
        assertFalse(isSolved);

    }

}

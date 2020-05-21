import guess.GuessNode;
import guess.GuessNodeRoot;
import model.AllFields;
import model.field.SudokuField;
import model.field.Values;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuessNodeTest {

    @Test
    void shouldSetParentGuessWhileChildrenCreation(){
        //arrange
        AllFields allFields = new AllFields(Sudokus.SUDOKU_4);
        GuessNodeRoot root = new GuessNodeRoot(allFields);

        Set<GuessNode> children = root.getAllLeafs();
        GuessNode child1 = children.stream().findFirst().orElseThrow(() -> new RuntimeException("Empty Children"));

        //act
        child1.generateChildren(allFields);

        //assert
        SudokuField field = child1.getGuess().getField();
        field.setValue(child1.getGuess().getValue());

        int indexOfField = (allFields.getAll().indexOf(field));
        Values valueInField = allFields.getAll().get(indexOfField).getValue();

        assertEquals(child1.getGuess().getValue(), valueInField);
    }
}

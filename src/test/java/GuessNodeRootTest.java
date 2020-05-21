import guess.Guess;
import guess.GuessNode;
import guess.GuessNodeRoot;
import model.AllFields;
import model.field.SudokuField;
import model.field.Values;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class GuessNodeRootTest {

    public static Stream<AllFields> allSudokus() {
        return Sudokus.allSudokus();
    }

    @Test
    void shouldCreateChildrenOfOneChild(){
        //arrange
        AllFields allFields = new AllFields(Sudokus.SUDOKU_4);
        GuessNodeRoot rootNode = new GuessNodeRoot(allFields);

        //act
        Set<GuessNode> guessNodes = rootNode.getAllLeafs();
        for(GuessNode node : guessNodes){
            node.markAsTried();
        }

        rootNode.getNextGuessNode(allFields);

        //assert
        GuessNode child1 = new GuessNode(rootNode, new Guess(new SudokuField(0, 1,1,0), Values.THREE));
        GuessNode child1a = new GuessNode(child1, new Guess(new SudokuField(0, 8, 1, 0), Values.ONE));

        assertThat(rootNode.getAllLeafs(), hasSize(3));
        assertThat(rootNode.getAllLeafs(), hasItem(child1a));
    }

    @Test
    void shouldHaveTwoChildren(){
        //arrange
        GuessNodeRoot rootNode = new GuessNodeRoot(new AllFields(Sudokus.SUDOKU_4));

        //act
        Set<GuessNode> guessNodes = rootNode.getAllLeafs();

        //assert
        GuessNode child1 = new GuessNode(rootNode, new Guess(new SudokuField(0, 1,1,0), Values.ONE));
        GuessNode child2 = new GuessNode(rootNode, new Guess(new SudokuField(0, 1,1,0), Values.THREE));

        assertThat(guessNodes, hasSize(2));
        assertThat(guessNodes, containsInAnyOrder(child1, child2));

        for(GuessNode node : guessNodes){
            assertEquals(Values.EMPTY, node.getGuess().getField().getValue());
        }
    }


    @ParameterizedTest(name = "GuessRootNode initialized correctly for Sudoku{index}")
    @MethodSource("allSudokus")
    void shouldInitCorrectly(AllFields allFields){
        //arrange & act
        GuessNodeRoot rootNode = new GuessNodeRoot(allFields);

        //assert
        assertTrue(rootNode.isRoot());
        assertFalse(rootNode.isLeaf());
        assertFalse(rootNode.isWrong());
        assertNotEquals(0, rootNode.getAllLeafs().size());
    }


}

package guess;

import exceptions.UnsolveableException;
import model.AllFields;

public class Guesser {
    private final GuessNodeRoot rootNode;
    private GuessNode lastGuessNode;

    public Guesser(AllFields allFields) {
        this.rootNode = new GuessNodeRoot(allFields);
    }

    public Guess getNextGuess(AllFields allFields) throws UnsolveableException {
        if(!allFields.isSolveable() | !allFields.isUniqueInAllGroups()){
            lastGuessNode.markAsWrong();
            System.out.println("######### Marked as wrong: " + lastGuessNode);
        }

        GuessNode nextGuessNode = rootNode.getNextGuessNode(allFields);
        lastGuessNode = nextGuessNode;

        System.out.println("Guessed next: " + nextGuessNode);

        return nextGuessNode.getGuess();
    }

    public void print(){
        rootNode.print();
    }
}

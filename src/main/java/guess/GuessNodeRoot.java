package guess;

import exceptions.UnsolveableException;
import model.AllFields;
import model.field.SudokuField;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GuessNodeRoot extends GuessNode {

    private Set<SudokuField> initialEmptyFields;
    private Set<GuessNode> children;
    private Set<GuessNode> currentLeafs;

    public GuessNodeRoot(AllFields allFields) {
        super(null, null);

        this.initialEmptyFields = allFields.getAllEmptyFields();

        this.children = allFields.getGuessesOfBestField()
                .stream()
                .map(guess -> new GuessNode(this, guess))
                .collect(Collectors.toCollection(HashSet::new));

        this.currentLeafs = children;
    }

    private GuessNodeRoot(GuessNode parent, Guess guess) {
        super(parent, guess);
    }


    public GuessNode getNextGuessNode(AllFields allFields) {

        // always start with the board before first guess
        rollback(allFields);

        // check if there are valid leafs over all
        Set<GuessNode> allValidLeafs = getValidNodes(getAllLeafs());

        if (allValidLeafs.size() == 0) {
            throw new UnsolveableException("This Sudoku has no solution");
        }

        Set<GuessNode> untriedLeafs = getUntriedNodes(currentLeafs);

        GuessNode nextGuessNode;
        if (untriedLeafs.size() > 0) {
            nextGuessNode = untriedLeafs.stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("This set should have at least one Element"));

            nextGuessNode.setAllParentGuesses(allFields);

        } else {
            GuessNode nextValidLeaf = allValidLeafs.stream()
                    .min(Comparator.comparing(GuessNode::getDepth))
                    .orElseThrow(() -> new RuntimeException("This set should have at least one Element"));

            currentLeafs = nextValidLeaf.generateChildren(allFields);

            nextGuessNode = currentLeafs.stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("The next current leafs have been created empty"));
        }

        nextGuessNode.markAsTried();

        return nextGuessNode;

    }

    private Set<GuessNode> getUntriedNodes(Set<GuessNode> nodes) {
        return nodes
                .stream()
                .filter(guessNode -> !guessNode.notTried())
                .collect(Collectors.toSet());
    }

    private Set<GuessNode> getValidNodes(Set<GuessNode> nodes) {
        return nodes
                .stream()
                .filter(guessNode -> !guessNode.isWrong())
                .collect(Collectors.toSet());
    }

    public void rollback(AllFields allFields) {
        allFields.getAll()
                .stream()
                .filter(field -> initialEmptyFields.contains(field))
                .forEach(SudokuField::setEmpty);
    }

    public Set<GuessNode> getAllLeafs() {
        Set<GuessNode> allLeafs = new HashSet<>();

        for (GuessNode child : children) {
            allLeafs.addAll(child.getAllLeafs(allLeafs));
        }

        return allLeafs;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public void print(){
        for(GuessNode child : children){
            child.print();
        }
    }

}

package guess;

import model.AllFields;

import java.util.*;
import java.util.stream.Collectors;

public class GuessNode {
    private final GuessNode parent;
    private final Guess guess;

    private final int depth;
    private Set<GuessNode> children;

    private boolean isWrong = false;
    private boolean wasTried = false;

    public GuessNode(GuessNode parent, Guess guess) {
        this.parent = parent;
        this.guess = guess;
        this.depth = setDepth(parent);

        this.children = new HashSet<>();
    }

    public Guess getGuess() {
        return this.guess;
    }

    public Set<GuessNode> generateChildren(AllFields allFields) {
        if (isLeaf()) {

            List<Guess> guesses = getAllAffectedGuesses();
            guesses.forEach(allFields::setGuess);

            this.children = allFields.getGuessesOfBestField()
                    .stream()
                    .map(guess -> new GuessNode(this, guess))
                    .collect(Collectors.toSet());

            return children;
        } else {
            throw new RuntimeException("You can't generate children if there are already some!");
        }

    }

    public ArrayList<Guess> getAllAffectedGuesses() {

        ArrayList<Guess> allParentGuesses;

        if (isRoot()){
            allParentGuesses = new ArrayList<>();
        } else {
            allParentGuesses = parent.getAllAffectedGuesses();
            allParentGuesses.add(this.guess);
        }

        return allParentGuesses;
    }


    public Set<GuessNode> getAllLeafs(Set<GuessNode> allLeafs) {

        if (isLeaf()) {
            allLeafs.add(this);
        } else {

            for (GuessNode child : children) {
                allLeafs.addAll(child.getAllLeafs(allLeafs));
            }

        }
        return allLeafs;

    }

    public boolean isRoot() {
        return false;
    }

    public boolean isLeaf() {
        return !isWrong() & children.size() == 0;
    }

    public void markAsWrong() {
        this.isWrong = true;
    }

    public void markAsTried() {
        this.wasTried = true;
    }

    public boolean notTried() {
        return this.wasTried;
    }

    public boolean isWrong() {
        return this.isWrong;
    }

    private int setDepth(GuessNode parent) {
        if (parent == null) {
            return 0;
        } else {
            return parent.getDepth() + 1;
        }
    }

    public int getDepth() {
        return this.depth;
    }


    @Override
    public String toString() {
        return "GuessNode ["
                + depth
                + "]: "
                + Arrays.toString(getAllAffectedGuesses().toArray());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuessNode guessNode = (GuessNode) o;
        return Objects.equals(parent, guessNode.parent) &&
                Objects.equals(guess, guessNode.guess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, guess);
    }

    protected void setAllParentGuesses(AllFields allFields) {
        List<Guess> allAffectedGuesses = getAllAffectedGuesses();
        allAffectedGuesses.remove(this.guess);
        allAffectedGuesses.forEach(allFields::setGuess);
    }

    public void print(){
        String spaces = "";
        for(int i = 0; i<=depth; i++){
            spaces = spaces + "   ";
        }
        System.out.println(spaces + guess.toString() + " wasTried: " + wasTried + ", isWrong: " + isWrong);

        for(GuessNode child : children){
            child.print();
        }
    }
}

import org.junit.jupiter.api.Test;

public class SolverTest {

    @Test
    public void shouldSolveSudoku1(){
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_1);
        solver.getSolution();
    }

    @Test
    public void shouldSolveSudoku2(){
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_2);
        solver.getSolution();
    }

    @Test
    public void shouldSolveSudoku3(){
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_3);
        solver.getSolution();
    }

    @Test
    public void shouldSolveSudoku4(){
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_4);
        solver.getSolution();
    }

    @Test
    public void shouldSolveSudoku5(){
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_5);
        solver.getSolution();
    }





}
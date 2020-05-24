import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SolverTest {

    @Test
    public void shouldSolveSudoku1() {
        int[][] solution = {
                {6, 3, 9, 7, 4, 8, 5, 1, 2},
                {8, 4, 7, 5, 1, 2, 6, 9, 3},
                {2, 1, 5, 6, 3, 9, 8, 4, 7},
                {1, 5, 4, 2, 6, 7, 3, 8, 9},
                {3, 8, 2, 1, 9, 4, 7, 6, 5},
                {9, 7, 6, 8, 5, 3, 1, 2, 4},
                {5, 9, 8, 3, 2, 6, 4, 7, 1},
                {7, 2, 1, 4, 8, 5, 9, 3, 6},
                {4, 6, 3, 9, 7, 1, 2, 5, 8}
        };

        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_1);
        int[][] result = solver.getSolution();

        assertArrayEquals(solution, result);
    }

    @Test
    public void shouldSolveSudoku2() {
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_2);
        solver.getSolution();
    }

    @Test
    public void shouldSolveSudoku3() {
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_3);
        solver.getSolution();
    }

    @Test
    public void shouldSolveSudoku4() {
        int[][] solution = {
                {8, 6, 3, 9, 2, 5, 7, 4, 1},
                {4, 1, 2, 7, 8, 6, 3, 5, 9},
                {7, 5, 9, 4, 1, 3, 2, 8, 6},
                {9, 7, 1, 2, 6, 4, 8, 3, 5},
                {3, 4, 6, 8, 5, 7, 9, 1, 2},
                {2, 8, 5, 3, 9, 1, 4, 6, 7},
                {1, 9, 8, 6, 3, 2, 5, 7, 4},
                {5, 2, 4, 1, 7, 8, 6, 9, 3},
                {6, 3, 7, 5, 4, 9, 1, 2, 8}
        };

        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_4);
        int[][] result = solver.getSolution();

        assertArrayEquals(solution, result);
    }

    @Test
    public void shouldSolveSudoku5() {
        SudokuSolver solver = new SudokuSolver(Sudokus.SUDOKU_5);
        solver.getSolution();
    }


}
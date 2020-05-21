import exceptions.WrongFormatException;

public class SudokuSolver {

    Sudoku sudoku;

    public SudokuSolver(int[][] sudoku) {

        if (isValidInput(sudoku)){
            this.sudoku = new Sudoku(sudoku);
        } else {
            throw new WrongFormatException();
        }

    }

    public int[][] getSolution() {
        int[][] solution = sudoku.solve();
        print(solution);

        return solution;
    }

    public static void print(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print((matrix[i][j] == 0 ? "_" : matrix[i][j]) + " " + (j == 2 | j == 5 ? "| " : ""));
            }
            System.out.println();
            if (i == 2 | i == 5){
                System.out.println("---------------------");
            }
        }

        System.out.println();
        System.out.println();
    }

    private boolean isValidInput(int[][] sudoku){

        // check board length
        if(sudoku.length != 9 && sudoku[0].length != 9) {
            return false;
        }

        // check values
        for(int i = 0; i < sudoku.length; i++){
            for (int j = 0; j < sudoku[0].length; j++){
                if (sudoku[i][j] < 0) {return false;}
                if (sudoku[i][j] > 9) {return false;}
            }
        }

        return true;

    }

}

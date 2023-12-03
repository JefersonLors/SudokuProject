package sudoku;

import sudoku.Utils.Utils;
import sudoku.enums.GameLevel;

public class Puzzle {
    //Tabuleiro do Sudoku
    int grid[][];
    int sizeSquare;
    boolean isGiven[][] = new boolean [SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle(){
        super();
        Double squareSizeDouble = Math.sqrt(SudokuConstants.GRID_SIZE);
        this.sizeSquare = squareSizeDouble.intValue();
    }

    public void newPuzzle(GameLevel level){
        this.grid = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        this.fillDiagonalBoxes();
        this.fillRemainingBoxes(0, sizeSquare);
        this.generateIsGiven(level);
    }
    private void fillDiagonalBoxes()
    {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i += sizeSquare){
            for (int row = 0; row < sizeSquare; row++) {
                for (int col = 0; col < sizeSquare; col++) {
                    int num;
                    do {
                        num = Utils.randomNumberBetween(1, SudokuConstants.GRID_SIZE);
                    } while (!checkUnusedInBox(i, i, num));
                    grid[i + row][i + col] = num;
                }
            }
        }
    }

    private boolean checkUnusedInBox(int rowStart, int colStart, int num)
    {
        for (int row = 0; row < sizeSquare; row++){
            for (int col = 0; col < sizeSquare; col++){
                if (grid[rowStart+row][colStart+col]==num){
                    return false;
                }
            }
        }
        return true;
    }
    private boolean CheckIfSafe(int row, int col, int num)
    {
        return (checkUnusedInRow(row, num) &&
                checkUnusedInCol(col, num) &&
                checkUnusedInBox(row-row % sizeSquare, col-col% sizeSquare, num));
    }
    private boolean checkUnusedInRow(int row, int num)
    {
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++)
            if (grid[row][col] == num)
                return false;
        return true;
    }
    private boolean checkUnusedInCol(int col, int num)
    {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++)
            if (grid[row][col] == num)
                return false;
        return true;
    }
    private boolean fillRemainingBoxes(int row, int col) {
        if (col >= SudokuConstants.GRID_SIZE && ++row < SudokuConstants.GRID_SIZE){
            col = 0;
        }
        if (row >= SudokuConstants.GRID_SIZE && col >= SudokuConstants.GRID_SIZE){
            return true;
        }
        if (row < sizeSquare && col < sizeSquare){
            col = sizeSquare;
        } else if (row < SudokuConstants.GRID_SIZE - sizeSquare && col == (int) (row / sizeSquare) * sizeSquare){
            col += sizeSquare;
        } else if (row >= SudokuConstants.GRID_SIZE - sizeSquare && col == SudokuConstants.GRID_SIZE - sizeSquare) {
            row++;
            col = 0;
            if (row >= SudokuConstants.GRID_SIZE)
                return true;
        }
        for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
            if (CheckIfSafe(row, col, num)) {
                grid[row][col] = num;
                if (fillRemainingBoxes(row, col + 1))
                    return true;
                grid[row][col] = 0;
            }
        }
        return false;
    }
    private void generateIsGiven( GameLevel level ){
        boolean hardcodedIsGiven[][] = {{true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true},
                                        {true, true, true, true, true, true, true, true, true}};

        this.generateToGuess( hardcodedIsGiven, level.getValue() );

        for( int row = 0; row < SudokuConstants.GRID_SIZE; row++){
            for( int col = 0; col < SudokuConstants.GRID_SIZE; col++){
                this.isGiven[row][col] = hardcodedIsGiven[row][col];
            }
        }
    }
    private void generateToGuess ( boolean[][] hardCodIsGiven, int qty){
        if( qty > 0 ){
            int row = Utils.randomNumberBetween( 0, 8);
            int col = Utils.randomNumberBetween( 0, 8);
            hardCodIsGiven[row][col] = false;
            generateToGuess(hardCodIsGiven, qty-1);
        }
    }
}

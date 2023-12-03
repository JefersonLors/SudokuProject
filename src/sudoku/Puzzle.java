package sudoku;

import sudoku.Utils.Utils;
import sudoku.enums.GameLevel;

public class Puzzle {
    //Tabuleiro do Sudoku
    int grid[][] = new int [SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    //Marca quais das células são dadas
    boolean isGiven[][] = new boolean [SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    public Puzzle(){
        super();
    }

    public void newPuzzle(GameLevel level){
        var newGrid = this.generateGrid();

        for( int row = 0; row < SudokuConstants.GRID_SIZE; row++){
            for( int col = 0; col < SudokuConstants.GRID_SIZE; col++){
                this.grid[row][col] = newGrid[row][col];
            }
        }

        var newIsGiven = this.generateIsGiven(level);

        for( int row = 0; row < SudokuConstants.GRID_SIZE; row++){
            for( int col = 0; col < SudokuConstants.GRID_SIZE; col++){
                this.isGiven[row][col] = newIsGiven[row][col];
            }
        }
    }
    private int[][] generateGrid(){
        int[][] hardcodedNumbers = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                                    {6, 7, 2, 1, 9, 5, 3, 4, 8},
                                    {1, 9, 8, 3, 4, 2, 5, 6, 7},
                                    {8, 5, 9, 7, 6, 1, 4, 2, 3},
                                    {4, 2, 6, 8, 5, 3, 7, 9, 1},
                                    {7, 1, 3, 9, 2, 4, 8, 5, 6},
                                    {9, 6, 1, 5, 3, 7, 2, 8, 4},
                                    {2, 8, 7, 4, 1, 9, 6, 3, 5},
                                    {3, 4, 5, 2, 8, 6, 1, 7, 9}};

//        int hardcodedNumbers[][] = new int[9][9];
//
//        Random random = new Random();
//
//        for( int pos = 0; pos < SudokuConstants.GRID_SIZE; pos++){
//            hardcodedNumbers[pos][pos] = random.nextInt(9) + 1;
//        }
//
//        for( int row = 0; row < SudokuConstants.GRID_SIZE; row++){
//            for( int col = 0; col < SudokuConstants.GRID_SIZE; col++){
//                System.out.print( (col == 8 ? hardcodedNumbers[row][col] + "\n" : hardcodedNumbers[row][col] + "  "));
//            }
//        }

        return hardcodedNumbers;
    }
    private boolean[][] generateIsGiven( GameLevel level){
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
        return hardcodedIsGiven;
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

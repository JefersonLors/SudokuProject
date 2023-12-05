package sudoku;

import sudoku.enums.CellStatus;
import sudoku.enums.GameLevel;
import sudoku.enums.StatusGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PuzzleBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Cell[][] cells;
    private Puzzle puzzle;
    private ArrayList<Cell> correctGuesses;
    private StatusGame statusGame;
    public PuzzleBoardPanel(){
        super();
        this.correctGuesses = new ArrayList<Cell>();
        this.cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        this.puzzle = new Puzzle();
        this.boardConfig();
    }
    private void boardConfig(){
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));
        super.setPreferredSize(new Dimension(SudokuConstants.BOARD_WIDTH, SudokuConstants.BOARD_HEIGHT));
        super.setVisible(true);
    }
    private void createCellsGrid(){
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if( cells[row][col] != null ) {super.remove(cells[row][col]);}
                this.cells[row][col] = new Cell(row, col);
                this.cells[row][col].setEditable(false);
                super.add(cells[row][col]);
            }
        }
    }
    private void setCellsGridValues(){
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                this.cells[row][col].newGame(puzzle.grid[row][col], puzzle.isGiven[row][col]);
                if( this.cells[row][col].isEditable() ){
                    this.cells[row][col].addKeyListener(new CellInputListener());
                }
            }
        }
    }
    public void newGame(GameLevel level){
        this.correctGuesses.clear();
        this.createCellsGrid();

        if( level != GameLevel.NON_SELECTED){
            this.puzzle.newPuzzle(level);
            this.setCellsGridValues();
            this.statusGame = StatusGame.PLAYING;
        }
        this.paintSubGrid();
    }
    public boolean haveProgres(){
        if( this.correctGuesses.size() != 0){
            return true;
        }
        return false;
    }
    public StatusGame setPause(){
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if( !this.puzzle.isGiven[row][col] && this.statusGame == StatusGame.PLAYING){
                    this.cells[row][col].setEditable(false);
                }else if( !this.puzzle.isGiven[row][col] && this.statusGame == StatusGame.PAUSED){
                    this.cells[row][col].setEditable(true);
                }
            }
        }
        this.statusGame = ( this.statusGame == StatusGame.PLAYING ? StatusGame.PAUSED : StatusGame.PLAYING);
        return this.statusGame;
    }
    public StatusGame getStatusGame(){
        return this.statusGame;
    }
    public void restartGame(){
        this.createCellsGrid();
        this.setCellsGridValues();
        this.paintSubGrid();
        this.correctGuesses.clear();
    }
    private void paintSubGrid(){
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE ; col++) {
                this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                if( row == 0 ){
                    if( col == 0){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.BLACK));
                    } else if( (col + 1) % 3 != 0 ){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.BLACK));
                    }
                }
                if( col == 0 ){
                    if( row == 0){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.BLACK));
                    } else if( (row + 1) % 3 != 0 ){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.BLACK));
                    }
                }
                if( row == SudokuConstants.GRID_SIZE-1 && col == 0){
                    this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.BLACK));
                }
                if( row == SudokuConstants.GRID_SIZE-1 && col == SudokuConstants.GRID_SIZE-1){
                    this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
                }
                if( row == 0 && col == SudokuConstants.GRID_SIZE-1){
                    this.cells[row][col].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.BLACK));
                }
                if( (row + 1) % 3 == 0 ){
                    if( col == 0 ){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.BLACK));
                    }else if( ( col + 1 ) % 3 == 0 ){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
                    }else{
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
                    }
                }
                if( (col + 1) % 3 == 0 ){
                    if( row == 0 ){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.BLACK));
                    }else if( ( row + 1 ) % 3 == 0 ){
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
                    }else{
                        this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
                    }
                }
            }
        }
    }
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS ||
                        cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }
    private class CellInputListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            this.keyPressed(e);
        }
        @Override
        public void keyPressed(KeyEvent e) {
            Cell sourceCell = (Cell) e.getSource();

            try{
                int numberIn = Integer.parseInt(sourceCell.getText());

                if( numberIn >= 1 && numberIn <=9 ){
                    if( numberIn == sourceCell.number ){
                        sourceCell.status = CellStatus.CORRECT_GUESS;
                        PuzzleBoardPanel.this.correctGuesses.add(sourceCell);
                    }else{
                        sourceCell.status = CellStatus.WRONG_GUESS;
                        PuzzleBoardPanel.this.correctGuesses.remove(sourceCell);
                    }
                }else{
                    sourceCell.status = CellStatus.TO_GUESS;
                    sourceCell.setText("");
                }
            }catch( Exception ex ){
                sourceCell.status = CellStatus.TO_GUESS;
                sourceCell.setText("");
            } finally {
                sourceCell.paint();
            }
            if( PuzzleBoardPanel.this.isSolved() ){
                JOptionPane.showMessageDialog(PuzzleBoardPanel.this, "Você arrasou!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
                PuzzleBoardPanel.this.correctGuesses.stream().forEach(cell -> cell.setEditable(false));
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            this.keyPressed(e);
        }
    }
}

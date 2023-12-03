package sudoku;

import sudoku.enums.CellStatus;
import sudoku.enums.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PuzzleBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Cell[][] cells;
    private Puzzle puzzle;
    private ArrayList<Cell> correctGuesses;
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
                    this.cells[row][col].addActionListener(new CellInputListener());
                }
            }
        }
    }
    public void newGame(GameLevel level){
        this.createCellsGrid();

        if( level != GameLevel.NON_SELECTED){
            this.puzzle.newPuzzle(level);
            this.setCellsGridValues();
        }
        this.paintSubGrid();
    }
    public boolean haveProgres(){
        if( this.correctGuesses.size() != 0){
            return true;
        }
        return false;
    }
    public void restartGame(){
        this.createCellsGrid();
        this.setCellsGridValues();
        this.paintSubGrid();
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
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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
    }

}

package sudoku;

import sudoku.enums.CellStatus;
import sudoku.enums.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PuzzleBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Cell[][] cells;
    private Puzzle puzzle;

    public PuzzleBoardPanel(){
        super();
        this.cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        this.puzzle = new Puzzle();
        this.boardConfig();
    }
    private void boardConfig(){
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));
        super.setPreferredSize(new Dimension(SudokuConstants.BOARD_WIDTH, SudokuConstants.BOARD_HEIGHT));
        super.setVisible(true);
    }
    public void newGame(){
        this.puzzle.newPuzzle(GameLevel.EASY);

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                this.cells[row][col] = new Cell(row, col);
                this.cells[row][col].newGame(puzzle.grid[row][col], puzzle.isGiven[row][col]);
                this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                if( this.cells[row][col].isEditable() ){
                    this.cells[row][col].addActionListener(new CellInputListener());
                }
                super.add(cells[row][col]);
            }
        }
        this.paintSubGrid();
    }
    private void paintSubGrid(){
//        for (int row = 2; row < SudokuConstants.GRID_SIZE; row += 3) {
//            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
//                this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
//            }
//        }
//        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
//            for (int col = 2; col < SudokuConstants.GRID_SIZE ; col += 3) {
//                if( (row + 1) % 3 == 0){
//                    this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
//                }else{
//                    this.cells[row][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
//                }
//            }
//        }
//
//        for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
//            if( (col + 1) % 3 == 0){
//                this.cells[0][col].setBorder(BorderFactory.createMatteBorder(3, 1, 3, 3, Color.BLACK));
//            }else{
//                this.cells[0][col].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
//            }
//        }

        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE ; col++) {
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
                    }else{
                        sourceCell.status = CellStatus.WRONG_GUESS;
                    }
                }else{
                    sourceCell.setText("");
                }
            }catch( Exception ex ){
                sourceCell.setText("");
            } finally {
                sourceCell.paint();
            }
            if( PuzzleBoardPanel.this.isSolved() ){
                JOptionPane.showMessageDialog(PuzzleBoardPanel.this, "Você ganhou!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


}

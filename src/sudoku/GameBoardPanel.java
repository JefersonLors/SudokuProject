package sudoku;

import sudoku.enums.CellStatus;
import sudoku.enums.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private Cell[][] cells;
    private Puzzle puzzle;

    public GameBoardPanel(){
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
                if( this.cells[row][col].isEditable() ){
                    this.cells[row][col].addActionListener(new CellInputListener());
                    this.cells[row][col].isFocusable();
                }
                super.add(cells[row][col]);
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
        }
    }
}

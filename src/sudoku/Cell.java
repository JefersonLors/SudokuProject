package sudoku;

import sudoku.enums.CellStatus;

import javax.swing.*;
import java.awt.*;

public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;

    //Posição da céula no eixo x e y e valor
    int row;
    int col;
    int number;
    CellStatus status;

    public Cell( int row, int col){
        super();

        //Seta posição da célula
        this.row = row;
        this.col = col;

        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(SudokuConstants.FONT_NUMBERS);
    }

    //Configura a célula quando o jogo inicia
    public void newGame( int number, boolean isGiven ){
        this.number = number;
        this.status = (isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS);
        this.paint();
    }

    //Seta a cor da célula conforme o status dela
    public void paint(){
        if( this.status == CellStatus.GIVEN){
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(SudokuConstants.BG_GIVEN);
            super.setForeground(SudokuConstants.FG_GIVEN);
        }else if( this.status == CellStatus.TO_GUESS){
            super.setText("");
            super.setEditable(true);
            super.setBackground(SudokuConstants.BG_TO_GUESS);
            super.setForeground(SudokuConstants.FG_NOT_GIVEN);
        }else if( this.status == CellStatus.CORRECT_GUESS){
            super.setBackground(SudokuConstants.BG_CORRECT_GUESS);
        }else if( this.status == CellStatus.WRONG_GUESS){
            super.setBackground(SudokuConstants.BG_WRONG_GUESS);
        }
    }
}

package sudoku;

import sudoku.enums.CellStatus;

import javax.swing.*;

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
    public void setColorCell(int number ){
        switch (number){
            case 1: this.setBackground(SudokuConstants.BG_NUMBER_ONE);
                break;
            case 2: this.setBackground(SudokuConstants.BG_NUMBER_TWO);
                break;
            case 3: this.setBackground(SudokuConstants.BG_NUMBER_TREE);
                break;
            case 4: this.setBackground(SudokuConstants.BG_NUMBER_FOR);
                break;
            case 5: this.setBackground(SudokuConstants.BG_NUMBER_FIVE);
                break;
            case 6: this.setBackground(SudokuConstants.BG_NUMBER_SIX);
                break;
            case 7: this.setBackground(SudokuConstants.BG_NUMBER_SEVEN);
                break;
            case 8: this.setBackground(SudokuConstants.BG_NUMBER_EIGTH);
                break;
            case 9: this.setBackground(SudokuConstants.BG_NUMBER_NINE);
                break;
        }
    }

    //Configura a célula quando o jogo inicia
    public void newGame( int number, boolean isGiven ){
        this.number = number;
        this.status = (isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS);
        this.paint();
    }

    //Seta a cor da célula conforme o status dela
    public void paint(){
        super.setBackground(SudokuConstants.BG_TO_GUESS);
        this.setForeground(SudokuConstants.FG_GIVEN);

        if( this.status == CellStatus.GIVEN){
            super.setText(number + "");
            super.setEditable(false);
            this.setColorCell(this.number);
        }else if( this.status == CellStatus.TO_GUESS){
            super.setText("");
            super.setEditable(true);
        }else if( this.status == CellStatus.CORRECT_GUESS){
            //this.setColorCell(this.number);
            this.setForeground(SudokuConstants.FG_CORRECT_GUESS);

        }else if( this.status == CellStatus.WRONG_GUESS){
            super.setForeground(SudokuConstants.FG_WRONG_GUESS);
        }
    }
}

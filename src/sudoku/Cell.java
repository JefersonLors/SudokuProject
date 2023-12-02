package sudoku;

import sudoku.enums.CellStatus;

import javax.swing.*;
import java.awt.*;

public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;

    //Estilização das células
    public static final Color BG_GIVEN = new Color(240, 240, 240);
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.GRAY;
    public static final Color BG_TO_GUESS = Color.YELLOW;
    public static final Color BG_CORRECT_GUESS = new Color(0,216, 0);
    public static final Color BG_WRONG_GUESS = new Color(216, 0, 0);
    public static final Font FONT_NUMBERS = new Font("OCR A Extend", Font.PLAIN, 28);

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
        super.setFont(this.FONT_NUMBERS);
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
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        }else if( this.status == CellStatus.TO_GUESS){
            super.setText("");
            super.setEditable(true);
            super.setBackground(this.BG_TO_GUESS);
            super.setForeground(this.FG_NOT_GIVEN);
        }else if( this.status == CellStatus.CORRECT_GUESS){
            super.setBackground(this.BG_CORRECT_GUESS);
        }else if( this.status == CellStatus.WRONG_GUESS){
            super.setBackground(this.BG_WRONG_GUESS);
        }
    }
}

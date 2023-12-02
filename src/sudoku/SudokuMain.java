package sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;
    private GameBoardPanel gameBoard;
    public static void main( String[] args ){
        EventQueue.invokeLater(() -> {
            try {
                new SudokuMain();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public SudokuMain(){
        super();
        this.gameBoard = new GameBoardPanel();
        this.newGame();
    }
    public void newGame(){
        this.gameBoard.newGame();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(700, 200); //Posiciona o frame na tela
        this.setTitle("Sudoku Game");
        //this.setSize(SudokuConstants.BOARD_WIDTH, SudokuConstants.BOARD_HEIGHT); //seta as dimensões da tela
        this.getContentPane().add(gameBoard, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }
}

package sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;
    private PuzzleBoardPanel gameBoard;
    private JToolBar toolBar;
    private JButton restartButton;
    private JButton newGameButton;
    private JRadioButton easyLevel;
    private JRadioButton mediumLevel;
    private JRadioButton hardLevel;
    private ButtonGroup levels;
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
        this.inicializeComponents();
        this.newGame();
    }
    private void inicializeComponents(){
        this.gameBoard = new PuzzleBoardPanel();
        this.toolBar = new JToolBar();
        this.restartButton = new JButton("   Restart    ");
        this.newGameButton = new JButton("   New Game   ");
        this.easyLevel = new JRadioButton("easy");
        this.mediumLevel = new JRadioButton("medium");
        this.hardLevel = new JRadioButton("hard");
        this.levels = new ButtonGroup();
    }
    public void newGame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(700, 200); //Posiciona o frame na tela
        this.setTitle("Sudoku Game");
        this.setToolBar();

        this.gameBoard.newGame();
        this.getContentPane().add(this.gameBoard, BorderLayout.CENTER);
        this.pack();
//        this.setSize(SudokuConstants.BOARD_WIDTH, SudokuConstants.BOARD_HEIGHT); //seta as dimensões da tela
        this.setVisible(true);

    }
    private void setToolBar(){
        this.toolBar.setVisible(true);

        this.toolBar.add(this.restartButton);
        this.toolBar.add(Box.createHorizontalStrut(50));
        this.toolBar.add(this.newGameButton);
        this.toolBar.add(Box.createHorizontalStrut(25));

        this.levels.add(this.easyLevel);
        this.levels.add(this.mediumLevel);
        this.levels.add(this.hardLevel);

        this.toolBar.add(this.easyLevel);
        this.toolBar.add(this.mediumLevel);

        this.toolBar.add(this.hardLevel);

        this.getContentPane().add(this.toolBar, BorderLayout.NORTH);
    }
}

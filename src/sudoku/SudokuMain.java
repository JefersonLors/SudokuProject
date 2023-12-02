package sudoku;

import sudoku.enums.GameLevel;

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
    private ButtonGroup levelsGroup;
    private GameLevel gameLevel = GameLevel.NON_SELECTED;
    private GameLevel lastGameLevel = GameLevel.NON_SELECTED;

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
        this.restartButton.addActionListener(e -> {
            this.getContentPane().remove(this.gameBoard);
            this.gameBoard = new PuzzleBoardPanel();
            this.gameBoard.newGame(lastGameLevel);
            this.getContentPane().add(this.gameBoard);
            revalidate();
        });

        this.newGameButton = new JButton("   New Game   ");
        this.newGameButton.addActionListener(e->{
            if( gameLevel == GameLevel.NON_SELECTED ){
                JOptionPane.showMessageDialog(this, "Por favor, selecione um nível de jogo", "Aviso", JOptionPane.ERROR_MESSAGE);
            }else{
                this.getContentPane().remove(this.gameBoard);
                this.gameBoard = new PuzzleBoardPanel();
                this.gameBoard.newGame(gameLevel);
                this.getContentPane().add(this.gameBoard);
                revalidate();
                lastGameLevel = gameLevel;
            }
        });

        this.easyLevel = new JRadioButton("easy");
        this.easyLevel.addActionListener(e->{
            gameLevel = GameLevel.EASY;
            System.out.println(gameLevel);
        });

        this.mediumLevel = new JRadioButton("medium");
        this.mediumLevel.addActionListener(e->{
            gameLevel = GameLevel.MEDIUM;
            System.out.println(gameLevel);
        });

        this.hardLevel = new JRadioButton("hard");
        this.hardLevel.addActionListener(e->{
            gameLevel = GameLevel.HARD;
            System.out.println(gameLevel);
        });

        this.levelsGroup = new ButtonGroup();
    }
    public void newGame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(700, 200); //Posiciona o frame na tela
        this.setTitle("Sudoku Game");
        this.setToolBar();
        this.gameBoard.newGame(gameLevel);
        this.getContentPane().add(this.gameBoard, BorderLayout.CENTER);
        this.pack();
//        this.setSize(SudokuConstants.BOARD_WIDTH, SudokuConstants.BOARD_HEIGHT); //seta as dimensões da tela
        this.setVisible(true);

    }
    private void setToolBar(){
        //ADICIONAR UMA LABEL COM O NIVEL ATUAL
        this.toolBar.setVisible(true);

        this.toolBar.add(this.restartButton);
        this.toolBar.add(Box.createHorizontalStrut(50));
        this.toolBar.add(this.newGameButton);
        this.toolBar.add(Box.createHorizontalStrut(25));

        this.levelsGroup.add(this.easyLevel);
        this.levelsGroup.add(this.mediumLevel);
        this.levelsGroup.add(this.hardLevel);

        this.toolBar.add(this.easyLevel);
        this.toolBar.add(this.mediumLevel);
        this.toolBar.add(this.hardLevel);

        this.getContentPane().add(this.toolBar, BorderLayout.NORTH);
    }
}

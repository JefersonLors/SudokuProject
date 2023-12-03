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
    private JTextField screenCurrLevel;
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
            if( gameLevel != GameLevel.NON_SELECTED ){
//                this.getContentPane().remove(this.gameBoard);
//                this.gameBoard.restartGame();
//                this.getContentPane().add(this.gameBoard);
//                revalidate();
            }
        });

        this.newGameButton = new JButton("   New Game   ");
        this.newGameButton.addActionListener(e -> {
            if( gameLevel == GameLevel.NON_SELECTED ){
                JOptionPane.showMessageDialog(this, "Por favor, selecione um nível de jogo", "Aviso", JOptionPane.ERROR_MESSAGE);
            }else{
                if( this.gameBoard.haveProgres() ){
                    int confirmation = JOptionPane.showConfirmDialog(this,
                                    "Deseja abandonar o jogo atual? Todo o seu progresso será perdido.",
                                    "Aviso", JOptionPane.YES_NO_OPTION);

                    if( confirmation == 0){
                        this.getContentPane().remove(this.gameBoard);
                        this.gameBoard = new PuzzleBoardPanel();
                        this.gameBoard.newGame(gameLevel);
                        this.screenCurrLevel.setText("Level: " + (gameLevel == GameLevel.NON_SELECTED ? "" : gameLevel));
                        this.getContentPane().add(this.gameBoard);
                        revalidate();
                        lastGameLevel = gameLevel;
                    }
                }else{
                    this.getContentPane().remove(this.gameBoard);
                    this.gameBoard = new PuzzleBoardPanel();
                    this.gameBoard.newGame(gameLevel);
                    this.screenCurrLevel.setText("Level: " + (gameLevel == GameLevel.NON_SELECTED ? "" : gameLevel));
                    this.getContentPane().add(this.gameBoard);
                    revalidate();
                    lastGameLevel = gameLevel;
                }
            }
        });

        this.easyLevel = new JRadioButton("easy");
        this.easyLevel.addActionListener(e->{
            gameLevel = GameLevel.EASY;
        });

        this.mediumLevel = new JRadioButton("medium");
        this.mediumLevel.addActionListener(e->{
            gameLevel = GameLevel.MEDIUM;
        });

        this.hardLevel = new JRadioButton("hard");
        this.hardLevel.addActionListener(e->{
            gameLevel = GameLevel.HARD;
        });

        this.screenCurrLevel = new JTextField("Level: " + (gameLevel == GameLevel.NON_SELECTED ? "" : gameLevel));
        this.screenCurrLevel.setEditable(false);
        this.screenCurrLevel.setBorder(null);
        this.screenCurrLevel.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.levelsGroup = new ButtonGroup();
    }
    public void newGame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
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
        JPanel toolBarPanel =  new JPanel();
        toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBarPanel.add(this.restartButton);

        toolBarPanel.add(this.restartButton);
        toolBarPanel.add(Box.createHorizontalStrut(10));
        toolBarPanel.add(this.newGameButton);

        this.levelsGroup.add(this.easyLevel);
        this.levelsGroup.add(this.mediumLevel);
        this.levelsGroup.add(this.hardLevel);

        toolBarPanel.add(this.easyLevel);
        toolBarPanel.add(this.mediumLevel);
        toolBarPanel.add(this.hardLevel);
        toolBarPanel.add(Box.createHorizontalStrut(30));
        toolBarPanel.add(this.screenCurrLevel);

        this.toolBar.add(toolBarPanel);
        this.toolBar.setVisible(true);
        this.getContentPane().add(this.toolBar, BorderLayout.NORTH);
    }
}

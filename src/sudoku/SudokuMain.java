package sudoku;

import sudoku.enums.GameLevel;
import sudoku.enums.StatusGame;

import javax.swing.*;
import java.awt.*;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;
    private PuzzleBoardPanel gameBoard;
    private JMenuBar menuBar;
    private JMenuBar statusBar;
    private JButton restartButton;
    private JButton pauseButton;
    private JButton startButton;
    private JButton tipButton;
    private JRadioButton easyLevelButton;
    private JRadioButton mediumLevelButton;
    private JRadioButton hardLevelButton;
    private ButtonGroup levelsGroup;
    private GameLevel gameLevel = GameLevel.NON_SELECTED;
    private StatusGame statusGame = StatusGame.NON_INICIALIZED;
    private JTextField screenCurrLevel;
    private JTextField screenStatusGame;
    private JTextField screenErrorsAmount;
    private JTextField screenHitsAmount;
    private JTextField screenGameTimer;
    private int gameSeconds;
    private int gameMinutes;
    private Timer gameTimer;
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
        this.menuBar = new JMenuBar();
        this.statusBar = new JMenuBar();

        //Icon restart = new ImageIcon( "src/sudoku/images/restart-icon-7.png");
        this.restartButton = new JButton( "Restart");
        this.restartButton.setPreferredSize(new Dimension(85, 20));
        this.restartButton.addActionListener(e -> {
            if( gameLevel != GameLevel.NON_SELECTED ){
                this.gameSeconds = 0;
                this.gameBoard.restartGame();
                this.startGameTimer();
                this.startErrorCounter();
                this.startHitsCounter();
                revalidate();
            }
        });

        this.pauseButton = new JButton( "Pause");
        this.pauseButton.setPreferredSize(new Dimension(85, 20));
        this.pauseButton.addActionListener(e -> {
            if( this.statusGame != StatusGame.NON_INICIALIZED){
                this.screenStatusGame.setText("Status : " + this.gameBoard.setPause());
                this.pauseButton.setText(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? "Continue" : "Pause");
                this.statusGame = this.gameBoard.getStatusGame();
                this.tipButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.restartButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.startButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.easyLevelButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.mediumLevelButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.hardLevelButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
            }
        });

        this.startButton = new JButton("Start");
        this.startButton.setPreferredSize(new Dimension(85, 20));
        this.startButton.addActionListener(e -> {
            int confirmation = 0;
            if( gameLevel == GameLevel.NON_SELECTED ){
                JOptionPane.showMessageDialog(this, "Por favor, selecione um nível de jogo", "Aviso", JOptionPane.ERROR_MESSAGE);
            }else{
                if( this.gameBoard.haveProgres() ){
                    confirmation = JOptionPane.showConfirmDialog(this,
                                    "Deseja abandonar o jogo atual? Todo o seu progresso será perdido.",
                                    "Aviso", JOptionPane.YES_NO_OPTION);
                }
                if( confirmation == 0 || !this.gameBoard.haveProgres() ){
                    this.gameBoard.newGame(gameLevel);
                    this.screenCurrLevel.setText("Level: " + (gameLevel == GameLevel.NON_SELECTED ? "" : gameLevel));
                    this.restartButton.setEnabled(true);
                    this.pauseButton.setEnabled(true);
                    this.tipButton.setEnabled(true);
                    this.statusGame = StatusGame.PLAYING;
                    this.screenStatusGame.setText("Status : " + this.statusGame);
                    this.startGameTimer();
                    this.startErrorCounter();
                    this.startHitsCounter();
                    revalidate();
                }
            }
        });

        this.tipButton = new JButton("Tipo");
        this.tipButton.setPreferredSize(new Dimension(85, 20));
        this.tipButton.addActionListener(e -> {

        });

        this.easyLevelButton = new JRadioButton("easy");
        this.easyLevelButton.addActionListener(e -> gameLevel = GameLevel.EASY);

        this.mediumLevelButton = new JRadioButton("medium");
        this.mediumLevelButton.addActionListener(e -> gameLevel = GameLevel.MEDIUM);

        this.hardLevelButton = new JRadioButton("hard");
        this.hardLevelButton.addActionListener(e -> gameLevel = GameLevel.HARD);

        this.screenCurrLevel = new JTextField("Level: ");
        this.screenCurrLevel.setPreferredSize(new Dimension(130, 20));
        this.screenCurrLevel.setEditable(false);
        this.screenCurrLevel.setBorder(null);
        this.screenCurrLevel.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenStatusGame = new JTextField("Status: " );
        this.screenStatusGame.setPreferredSize(new Dimension(150, 20));
        this.screenStatusGame.setEditable(false);
        this.screenStatusGame.setBorder(null);
        this.screenStatusGame.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenErrorsAmount = new JTextField("Errors: " + this.gameBoard.getErrorsAmount());
        this.screenErrorsAmount.setPreferredSize(new Dimension(100, 20));
        this.screenErrorsAmount.setEditable(false);
        this.screenErrorsAmount.setBorder(null);
        this.screenErrorsAmount.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenHitsAmount = new JTextField("Hits: " + this.gameBoard.getHitsAmount());
        this.screenHitsAmount.setPreferredSize(new Dimension(100, 20));
        this.screenHitsAmount.setEditable(false);
        this.screenHitsAmount.setBorder(null);
        this.screenHitsAmount.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenGameTimer = new JTextField("Timer: " + String.format( "%02d", this.gameMinutes ) + ":" + String.format( "%02d", this.gameSeconds));
        this.screenGameTimer.setPreferredSize(new Dimension(110, 20));
        this.screenGameTimer.setEditable(false);
        this.screenGameTimer.setBorder(null);
        this.screenGameTimer.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.levelsGroup = new ButtonGroup();
    }
    public void newGame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocation(700, 200); //Posiciona o frame na tela
        this.setTitle("Sudoku Game");
        this.setIconImage(new ImageIcon("src/sudoku/images/sudoku.png").getImage());
        this.setMenuBar();
        this.setStatusBar();

        this.gameBoard.newGame(gameLevel);
        this.getContentPane().add(this.gameBoard, BorderLayout.CENTER);
        this.restartButton.setEnabled(false);
        this.pauseButton.setEnabled(false);
        this.tipButton.setEnabled(false);

        this.pack();
//        this.setSize(SudokuConstants.BOARD_WIDTH, SudokuConstants.BOARD_HEIGHT); //seta as dimensões da tela
        this.setVisible(true);

    }
    private void setMenuBar(){
        JPanel menuBarPanel =  new JPanel();
        menuBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.levelsGroup.add(this.easyLevelButton);
        this.levelsGroup.add(this.mediumLevelButton);
        this.levelsGroup.add(this.hardLevelButton);

        menuBarPanel.add(Box.createHorizontalStrut(5));
        menuBarPanel.add(this.easyLevelButton);
        menuBarPanel.add(this.mediumLevelButton);
        menuBarPanel.add(this.hardLevelButton);

        menuBarPanel.add(Box.createHorizontalStrut(10));
        menuBarPanel.add(this.startButton);

        menuBarPanel.add(Box.createHorizontalStrut(10));
        menuBarPanel.add(this.pauseButton);

        menuBarPanel.add(Box.createHorizontalStrut(10));
        menuBarPanel.add(this.restartButton);

        menuBarPanel.add(Box.createHorizontalStrut(10));
        menuBarPanel.add(this.tipButton);

        this.menuBar.add(menuBarPanel);
        this.menuBar.setVisible(true);
        this.getContentPane().add(this.menuBar, BorderLayout.NORTH);
    }
    private void setStatusBar(){
        JPanel statusBarPanel =  new JPanel();
        statusBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        statusBarPanel.add(Box.createHorizontalStrut(10));
        statusBarPanel.add(this.screenCurrLevel);
        statusBarPanel.add(this.screenStatusGame);
        statusBarPanel.add(this.screenHitsAmount);
        statusBarPanel.add(this.screenErrorsAmount);
        statusBarPanel.add(this.screenGameTimer);

        this.statusBar.add(statusBarPanel);
        this.statusBar.setVisible(true);

        this.getContentPane().add(this.statusBar, BorderLayout.SOUTH);
    }
    private void startErrorCounter(){
        new Timer(0, e ->  this.screenErrorsAmount.setText("Errors: " + this.gameBoard.getErrorsAmount())).start();
    }
    private void startHitsCounter(){
        new Timer(0, e ->  this.screenHitsAmount.setText("Hits: " + this.gameBoard.getHitsAmount())).start();
    }
    private void startGameTimer(){
        this.gameSeconds = 0;
        this.gameMinutes = 0;
        if( this.gameTimer != null ){
            this.gameTimer.restart();
        }else{
            this.gameTimer = new Timer(1000, e -> {
                    if( SudokuMain.this.statusGame == StatusGame.PLAYING){
                        this.gameSeconds = ++this.gameSeconds % 60;
                        this.gameMinutes += (this.gameSeconds % 60 == 0 ? 1: 0);
                        this.screenGameTimer.setText("Timer: " + String.format( "%02d", this.gameMinutes ) + ":" + String.format( "%02d", this.gameSeconds));
                    }
            });
            this.gameTimer.start();
        }
    }
}

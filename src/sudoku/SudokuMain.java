package sudoku;

import sudoku.enums.GameLevel;
import sudoku.enums.StatusGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;
    private PuzzleBoardPanel gameBoard;
    private JMenuBar menuBar;
    private JMenuBar statusBar;
    private JButton restartButton;
    private JButton pauseButton;
    private JButton newGameButton;
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
    private JTextField screenGameTipAmount;
    private int gameSeconds;
    private int gameMinutes;
    private Timer gameTimer;
    private int tipNumberSelected;
    private int tipsUsed;
    public static void main( String[] args ){
        EventQueue.invokeLater(() -> {
            try {
                new SudokuMain();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
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

        this.restartButton = new JButton( "Restart");
        this.restartButton.setPreferredSize(new Dimension(95, 20));
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
        this.pauseButton.setPreferredSize(new Dimension(95, 20));
        this.pauseButton.addActionListener(e -> {
            if( this.statusGame != StatusGame.NON_INICIALIZED){
                this.screenStatusGame.setText("Status : " + this.gameBoard.setPause());
                this.pauseButton.setText(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? "Continue" : "Pause");
                this.statusGame = this.gameBoard.getStatusGame();
                this.tipButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.restartButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.newGameButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.easyLevelButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.mediumLevelButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
                this.hardLevelButton.setEnabled(this.gameBoard.getStatusGame() == StatusGame.PAUSED ? false : true);
            }
        });

        this.newGameButton = new JButton("New Game");
        this.newGameButton.setPreferredSize(new Dimension(95, 20));
        this.newGameButton.addActionListener(e -> {
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
                    this.tipsUsed = 0;
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

        this.tipButton = new JButton("Tip");
        this.tipButton.setPreferredSize(new Dimension(95, 20));
        this.tipButton.addActionListener(e -> {
            if( this.tipsUsed < this.gameLevel.getAmoutTip() ){
                this.tipNumberSelected = new TipPanelDiolog().getSelectNumberTip();
                if( this.tipNumberSelected >= 1 && this.tipNumberSelected <= 9  ){
                    this.tipsUsed++;
                    this.gameBoard.giveATip(tipNumberSelected);
                    this.tipButton.setEnabled(false);
                    Timer timer = new Timer( SudokuConstants.TIME_TIP_GIVEN, ev -> {
                        if( this.statusGame == StatusGame.PLAYING ){
                            this.tipButton.setEnabled(true);
                        }
                    });
                    timer.start();
                    timer.setRepeats(false);
                }
                this.screenGameTipAmount.setText("Tips: " + this.tipsUsed + "/"+ this.gameLevel.getAmoutTip());
            }else{
                JOptionPane.showMessageDialog(SudokuMain.this, "Ops! Parece que você não tem mais dicas :/", "Tip", JOptionPane.INFORMATION_MESSAGE);
            }
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
        this.screenStatusGame.setPreferredSize(new Dimension(140, 20));
        this.screenStatusGame.setEditable(false);
        this.screenStatusGame.setBorder(null);
        this.screenStatusGame.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenErrorsAmount = new JTextField("Errors: " + this.gameBoard.getErrorsAmount());
        this.screenErrorsAmount.setPreferredSize(new Dimension(85, 20));
        this.screenErrorsAmount.setEditable(false);
        this.screenErrorsAmount.setBorder(null);
        this.screenErrorsAmount.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenGameTipAmount = new JTextField("Tips: " + this.tipsUsed + "/" + this.gameLevel.getAmoutTip());
        this.screenGameTipAmount.setPreferredSize(new Dimension(80, 15));
        this.screenGameTipAmount.setEditable(false);
        this.screenGameTipAmount.setBorder(null);
        this.screenGameTipAmount.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenHitsAmount = new JTextField("Hits: " + this.gameBoard.getHitsAmount());
        this.screenHitsAmount.setPreferredSize(new Dimension(60, 20));
        this.screenHitsAmount.setEditable(false);
        this.screenHitsAmount.setBorder(null);
        this.screenHitsAmount.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.screenGameTimer = new JTextField("Timer: " + String.format( "%02d", this.gameMinutes ) + ":" + String.format( "%02d", this.gameSeconds));
        this.screenGameTimer.setPreferredSize(new Dimension(110, 20));
        this.screenGameTimer.setEditable(false);
        this.screenGameTimer.setBorder(null);
        this.screenGameTimer.setFont( new Font("OCR A Extend", Font.HANGING_BASELINE, 15));

        this.levelsGroup = new ButtonGroup();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocation(700, 200);
        this.setTitle("Sudoku Game");
        this.setIconImage(new ImageIcon("src/sudoku/images/sudoku.png").getImage());
        this.setMenuBar();
        this.setStatusBar();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if( SudokuMain.this.gameBoard.haveProgres() ){
                    int confirmation = JOptionPane.showConfirmDialog(SudokuMain.this,
                            "Deseja abandonar o jogo atual? Todo o seu progresso será perdido.",
                            "Aviso", JOptionPane.YES_NO_OPTION);
                    if( confirmation == 0 ){
                        System.exit(0);
                    }
                }else{
                    System.exit(0);
                }
            }
        });
    }
    public void newGame(){
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

        menuBarPanel.add(this.easyLevelButton);
        menuBarPanel.add(this.mediumLevelButton);
        menuBarPanel.add(this.hardLevelButton);

        menuBarPanel.add(Box.createHorizontalStrut(10));
        menuBarPanel.add(this.newGameButton);

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
        statusBarPanel.add(this.screenGameTipAmount);
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
    private class TipPanelDiolog extends JDialog{
        private int panelWidth = 600;
        private int panelHeigth = 140;
        private JPanel optionsPane;
        private JButton tipNumberOne;
        private JButton tipNumberTwo;
        private JButton tipNumberThree;
        private JButton tipNumberFour;
        private JButton tipNumberFive;
        private JButton tipNumberSix;
        private JButton tipNumberSeven;
        private JButton tipNumberEigth;
        private JButton tipNumberNine;
        private int numberTipSelected;

        public TipPanelDiolog(){
            super(SudokuMain.this, true);
            super.setTitle("Tip");
            this.setResizable(false);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setLayout(new FlowLayout());
            this.setSize(this.panelWidth, this.panelHeigth);
            this.setLocationRelativeTo(SudokuMain.this);
            this.inicializeComponents();
            this.numberTipSelected = 0;
        }

        private void inicializeComponents(){
            this.optionsPane = new JPanel();
            this.optionsPane.setLayout(new FlowLayout());
            this.optionsPane.setSize(new Dimension(this.panelWidth, this.panelHeigth));
            this.optionsPane.setBackground(SudokuConstants.BG_PANEL_TIP);

            this.tipNumberOne = new JButton("1");
            this.tipNumberOne.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberOne.setPreferredSize(new Dimension(60, 60));
            this.tipNumberOne.setBackground( SudokuConstants.BG_NUMBER_ONE);
            this.tipNumberOne.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberOne.addActionListener( e -> {
                this.numberTipSelected = 1;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberOne);

            this.tipNumberTwo = new JButton("2");
            this.tipNumberTwo.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberTwo.setPreferredSize(new Dimension(60, 60));
            this.tipNumberTwo.setBackground( SudokuConstants.BG_NUMBER_TWO);
            this.tipNumberTwo.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberTwo.addActionListener( e -> {
                this.numberTipSelected = 2;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberTwo);

            this.tipNumberThree = new JButton("3");
            this.tipNumberThree.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberThree.setPreferredSize(new Dimension(60, 60));
            this.tipNumberThree.setBackground( SudokuConstants.BG_NUMBER_THREE);
            this.tipNumberThree.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberThree.addActionListener( e -> {
                this.numberTipSelected = 3;
                this.dispose();
            });
            this.optionsPane.add(this.tipNumberThree);

            this.tipNumberFour = new JButton("4");
            this.tipNumberFour.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberFour.setPreferredSize(new Dimension(60, 60));
            this.tipNumberFour.setBackground( SudokuConstants.BG_NUMBER_FOUR);
            this.tipNumberFour.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberFour.addActionListener( e -> {
                this.numberTipSelected = 4;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberFour);

            this.tipNumberFive = new JButton("5");
            this.tipNumberFive.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberFive.setPreferredSize(new Dimension(60, 60));
            this.tipNumberFive.setBackground( SudokuConstants.BG_NUMBER_FIVE);
            this.tipNumberFive.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberFive.addActionListener( e -> {
                this.numberTipSelected = 5;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberFive);

            this.tipNumberSix = new JButton("6");
            this.tipNumberSix.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberSix.setPreferredSize(new Dimension(60, 60));
            this.tipNumberSix.setBackground( SudokuConstants.BG_NUMBER_SIX);
            this.tipNumberSix.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberSix.addActionListener( e -> {
                this.numberTipSelected = 6;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberSix);

            this.tipNumberSeven = new JButton("7");
            this.tipNumberSeven.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberSeven.setPreferredSize(new Dimension(60, 60));
            this.tipNumberSeven.setBackground( SudokuConstants.BG_NUMBER_SEVEN);
            this.tipNumberSeven.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberSeven.addActionListener( e -> {
                this.numberTipSelected = 7;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberSeven);

            this.tipNumberEigth = new JButton("8");
            this.tipNumberEigth.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberEigth.setPreferredSize(new Dimension(60, 60));
            this.tipNumberEigth.setBackground( SudokuConstants.BG_NUMBER_EIGTH);
            this.tipNumberEigth.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberEigth.addActionListener( e -> {
                this.numberTipSelected = 8;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberEigth);

            this.tipNumberNine = new JButton("9");
            this.tipNumberNine.setFont(SudokuConstants.FONT_NUMBERS);
            this.tipNumberNine.setPreferredSize(new Dimension(60, 60));
            this.tipNumberNine.setBackground( SudokuConstants.BG_NUMBER_NINE);
            this.tipNumberNine.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            this.tipNumberNine.addActionListener( e -> {
                this.numberTipSelected = 9;
                this.dispose();
            } );
            this.optionsPane.add(this.tipNumberNine);
            super.getContentPane().add(this.optionsPane);
        }
        public int getSelectNumberTip(){
            this.optionsPane.setVisible(true);
            this.setVisible(true);
            return this.numberTipSelected;
        }
    }
}

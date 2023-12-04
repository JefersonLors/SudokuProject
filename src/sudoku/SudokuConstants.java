package sudoku;

import java.awt.*;

public class SudokuConstants {
    public static final int GRID_SIZE = 9;
    public static final int SUBGRID_SIZE = 3;
    public static final int CELL_SIZE = 70;
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    //FONTS
    public static final Font FONT_NUMBERS = new Font("OCR A Extend", Font.PLAIN, 28);

    //FOREGROUNDS
    public static final Color FG_GIVEN = new Color(0, 0, 0);
    public static final Color FG_WRONG_GUESS = new Color(192, 44, 44);
    public static final Color FG_CORRECT_GUESS = new Color(48, 71, 190);

    //BACKGROUNDS
    public static final Color BG_TO_GUESS = new Color(217, 217, 217);
    public static final Color BG_NUMBER_ONE = new Color(210, 164, 164);
    public static final Color BG_NUMBER_TWO = new Color(208, 187, 161);
    public static final Color BG_NUMBER_TREE = new Color(200, 208, 163);
    public static final Color BG_NUMBER_FOR = new Color(180, 199, 168);
    public static final Color BG_NUMBER_FIVE = new Color(178, 194, 211);
    public static final Color BG_NUMBER_SIX = new Color(178, 179, 211);
    public static final Color BG_NUMBER_SEVEN = new Color(189, 175, 175);
    public static final Color BG_NUMBER_EIGTH = new Color(190, 175, 206);
    public static final Color BG_NUMBER_NINE = new Color(208, 187, 180, 234);
}

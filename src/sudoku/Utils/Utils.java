package sudoku.Utils;

import java.util.Random;

public class Utils {
    public static int randomNumberBetween(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}

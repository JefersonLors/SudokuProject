package sudoku.enums;

public enum GameLevel {
    EASY(40),
    MEDIUM(80),
    HARD(150),
    NON_SELECTED(0);
    private int val;
    GameLevel( int qtyToGuess){
        this.val = qtyToGuess;
    }
    public int getValue(){
        return this.val;
    }
}

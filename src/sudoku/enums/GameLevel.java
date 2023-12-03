package sudoku.enums;

public enum GameLevel {
    EASY(20),
    MEDIUM(50),
    HARD(70),
    NON_SELECTED(0);
    private int qtyToGuess;
    GameLevel( int qtyToGuess){
        this.qtyToGuess = qtyToGuess;
    }
    public int getValue(){
        return this.qtyToGuess;
    }
}

package sudoku.enums;

public enum GameLevel {
    EASY(40, 1),
    MEDIUM(80, 2),
    HARD(150,3),
    NON_SELECTED(0, 0);
    private int amountToGuess;
    private int amountTips;
    GameLevel( int amountToGuess, int amountTips){
        this.amountToGuess = amountToGuess;
        this.amountTips = amountTips;
    }
    public int getAmountToGuess(){
        return this.amountToGuess;
    }
    public int getAmoutTip(){return this.amountTips;}
}

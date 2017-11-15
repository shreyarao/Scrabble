/**
 * Created by shrey on 11/13/2017.
 */
public class Square {

    //scrabble tile at board square
    Tile tile;

    //score multiplier if a tile is placed on the square
    int multiplier;

    //true if score multiplier applies to the whole word, false if it only applies to the letter
    boolean multiplyWord;

    /**
     * Constructor for board square
     * @param newTile tile at square
     * @param newMultiplier score multiplier at square
     * @param newMultiplyWord whether or not new square has a word multiplier
     */
    public Square(Tile newTile, int newMultiplier, boolean newMultiplyWord){
        tile = newTile;
        multiplier = newMultiplier;
        multiplyWord = newMultiplyWord;
    }

    /**
     * Default constructor for board square
     */
    public Square(){
        tile = null;
        multiplier = 0;
        multiplyWord = false;
    }

    /**
     * sets a tile to the board square
     * @param newTile
     */
    public void setTile(Tile newTile) {
        tile = newTile;
    }

    /**
     * gets the tile at a board square
     * @return tile at the board square
     */
    public Tile getTile(){
        return tile;
    }

    /**
     * sets the square score multiplier
     * @param newMultiplier multiplier to be added to square
     */
    public void setMultiplier(int newMultiplier){
        multiplier = newMultiplier;
    }

    /**
     * gets the score multiplier at a square
     * @return score multiplier at square
     */
    public int getMultiplier(){
        return multiplier;
    }

    /**
     * sets the indicator for whether the score multiplier for the square applies to a word or letter
     * @param newMultiplyWord boolean indicator for word or letter
     */
    public void setMultiplyWord(boolean newMultiplyWord){
        multiplyWord = newMultiplyWord;
    }

    /**
     * gets the square indicator for whether the score multiplier for the square applies to a word or letter
     * @return the square indicator for whether the score multiplier for the square applies to a word or letter
     */
    public boolean getMultiplyWord(){
        return multiplyWord;
    }
}

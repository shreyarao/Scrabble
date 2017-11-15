/**
 * Created by shrey on 11/13/2017.
 */
public class Tile {

    //letter on the tile
    char letter;
    //numerical value of the tile
    int value;

    //x coordinate of tile on board (-1 if tile is not on board)
    int xCoordinate;
    //y coordinate of tile on board (-1 if tile is not on board)
    int yCoordinate;

    /**
     * scrabble tile constructor
     * @param newLetter letter value of tile
     * @param newValue number value of tile
     * @param newX x coordinate of tile
     * @param newY y coordinate of tile
     */
    public Tile(char newLetter, int newValue, int newX, int newY){
        letter = newLetter;
        value = newValue;
        xCoordinate = newX;
        yCoordinate = newY;
    }

    /**
     * sets the letter value of the tile
     * @param newLetter desired letter value
     */
    public void setLetter(char newLetter){
        letter = newLetter;
    }

    /**
     * gets the letter value of the tile
     * @return letter value of the tile
     */
    public char getLetter(){
        return letter;
    }

    /**
     * sets the number value of the tile
     * @param newValue desired number value
     */
    public void setValue(int newValue){
        value = newValue;
    }

    /**
     * gets the number value of the tile
     * @return number value of the tile
     */
    public int getValue(){
        return value;
    }

    /**
     * places the tile on the board (sets x and y coordinate)
     * @param newXCoordinate desired x coordinate for tile to be set at
     * @param newYCoordinate desired y coordinate for tile to be set at
     */
    public void setCoordinate(int newXCoordinate, int newYCoordinate){
        xCoordinate = newXCoordinate;
        yCoordinate = newYCoordinate;
    }

    /**
     * returns x coordinate of tile
     * @return x coordinate of tile
     */
    public int getxCoordinate(){
        return xCoordinate;
    }

    /**
     * returns y coordinate of tile
     * @return y coordinate of tile
     */
    public int getyCoordinate(){
        return yCoordinate;
    }
}

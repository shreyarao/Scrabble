import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by shrey on 11/28/2017.
 */
public class Scrabble {

    //scrabble board we are playing on
    Board board;

    //bag of tiles we draw from
    Bag tileBag;

    //current tiles of the automatic player
    ArrayList<Tile> computerTiles;

    //current tiles of the human player
    ArrayList<Tile> playerTiles;

    //list of tiles on the board
    ArrayList<Tile> tilesOnBoard;

    //Scrabble dictionary
    DictionaryStructure dictionary;

    /**
     * called at the beginning of the computer player's turn-- fraws tiles from bag
     */
    public void computerDraw(){

        ArrayList<Tile> newTiles = tileBag.drawFromBag(7-computerTiles.size());
        for (int i = 0; i < newTiles.size(); i++){
            computerTiles.add(newTiles.get(i));
        }
    }

    /**
     * called at the beginning of the human player's turn-- draws tiles from bag
     */
    public void playerDraw(){
        ArrayList<Tile> newTiles = tileBag.drawFromBag(7-playerTiles.size());
        for (int i = 0; i < newTiles.size(); i++){
            playerTiles.add(newTiles.get(i));
        }
    }

    /**
     * checks whether the tiles that the manual player has placed down actually consist of a real word
     * @param word the tiles the player has played
     * @return true if the player placed a legitimate word
     */
    public boolean playerWordCheck(ArrayList<Tile> word){
        char [] placed = new char[word.size()];
        for (int i = 0; i < word.size(); i++){
            placed[i] = word.get(i).getLetter();
        }

        return dictionary.ScrabbleDictionaryTrie.search(String.valueOf(placed));
    }


    /**
     * starts the Scrabble game by initializing all structures
     */
    public void startGame(){
        ScrabbleInterface scrabble = new ScrabbleInterface();
        scrabble.generateScrabbleUI();
        board = scrabble.gameBoard;
        computerTiles = new ArrayList<>();
        playerTiles = new ArrayList<>();
        tileBag = new Bag();
        dictionary = new DictionaryStructure();
        dictionary.readInDictionary();
    }

    /**
     * retuns the game
     * @param args
     */
    public static void main (String [] args){
        Scrabble scrabble = new Scrabble();
        scrabble.startGame();
    }
}

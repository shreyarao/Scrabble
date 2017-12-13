import java.lang.reflect.AnnotatedArrayType;
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

    //Scrabble Interface
    ScrabbleInterface scrabbleInterface;

    //public enum Direction { UP, DOWN, LEFT, RIGHT}

    /**
     * called at the beginning of the computer player's turn-- draws tiles from bag
     */
    public void computerDraw(){

        ArrayList<Tile> newTiles = scrabbleInterface.tileBag.drawFromBag(7-scrabbleInterface.computerTiles.size());
        for (int i = 0; i < newTiles.size(); i++){
            scrabbleInterface.computerTiles.add(newTiles.get(i));
        }
    }

    /**
     * called at the beginning of the human player's turn-- draws tiles from bag
     */
    public void playerDraw(){
        ArrayList<Tile> newTiles = scrabbleInterface.tileBag.drawFromBag(7-scrabbleInterface.playerTiles.size());
        for (int i = 0; i < newTiles.size(); i++){
            scrabbleInterface.playerTiles.add(newTiles.get(i));
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

        return scrabbleInterface.dictionary.ScrabbleDictionaryTrie.search(String.valueOf(placed));
    }

    /**
     * finds the range of a tile
     * @param currentTile tile in question
     * @param direction direction in question
     * @return range of tile in given direction
     */
    public int findRange(Tile currentTile, ScrabbleInterface.Direction direction){
        int xCoord = currentTile.getxCoordinate();
        int yCoord = currentTile.getyCoordinate();

        int range = 0;

        if (direction == ScrabbleInterface.Direction.UP){
            for (int i = yCoord-1; i > 0; i--){
                if (scrabbleInterface.gameBoard.scrabbleBoard[xCoord][i].getTile() != null){
                    return range;
                }
                range++;
            }
        }
        else if (direction == ScrabbleInterface.Direction.DOWN){
            for (int i = yCoord+1; i < 14; i++){
                if (scrabbleInterface.gameBoard.scrabbleBoard[xCoord][i].getTile() != null){
                    return range;
                }
                range++;
            }
        }
        else if (direction == ScrabbleInterface.Direction.LEFT){

            for (int i = xCoord-1; i > 0; i--){

                if (scrabbleInterface.gameBoard.scrabbleBoard[i][yCoord].getTile() != null){
                    return range;
                }
                range++;
            }
        }
        else{
            for (int i = xCoord+1; i < 14; i++){
                //System.out.print(i+ " ");
                if (scrabbleInterface.gameBoard.scrabbleBoard[i][yCoord].getTile() != null){
                    return range;
                }
                range++;
            }
        }
        return range;
    }

    /**
     * prints out potential word choices given a tile
     * @param currentTile tile in question
     * @return potential word choices
     */
    public ArrayList<ArrayList<Tile>> oneTileChoices(Tile currentTile){
        int leftReach = findRange(currentTile, ScrabbleInterface.Direction.LEFT);
        int rightReach =  findRange(currentTile, ScrabbleInterface.Direction.RIGHT);


        Tile [] x = new Tile[scrabbleInterface.computerTiles.size()];
        for (int i = 0; i < x.length; i++){
            x[i] = scrabbleInterface.computerTiles.get(i);
        }
        ArrayList<ArrayList<Tile>> permutations = permutePowerSet(scrabbleInterface.computerTiles);
        System.out.println(permutations.size());
        ArrayList<ArrayList<Tile>> masterList = new ArrayList<>();
        int fullReach = leftReach + rightReach;

        for (int i = 0; i < permutations.size(); i++){
            if (permutations.get(i).size() > fullReach)
                break;

            for (int left = 0; left < leftReach; left++){
                permutations.get(i).add(left, currentTile);
                masterList.add(permutations.get(i));
            }
        }
        System.out.println(masterList.size());
        return masterList;
    }

    /**
     * converts list of tiles into a string word
     * @param tiles list of tiles in question
     * @return string representation
     */
    public String tilesToString(ArrayList<Tile> tiles){
        char [] ret = new char[tiles.size()];
        for (int i = 0; i < tiles.size(); i++){
            ret[i] = tiles.get(i).getLetter();
        }
        return String.valueOf(ret);
    }

    /**
     * automatically searches for a proper word to place on the board
     * @return list of potential words on the board
     */
    public ArrayList<ArrayList<Tile>> computerWordSearch(){
        ArrayList<ArrayList<Tile>> potentialWords = new ArrayList<>();

        for (int i = 0; i < scrabbleInterface.tilesOnBoard.size(); i++){
            ArrayList<ArrayList<Tile>> words = oneTileChoices(scrabbleInterface.tilesOnBoard.get(i));
            System.out.print(words.size());
            for (int j = 0; j < words.size(); j++){
                System.out.println(tilesToString(words.get(j)));
                if (scrabbleInterface.dictionary.ScrabbleDictionaryTrie.search(tilesToString(words.get(j)))){

                    potentialWords.add(words.get(j));
                }
            }
        }

        return potentialWords;
    }

    /**
     * creates a powerset of tiles
     * @param set array to create a powerset of
     * @return powerset of tiles
     */
    public ArrayList<ArrayList<Tile>> ps(Tile [] set){
        int n = set.length;
        ArrayList<ArrayList<Tile>> ret = new ArrayList<>();
        for (int i = 0; i < (1<<n); i++){
            ArrayList<Tile> temp = new ArrayList<>();
            for (int j = 0; j < n; j++){
                if ((i & (1<<j)) > 0){
                    temp.add(set[j]);
                }
            }ret.add(temp);
        }
        return ret;
    }

    /**
     * returns a permutation of a tile list
     * @param myTiles tiles to create a permutaiton of
     * @return permutation of a tile list
     */
    public ArrayList<ArrayList<Tile>> permute(Tile[] myTiles){
        ArrayList<ArrayList<Tile>> result = new ArrayList<>();
        ArrayList<ArrayList<Tile>> rr = new ArrayList<>();
        result.add(new ArrayList<Tile>());

        for (int i = 0; i < myTiles.length; i++){
            ArrayList<ArrayList<Tile>> curr = new ArrayList<>();
            for (ArrayList<Tile> l : result){
                for (int j = 0; j < l.size() + 1; j++){
                    l.add(j, myTiles[i]);

                    ArrayList<Tile> temp = new ArrayList<>(l);
                    curr.add(temp);
                    l.remove(j);
                }

            }
            result = new ArrayList<ArrayList<Tile>>(curr);
        }

        return result;
    }

    /**
     * returns the permutation of a powerset of tiles
     * @param myTiles tile list in question
     * @return permutation of a powerset of tiles
     */
    public ArrayList<ArrayList<Tile>> permutePowerSet(ArrayList<Tile> myTiles) {
        Tile [] myTileArray = new Tile[myTiles.size()];
        for (int i = 0; i < myTileArray.length; i++){
            myTileArray[i] = myTiles.get(i);
        }
        ArrayList<ArrayList<Tile>> ret = new ArrayList<>();
        ArrayList<ArrayList<Tile>> list = ps(myTileArray);
        for (int i = 0; i < list.size(); i++){
            Tile [] otherArray = new Tile[list.get(i).size()];
            for (int k = 0; k < list.get(i).size(); k++){
                otherArray[k] = list.get(i).get(k);
            }
            ArrayList<ArrayList<Tile>> temp = permute(otherArray);
            ret.addAll(temp);
        }

        return ret;
    }

    /**
     * starts the Scrabble game by initializing all structures
     */
    public void startGame(){

        scrabbleInterface = new ScrabbleInterface();
        computerDraw();
        playerDraw();
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

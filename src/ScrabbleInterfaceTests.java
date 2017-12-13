/**
 * Created by shrey on 12/6/2017.
 */
import org.junit.Test;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.stream.StreamSupport;

public class ScrabbleInterfaceTests {

    /**
     * tests the function to find all permutations of a set of tiles
     */
    @Test
    public void permuteTest(){
        Scrabble scrabble = new Scrabble();
        ArrayList<Tile> t = new ArrayList<>();
        t.add(new Tile('A', 1, 1, 1));
        t.add(new Tile('B', 1, 1, 1));
        t.add(new Tile('C', 1, 1, 1));
        Tile [] x = new Tile[t.size()];
        for (int i = 0; i < x.length; i++){
            x[i] = t.get(i);
        }
        ArrayList<ArrayList<Tile>> a = scrabble.permute(x);
        for (int i = 0; i < a.size(); i++){
            System.out.println(scrabble.tilesToString(a.get(i)));
        }
    }

    /**
     * tests the function to find all the permutations of a powerset of a set of tiles
     */
    @Test
    public void permutePowerSetTest(){
        Scrabble scrabble = new Scrabble();
        ArrayList<Tile> t = new ArrayList<>();
        t.add(new Tile('A', 1, 1, 1));
        t.add(new Tile('B', 1, 1, 1));
        t.add(new Tile('C', 1, 1, 1));
        ArrayList<ArrayList<Tile>> i = scrabble.permutePowerSet(t);
        for (int it = 0; it <i.size(); it++){
            System.out.println(scrabble.tilesToString(i.get(it)));
        }
    }

    /**
     * tests the function to find the powerset of a set of tiles
     */
    @Test
    public void powersetTest(){
        Scrabble scrabble = new Scrabble();
        ArrayList<Tile> t = new ArrayList<>();
        t.add(new Tile('A', 1, 1, 1));
        t.add(new Tile('B', 1, 1, 1));
        t.add(new Tile('C', 1, 1, 1));
        Tile [] x = new Tile[t.size()];
        for (int i = 0; i < x.length; i++){
            x[i] = t.get(i);
        }
        ArrayList<ArrayList<Tile>> s = scrabble.ps(x);
        for (int i = 0; i < s.size(); i++){
            for (int j = 0; j < s.get(i).size(); j++){
                System.out.print(s.get(i).get(j).getLetter());
            }
            System.out.println();
        }
    }

    /**
     * tests the potential combinations that can be made with a certain tile on the board added to the computer's
     * potential tiles
     */
    @Test
    public void onetileTest(){
        Scrabble scrabble = new Scrabble();
        scrabble.startGame();
        System.out.println(scrabble.tilesToString(scrabble.scrabbleInterface.computerTiles));
        Tile x = new Tile('A',1, 3, 3);
        scrabble.scrabbleInterface.gameBoard.scrabbleBoard[3][3].setTile(x);
        scrabble.scrabbleInterface.tilesOnBoard.add(x);

        ArrayList<ArrayList<Tile>> r = scrabble.computerWordSearch();
        for (int i = 0; i < r.size(); i++){
            System.out.println(scrabble.tilesToString(r.get(i)));
        }
        System.out.println(r.size());
    }

    /**
     * tests the function to find a full combination set of all the potential "words" a set of tiles and tiles on the
     * board can make
     */
    @Test
    public void fullWordSetTest(){
        Scrabble scrabble = new Scrabble();
        scrabble.startGame();
        ArrayList<ArrayList<Tile>> wordset = scrabble.permutePowerSet(scrabble.scrabbleInterface.computerTiles);
        for (int i = 0; i < wordset.size(); i++){
            System.out.println(scrabble.tilesToString(wordset.get(i)));
        }
    }

    /**
     * tests the function to find all words which are in the dictionary that can be created with the given tile set
     */
    @Test
    public void spellCheckWordSetTest(){
        Scrabble scrabble = new Scrabble();
        scrabble.startGame();
        for (int i = 0; i < scrabble.scrabbleInterface.computerTiles.size(); i++){
            System.out.print(scrabble.scrabbleInterface.computerTiles.get(i).getLetter());
        }
        System.out.println("#");
        ArrayList<ArrayList<Tile>> wordset = scrabble.permutePowerSet(scrabble.scrabbleInterface.computerTiles);
        for (int i = 0; i < wordset.size(); i++){
            String word = scrabble.tilesToString(wordset.get(i));
            if (scrabble.scrabbleInterface.dictionary.ScrabbleDictionaryTrie.search(word)){
                System.out.println(word);
            }
        }
    }
}

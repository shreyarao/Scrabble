import org.junit.Test;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

/**
 * Created by shrey on 11/15/2017.
 */

public class Tests {

    /**
     * tests whether the dictionary is properly parsed into a trie data structure
     */
    @Test
    public void readIntoDictionaryTest(){
        DictionaryStructure testDictionary = new DictionaryStructure();
        testDictionary.readInDictionary();
        assert(testDictionary.ScrabbleDictionaryTrie != null);
    }

    /**
     * tests whether a version of the dictionary where the words are all reversed is properly parsed
     *into a data structure
     */
    @Test
    public void reverseReadIntoDictionaryTest(){
        DictionaryStructure testDictionary = new DictionaryStructure();
        testDictionary.reverseReadInDictionary();
        assert(testDictionary.ScrabbleDictionaryTrie != null);
    }

    /**
     * tests whether the structure is properly able to insert a new word
     */
    @Test
    public void insertTest(){
        DictionaryStructure testDictionary = new DictionaryStructure();
        testDictionary.readInDictionary();
        testDictionary.ScrabbleDictionaryTrie.insert("thisisafakeword");
        assert(testDictionary.ScrabbleDictionaryTrie.search("thisisafakeword") == true);
    }

    /**
     * tests whether the library structure can find a full word
     */
    @Test
    public void searchFullWordTest(){
        DictionaryStructure testDictionary = new DictionaryStructure();
        testDictionary.readInDictionary();
        assert(testDictionary.ScrabbleDictionaryTrie.search("ABAS")==true);
        assert(testDictionary.ScrabbleDictionaryTrie.search("A")==false);
    }

    /**
     * tests whether the library structure can find words with a given prefix
     */
    @Test
    public void searchPrefixTest(){
        DictionaryStructure testDictionary = new DictionaryStructure();
        testDictionary.readInDictionary();
        assert(testDictionary.ScrabbleDictionaryTrie.searchPrefix("A")!=null);
        assert(testDictionary.ScrabbleDictionaryTrie.searchPrefix("QQ")==null);
    }

    /**
     * tests whether the library structure can find words with a given suffix
     */
    @Test
    public void searchSuffixTest(){
        DictionaryStructure testDictionary = new DictionaryStructure();
        testDictionary.reverseReadInDictionary();
        assert(testDictionary.ScrabbleDictionaryTrie.searchSuffix("LLY")!=null);
        assert(testDictionary.ScrabbleDictionaryTrie.searchSuffix("FAKESUFFIX")==null);
    }

    /**
     * tests whether the last letter node of a string is returned if the string exists in the dictionary
     */
    @Test
    public void searchNodeTest(){
        DictionaryStructure testDictionary = new DictionaryStructure();
        testDictionary.readInDictionary();
        assert(testDictionary.ScrabbleDictionaryTrie.searchNode("AAS").isLeaf == true);
        assert(testDictionary.ScrabbleDictionaryTrie.searchNode("FAKEWORD") == null);
    }

    /**
     * tests whether the user can randomly draw any number of letters from the bag of remaining letters
     */
    @Test
    public void drawFromBagTest(){
        Bag testBag = new Bag();
        ArrayList<Tile> drawnTiles = testBag.drawFromBag(7);
        assert(drawnTiles.size() == 7);
        ArrayList<Tile> tooManyTiles = testBag.drawFromBag(100);
        assert(tooManyTiles == null);
    }

    /**
     * tests the constructors for the bag, board, square, and tile data structures
     */
    @Test
    public void allConstructorsTest(){
        Bag testBag = new Bag();
        Board testBoard = new Board();
        Tile testTile = new Tile('A', 1, -1, -1);
        Square testSquare = new Square(testTile, 1, false);

        assert (testBag.bag.size() == 100);
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 14; j++){
                assert(testBoard.scrabbleBoard[i][j].tile == null);
                assert(testBoard.scrabbleBoard[i][j].multiplier != 0);
            }
        }

        assert (testSquare.getMultiplier() == 1 && testSquare.getMultiplyWord()==false);
    }

    /**
     * tests the player draw from bag function
     */
    @Test
    public void playerDrawTest(){
        Scrabble scrabble = new Scrabble();
        scrabble.startGame();

        int bagSize = scrabble.tileBag.bag.size();
        scrabble.playerDraw();
        assert(scrabble.playerTiles.size() == 7);
        assert(scrabble.tileBag.bag.size() == bagSize - 7);

        scrabble.playerTiles.remove(1);
        scrabble.playerTiles.remove(2);
        scrabble.playerDraw();
        assert(scrabble.playerTiles.size() == 7);
        assert(scrabble.tileBag.bag.size() == bagSize - 9);
    }

    /**
     * tests the computer draw from bag function
     */
    @Test
    public void computerDrawTest(){
        Scrabble scrabble = new Scrabble();
        scrabble.startGame();

        int bagSize = scrabble.tileBag.bag.size();
        scrabble.computerDraw();
        assert(scrabble.computerTiles.size() == 7);
        assert(scrabble.tileBag.bag.size() == bagSize - 7);

        scrabble.computerTiles.remove(1);
        scrabble.computerTiles.remove(2);
        scrabble.computerDraw();
        assert(scrabble.computerTiles.size() == 7);
        assert(scrabble.tileBag.bag.size() == bagSize - 9);
    }

    /**
     * tests the word check function
     */
    @Test
    public void wordCheckTest(){
        Scrabble scrabble = new Scrabble();
        scrabble.startGame();

        ArrayList<Tile> trueWord = new ArrayList<>();
        trueWord.add(new Tile('A', 1, 1, 1));
        trueWord.add(new Tile('B', 1, 1, 1));
        assert(scrabble.playerWordCheck(trueWord) == true);

        trueWord.add(new Tile('X',1,1, 1));
        assert(scrabble.playerWordCheck(trueWord) == false);
    }

}

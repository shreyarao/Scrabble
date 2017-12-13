/**
 * Created by shrey on 12/13/2017.
 */
import org.junit.Test;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.stream.StreamSupport;
public class GameTests {

    /**
     * tests whether the game increments the player and computer scores upon correctly spelled word placement
     */
    @Test
    public void scoreIncrementCorrectTest(){
        ScrabbleInterface scrabbleInterface = new ScrabbleInterface();
        scrabbleInterface.mostRecentTiles.add(new Tile('A', 1, 1, 2));
        scrabbleInterface.mostRecentTiles.add(new Tile('D', 1, 1, 3));
        scrabbleInterface.mostRecentTiles.add(new Tile('D', 1, 1, 4));
        scrabbleInterface.spellcheckAddPoints(scrabbleInterface.mostRecentTiles);

        assert(scrabbleInterface.myScore == 3);
    }

    /**
     * tests whether the game doesn't increment the player score upon an incorrectly spelled word placement
     */
    @Test
    public void scoreIncrementFalseTest(){
        ScrabbleInterface scrabbleInterface = new ScrabbleInterface();
        scrabbleInterface.mostRecentTiles.add(new Tile('F', 1, 1, 2));
        scrabbleInterface.mostRecentTiles.add(new Tile('F', 1, 1, 3));
        scrabbleInterface.spellcheckAddPoints(scrabbleInterface.mostRecentTiles);

        assert(scrabbleInterface.myScore == 0);
    }

    @Test
    public void multiplierTest(){
        ScrabbleInterface scrabbleInterface = new ScrabbleInterface();
        scrabbleInterface.mostRecentTiles.add(new Tile('A', 1, 1, 1));
        scrabbleInterface.mostRecentTiles.add(new Tile('D', 1, 1, 1));
        scrabbleInterface.mostRecentTiles.add(new Tile('D', 1, 1, 1));
        scrabbleInterface.spellcheckAddPoints(scrabbleInterface.mostRecentTiles);

        assert(scrabbleInterface.myScore == 6);
    }

    /**
     * tests whether the game ends correctly when the game bag runs out of tiles
     */
    @Test
    public void gameEndWinTest(){
        ScrabbleInterface scrabbleInterface = new ScrabbleInterface();
        scrabbleInterface.myScore = 10;
        scrabbleInterface.opponentScore = 0;
        scrabbleInterface.bagNumber = 10;
        scrabbleInterface.computerDraw();

        assert(scrabbleInterface.forfeit == true);
        assert(scrabbleInterface.winLoseTie == 0);
    }


}

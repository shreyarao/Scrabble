import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by shrey on 11/13/2017.
 */
public class Bag {

    //bag of remaining tiles that both players can draw from
    public ArrayList<Tile> bag;

    /**
     * Populates the bag with different tiles (with their respective values)
     */
    public Bag(){
        bag = new ArrayList<>();

        for (int i = 0; i < 12; i++)
            bag.add(new Tile('E', 1, -1, -1));

        for (int i = 0; i < 9; i++){
            bag.add(new Tile('A', 1, -1, -1));
            bag.add(new Tile('I', 1, -1, -1));
        }

        for (int i = 0; i < 8; i++)
            bag.add(new Tile('O', 1, -1, -1));

        for (int i = 0; i < 6; i++){
            bag.add(new Tile('N', 1, -1, -1));
            bag.add(new Tile('R', 1, -1, -1));
            bag.add(new Tile('T', 1, -1, -1));
        }

        for (int i = 0; i < 4; i++){
            bag.add(new Tile('L', 1, -1, -1));
            bag.add(new Tile('S', 1, -1, -1));
            bag.add(new Tile('U', 1, -1, -1));
            bag.add(new Tile('D', 2, -1, -1));
        }

        for (int i = 0; i < 3; i++){
            bag.add(new Tile('G', 2, -1, -1));
        }

        for (int i = 0; i < 2; i++){
            bag.add(new Tile('-', 0, -1, -1));
            bag.add(new Tile('B', 3, -1, -1));
            bag.add(new Tile('C', 3, -1, -1));
            bag.add(new Tile('M', 3, -1, -1));
            bag.add(new Tile('P', 3, -1, -1));
            bag.add(new Tile('F', 4, -1, -1));
            bag.add(new Tile('H', 4, -1, -1));
            bag.add(new Tile('V', 4, -1, -1));
            bag.add(new Tile('W', 4, -1, -1));
            bag.add(new Tile('Y', 4, -1, -1));
        }

        bag.add(new Tile('K', 5, -1, -1));
        bag.add(new Tile('J', 8, -1, -1));
        bag.add(new Tile('X', 8, -1, -1));
        bag.add(new Tile('Q', 10, -1, -1));
        bag.add(new Tile('Z', 10, -1, -1));
    }

    /**
     * method to remove and return a certain number of tiles from the bag
     * @param numberOfTiles number of tiles we wish to draw from bag
     * @return a list of the tiles drawn from the bag
     */
    public ArrayList<Tile> drawFromBag(int numberOfTiles){
        if (numberOfTiles > bag.size()){
            return null;
        }

        ArrayList<Tile> returnTiles = new ArrayList<>();
        for (int i = 0; i < numberOfTiles; i++){
            Tile removeTile = bag.get(new Random().nextInt(bag.size()));
            returnTiles.add(removeTile);
            bag.remove(bag.indexOf(removeTile));
        }

        return returnTiles;
    }
}

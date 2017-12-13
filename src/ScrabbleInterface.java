/**
 * Created by shrey on 11/28/2017.
 */
import javafx.util.Pair;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ScrabbleInterface extends  JFrame implements ActionListener{

    //container for UI elements
    Container pane;

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

    //grid of clickable buttons for the Scrabble board
    JButton grid[][] = new JButton[15][15];

    //initialized game structure board
    final Board gameBoard = new Board();

    //UI representation of the scrabble board
    final JPanel scrabbleGrid = new JPanel();

    //UI representation of the panel of side buttons used to aide the game
    final JPanel sideButtons = new JPanel();

    //UI representation of player tiles
    JPanel myTiles = new JPanel();

    //list of buttons representing the player tile list
    ArrayList<javax.swing.JButton> myTilesAccessList;

    //button that represents player score
    JButton myScoreButton;

    //button that represents opponent score
    JButton opponentScoreButton;

    //button that represents game bag
    JButton bagButton;

    //debugging tool-- did the player forfeit
    boolean forfeit;

    //debugging tool-- did the player win (0), lose(1), or tie(2)
    int winLoseTie;

    //player scorer
    int myScore = 0;

    //computer score
    int opponentScore = 0;

    //number of tiles in bag
    int bagNumber = 100;

    //different possible directions for each tile
    public enum Direction { UP, DOWN, LEFT, RIGHT}

    //button representing the first button the user clicks in a two-click series
    JButton firstClick = null;

    //button that represents tiles played from the last turn
    ArrayList<Tile> mostRecentTiles;

    /**
     * method to determine what happens when the user clicks a button on the UI
     * @param actionEvent the action that was performed
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent){

        JButton x = (JButton)actionEvent.getSource();
        if (x.getText().equals("Submit Word")){
            spellcheckAddPoints(mostRecentTiles);
            mostRecentTiles.clear();
            playerDraw();
            placeComputerWord();
            computerDraw();
            changeMyTiles();
        }
        else if (x.getText().equals("Forfeit Game") || forfeit== true){
            if (myScore > opponentScore) {
                JOptionPane.showMessageDialog(pane, "You Win!\n" + myScore + " - " + opponentScore);
                winLoseTie = 0;
            }
            else if (myScore == opponentScore){
                JOptionPane.showMessageDialog(pane, "You Tie!\n" + myScore + " - " + opponentScore);
                winLoseTie = 2;
            }
            else {
                winLoseTie = 1;
                JOptionPane.showMessageDialog(pane, "You Lose!\n" + myScore + " - " + opponentScore);
            }

            System.exit(0);
        }
        else {

            if (firstClick == null) {

                firstClick = (JButton) actionEvent.getSource();
            } else {

                JButton secondClick = (JButton) actionEvent.getSource();
                moveTile(firstClick, secondClick);
                firstClick = null;
            }
        }
    }

    /**
     * checks the last played word and adds the score to the current score if the word is spelled correctly
     * @param word word array to check
     */
    public void spellcheckAddPoints(ArrayList<Tile> word){
        placeMissingTile(word);
        char [] temp = new char[word.size()];
        for (int i = 0; i < word.size(); i++){
            temp[i] = word.get(i).getLetter();
        }
        if (dictionary.ScrabbleDictionaryTrie.search(String.valueOf(temp)) == true){
            for (int i = 0; i < word.size(); i++){
                int multiplier = gameBoard.scrabbleBoard[word.get(i).getxCoordinate()][word.get(i).getyCoordinate()].getMultiplier();
                myScore+=(word.get(i).getValue()*multiplier);
            }
        }
        myScoreButton.setText("My Score: "+myScore);
    }

    /**
     * move a tile from the player's tiles to the board upon player clicks
     * @param source a button representing the tile in the player's tiles that is being moved
     * @param destination a button representing the square on the board to which it is being moved
     */
    public void moveTile(JButton source, JButton destination){
        String letter = source.getText();
        Tile choice = getPlayerTileFromLetter(letter);
        Pair<Integer, Integer> destCoordinate = getCoordinateFromButton(destination);

        if (choice == null) return;
        source.setText("");
        source.setBackground(Color.white);
        destination.setText(letter);
        destination.setBackground(new Color(222, 184, 135));
        playerTiles.remove(choice);
        choice.setCoordinate(destCoordinate.getKey(), destCoordinate.getValue());
        tilesOnBoard.add(choice);
        mostRecentTiles.add(choice);
        gameBoard.scrabbleBoard[choice.getxCoordinate()][choice.getyCoordinate()].setTile(choice);

    }

    /**
     * returns the tile the player has that represents a certain  letter
     * @param letter letter in question
     * @return the tile which represents the letter in question
     */
    public Tile getPlayerTileFromLetter(String letter){

        for (int i = 0; i < 7; i++){

            if ((Character.toString(playerTiles.get(i).getLetter())).equals(letter)){
                return playerTiles.get(i);
            }

        }
        return null;
    }

    /**
     * returns the coordinates on the scrabble board grid of a certain button
     * @param button button in question
     * @return a pair of integers representing the (x, y) coordinate of the button on the grid
     */
    public Pair<Integer, Integer> getCoordinateFromButton(JButton button){
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if (grid[i][j].equals(button))
                    return new Pair(i, j);
            }
        }
        return null;
    }

    /**
     * places the missing tile from the most recently played word (the letter which was already placed) on the board
     * @param tiles tiles in the most recently placed list
     */
    public void placeMissingTile(ArrayList<Tile> tiles){
        for (int i = 0; i < tiles.size(); i++){
            int x = tiles.get(i).getxCoordinate();
            int y = tiles.get(i).getyCoordinate();

            if (x+1 < 15 && gameBoard.scrabbleBoard[x+1][y].getTile() != null && !mostRecentTiles.contains(gameBoard.scrabbleBoard[x+1][y].getTile())){
                mostRecentTiles.add(i, gameBoard.scrabbleBoard[x+1][y].getTile());
            }
            else if (x-1 >= 0 && gameBoard.scrabbleBoard[x-1][y].getTile() != null && !mostRecentTiles.contains(gameBoard.scrabbleBoard[x-1][y].getTile())){
                mostRecentTiles.add(i, gameBoard.scrabbleBoard[x-1][y].getTile());
            }
            else if (y + 1 < 15 && gameBoard.scrabbleBoard[x][y+1].getTile() != null && !mostRecentTiles.contains(gameBoard.scrabbleBoard[x][y+1].getTile())){
                mostRecentTiles.add(i, gameBoard.scrabbleBoard[x][y+1].getTile());
            }
            else if (y - 1 >= 0 && gameBoard.scrabbleBoard[x][y-1].getTile() != null && !mostRecentTiles.contains(gameBoard.scrabbleBoard[x][y-1].getTile())){
                mostRecentTiles.add(i, gameBoard.scrabbleBoard[x][y-1].getTile());
            }
            else
            {}
        }
    }

    /**
     * constructs a grid of buttons representing the board and inserts it into the UI
     * @param pane
     */
    public void addComponentsGrid(final Container pane){
        scrabbleGrid.setLayout(new GridLayout(15, 15));
        for (int width = 0; width < 15; width++){
            for (int height = 0; height < 15; height++){
                String squareDescriptor = "";
                Color squareColor = Color.gray;
                int multiplier = gameBoard.scrabbleBoard[width][height].getMultiplier();
                boolean isWord = gameBoard.scrabbleBoard[width][height].getMultiplyWord();

                if (multiplier == 2){
                    if (isWord){
                        squareDescriptor = "2W";
                        squareColor = Color.pink;
                    }
                    else{
                        squareDescriptor = "2L";
                        squareColor = new Color(0, 204, 0);
                    }
                }
                if (multiplier == 3){
                    if (isWord){
                        squareDescriptor = "3W";
                        squareColor = Color.red;
                    }
                    else{
                        squareDescriptor = "3L";
                        squareColor = new Color(135, 206, 235);
                    }
                }

                if (width == 7 && height == 7){
                    squareDescriptor = "\u2605";
                    squareColor = Color.pink;
                }
                JButton button = new JButton("<html><center>"+squareDescriptor+"</center></html>");
                button.setBorder(new LineBorder(Color.black));
                button.setBackground(squareColor);
                button.setPreferredSize(new Dimension(120, 120));
                button.setFont(new Font("Arial", Font.PLAIN, 70));
                button.addActionListener(this);
                scrabbleGrid.add(button);
                grid[width][height] = button;
            }
        }
        pane.add(scrabbleGrid, BorderLayout.CENTER);

    }

    /**
     * constructs a panel of buttons representing the helper buttons on the side to allow for different player
     * actions and inserts them into the UI
     * @param pane
     */
    public void addComponentsButtons(final Container pane){
        String [] howToStrings = {"Player Guide","Basic Rules", "Board Guide", "What is a word?", "Challenges"};
        JComboBox howToBox = new JComboBox(howToStrings);
        howToBox.setPreferredSize(new Dimension(240, 120));
        howToBox.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton gameOptionsBox = new JButton("Submit Word");
        gameOptionsBox.setPreferredSize(new Dimension(240, 120));
        gameOptionsBox.setFont(new Font("Arial", Font.PLAIN, 30));
        gameOptionsBox.addActionListener(this);

        sideButtons.setLayout(new GridLayout(6, 1));
        opponentScoreButton = createSidePanelButton("Opponent Score: "+opponentScore);
        sideButtons.add(opponentScoreButton);
        sideButtons.add(howToBox);
        sideButtons.add(gameOptionsBox);
        sideButtons.add(createSidePanelButton("Forfeit Game"));
        bagButton = createSidePanelButton("Bag (" + bagNumber + ")");
        sideButtons.add(bagButton);
        myScoreButton = createSidePanelButton("My Score: "+myScore);
        sideButtons.add(myScoreButton);

        pane.add(sideButtons, BorderLayout.LINE_END);
    }

    /**
     * helper function to create buttons for the side panel
     * @param text button label
     * @return button for the side panel with desired label
     */
    public JButton createSidePanelButton(String text){
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(240, 120));
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.addActionListener(this);
        return button;
    }

    /**
     * constructs a set of player tiles and inserts them into the bottom of the UI
     * @param pane
     */
    public void addComponentsMyTiles(final Container pane){
        myTiles.setLayout(new GridLayout(1, 17));

        for (int i = 0; i < 17; i++) {

            JButton b;
            int addtl = 0;
            if (i > 10 || i < 4 || playerTiles.size() == 0){
                b = new JButton("");
                b.setBorder(new LineBorder(Color.white));
            }
            else {
                addtl = 1;
                b = new JButton( ""+playerTiles.get(i-4).getLetter() );
                b.setFont(new Font("Arial", Font.PLAIN, 70));
                b.setBackground(new Color(222, 184, 135));
                b.setBorder(new LineBorder(Color.black));

            }
            b.addActionListener(this);
            b.setPreferredSize(new Dimension(120, 120));
            myTiles.add(b);
            if (addtl == 1)
                myTilesAccessList.add(b);
        }

        pane.add(myTiles, BorderLayout.SOUTH);
    }

    /**
     * changes the player tiles into the correct format when new tiles are drawn
     */
    public void changeMyTiles(){
        for (int i = 0; i < myTilesAccessList.size(); i++){
            myTilesAccessList.get(i).setText(Character.toString(playerTiles.get(i).getLetter()));
            myTilesAccessList.get(i).setBackground(new Color(222, 184, 135));
        }
    }

    /**
     * checks if the chosen word could fit within the bounds of the scrabble board without running into other pieces
     * @param checkAgainst tiles on board to check if we run into
     * @param tiles tiles we are trying to place on the board
     * @return letter that represents the intersection of the new word and current board
     */
    public Tile checkIfWordCouldFitOnBoard(ArrayList<Tile> checkAgainst, ArrayList<Tile> tiles){
        for (int i = 0; i < tiles.size(); i++){
            for (int j = 0;  j < checkAgainst.size(); j++){
                if (tiles.get(i).getLetter() == checkAgainst.get(j).getLetter())
                    return checkAgainst.get(j);
            }
        }
        return null;
    }


    /**
     * finds a proper word to place on the board and places it from the computer end; if it can't find a word it passes
     * up its turn
     */
    public void placeComputerWord(){
        Scrabble scrabble = new Scrabble();
        ArrayList<Tile> totalTiles = new ArrayList<>();
        for (int i = 0; i < computerTiles.size(); i++){
            totalTiles.add(computerTiles.get(i));
        }
        totalTiles.add(tilesOnBoard.get(0));
        ArrayList<ArrayList<Tile>> wordsetNoSpellcheck = (scrabble.permutePowerSet(totalTiles));
        ArrayList<ArrayList<Tile>> wordset = new ArrayList<>();

        for (int i = 0; i < wordsetNoSpellcheck.size(); i++){
            Tile temp = checkIfWordCouldFitOnBoard(tilesOnBoard, wordsetNoSpellcheck.get(i));
            if ( temp == null){
                continue;
            }
            if (dictionary.ScrabbleDictionaryTrie.search(scrabble.tilesToString(wordsetNoSpellcheck.get(i)))){
                wordset.add((wordsetNoSpellcheck.get(i)));
            }
        }
        int count = 0;
        while (true) {
            ArrayList<Tile> wordPick = wordset.get(new Random().nextInt(wordset.size()));
            Tile onBoardTile = checkIfWordCouldFitOnBoard(tilesOnBoard, wordPick);

            ArrayList<Character> pickChars = new ArrayList<>();
            for (int i = 0; i < wordPick.size(); i++) {
                pickChars.add(wordPick.get(i).getLetter());
            }

            int x = onBoardTile.getxCoordinate();
            int y = onBoardTile.getyCoordinate();


            int index = 0;
            for (int i = 0; i < wordPick.size(); i++) {
                if (wordPick.get(i).getLetter() == onBoardTile.getLetter())
                    index = i;
            }

            boolean ch = changeBoardWithLetters(x, y, index, wordPick);
            if (ch == true || count == 20) {
                break;
            }
            count ++;
        }
    }

    /**
     * chandes the board interface given the current word being chosen
     * @param x x value of board intercept with new word
     * @param y y value of board intercept with new word
     * @param index index of intersection letter on new word
     * @param wordPick word to put on board
     * @return whether or not placing the word on the board was successful
     */
    public boolean changeBoardWithLetters(int x, int y, int index, ArrayList<Tile> wordPick){
        Tile curr = gameBoard.scrabbleBoard[x][y].getTile();
        int horizontalLeftReach = 0;
        for (int i = y-1; i >= 0; i--){
            if (gameBoard.scrabbleBoard[x][i].getTile() == null)
                horizontalLeftReach++;
            else
                break;
        }

        int horizontalRightReach = 0;
        for (int i = y+1; i < 15; i++){
            if (gameBoard.scrabbleBoard[x][i].getTile() == null)
                horizontalRightReach++;
            else
                break;
        }

        int verticalUpReach = 0;
        for (int i = x-1; i >= 0; i--){
            if (gameBoard.scrabbleBoard[i][y].getTile() == null)
                verticalUpReach++;
            else
                break;
        }

        int verticalDownReach = 0;
        for (int i = x+1; i < 15; i++){
            if (gameBoard.scrabbleBoard[i][y].getTile() == null) {
                verticalDownReach++;
            }
            else
                break;
        }

        if (wordPick.size() - index < horizontalRightReach && index < horizontalLeftReach){

            for (int i = 0; i < wordPick.size(); i++){
                grid[x][(y-index)+i].setText(String.valueOf(wordPick.get(i).getLetter()));
                grid[x][(y-index)+i].setBackground(new Color(222, 184, 135));
                gameBoard.scrabbleBoard[x][(y-index)+i].setTile(wordPick.get(i));

                if (i != index){
                    computerTiles.remove(computerTiles.indexOf(wordPick.get(i)));
                }
            }
            for (int i = 0; i < wordPick.size(); i++){
                opponentScore+= wordPick.get(i).getValue();
            }
            opponentScoreButton.setText("Op Score: "+opponentScore);
            return true;
        }

        if (wordPick.size() - index < verticalUpReach && index < verticalDownReach){
            for (int i = 0; i < wordPick.size(); i++){
                grid[(x-index)+i][y].setText(String.valueOf(wordPick.get(i).getLetter()));
                grid[(x-index)+i][y].setBackground(new Color(222, 184, 135));
                gameBoard.scrabbleBoard[(x-index)+i][y].setTile(wordPick.get(i));

                if (i != index){
                    computerTiles.remove(computerTiles.indexOf(wordPick.get(i)));
                }
            }
            for (int i = 0; i < wordPick.size(); i++){
                opponentScore+= wordPick.get(i).getValue();
            }
            opponentScoreButton.setText("Op Score: "+opponentScore);
            return true;
        }
    return false;
    }

    /**
     * called at the beginning of the human player's turn-- draws tiles from bag
     */
    public void playerDraw(){
        int difference = 7-playerTiles.size();
        bagNumber -= difference;
        if (bagNumber <= 0){
            if (myScore > opponentScore) {
                winLoseTie =0;
                JOptionPane.showMessageDialog(pane, "Game Over: Ran out of tiles. You Win!\n" + myScore + " - " + opponentScore);

            }
            else if (myScore == opponentScore){
                winLoseTie =2;
                JOptionPane.showMessageDialog(pane, "Game Over: Ran out of tiles. You Tie!\n" + myScore + " - " + opponentScore);

            }
            else {
                winLoseTie = 1;
                JOptionPane.showMessageDialog(pane, "Game Over: Ran out of tiles. You Lose!\n" + myScore + " - " + opponentScore);
            }

            System.exit(0);
        }
        bagButton.setText("Bag (" + bagNumber + ")");
        ArrayList<Tile> newTiles = tileBag.drawFromBag(7-playerTiles.size());
        for (int i = 0; i < newTiles.size(); i++){
            playerTiles.add(newTiles.get(i));
        }
    }

    /**
     * called at the beginning of the computer player's turn-- draws tiles from bag
     */
    public void computerDraw(){
        int difference = 7-computerTiles.size();
        bagNumber -= difference;
        if (bagNumber <= 0){
            forfeit = true;
            if (myScore > opponentScore) {
                winLoseTie = 0;
                JOptionPane.showMessageDialog(pane, "Game Over: Ran out of tiles. You Win!\n" + myScore + " - " + opponentScore);
            }
            else if (myScore == opponentScore){
                winLoseTie = 2;
                JOptionPane.showMessageDialog(pane, "Game Over: Ran out of tiles. You Tie!\n" + myScore + " - " + opponentScore);
            }
            else {
                winLoseTie = 1;
                JOptionPane.showMessageDialog(pane, "Game Over: Ran out of tiles. You Lose!\n" + myScore + " - " + opponentScore);
            }

            System.exit(0);
        }
        bagButton.setText("Bag (" + bagNumber + ")");
        ArrayList<Tile> newTiles = tileBag.drawFromBag(7-computerTiles.size());
        for (int i = 0; i < newTiles.size(); i++){
            computerTiles.add(newTiles.get(i));
        }
    }

    /**
     * constructor called every time the class is instantiated-- initializes variables
     */
    public ScrabbleInterface(){
        computerTiles = new ArrayList<>();
        playerTiles = new ArrayList<>();
        tilesOnBoard = new ArrayList<>();
        myTilesAccessList = new ArrayList<>();
        tileBag = new Bag();
        dictionary = new DictionaryStructure();
        dictionary.readInDictionary();
        bagButton = new JButton();
        mostRecentTiles = new ArrayList<>();
        forfeit = true;
        winLoseTie =0;
        playerDraw();
        computerDraw();

        pane = getContentPane();
        addComponentsGrid(pane);
        addComponentsButtons(pane);
        addComponentsMyTiles(pane);
        pack();
        setVisible(true);
    }

    public static void main(String [] args){
        ScrabbleInterface scrabble = new ScrabbleInterface();
    }
}

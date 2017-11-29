/**
 * Created by shrey on 11/28/2017.
 */
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrabbleInterface extends  JFrame implements ActionListener{

    //grid of clickable buttons for the Scrabble board
    JButton grid[][] = new JButton[15][15];

    //initialized game structure board
    final Board gameBoard = new Board();

    //UI representation of the scrabble board
    final JPanel scrabbleGrid = new JPanel();

    //UI representation of the panel of side buttons used to aide the game
    final JPanel sideButtons = new JPanel();

    //UI representation of player tiles
    final JPanel myTiles = new JPanel();

    //player scorer
    int myScore = 0;

    //computer score
    int opponentScore = 0;

    //number of tiles in bag
    int bagNumber = 86;

    //panel of tiles the player is given
    char [] tiles = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

    @Override
    public void actionPerformed(ActionEvent actionEvent){

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
                        squareColor = Color.green;
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
                scrabbleGrid.add(button);
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

        String [] gameOptionStrings = {"Game Options","Forfeit Game", "Undo Tiles", "Submit Word", "Challenge", "Quit"};
        JComboBox gameOptionsBox = new JComboBox(gameOptionStrings);
        gameOptionsBox.setPreferredSize(new Dimension(240, 120));
        gameOptionsBox.setFont(new Font("Arial", Font.PLAIN, 30));

        sideButtons.setLayout(new GridLayout(6, 1));
        sideButtons.add(createSidePanelButton("Opponent Score: "+opponentScore));
        sideButtons.add(howToBox);
        sideButtons.add(gameOptionsBox);
        sideButtons.add(createSidePanelButton("Total Score: "+ myScore + "-" + opponentScore));
        sideButtons.add(createSidePanelButton("Bag (" + bagNumber + ")"));
        sideButtons.add(createSidePanelButton("My Score: "+myScore));

        pane.add(sideButtons, BorderLayout.LINE_END);
    }

    /**
     * helper function to create buttons for the side panel
     * @param text button label
     * @return button for the side panel with desired label
     */
    public JButton createSidePanelButton(String text){
        JButton button = new JButton("<html><center>"+text+"</center></html>");
        button.setPreferredSize(new Dimension(240, 120));
        button.setFont(new Font("Arial", Font.PLAIN, 40));
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
            if (i > 10 || i < 4){
                b = new JButton("");
                b.setBorder(new LineBorder(Color.white));
            }
            else {
                b = new JButton("<html><center>" + tiles[i-4] + "</center></html>");
                b.setFont(new Font("Arial", Font.PLAIN, 70));
                b.setBackground(new Color(222, 184, 135));
                b.setBorder(new LineBorder(Color.black));
            }
            b.setPreferredSize(new Dimension(120, 120));
            myTiles.add(b);
        }

        pane.add(myTiles, BorderLayout.SOUTH);
    }

    /**
     * generates the full scrabble interface
     */
    public void generateScrabbleUI(){
        ScrabbleInterface scrabble = new ScrabbleInterface();
        Container pane = scrabble.getContentPane();
        scrabble.addComponentsGrid(pane);
        scrabble.addComponentsButtons(pane);
        scrabble.addComponentsMyTiles(pane);
        scrabble.pack();
        scrabble.setVisible(true);
    }
}

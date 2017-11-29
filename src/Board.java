/**
 * Created by shrey on 11/13/2017.
 */
public class Board {

    //grid of squares representing a scrabble board
    public Square [][] scrabbleBoard;

    /**
     * Initializes the Scrabble board with squares of certain values (double word score, double letter score, triple
     * word score, triple letter score)
     */
    public Board(){
        scrabbleBoard = new Square[15][15];
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                scrabbleBoard[i][j] = new Square();


                if ((i==0&&j==0) || (i==0&&j==7) || (i==0&&j==14) || (i==7&&j==0) || (i==14&&j==0) || (i==14&&j==0) || (i==14&&j==7) || (i==14&&j==14) || (i==7 && j == 14)){
                    scrabbleBoard[i][j].setMultiplier(3);
                    scrabbleBoard[i][j].setMultiplyWord(true);
                }

                else if ((i==0&&j==3)||(i==0&&j==11)||(i==2&&j==6)||(i==2&&j==8)||(i==3&&j==0)||(i==3&&j==7)||(i==3&&j==14)||(i==6&&j==2)||(i==6&&j==6)||(i==6&&j==8)||(i==6&&j==12)||(i==7&&j==3)||(i==7&&j==11)||(i==8&&j==2)||(i==8&&j==6)||(i==8&&j==8)||(i==8&&j==12)||(i==11&&j==0)||(i==11&&j==7)||(i==11&&j==14)||(i==12&&j==6)||(i==12&&j==8)||(i==14&&j==3)||(i==14&&j==11)){
                    scrabbleBoard[i][j].setMultiplier(2);
                    scrabbleBoard[i][j].setMultiplyWord(false);
                }

                else if ((i==1&&j==5)||(i==1&&j==9)||(i==5&&j==1)||(i==5&&j==5)||(i==5&&j==9)||(i==5&&j==13)||(i==9&&j==1)||(i==9&&j==1)||(i==9&&j==5)||(i==9&&j==9)||(i==9&&j==13)||(i==13&&j==5)||(i==13&&j==9)) {
                    scrabbleBoard[i][j].setMultiplier(3);
                    scrabbleBoard[i][j].setMultiplyWord(false);
                }

                else if (((i==j)||(j ==14 -i))&&!(i==7&&j==7)){
                    scrabbleBoard[i][j].setMultiplier(2);
                    scrabbleBoard[i][j].setMultiplyWord(true);
                }

                else{
                    scrabbleBoard[i][j].setMultiplier(1);
                    scrabbleBoard[i][j].setMultiplyWord(false);
                }
            }
        }
    }
}

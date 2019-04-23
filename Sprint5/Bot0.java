package Sprint5;

import javafx.geometry.Pos;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Bot0 implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me, opponent;
    private BoardAPI board;
    private CubeAPI cube;
    private MatchAPI match;
    private InfoPanelAPI info;
    private int[] weights = {1, 1, 1, 1, 1, 1, 1, 1, 1};

    Bot0(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.cube = cube;
        this.match = match;
        this.info = info;
    }

    public String getName() {
        return "Bot0"; // must match the class name
    }

    public String getCommand(Plays possiblePlays) {
        // Add your code here
      //  getDoubleDecision();
        return "1";
    }

    public String getDoubleDecision() {
        // Add your code here
        int chanceOfWinning = calculateChanceOfWinning();
        System.out.println("" + chanceOfWinning);
        //If both players 2 points away from winning
        if(me.getScore() == 13 && opponent.getScore() == 13){
            if(chanceOfWinning >= 0 && chanceOfWinning <= 50){
                return "n";
            }
            else if(chanceOfWinning >= 50 && chanceOfWinning <= 75){
                return "y";
            }
            else if(chanceOfWinning >= 75 && chanceOfWinning <= 100){
                return "y";
            }
        }
        else{
            if(chanceOfWinning >= 0 && chanceOfWinning <= 66){
                return "n";
            }
            else if(chanceOfWinning >= 66 && chanceOfWinning <= 75){
                return "y";
            }
            else if(chanceOfWinning >= 75 && chanceOfWinning <= 80){
                return "y";
            }}


        return "n";
    }

    private int calculateChanceOfWinning(){
        return me.getScore() / (me.getScore() + opponent.getScore()) * 100;
    }

    //  Gets the average weighted feature score for a possible play
    private int getFeatureScore(Play possiblePlay) {
        int featureScore = 0;

        /*
            Move Position to new Position according to possiblePlay
        */

        featureScore += weights[0] * pipCountDifference();
        featureScore += weights[1] * blockBlotDifference();
        featureScore += weights[2] * homeboardBlocks();
        featureScore += weights[3] * capturedPrime();
        featureScore += weights[4] * AnchorChecker();
        featureScore += weights[5] * escapedCheckers();
        featureScore += weights[6] * homeCheckersNumber();
        featureScore += weights[7] * bearedOffNumber();
        featureScore += weights[8] * pointsCovered();

        int totalWeights = IntStream.of(weights).sum();

        return featureScore / totalWeights;
    }

//  Board Feature Calculations for Bot Decision Making
    //  Pip Count Difference (Feature 1)
        //  Input           - Board State
        //  Output          - Feature score for feature 1 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   Pd = P0 - P1
            //                  P0 = (25)E(p=0) pC0(p)
    private int pipCountDifference() {
        int Pd;     //  Player Difference Score
        int P0 = 0; //  Player 0 Score
        int P1 = 0; //  Player 1 Score

        for(int i=1; i<25; i++) {
            P0 += i * board.getNumCheckers(me.getId(), i);
            P1 += i * board.getNumCheckers(opponent.getId(), i);
        }

        Pd = (P0-P1)/360;   //  Player Difference divided by 360 (max number of points)
        return Pd;
    }

    //  Block-Blot Difference (Feature 2)
        //  Input           - Board State
        //  Output          - Feature score for feature 2 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   Sd = Kx - Tx
    private int blockBlotDifference() {
        int Sd;
        int Kx = 0; //  Number of Blocks by Player 0
        int Tx = 0; //  Number of Blots by Player 1

        /*
            Count board counters to get Kx and Tx
        */
        for(int i=1; i<25; i++) {
            if(board.getNumCheckers(me.getId(), i) > 1)
                Kx++;
            else if(board.getNumCheckers(opponent.getId(), i) == 1)
                Tx++;
        }

        Sd = (Kx - Tx) / 7; //  Blot-Block Difference divided by 7 (max number of points)
        return Sd;
    }

    //  Number of Homeboard Blocks (Feature 3)
        //  Input           - Board State
        //  Output          - Feature score for feature 3 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   H0 = (6)E(p=1) (P0(p) > 1)
    private int homeboardBlocks() {
        int count = 0;

        /*
            Count board counters to get count;
        */
        for(int i=1; i<7; i++) {
            if(board.getNumCheckers(me.getId(), i) > 1)
                count += i;
        }

        count /= 21;    //  Home board Block count divided by 21 (Max number of points)
        return count;
    }

    //  Length of Prime with Captured Checker (Feature 4)
        //  Input           - Board State
        //  Output          - Feature score for feature 4 (between 0 and 1 where 1 is a definite win)
    private int capturedPrime() {
        int points = 0;
        int sizeOfPrime = 0;
        int numberOfCaptured = 0;

        /*
            Count board counters to get sizeOfPrime and numberOfCaptured
        */
        for(int i=1; i<25; i++) {
            if(board.getNumCheckers(me.getId(), i) > 1)
               sizeOfPrime++;
            else if(board.getNumCheckers(me.getId(), i) == 0) {
                points += sizeOfPrime*numberOfCaptured;
                sizeOfPrime = 0;
            }

            if(board.getNumCheckers(opponent.getId(), i) > 0)
                numberOfCaptured += board.getNumCheckers(opponent.getId(), i);
        }

//  Recheck max number of points    --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        points /= 105; //  capturedPrime points divided by 105 (max number of points)
        return points;
    }

    //  Anchor Checker (Feature 5)
        //  Input           - Board State
        //  Output          - Feature score for feature 5 (between 0 and 1 where 1 is a definite win)
    private int AnchorChecker() {
        int anchorPoint = 0;

        /*
            Count board counters to get anchorPoints
        */
        for(int i=1; i<7; i++) {
            if(board.getNumCheckers(me.getId(), i) > 1)
                anchorPoint += i;
        }

        return anchorPoint/21; //   anchorPoints divided by 21 (max number of points)
    }

    //  Number of Escaped Checkers (Feature 6)
        //  Input           - Board State
        //  Output          - Feature score for feature 6 (between 0 and 1 where 1 is a definite win)
    private int escapedCheckers() {
        int escaped = 0;
        int opponentCheckers = 0;

        /*
            Count board counters to get escaped checkers
        */
        for(int i=0; i<25 && opponentCheckers < 15; i++) {
            if(board.getNumCheckers(opponent.getId(), i) > 0)
                opponentCheckers += board.getNumCheckers(opponent.getId(), i);
            else if(board.getNumCheckers(me.getId(), i) > 0)
                escaped++;
        }
        escaped = 15 - escaped;

        escaped /= 15;  //  escaped divided by 15 (max number of points)
        return escaped;
    }

    //  Number of Home Checkers (Feature 7)
        //  Input           - Board State
        //  Output          - Feature score for feature 7 (between 0 and 1 where 1 is a definite win)
    private int homeCheckersNumber() {
        int homeCheckers = 0;

        /*
            Count board counters to get home checkers
        */
        for(int i=19; i<25; i++)
            homeCheckers += board.getNumCheckers(me.getId(), i);

        homeCheckers /= 15; //  homeCheckers divided by 15 (max number of points)
        return homeCheckers;
    }

    //  Number of Beared Off Checkers (Feature 8)
        //  Input           - Board State
        //  Output          - Feature score for feature 8 (between 0 and 1 where 1 is a definite win)
    private int bearedOffNumber() {
        int bearOff = 0;

        /*
            Count board counters to get bear off
        */
        bearOff += board.getNumCheckers(me.getId(), 25);

        bearOff /= 15;  //  bearOff divided by 15 (max number of points)
        return bearOff;
    }

    //  pointsCoveredNumbers (Feature 9)
        //  Input           - Board State
        //  Output          - Feature score for feature 9 (between 0 and 1 where 1 is a definite win)
    private int pointsCovered() {
        int pointsCovered = 0;

        /*
            Count board counters to get pointsCovered
        */
        for(int i=1; i<25; i++) {
            if(board.getNumCheckers(me.getId(), i) > 0)
                pointsCovered += i;
        }

        pointsCovered /= 300; // pointsCovered divided by 225 (max number of points)
        return pointsCovered;
    }
}

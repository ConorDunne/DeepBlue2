package Sprint5;

import javafx.geometry.Pos;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DeepBlue2 implements BotAPI {
	
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
    private int[] myPositions, opponentPositions = new int[26];

    DeepBlue2(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.cube = cube;
        this.match = match;
        this.info = info;
    }

    public String getName() {
        return "DeepBlue2"; // must match the class name
    }

    public String getCommand(Plays possiblePlays) {
        // Add your code here
        return "A";
    }

    public String getDoubleDecision() {
        // Add your code here
     /*   int chanceOfWinning = calculateChanceOfWinning();
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
            }}*/


        return "n";
    }

    private int calculateChanceOfWinning(){
        return me.getScore() / (me.getScore() + opponent.getScore()) * 100;
    }


    //  Needs input of list of moves
    private int bestMove() {
        int bestMove = 0;

        //  Get current positions
        for(int i=0; i<26; i++) {
            myPositions[i] = board.getNumCheckers(me.getId(), i);
            opponentPositions[i] = board.getNumCheckers(opponent.getId(), i);
        }

        /*
            Loop through possible moves and input adjusted position array to getFeatureScore
        */

        //              Change to return of best move
        return bestMove;
    }


    //  Calculates probability of winning by looking at current position and win features
    int currentWinProbability() {
        for(int i=0; i<26; i++) {
            myPositions[i] = board.getNumCheckers(me.getId(), i);
            opponentPositions[i] = board.getNumCheckers(opponent.getId(), i);
        }

        return getFeatureScore(myPositions, opponentPositions);
    }


    //  Gets the average weighted feature score for a possible play
    private int getFeatureScore(int[] myCounter, int[] opponentCounters) {
        int featureScore = 0;

        featureScore += weights[0] * pipCountDifference(myCounter, opponentCounters);
        featureScore += weights[1] * blockBlotDifference(myCounter, opponentCounters);
        featureScore += weights[2] * homeboardBlocks(myCounter);
        featureScore += weights[3] * capturedPrime(myCounter, opponentCounters);
        featureScore += weights[4] * AnchorChecker(myCounter);
        featureScore += weights[5] * escapedCheckers(myCounter, opponentCounters);
        featureScore += weights[6] * homeCheckersNumber(myCounter);
        featureScore += weights[7] * bearedOffNumber(myCounter);
        featureScore += weights[8] * pointsCovered(myCounter);

        int totalWeights = IntStream.of(weights).sum();

        return featureScore / totalWeights;
    }

//  Board Feature Calculations for Bot Decision Making
    //  Pip Count Difference (Feature 1)
        //  Input           - Board State
        //  Output          - Feature score for feature 1 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   Pd = P0 - P1
            //                  P0 = (25)E(p=0) pC0(p)
    private int pipCountDifference(int[] myCounter, int[] opponentCounters) {
        int Pd;     //  Player Difference Score
        int P0 = 0; //  Player 0 Score
        int P1 = 0; //  Player 1 Score

        for(int i=1; i<25; i++) {
            P0 += i * myCounter[i];
            P1 += i * opponentCounters[i];
        }

        Pd = (P0-P1)/360;   //  Player Difference divided by 360 (max number of points)
        return Pd;
    }

    //  Block-Blot Difference (Feature 2)
        //  Input           - Board State
        //  Output          - Feature score for feature 2 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   Sd = Kx - Tx
    private int blockBlotDifference(int[] myCounter, int[] opponentCounters) {
        int Sd;
        int Kx = 0; //  Number of Blocks by Player 0
        int Tx = 0; //  Number of Blots by Player 1

        /*
            Count board counters to get Kx and Tx
        */
        for(int i=1; i<25; i++) {
            if(myCounter[i] > 1)
                Kx++;
            else if(opponentCounters[i] == 1)
                Tx++;
        }

        Sd = (Kx - Tx) / 7; //  Blot-Block Difference divided by 7 (max number of points)
        return Sd;
    }

    //  Number of Homeboard Blocks (Feature 3)
        //  Input           - Board State
        //  Output          - Feature score for feature 3 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   H0 = (6)E(p=1) (P0(p) > 1)
    private int homeboardBlocks(int[] myCounter) {
        int count = 0;

        /*
            Count board counters to get count;
        */
        for(int i=1; i<7; i++) {
            if(myCounter[i] > 1)
                count += i;
        }

        count /= 21;    //  Home board Block count divided by 21 (Max number of points)
        return count;
    }

    //  Length of Prime with Captured Checker (Feature 4)
        //  Input           - Board State
        //  Output          - Feature score for feature 4 (between 0 and 1 where 1 is a definite win)
    private int capturedPrime(int[] myCounter, int[] opponentCounters) {
        int points = 0;
        int sizeOfPrime = 0;
        int numberOfCaptured = 0;

        /*
            Count board counters to get sizeOfPrime and numberOfCaptured
        */
        for(int i=1; i<25; i++) {
            if(myCounter[i] > 1)
               sizeOfPrime++;
            else if(myCounter[i] == 0) {
                points += sizeOfPrime*numberOfCaptured;
                sizeOfPrime = 0;
            }

            if(opponentCounters[i] > 0)
                numberOfCaptured += opponentCounters[i];
        }

//  Recheck max number of points    --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        points /= 105; //  capturedPrime points divided by 105 (max number of points)
        return points;
    }

    //  Anchor Checker (Feature 5)
        //  Input           - Board State
        //  Output          - Feature score for feature 5 (between 0 and 1 where 1 is a definite win)
    private int AnchorChecker(int[] myCounter) {
        int anchorPoint = 0;

        /*
            Count board counters to get anchorPoints
        */
        for(int i=1; i<7; i++) {
            if(myCounter[i] > 1)
                anchorPoint += i;
        }

        return anchorPoint/21; //   anchorPoints divided by 21 (max number of points)
    }

    //  Number of Escaped Checkers (Feature 6)
        //  Input           - Board State
        //  Output          - Feature score for feature 6 (between 0 and 1 where 1 is a definite win)
    private int escapedCheckers(int[] myCounter, int[] opponentCounters) {
        int escaped = 0;
        int opponentCheckers = 0;

        /*
            Count board counters to get escaped checkers
        */
        for(int i=0; i<25 && opponentCheckers < 15; i++) {
            if(opponentCounters[i] > 0)
                opponentCheckers += opponentCounters[i];
            else if(myCounter[i] > 0)
                escaped++;
        }
        escaped = 15 - escaped;

        escaped /= 15;  //  escaped divided by 15 (max number of points)
        return escaped;
    }

    //  Number of Home Checkers (Feature 7)
        //  Input           - Board State
        //  Output          - Feature score for feature 7 (between 0 and 1 where 1 is a definite win)
    private int homeCheckersNumber(int[] myCounter) {
        int homeCheckers = 0;

        /*
            Count board counters to get home checkers
        */
        for(int i=19; i<25; i++)
            homeCheckers += myCounter[i];

        homeCheckers /= 15; //  homeCheckers divided by 15 (max number of points)
        return homeCheckers;
    }

    //  Number of Beared Off Checkers (Feature 8)
        //  Input           - Board State
        //  Output          - Feature score for feature 8 (between 0 and 1 where 1 is a definite win)
    private int bearedOffNumber(int[] myCounter) {
        int bearOff = 0;

        /*
            Count board counters to get bear off
        */
        bearOff += myCounter[25];

        bearOff /= 15;  //  bearOff divided by 15 (max number of points)
        return bearOff;
    }

    //  pointsCoveredNumbers (Feature 9)
        //  Input           - Board State
        //  Output          - Feature score for feature 9 (between 0 and 1 where 1 is a definite win)
    private int pointsCovered(int[] myCounter) {
        int pointsCovered = 0;

        /*
            Count board counters to get pointsCovered
        */
        for(int i=1; i<25; i++) {
            if(myCounter[i] > 0)
                pointsCovered += i;
        }

        pointsCovered /= 300; // pointsCovered divided by 225 (max number of points)
        return pointsCovered;
    }
}

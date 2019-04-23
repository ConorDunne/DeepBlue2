import java.lang.reflect.Array;
import java.util.ArrayList;
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
    private int[] myPositions = new int[26];
    private int[] opponentPositions = new int[26];
    private int[] weights = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        /*
                    Weight Value Position Meanings
            1   Pip-Count Difference
            2   Block-Blot Difference
            3   Number Home Board Blocks
            4   Length of Prime
            5   Anchor in Opponents Home Board
            6   Escaped Checkers
            7   Home Board Checkers
            8   Checkers Taken off
            9   Number Points Covered
        */


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
        int move = bestMove(possiblePlays);
        System.out.println("" + me.getScore() + " Opp: " + opponent.getScore() + " Chance: " + currentWinProbability());
        System.out.println(possiblePlays.plays);
        return Integer.toString(move);
    }

    public String getDoubleDecision() {
        // Add your code here
        double chanceOfWinning = currentWinProbability();
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


    //  Needs input of list of moves
    private int bestMove(Plays p) {
        int bestMove = 0;
        ArrayList<Play> plays = p.plays;
        double[] moveFeatures = new double[plays.size()];
        Arrays.fill(moveFeatures, 0);

        /*
            Loop through possible moves and input adjusted position array to getFeatureScore
        */
        for(int i=0; i<plays.size(); i++) {
            ArrayList<int[]> positions = getCurrentPosition();
            myPositions = positions.get(0);
            opponentPositions = positions.get(1);

            Play play = plays.get(i);

            for(int j=0; j<play.numberOfMoves(); j++) {
                Move m = play.getMove(j);

                myPositions[m.getFromPip()]--;
                myPositions[m.getToPip()]++;

                if(m.isHit()) {
                    opponentPositions[m.getToPip()]--;
                    opponentPositions[0]++;
                }
            }

            moveFeatures[i] = getFeatureScore(myPositions, opponentPositions);
            if(moveFeatures[i] > moveFeatures[bestMove])
                bestMove = i;
        }

        System.out.println("Best Move Index: " + bestMove + "\tBest Move Percentage: " + (moveFeatures[bestMove]));
        return bestMove+1;
    }

    private ArrayList<int[]> getCurrentPosition() {
        ArrayList<int[]> positions = new ArrayList<>();

        for(int i=0; i<26; i++) {
            myPositions[i] = board.getNumCheckers(me.getId(), i);
            opponentPositions[i] = board.getNumCheckers(opponent.getId(), i);
        }

        positions.add(0, myPositions);
        positions.add(1, opponentPositions);
        return positions;
    }

    //  Calculates probability of winning by looking at current position and win features
    private double currentWinProbability() {
        ArrayList<int[]> positions = getCurrentPosition();

        return getFeatureScore(positions.get(0), positions.get(1));
    }


    //  Gets the average weighted feature score for a possible play
    private double getFeatureScore(int[] myCounter, int[] opponentCounters) {
        double featureScore = 0;

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
    private double pipCountDifference(int[] myCounter, int[] opponentCounters) {
        int Pd;     //  Player Difference Score
        int P0 = 0; //  Player 0 Score
        int P1 = 0; //  Player 1 Score

        for(int i=1; i<25; i++) {
            P0 += i * myCounter[i];
            P1 += i * opponentCounters[i];
        }

        Pd = P0 - P1;
        return Pd;   //  Player Difference divided by 360 (max number of points)
    }

    //  Block-Blot Difference (Feature 2)
        //  Input           - Board State
        //  Output          - Feature score for feature 2 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   Sd = Kx - Tx
    private double blockBlotDifference(int[] myCounter, int[] opponentCounters) {
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

        Sd = Kx - Tx;
        return Sd;   //  Blot-Block Difference divided by 7 (max number of points)
    }

    //  Number of Homeboard Blocks (Feature 3)
        //  Input           - Board State
        //  Output          - Feature score for feature 3 (between 0 and 1 where 1 is a definite win)
            //  Equations   -   H0 = (6)E(p=1) (P0(p) > 1)
    private double homeboardBlocks(int[] myCounter) {
        int count = 0;

        /*
            Count board counters to get count;
        */
        for(int i=1; i<7; i++) {
            if(myCounter[i] > 1)
                count += i;
        }

        return count;    //  Home board Block count divided by 21 (Max number of points)
    }

    //  Length of Prime with Captured Checker (Feature 4)
        //  Input           - Board State
        //  Output          - Feature score for feature 4 (between 0 and 1 where 1 is a definite win)
    private double capturedPrime(int[] myCounter, int[] opponentCounters) {
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
        return points; //  capturedPrime points divided by 105 (max number of points)
    }

    //  Anchor Checker (Feature 5)
        //  Input           - Board State
        //  Output          - Feature score for feature 5 (between 0 and 1 where 1 is a definite win)
    private double AnchorChecker(int[] myCounter) {
        int anchorPoint = 0;

        /*
            Count board counters to get anchorPoints
        */
        for(int i=1; i<7; i++) {
            if(myCounter[i] > 1)
                anchorPoint += i;
        }

        return anchorPoint; //   anchorPoints divided by 21 (max number of points)
    }

    //  Number of Escaped Checkers (Feature 6)
        //  Input           - Board State
        //  Output          - Feature score for feature 6 (between 0 and 1 where 1 is a definite win)
    private double escapedCheckers(int[] myCounter, int[] opponentCounters) {
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

        return escaped;  //  escaped divided by 15 (max number of points)
    }

    //  Number of Home Checkers (Feature 7)
        //  Input           - Board State
        //  Output          - Feature score for feature 7 (between 0 and 1 where 1 is a definite win)
    private double homeCheckersNumber(int[] myCounter) {
        int homeCheckers = 0;

        /*
            Count board counters to get home checkers
        */
        for(int i=19; i<25; i++)
            homeCheckers += myCounter[i];

        return homeCheckers; //  homeCheckers divided by 15 (max number of points)
    }

    //  Number of Beared Off Checkers (Feature 8)
        //  Input           - Board State
        //  Output          - Feature score for feature 8 (between 0 and 1 where 1 is a definite win)
    private double bearedOffNumber(int[] myCounter) {
        int bearOff = 0;

        /*
            Count board counters to get bear off
        */
        bearOff += myCounter[25];

        return bearOff;  //  bearOff divided by 15 (max number of points)
    }

    //  pointsCoveredNumbers (Feature 9)
        //  Input           - Board State
        //  Output          - Feature score for feature 9 (between 0 and 1 where 1 is a definite win)
    private double pointsCovered(int[] myCounter) {
        int pointsCovered = 0;

        /*
            Count board counters to get pointsCovered
        */
        for(int i=1; i<25; i++) {
            if(myCounter[i] > 0)
                pointsCovered += i;
        }

        return pointsCovered;
    }
}

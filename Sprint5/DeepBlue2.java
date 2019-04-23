package Sprint5;

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

    DeepBlue2(PlayerAPI me, PlayerAPI opponent, BoardAPI board, CubeAPI cube, MatchAPI match, InfoPanelAPI info) {
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

        /*
            Count board counters to get P0 and P1
        */

        Pd = (P0-P1)/375;   //  Player Difference divided by 375 (max number of points)
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

        Sd = (Kx - Tx) / 7; //  Blot-Block Difference divided by 7 (max number of points)
        return 1;
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

        count /= 21;    //  Homeboard Block count divided by 20 (Max number of points)
        return count;
    }

    //  Length of Prime with Captured Checker (Feature 4)
        //  Input           - Board State
        //  Output          - Feature score for feature 4 (between 0 and 1 where 1 is a definite win)
    private int capturedPrime() {
        int points;
        int sizeOfPrime = 0;
        int numberOfCaptured = 0;

        /*
            Count board counters to get sizeOfPrime and numberOfCaptured
        */

        points = (sizeOfPrime*numberOfCaptured);
        points /= 90; //  capturedPrime points divided by 90 (max number of points)

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

        return anchorPoint/21; //   anchorPoints divided by 21 (max number of points)
    }

    //  Number of Escaped Checkers (Feature 6)
        //  Input           - Board State
        //  Output          - Feature score for feature 6 (between 0 and 1 where 1 is a definite win)
    private int escapedCheckers() {
        int escaped = 0;

        /*
            Count board counters to get escaped checkers
        */

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

        pointsCovered /= 225; // pointsCovered divided by 225 (max number of points)
        return pointsCovered;
    }
}

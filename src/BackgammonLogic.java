/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package src;

//import packages for handling stage, colors

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import src.Objects.Player;
import src.Objects.PossibleMove;
import src.Objects.Spike;
import src.Objects.moveType;
import src.UI.UI;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Math.*;

public class BackgammonLogic extends UI {

    //initialise objects
    private Spike f, t;                 //  Temporary Spike Objects

    private Player playerOne;           //  Player One Object
    private Player playerTwo;           //  Player Two Object

    private int playerOneScore;         //  Player One Score
    private int playerTwoScore;         //  Player Two Score
    private int playerOneTotalScore, playerTwoTotalScore;

    //constructor to create stage/click options
    public BackgammonLogic(Stage stage) {
        super(stage);
        playerOneTotalScore = 0;
        playerTwoTotalScore = 0;
        enterBtnClick();
        commandBtnClick();
    }

    private void enterBtnClick() {
        //When user presses enter the names in the textfields are put into variables and are displayed in information panel
        getStartMenu().getEnterButton().setOnMouseClicked((event) -> {
            String playerOneName;
            String playerTwoName;

            //names entered into fields are stored in player name objects
            playerOneName = getStartMenu().getPlayerOneTextField().getText();
            playerTwoName = getStartMenu().getPlayerTwoTextField().getText();

            //players are assigned counter colors
            playerOne = new Player(playerOneName, Color.RED, 25, 0);
            playerTwo = new Player(playerTwoName, Color.BLUE, 26, 27);
            //player info is displayed on the info panel
            getInfoPanel().addPlayerInfo(playerOne, playerTwo);
            getStartMenu().getDialog().close();

            //first roll of dice
            initialRoll();

        });
    }

    private void commandBtnClick() {
        //Text entered in command panel is appended to the information panel
        getCommandPanel().getCommandPanel().setOnKeyPressed((e) -> {
            //Inserts text to the information panel after the user presses enter
            if (e.getCode() == KeyCode.ENTER) {
                command(getCommandPanel().getCommandPanel().getText());
                getCommandPanel().getCommandPanel().clear();
            }
        });
    }

    //Initial Roll of the dice to determine which player moves first. Called after the player names are entered
    private void initialRoll() {
        boolean repeat = true;

        //dice will be rolled at least once before values are compared
        do {
            setDice1(getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            setDice2(getD2().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));

            //if a player rolls more than opponent, no need to roll again
            if (getDice1() != getDice2()) {
                repeat = false;
                draw();

                //case where player one goes first
                if (getDice1() > getDice2()) {
                    getInfoPanel().getInfoPanel().appendText("Player One goes first.\n");
                    setWhoseGo(0);
                }

                //case where player two goes first
                else if (getDice2() > getDice1()) {
                    getInfoPanel().getInfoPanel().appendText("Player Two goes first.\n");
                    setWhoseGo(1);
                }
            }
        } while (repeat);

        //dice rolls are displayed on info panel
        getInfoPanel().getInfoPanel().appendText("Dice >" + getDice1() + "|" + getDice2() + "\n");
    }

    //  Process Commands entered into Command Panel
    private void command(String s) {
        Queue<PossibleMove> move = new LinkedList<PossibleMove>();

        //If user enters quit, exit the program
        if (s.equals("quit")) {
            System.exit(0);
        }
        //Place hold for when the game finishes
        else if (s.equals("finish")) {
            endOfGame();
        } else if (s.equals("next")) {
            //next players turn
            setWhoseGo(getWhoseGo() + 1);

            setDice1(getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            setDice2(getD2().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));

            getInfoPanel().getInfoPanel().appendText("Dice >" + getDice1() + "|" + getDice2() + "\n");

            if (getWhoseGo() == 1) {
                setDice1(0 - abs(getDice1()));
                setDice2(0 - abs(getDice2()));
            }

            findPossibleMoves(move);
            System.out.println("-----------------");

            printQueue(move);
            //
            displayMoves(move);
            
        } else if (s.startsWith("cheat")) {
            String[] args = s.split(" ");
            //cheat mode activated
            cheat(Integer.parseInt(args[1]));
        } else if (s.matches("cheat")) {
            cheat(1);
        } else if (s.equals("roll")) {
            getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight());
            getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight());
        } else {
            chooseMove(s, move);
        }

        getCommandPanel().getCommandPanel().clear();
        getInfoPanel().getInfoPanel().appendText(s + "\n");

        playerOneScore = getBoard().getSpike()[26].getSizeOfSpike();
        playerTwoScore = getBoard().getSpike()[25].getSizeOfSpike();

        //Updates player scores according to number of counters in spike 25 and 26
        getInfoPanel().getScorePanel().setText("SCORE\nPlayer 1: " + playerOneScore + " [Total: " + playerOneTotalScore + "]" +
                "\nPlayer 2: " + playerTwoScore + " [Total: " + playerTwoTotalScore + "]" );
        if(playerOneScore == 15 || playerTwoScore == 15)
            endOfGame();
        draw();
    }

    private void endOfGame(){
        getFinishGameMenu().endOfGame();
        if (playerOneScore > playerTwoScore) {
            getFinishGameMenu().getResultsLabel().setText("Congratulations Player 1! You won the game!");
            playerOneTotalScore++;
        } else if (playerTwoScore > playerOneScore) {
            getFinishGameMenu().getResultsLabel().setText("Congratulations Player 2! You won the game!");
            playerTwoScore++;
        } else {
            getFinishGameMenu().getResultsLabel().setText("The game is a draw!");
        }
        playerOneScore = 0;
        playerTwoScore = 0;

    }

    private void chooseMove(String s, Queue move) {
        findPossibleMoves(move);

        int moveNumber = 0;
        boolean invalid = false;

        for (int i = 0; i < s.length() && !invalid; i++) {
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                moveNumber += s.charAt(i) + 1 - 'A';
            } else
                invalid = true;
        }

        if (invalid) {
            System.out.println("Invalid Input");
        } else if (moveNumber > move.size()) {
            System.out.println("ERROR: No Corresponding Move " + moveNumber + " " + move.size());
            printQueue(move);
        } else {
            System.out.println(" Number: " + moveNumber);
            PossibleMove chosenMove = (PossibleMove) move.remove();

            for (int i = 1; i < moveNumber; i++)
                chosenMove = (PossibleMove) move.remove();

            System.out.println(" Move: " + chosenMove.getMoves());
            for (int i = 0; i < chosenMove.getNumberOfMoves(); i++) {
                move(getWhoseGo(), chosenMove.getStartSpike(i), chosenMove.getEndSpike(i), chosenMove.getMoveType(i));
            }
        }
    }

    //  Move a counter
    public void move(int whoseMoving, int from, int dest, moveType type) {
        System.out.println("\tMove " + from + " to " + dest);

        if (whoseMoving == 0) {
            if (from == 0)
                from = playerOne.getKnockedOutLocation();
            else if (from == 25)
                from = playerOne.getHomeLocation();

            if (dest == 0)
                dest = playerOne.getKnockedOutLocation();
            else if (dest == 25)
                dest = playerOne.getHomeLocation();
        } else {
            if (from == 0)
                from = playerTwo.getKnockedOutLocation();
            else if (from == 25)
                from = playerTwo.getHomeLocation();
            else
                from = 25 - from;

            if (dest == 0)
                dest = playerTwo.getKnockedOutLocation();
            else if (dest == 25)
                dest = playerTwo.getHomeLocation();
            else
                dest = 25 - dest;
        }

        if (type == moveType.Hit) {
            f = getBoard().getSpike()[dest];

            if (whoseMoving == 0)
                t = getBoard().getSpike()[playerTwo.getKnockedOutLocation()];
            else
                t = getBoard().getSpike()[playerOne.getKnockedOutLocation()];

            t.addToSpike(f.removeFromSpike());
        }

        f = getBoard().getSpike()[from];
        t = getBoard().getSpike()[dest];


        t.addToSpike(f.removeFromSpike());

        getInfoPanel().getInfoPanel().appendText("Move " + from + " to " + dest + "\n");
    }

    //logic for the cheat mode
    public void cheat(int position) {
        f = new Spike(-1, 0);
        t = new Spike(-2, 0);

        collectCounters();

        if (position == 1)
            cheatPositionOne();
        else if (position == 2)
            cheatPositionTwo();
    }

    private void cheatPositionOne() {
        cheatMove(getBoard().getSpike()[playerOne.getHomeLocation()], f, 3);
        cheatMove(getBoard().getSpike()[playerOne.getKnockedOutLocation()], f, 3);
        cheatMove(getBoard().getSpike()[24], f, 3);
        cheatMove(getBoard().getSpike()[22], f, 3);
        cheatMove(getBoard().getSpike()[21], f, 3);

        //  Move Player 2
        cheatMove(getBoard().getSpike()[playerTwo.getHomeLocation()], t, 2);
        cheatMove(getBoard().getSpike()[playerTwo.getKnockedOutLocation()], t, 3);
        cheatMove(getBoard().getSpike()[2], t, 2);
        cheatMove(getBoard().getSpike()[1], t, 2);
        cheatMove(getBoard().getSpike()[3], t, 2);
        cheatMove(getBoard().getSpike()[4], t, 2);
        cheatMove(getBoard().getSpike()[5], t, 2);
    }

    private void cheatPositionTwo() {
        cheatMove(getBoard().getSpike()[playerOne.getHomeLocation()], f, 2);
        cheatMove(getBoard().getSpike()[playerOne.getKnockedOutLocation()], f, 1);
        cheatMove(getBoard().getSpike()[playerTwo.getHomeLocation()], t, 2);
        cheatMove(getBoard().getSpike()[playerTwo.getKnockedOutLocation()], t, 1);

        for (int i = 1; !f.isEmpty(); i++) {
            cheatMove(getBoard().getSpike()[i], f, 1);
            cheatMove(getBoard().getSpike()[25 - i], t, 1);
        }
    }

    private void collectCounters() {
        for (int i = 0; i < 28; i++) {
            Spike pointerSpike = getBoard().getSpike()[i];

            while (!pointerSpike.isEmpty()) {
                if (pointerSpike.getCounterPlayer() == 1)
                    f.addToSpike(pointerSpike.removeFromSpike());
                else
                    t.addToSpike(pointerSpike.removeFromSpike());
            }
        }
    }

    //  Moves the Checkers. Moves x counters from a to b - Only for Cheat Command
    private void cheatMove(Spike dest, Spike src, int number) {
        for (int i = 0; i < number; i++)
            dest.addToSpike(src.removeFromSpike());
    }

    //  Fills Queue moves all possible moves checking if the bar is empty
    private Queue findPossibleMoves(Queue moves) {
        Spike bar;
        int d1, d2;

        //  Int d1 is the smaller of the 2 dice rolls, d2 is the larger
        d1 = min(abs(getDice1()), abs(getDice2()));
        d2 = max(abs(getDice1()), abs(getDice2()));

        //  Sets the bar
        if (getWhoseGo() == 0)
            bar = getBoard().getSpike()[playerOne.getKnockedOutLocation()];
        else
            bar = getBoard().getSpike()[playerTwo.getKnockedOutLocation()];

        //  Searches in bar or rest of spikes
        if (bar.isEmpty()) {
            findFirstMove(1, d1, d2, moves);
        } else {
            if(getWhoseGo() == 0)
                testBar(bar, d1, d2, playerOne.getKnockedOutLocation(), moves);
            else
                testBar(bar, d1, d2, playerTwo.getKnockedOutLocation(), moves);
        }

        return moves;
    }

    //  Finds the possible moves on the bar
    private Queue testBar(Spike bar, int d1, int d2, int spikeNum, Queue moves) {
        //  If there is only 1 counter on the bar (1 move from bar, 1 move from spikes)
        if (bar.getSizeOfSpike() == 1) {
            //  Tests dice 1 on bar
            moveType test = testBar(d1);
            if(test != moveType.NotValid) {
                //  Adds dice 1 move from bar
                PossibleMove pm = new PossibleMove();
                pm.add(0, abs(d1), test, (byte) getWhoseGo());

                //  Sets destination spike
                Spike dest;
                if (getWhoseGo() == 0)
                    dest = getBoard().getSpike()[d1];
                else
                    dest = getBoard().getSpike()[25 - d1];

                //  Moves counter to destination and finds second move
                dest.addToSpike(bar.removeFromSpike());
                findSecondMove(1, d2, pm, moves, true);
                bar.addToSpike(dest.removeFromSpike());
            }

            //  Tests dice 2 on bar
            test = testBar(d2);
            if(test != moveType.NotValid) {
                //  Adds dice 2 move from bar
                PossibleMove pm = new PossibleMove();
                pm.add(spikeNum, d2, test, (byte) getWhoseGo());

                //  Sets destination spike
                Spike dest;
                if (getWhoseGo() == 0)
                    dest = getBoard().getSpike()[d2];
                else
                    dest = getBoard().getSpike()[25 - d2];

                //  Moves counter to destination and finds second move
                dest.addToSpike(bar.removeFromSpike());
                findSecondMove(1, d1, pm, moves, true);
                bar.addToSpike(dest.removeFromSpike());
            }
            //  If more than 1 counter on the bar (Both moves from bar)
        } else {
            //  Finds dice 1 move
            moveType test = testBar(d1);
            PossibleMove pm = new PossibleMove();
            if(test != moveType.NotValid) {
                pm.add(spikeNum, d1, test, (byte) getWhoseGo());
            }

            //  Finds dice 2 move
            test = testBar(d2);
            if(test != moveType.NotValid) {
                pm.add(spikeNum, d2, test, (byte) getWhoseGo());
            }

            moves.add(pm);
        }

        return moves;
    }

    //  Finds the first move of the turn if the bar is empty
    private Queue findFirstMove(int spikeNum, int d1, int d2, Queue moves) {
        //  realNum is player viewed spike number converted to index number
        //  realRoll is the dice roll with the direction (player 2 moves in a different direction to 1)
        int realNum, realRoll;

        if (getWhoseGo() == 0) {
            realNum = spikeNum;
            realRoll = d1;
        } else {
            realNum = 25 - abs(spikeNum);
            realRoll = 0 - abs(d1);
        }

        //  Tests possible move
        moveType test = testType(realNum, realRoll);
        PossibleMove pm = new PossibleMove();

        if (test != moveType.NotValid) {
            Spike one = getBoard().getSpike()[realNum];
            Spike two;

            //  Gets spike 2 object - destination
            if (spikeNum + d1 > 24 && getWhoseGo() == 0)
                two = getBoard().getSpike()[playerOne.getHomeLocation()];
            else if (spikeNum + d1 > 24 && getWhoseGo() == 1)
                two = getBoard().getSpike()[playerTwo.getHomeLocation()];
            else
                two = getBoard().getSpike()[realNum + realRoll];

            //  Adds possible move
            pm.add(realNum, realRoll, test, (byte) getWhoseGo());

            //  Moves counter and finds second move
            two.addToSpike(one.removeFromSpike());
            findSecondMove(spikeNum, d2, pm, moves, true);
            one.addToSpike(two.removeFromSpike());
        }

        //  If searched spike number is too large, reached end
        if (spikeNum > 23)
            return moves;
            //  If d1 is less than d2, search with d2 and d1 swapped
        else if (abs(d1) < abs(d2))
            return findFirstMove(spikeNum, d2, abs(d1), moves);
            //  Otherwise start the search starting on the next spike
        else
            return findFirstMove(spikeNum + 1, d2, abs(d1), moves);
    }

    //  Finds the second possible move - After first move or moving from the bar
    private Queue findSecondMove(int spike, int roll, PossibleMove m, Queue moves, boolean addSingleMove) {
        //  realNum is player viewed spike number converted to index number
        //  realRoll is the dice roll with the direction (player 2 moves in a different direction to 1)
        int realNum, realRoll;

        if (getWhoseGo() == 0) {
            realNum = spike;
            realRoll = roll;
        } else {
            realNum = 25 - abs(spike);
            realRoll = 0 - abs(roll);
        }

        //  Tests possible move
        moveType test = testType(realNum, realRoll);

        if(test != moveType.NotValid) {
            PossibleMove m2 = new PossibleMove();
            //  Clones possible move from first move
            //  Other second moves with same first move
            m2.clone(m);
            m2.add(realNum, realRoll, test, (byte) getWhoseGo());

            moves.add(m2);
            addSingleMove = false;
        }

        //  Tests if reached end and no possible second move
        if (spike > 24 && addSingleMove) {
            moves.add(m);
        }
        //  Searches next spike
        else if (spike < 24) {
            findSecondMove(spike+1, roll, m, moves, addSingleMove);
        }

        return moves;
    }

    private int printQueue(Queue moves) {
        Queue<PossibleMove> queue2 = new LinkedList<PossibleMove>();
        int i;

        if (getWhoseGo() == 0)
            System.out.println("Player 1");
        else
            System.out.println("Player 2");

        for (i = 0; !moves.isEmpty(); i++) {
            String num = "";

            int firstLetter = i / 26;
            int units = i % 26;

            if (firstLetter > 0)
                num += (char) ('A' + firstLetter);
            num += (char) ('A' + units);

            PossibleMove temp = (PossibleMove) moves.remove();
            System.out.println(num + "\t" + temp.getMoves());
            queue2.add(temp);
        }

        while (!queue2.isEmpty()) {
            moves.add(queue2.remove());
        }

        return i;
    }
    
    //displays the possible moves to the info panel
    private void displayMoves(Queue moves) {
    	Queue<PossibleMove> secondQueue = new LinkedList<PossibleMove>();
    	getInfoPanel().getInfoPanel().clear();	//clear panel before outputting moves
    	
    	//ensures correct player heading
    	if (getWhoseGo() == 0)
    		getInfoPanel().getInfoPanel().appendText("Player 1 - Possible moves:" + "\n");
        else
        	getInfoPanel().getInfoPanel().appendText("Player 2 - Possible moves:" + "\n");
    	
    	//as long as moves remain to be printed
    	for (int i = 0; !moves.isEmpty(); i++)	{
    		String message = "";

    		//if letters go beyond Z, a second row of letters is added
            int firstLetter = i / 26;
            firstLetter = (int) floor(firstLetter);
            int secondLetter = i % 26;

            //prints the letter depending on how far down the list
            if (firstLetter > 0)
                message += (char) ('A' + firstLetter);
            message += (char) ('A' + secondLetter);
    		
            PossibleMove temp = (PossibleMove) moves.remove();
            getInfoPanel().getInfoPanel().appendText(message + "\t" + temp.getMoves());
            secondQueue.add(temp);
    		
    		getInfoPanel().getInfoPanel().appendText("\n");
    	}
    	
    	while (!secondQueue.isEmpty()) {
            moves.add(secondQueue.remove());
        }
    }

    private void test(int test) {
        int r1 = getDice1();
        int r2 = getDice2();

        if (getWhoseGo() == 1 && (test > 0 && test < 25)) {
            test = 25 - test;

            printMoveType(test, 0 - r1);
            printMoveType(test, 0 - r2);
        } else {
            printMoveType(test, r1);
            printMoveType(test, r2);
        }
    }

    //  Tests is a move is possible
    private void printMoveType(int start, int roll) {
        moveType m = testType(start, roll);

        //  Print to console move type (testing purpose)
        if(getWhoseGo() == 1) {
            roll = abs(roll);
            start = 25 - start;
        }

        switch (m) {
            case Normal:
                System.out.println(start + " to " + (start + roll) + "> Normal");
                break;
            case NotValid:
                System.out.println(start + " to " + (start + roll) + "> Invalid");
                break;
            case BearOff:
                System.out.println(start + " to " + (start + roll) + "> BearOff");
                break;
            case Hit:
                System.out.println(start + " to " + (start + roll) + "> Hit");
                break;
            default:
                System.out.println(start + " to " + (start + roll) + "> Error");
                break;
        }
    }

    //  Tests for type of move
    private moveType testType(int s, int roll) {
        Spike s1 = getBoard().getSpike()[s];

        if (s1.isEmpty() || s1.getCounterPlayer() != getWhoseGo())
            return moveType.NotValid;
        else if (s + roll > 24 || s + roll < 1)
            return moveType.BearOff;

        Spike s2 = getBoard().getSpike()[s + roll];

        if (s2.isEmpty() || s2.getCounterPlayer() == getWhoseGo())
            return moveType.Normal;
        else if (s2.getSizeOfSpike() == 1)
            return moveType.Hit;

        return moveType.NotValid;
    }

    private moveType testBar(int roll) {
        Spike pip;

        if (getWhoseGo() == 0)
            pip = getBoard().getSpike()[roll];
        else
            pip = getBoard().getSpike()[25 - roll];

        if (pip.isEmpty() || pip.getCounterPlayer() == getWhoseGo())
            return moveType.Normal;
        else if(pip.getNumber() == 1)
            return moveType.Hit;

        return moveType.NotValid;
    }
}

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
    private Spike f, t;             //  Temporary Spike Objects

    private Player playerOne;       //  Player One Object
    private Player playerTwo;       //  Player Two Object

    private int playerOneScore;
    private int playerTwoScore;

    //constructor to create stage/click options
    public BackgammonLogic(Stage stage) {
        super(stage);
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
        } else if (s.matches("move")) {
            //deal with player movement
            getInfoPanel().getInfoPanel().appendText("> move [from] [destination]\n");

        } else if (s.startsWith("move")) {
            String[] arg = s.split(" ");
            //parse move starting position and finishing postion
            int from = Integer.parseInt(arg[1]);
            int dest = Integer.parseInt(arg[2]);

            //calculating movement for the second player
            if (getWhoseGo() == 1) {
                if (from > 0 && from < 25)
                    from = 25 - from;
                if (dest > 0 && dest < 25)
                    dest = 25 - dest;
            }

            //case where move out of bounds
            if (from < 0 || from > 27 || dest < 0 || dest > 27) {
                getInfoPanel().getInfoPanel().appendText("Move Value out of bounds. No Corresponding Spike\n");
            }
            //move the counter, depending on whether the move is valid, spike has a counter...
            else {
                f = getBoard().getSpike()[from];
                t = getBoard().getSpike()[dest];

                if (f.getSizeOfSpike() > 0) {
                    t.addToSpike(f.removeFromSpike());

                    draw();
                }

            }
            playerOneScore = getBoard().getSpike()[26].getSizeOfSpike();
            playerTwoScore = getBoard().getSpike()[25].getSizeOfSpike();

            //Updates player scores according to number of counters in spike 25 and 26
            getInfoPanel().getScorePanel().setText("SCORE\nPlayer 1: " + playerOneScore +
                    "\nPlayer 2: " + playerTwoScore);

        }
        //Place hold for when the game finishes
        else if (s.equals("finish")) {
            getFinishGameMenu().endOfGame();
            if (playerOneScore > playerTwoScore) {
                getFinishGameMenu().getResultsLabel().setText("Congratulations Player 1! You won the game!");
            } else if (playerTwoScore > playerOneScore) {
                getFinishGameMenu().getResultsLabel().setText("Congratulations Player 2! You won the game!");
            } else {
                getFinishGameMenu().getResultsLabel().setText("The game is a draw!");

            }

        } else if (s.equals("next")) {
            //next players turn
            setWhoseGo(getWhoseGo() + 1);
            draw();

            setDice1(getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            setDice2(getD2().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            getInfoPanel().getInfoPanel().appendText("Dice >" + getDice1() + "|" + getDice2() + "\n");
        } else if (s.startsWith("cheat")) {
            String[] args = s.split(" ");
            //cheat mode activated
            cheat(Integer.parseInt(args[1]));
        } else if (s.matches("cheat")) {
            cheat(1);
        } else if (s.equals("roll")) {
            getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight());
        } else if (s.startsWith("test")) {
            String[] arg = s.split(" ");
            int test = Integer.parseInt(arg[1]);
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
        } else if (s.matches("find")) {
            if(getWhoseGo() == 1) {
                setDice1(0-getDice1());
                setDice2(0-getDice2());
            }

            findPossibleMoves(move);

            System.out.println("-----------------");

            printQueue(move);
        }

        getCommandPanel().getCommandPanel().clear();
        getInfoPanel().getInfoPanel().appendText(s + "\n");
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

        draw();
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

    private Queue findPossibleMoves(Queue moves) {
        Spike bar;
        int d1, d2;

        d1 = min(abs(getDice1()), abs(getDice2()));
        d2 = max(abs(getDice1()), abs(getDice2()));

        if (getWhoseGo() == 0)
            bar = getBoard().getSpike()[playerOne.getKnockedOutLocation()];
        else
            bar = getBoard().getSpike()[playerTwo.getKnockedOutLocation()];

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

    private Queue testBar(Spike bar, int d1, int d2, int spikeNum, Queue moves) {
        if (bar.getSizeOfSpike() == 1) {
            moveType test = testBar(d1);
            if(test != moveType.NotValid) {
                PossibleMove pm = new PossibleMove();
                pm.add(spikeNum, d1, test, (byte) getWhoseGo());

                Spike dest;
                if (getWhoseGo() == 0)
                    dest = getBoard().getSpike()[d1];
                else
                    dest = getBoard().getSpike()[25 - d1];

                dest.addToSpike(bar.removeFromSpike());
                findSecondMove(1, d2, pm, moves, true);
                bar.addToSpike(dest.removeFromSpike());
            }

            test = testBar(d2);
            if(test != moveType.NotValid) {
                PossibleMove pm = new PossibleMove();
                pm.add(spikeNum, d2, test, (byte) getWhoseGo());

                Spike dest;
                if (getWhoseGo() == 0)
                    dest = getBoard().getSpike()[d2];
                else
                    dest = getBoard().getSpike()[25 - d2];

                dest.addToSpike(bar.removeFromSpike());
                findSecondMove(1, d1, pm, moves, true);
                bar.addToSpike(dest.removeFromSpike());
            }
        } else {
            moveType test = testBar(d1);
            PossibleMove pm = new PossibleMove();

            if(test != moveType.NotValid) {
                pm.add(spikeNum, d1, test, (byte) getWhoseGo());
            }

            test = testBar(d2);
            if(test != moveType.NotValid) {
                pm.add(spikeNum, d2, test, (byte) getWhoseGo());
            }

            moves.add(pm);
        }

        return moves;
    }

    private Queue findFirstMove(int spikeNum, int d1, int d2, Queue moves) {
        int realNum, realRoll;

        if (getWhoseGo() == 0) {
            realNum = spikeNum;
            realRoll = d1;
        } else {
            realNum = 25 - abs(spikeNum);
            realRoll = 0 - abs(d1);
        }

        moveType test = testType(realNum, realRoll);

        PossibleMove pm = new PossibleMove();

        if (test != moveType.NotValid) {
            Spike one = getBoard().getSpike()[realNum];
            Spike two = getBoard().getSpike()[realNum + d1];

            pm.add(realNum, realRoll, test, (byte) getWhoseGo());

            two.addToSpike(one.removeFromSpike());
            findSecondMove(spikeNum, d2, pm, moves, true);
            one.addToSpike(two.removeFromSpike());
        }

        if (spikeNum > 24)
            return moves;
        if (abs(d1) < abs(d2))
            return findFirstMove(spikeNum, d2, abs(d1), moves);
        else
            return findFirstMove(spikeNum + 1, d2, abs(d1), moves);
    }

    private Queue findSecondMove(int spike, int roll, PossibleMove m, Queue moves, boolean addSingleMove) {
        int realNum, realRoll;

        if (getWhoseGo() == 0) {
            realNum = spike;
            realRoll = roll;
        } else {
            realNum = 25 - abs(spike);
            realRoll = 0 - abs(roll);
        }

        moveType test = testType(realNum, realRoll);

        if(test != moveType.NotValid) {
            PossibleMove m2 = new PossibleMove();
            m2.clone(m);
            m2.add(realNum, realRoll, test, (byte) getWhoseGo());

            moves.add(m2);
            addSingleMove = false;
        }


        if(test == moveType.NotValid && spike > 24 && addSingleMove) {
            moves.add(m);
        } else if (spike < 24){
            findSecondMove(spike+1, roll, m, moves, addSingleMove);
        }

        return moves;
    }

    private void printQueue(Queue moves) {
      /*  if(getWhoseGo() == 1)
            reverseQueue(moves);
*/
        while(!moves.isEmpty()) {
            PossibleMove temp = (PossibleMove) moves.remove();
            System.out.println(temp.getMoves());
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

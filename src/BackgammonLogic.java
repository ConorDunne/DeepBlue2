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
import src.Objects.Spike;
import src.Objects.moveType;
import src.UI.UI;

import java.util.Queue;

import static java.lang.Math.abs;

public class BackgammonLogic extends UI {

	//initialise objects
    private Spike f, t;             //  Temporary Spike Objects

    private Player playerOne;       //  Player One Object
    private Player playerTwo;       //  Player Two Object

    private Queue possibleMoves;

    //constructor to create stage/click options
    public BackgammonLogic(Stage stage){
        super(stage);
        enterBtnClick();
        commandBtnClick();
    }

    private void enterBtnClick(){
        //When user presses enter the names in the textfields are put into variables and are displayed in information panel
        getStartMenu().getEnterButton().setOnMouseClicked((event) -> {
            String playerOneName;
            String playerTwoName;

        	//names entered into fields are stored in player name objects
            playerOneName = getStartMenu().getPlayerOneTextField().getText();
            playerTwoName = getStartMenu().getPlayerTwoTextField().getText();

            //players are assigned counter colors
            playerOne = new Player(playerOneName, Color.RED, 0, 25);
            playerTwo = new Player(playerTwoName, Color.BLUE, 26, 27);
            //player info is displayed on the info panel
            getInfoPanel().addPlayerInfo(playerOne,playerTwo);
            getStartMenu().getDialog().close();

            //first roll of dice
            initialRoll();

        });
    }
    private void commandBtnClick(){
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
    private void initialRoll(){
        boolean repeat = true;

        //dice will be rolled at least once before values are compared
        do {
            setDice1(getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            setDice2(getD2().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));

            //if a player rolls more than opponent, no need to roll again
            if(getDice1() != getDice2()) {
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
        } while(repeat);

      //dice rolls are displayed on info panel
        getInfoPanel().getInfoPanel().appendText("Dice >" + getDice1() + "|" + getDice2() + "\n");
    }

    //  Process Commands entered into Command Panel
    private void command(String s) {
        int rollOne = -1, rollTwo = -1;

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
            if(getWhoseGo() == 1) {
                if(from > 0 && from < 25)
                    from = 25 - from;
                if(dest > 0 && dest < 25)
                    dest = 25 - dest;
            }

          //case where move out of bounds
            if(from < 0 || from > 27 || dest < 0 || dest > 27) {
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
        } else if(s.equals("next")){
        	//next players turn
            setWhoseGo(getWhoseGo()+1);
            draw();

            setDice1(getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            setDice2(getD2().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            getInfoPanel().getInfoPanel().appendText("Dice >" + getDice1() + "|" + getDice2() + "\n");
        } else if(s.equals("cheat")) {
        	//cheat mode activated
            cheat();
        } else if(s.startsWith("test")) {
            String[] arg = s.split(" ");
            int test = Integer.parseInt(arg[1]);
            int r1 = getDice1();
            int r2 = getDice2();

            if(getWhoseGo() == 1 && (test > 0 && test < 25)) {
                test = 25 - test;

                testMove(test, 0-r1);
                testMove(test, 0-r2);
            } else {
                testMove(test, r1);
                testMove(test, r2);
            }
        }

        getCommandPanel().getCommandPanel().clear();
        getInfoPanel().getInfoPanel().appendText(s + "\n");
    }

  //logic for the cheat mode
    public void cheat() {
        f = new Spike(-1, 0);
        t = new Spike(-2, 0);

    //  Collect Counters
        for(int i=0; i<28; i++) {
            Spike pointerSpike = getBoard().getSpike()[i];

            while(!pointerSpike.isEmpty()) {
                if (pointerSpike.getCounterPlayer() == 1)
                    f.addToSpike(pointerSpike.removeFromSpike());
                else
                    t.addToSpike(pointerSpike.removeFromSpike());
            }
        }

    //  Move Player 1
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

        draw();
    }

//  Moves the Checkers. Moves x counters from a to b - Only for Cheat Command
    private void cheatMove(Spike dest, Spike src, int number) {
        for(int i=0; i<number; i++)
            dest.addToSpike(src.removeFromSpike());
    }

    private void findPossibleMoves(int spike, Queue moves) {

    }

    //  Tests is a move is possible
    private moveType testMove(int start, int roll) {

        moveType move;
        Spike f = getBoard().getSpike()[start];
        Spike t = getBoard().getSpike()[start + roll];

        if(testInvalidMove(f, t))
            move = moveType.NotValid;
        else if (testKnockOutMove(t))
            move = moveType.KnockOut;
        else if (testBearOff(start, roll))
            move = moveType.BearOff;
        else
            move = moveType.Normal;

        switch(move) {
            case Normal:
                System.out.println("Normal");
                break;
            case NotValid:
                System.out.println("Invalid");
                break;
            case BearOff:
                System.out.println("BearOff");
                break;
            case KnockOut:
                System.out.println("KnockOut");
                break;
            default:
                System.out.println("Error");
                break;
        }

        return move;
    }

    private boolean testInvalidMove(Spike s1, Spike s2) {
        boolean test = false;

        if(s1.isEmpty())
            test = true;
        else if(s1.getCounterPlayer() != getWhoseGo())
            test = true;
        else if(s2.getSizeOfSpike() > 1 && s2.getCounterPlayer() != getWhoseGo())
            test = true;

        return test;
    }

    private boolean testKnockOutMove(Spike s) {
        boolean test = false;

        if(s.getSizeOfSpike() == 1 && s.getCounterPlayer() != getWhoseGo())
            test = true;

        return test;
    }

    private boolean testBearOff(int s, int r) {
        boolean test = false;

        if(s + abs(r) > 24)
            test = true;

        return test;
    }
}
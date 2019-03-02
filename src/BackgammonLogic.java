/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package src;


import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BackgammonLogic extends UI {

    private Spike f, t;
    private String playerOneName;
    private String playerTwoName;

    private Player playerOne;
    private Player playerTwo;

    public BackgammonLogic(Stage stage){
        super(stage);
        enterBtnClick();
        commandBtnClick();
    }

    private void enterBtnClick(){
        //When user presses enter the names in the textfields are put into variables and are displayed in information panel
        getStartMenu().getEnterButton().setOnMouseClicked((event) -> {

            playerOneName = getStartMenu().getPlayerOneTextField().getText();
            playerTwoName = getStartMenu().getPlayerTwoTextField().getText();

            playerOne = new Player(playerOneName, Color.RED, 0, 25);
            playerTwo = new Player(playerTwoName, Color.BLUE, 27, 26);
            getInfoPanel().addPlayerInfo(playerOne,playerTwo);
            getStartMenu().getDialog().close();

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
        do {
            setDice1(getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            setDice2(getD2().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));

            if(getDice1() != getDice2()) {
                repeat = false;
                draw();

                if (getDice1() > getDice2()) {
                    getInfoPanel().getInfoPanel().appendText("Player One goes first.\n");
                    setWhosGo(0);
                }
                else if (getDice2() > getDice1()) {
                    getInfoPanel().getInfoPanel().appendText("Player Two goes first.\n");
                    setWhosGo(1);
                }
            }
        } while(repeat);

        getInfoPanel().getInfoPanel().appendText("Dice >" + getDice1() + "|" + getDice2() + "\n");
    }

    private void command(String s) {
        int rollOne = -1, rollTwo = -1;

        //If user enters quit, exit the program
        if (s.equals("quit")) {
            System.exit(0);
        } else if (s.matches("move")) {
            getInfoPanel().getInfoPanel().appendText("> move [from] [destination]\n");
        } else if (s.startsWith("move")) {
            String[] arg = s.split(" ");
            int from = Integer.parseInt(arg[1]);
            int dest = Integer.parseInt(arg[2]);

            if(getWhosGo()%2 == 1) {
                if(from > 0 && from < 25)
                    from = 25 - from;
                if(dest > 0 && dest < 25)
                    dest = 25 - dest;
            }

            if(from < 0 || from > 27 || dest < 0 || dest > 27) {
                getInfoPanel().getInfoPanel().appendText("Move Value out of bounds. No Corresponding Spike\n");
            } else {
                f = getBoard().getSpike()[from];
                t = getBoard().getSpike()[dest];

                if (f.getSizeOfSpike() > 0) {
                    t.addToSpike(f.removeFromSpike());

                    draw();
                }
            }
        } else if(s.equals("next")){
            setWhosGo(getWhosGo()+1);
            draw();

            setDice1(getD1().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            setDice2(getD2().rollDice(getGc(), getCanvas().getWidth(), getCanvas().getHeight()));
            getInfoPanel().getInfoPanel().appendText("Dice >" + getDice1() + "|" + getDice2() + "\n");
        }

        getCommandPanel().getCommandPanel().clear();
        getInfoPanel().getInfoPanel().appendText(s + "\n");
    }


}
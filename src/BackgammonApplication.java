/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BackgammonApplication extends Application {

    private CommandPanel commandPanel = new CommandPanel();
    private InformationPanel infoPanel = new InformationPanel();
    private StartMenu startMenu = new StartMenu();

    private Board board;
    private GraphicsContext gc;
    private Canvas canvas;
    private Spike f, t;
    private Dice d1 = new Dice(1);
    private Dice d2 = new Dice(2);

    private String playerOneName;
    private String playerTwoName;

    private Player playerOne;
    private Player playerTwo;
    private int whosGo;
    private int dice1, dice2;

    @Override
    public void start(Stage stage) {

        stage.setTitle("Backgammon - DeepBlue2");
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, 700, 400);
        stage.setScene(scene);

        border.setBottom(commandPanel.addHBox());
        border.setRight(infoPanel.addVBox());

        Pane wrapperPane = new Pane();

        canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();
        board = new Board(gc, canvas.getWidth(), canvas.getHeight());           //  Initialises Board and Counters

        border.setCenter(wrapperPane); //Border at center of screen
        wrapperPane.getChildren().add(canvas); //Adds canvas to wrapper pane

        //Bind width and height property to wrapper pane
        canvas.widthProperty().bind(wrapperPane.widthProperty());
        canvas.heightProperty().bind(wrapperPane.heightProperty());

        //Text entered in command panel is appended to the information panel
        commandPanel.getCommandPanel().setOnKeyPressed((e) -> {
                //Inserts text to the information panel after the user presses enter
                if (e.getCode() == KeyCode.ENTER) {
                    command(commandPanel.getCommandPanel().getText());
                    commandPanel.getCommandPanel().clear();
                }
        });

        stage.show();
        startMenu.enterUserNames(stage);

        //When user presses enter the names in the textfields are put into variables and are displayed in information panel
        startMenu.getEnterButton().setOnMouseClicked((event) -> {

            playerOneName = startMenu.getPlayerOneTextField().getText();
            playerTwoName = startMenu.getPlayerTwoTextField().getText();

            playerOne = new Player(playerOneName, Color.BLUE);
            playerTwo = new Player(playerTwoName, Color.RED);
            infoPanel.addPlayerInfo(playerOne,playerTwo);
            startMenu.getDialog().close();

        });

        //Uses lambda expressions to call draw() every time the window is resized
        canvas.widthProperty().addListener(event -> draw());
        canvas.heightProperty().addListener(event -> draw());

        boolean repeat = true;
        do {
            dice1 = d1.rollDice(gc, canvas.getWidth(), canvas.getHeight());
            dice2 = d2.rollDice(gc, canvas.getWidth(), canvas.getHeight());

            if(dice1 != dice2) {
                repeat = false;
                draw();

                if (dice1 > dice2) {
                    infoPanel.getInfoPanel().appendText("Player One goes first.\n");
                    whosGo = 0;
                }
                else if (dice2 > dice1) {
                    infoPanel.getInfoPanel().appendText("Player Two goes first.\n");
                    whosGo = 1;
                }
            }
        } while(repeat);

        infoPanel.getInfoPanel().appendText("Dice >" + dice1 + "|" + dice2 + "\n");
    }

    private void command(String s) {
        int rollOne = -1, rollTwo = -1;

        //If user enters quit, exit the program
        if (s.equals("quit")) {
            System.exit(0);
        } else if (s.matches("move")) {
            infoPanel.getInfoPanel().appendText("> move [from] [destination]\n");
        } else if (s.startsWith("move")) {
            String[] arg = s.split(" ");
            int from = Integer.parseInt(arg[1]);
            int dest = Integer.parseInt(arg[2]);

            if(whosGo%2 == 1) {
                if(from > 0 && from < 25)
                    from = 25 - from;
                if(dest > 0 && dest < 25)
                    dest = 25 - dest;
            }

            if(from < 0 || from > 26 || dest < 0 || dest > 26) {
                infoPanel.getInfoPanel().appendText("Move Value out of bounds. No Corresponding Spike\n");
            } else {
                f = board.getSpike()[from ];
                t = board.getSpike()[dest ];

                if (f.getSizeOfSpike() > 0) {
                    t.addToSpike(f.removeFromSpike());

                   draw();
                }
            }
        } else if(s.equals("next")){
            whosGo++;
            draw();

            dice1 = d1.rollDice(gc, canvas.getWidth(), canvas.getHeight());
            dice2 = d2.rollDice(gc, canvas.getWidth(), canvas.getHeight());
            infoPanel.getInfoPanel().appendText("Dice >" + dice1 + "|" + dice2 + "\n");
        }

        commandPanel.getCommandPanel().clear();
        infoPanel.getInfoPanel().appendText(s + "\n");
    }

    //Redraws the updated game board
    private void draw() {
        board.drawBoard(gc, canvas.getWidth(), canvas.getHeight(), (byte) (whosGo%2));  //  Draws the board
        board.drawPlayerCounters(gc, canvas.getWidth(), canvas.getHeight());            //  Draws the counters

        d1.drawDice(gc, canvas.getWidth(), canvas.getHeight(), dice1);                  //  Draws Die 1
        d2.drawDice(gc, canvas.getWidth(), canvas.getHeight(), dice2);                  //  Draws Die 2
    }

    //CALLED WHENEVER WE START MAIN JAVA PROGRAM
    public static void main(String[] args) {
        launch(args);
    }
}
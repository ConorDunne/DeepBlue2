/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package src;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BackgammonApplication extends Application {

    CommandPanel commandPanel = new CommandPanel();
    InformationPanel infoPanel = new InformationPanel();
    StartMenu startMenu = new StartMenu();



    private Board board;
    private GraphicsContext gc;
    private Canvas canvas;
    private Spike f, t;
    private Dice d1 = new Dice();
    private Dice d2 = new Dice();

    private String playerOneName;
    private String playerTwoName;

    private Player playerOne;
    private Player playerTwo;
    private int whosGo = 0;

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


        border.setCenter(wrapperPane); //Border at center of screen
        wrapperPane.getChildren().add(canvas); //Adds canvas to wrapper pane

        //Bind width and height property to wrapper pane
        canvas.widthProperty().bind(wrapperPane.widthProperty());
        canvas.heightProperty().bind(wrapperPane.heightProperty());

        //Uses lambda expressions to call draw() every time the window is resized
        canvas.widthProperty().addListener(event -> draw(canvas));
        canvas.heightProperty().addListener(event -> draw(canvas));

        //Text entered in command panel is appended to the information panel
        commandPanel.getCommandPanel().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {

                //Inserts text to the information panel after the user presses enter
                if (e.getCode() == KeyCode.ENTER) {
                    command(commandPanel.getCommandPanel().getText());
                    commandPanel.getCommandPanel().clear();
                }
            }
        });

        stage.show();
        startMenu.enterUserNames(stage);

        //When user presses enter the names in the textfields are put into variables and are displayed in information panel
        startMenu.getEnterButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                playerOneName = startMenu.getPlayerOneTextField().getText();
                playerTwoName = startMenu.getPlayerTwoTextField().getText();

                System.out.println("Player One Name: " + playerOneName);
                System.out.println("Player Two Name: " + playerTwoName);


                playerOne = new Player(playerOneName, Color.BLUE);
                playerTwo = new Player(playerTwoName, Color.RED);
                infoPanel.addPlayerInfo(playerOne,playerTwo);
                startMenu.getDialog().close();
            }
        });
    }

    private void command(String s) {
        int rollOne = -1, rollTwo = -1;

        if (s.equals("quit")) {
            System.exit(0);
        } else if (s.matches("move")) {
            infoPanel.getInfoPanel().appendText("\n > move [from] [destination]");
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
                infoPanel.getInfoPanel().appendText("\n" + "Move Value out of bounds. No Corresponding Spike");
            } else {
                f = board.getSpike()[from ];
                t = board.getSpike()[dest ];

                if (f.getSizeOfSpike() > 0) {
                    t.addToSpike(f.removeFromSpike());

                    board.drawBoard(gc, canvas.getWidth(), canvas.getHeight(), (byte) (whosGo%2));
                    board.drawPlayerCounters(gc, canvas.getWidth(), canvas.getHeight());
                }
            }
        } else if(s.equals("next")){
           whosGo++;
           board.drawBoard(gc, canvas.getWidth(), canvas.getHeight(), (byte) (whosGo%2));
           board.drawPlayerCounters(gc, canvas.getWidth(), canvas.getHeight());
        }

        commandPanel.getCommandPanel().clear();
        infoPanel.getInfoPanel().appendText("\n" + s);
        if (rollOne != -1 && rollTwo != 1) {
            infoPanel.getInfoPanel().appendText(" " + rollOne + " " + rollTwo);
            rollOne = -1;
            rollTwo = -1;
        }
    }

    //Redraws the updated game board
    private void draw(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        board = new Board(gc, canvas.getWidth(), canvas.getHeight());           //  Initialises Board and Counters

        board.drawBoard(gc, canvas.getWidth(), canvas.getHeight(), (byte) (whosGo%2));  //  Draws the board
        board.drawPlayerCounters(gc, canvas.getWidth(), canvas.getHeight());    //  Draws the counters
    }

    //CALLED WHENEVER WE START MAIN JAVA PROGRAM
    public static void main(String[] args) {
        launch(args);
    }
}
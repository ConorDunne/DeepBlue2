/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package src;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BackgammonApplication extends Application {

    CommandPanel commandPanel = new CommandPanel();
    InformationPanel infoPanel = new InformationPanel();

    private Board board;
    private GraphicsContext gc;
    private Canvas canvas;
    private Spike f, t;

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
    }

    private void command(String s) {

        if (s.equals("quit")) {
            System.exit(0);
        } else if (s.matches("move")) {
            infoPanel.getInfoPanel().appendText("\n > move [from] [destination]");
        } else if (s.startsWith("move")) {
            String[] arg = s.split(" ");
            int from = Integer.parseInt(arg[1]);
            int dest = Integer.parseInt(arg[2]);

            if(from < 1 || from > 24 || dest < 1 || dest > 24) {
                infoPanel.getInfoPanel().appendText("\n" + "Move Value out of bounds. No Corresponding Spike");
            } else {
                f = board.getSpike()[from - 1];
                t = board.getSpike()[dest - 1];

                if (f.getSizeOfSpike() > 0) {
                    t.addToSpike(f.removeFromSpike());

                    board.drawBoard(gc, canvas.getWidth(), canvas.getHeight());
                    board.drawPlayerCounters(gc, canvas.getWidth(), canvas.getHeight());
                }
            }
        }

        commandPanel.getCommandPanel().clear();
        infoPanel.getInfoPanel().appendText("\n" + s);
    }

    //Redraws the updated game board
    private void draw(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
        board = new Board(gc, canvas.getWidth(), canvas.getHeight());           //  Initialises Board and Counters

        board.drawBoard(gc, canvas.getWidth(), canvas.getHeight());             //  Draws the Board
        board.drawPlayerCounters(gc, canvas.getWidth(), canvas.getHeight());    //  Draws the counters
    }

    //CALLED WHENEVER WE START MAIN JAVA PROGRAM
    public static void main(String[] args) {
        launch(args);
    }
}
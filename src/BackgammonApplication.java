package src;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class BackgammonApplication extends Application {

    private Button exportBtn;
    private TextArea infoPanel;
    private TextArea scorePanel;
    private TextField commandPanel;

    private Board board;
    private Spike spike;
    private Counter counter;

    private GraphicsContext gc;
    private Canvas canvas;

    private boolean assignFrom = true;
    private int from, to;
    private Spike f, t;



    @Override
    public void start(Stage stage) {

        stage.setTitle("Backgammon - DeepBlue2");
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border, 700, 400);
        stage.setScene(scene);

        border.setBottom(addHBox());
        border.setRight(addVBox());

        Pane wrapperPane = new Pane();

        canvas = new Canvas();

        border.setCenter(wrapperPane); //Border at center of screen
        wrapperPane.getChildren().add(canvas); //Adds canvas to wrapper pane

        //Bind width and height property to wrapper pane
        canvas.widthProperty().bind(wrapperPane.widthProperty());
        canvas.heightProperty().bind(wrapperPane.heightProperty());

        //Uses lambda expressions to call draw() everytime the window is resized
        canvas.widthProperty().addListener(event -> draw(canvas));
        canvas.heightProperty().addListener(event -> draw(canvas));

        stage.show();
        System.out.println("Stage Height: " + stage.getHeight() + "Stage Width: " + stage.getWidth()
                + "\n" + "Border Height: " + border.getHeight() + "Border Width: " + border.getWidth()
                + "\n" + "Command Panel Height: " + commandPanel.getHeight() + "Command Panel Width: " + commandPanel.getWidth()
                + "\n" + "InfoPanel Height: " + infoPanel.getHeight() + "InfoPanel Height: " + infoPanel.getWidth()
                + "\n" + "Button Height: " + exportBtn.getHeight() + "Button Width: " + exportBtn.getWidth()
        );

    }

    //Called to add hbox to border pane on bottom consisting of command panel and export button
    public HBox addHBox() {
        HBox hbox = new HBox();
        commandPanel = new TextField();
        exportBtn = new Button("Export to .txt file");

        HBox.setHgrow(commandPanel, Priority.ALWAYS);
        exportBtn.setPrefWidth(200);

        commandPanel.setPromptText("Enter Command");

        hbox.getChildren().addAll(commandPanel, exportBtn);

        return hbox;
    }

    //Called when adding Vbox to border pane on right consisting of information panel and score panel
    public VBox addVBox() {

        VBox vbox = new VBox();
        infoPanel = new TextArea("Welcome to Backgammon");
        scorePanel = new TextArea("SCORE: ");

        VBox.setVgrow(infoPanel, Priority.ALWAYS);

        //Sizes are temp values and should probably be changed
        infoPanel.setPrefSize(200, 1000);
        scorePanel.setPrefSize(200, 800);

        infoPanel.setEditable(false);
        scorePanel.setEditable(false);

        //Text entered in command panel is appended to the information panel
        commandPanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {

                //Inserts text to the information panel after the user presses enter
                if (e.getCode() == KeyCode.ENTER) {

                    //Exit the program if the user enters 'quit'
                    if (commandPanel.getText().equals("quit")) {
                        System.exit(0);
                    }
                    else if(commandPanel.getText().matches("[1-9]") || (commandPanel.getText().matches("[1-9][0-9]"))){
                        double radius = canvas.getHeight()*0.065;



                        /*
                        Initial entry to command panel indicates the spike you want to move the counter from. The next time entered indicates
                        the spike you want to move the counter too.
                        */
                        if(assignFrom == true) {
                            infoPanel.appendText("\nFROM: ");
                            from = Integer.parseInt(commandPanel.getText());
                            f = board.getSpike()[from - 1];
                            assignFrom = false;

                        }
                        else if (assignFrom == false){

                            infoPanel.appendText("\nTO: ");
                            to = Integer.parseInt(commandPanel.getText());
                            t = board.getSpike()[to - 1];
                            assignFrom = true;

                            if(f.getSizeOfSpike() > 0){
                                if(from < 13) {
                                    gc.clearRect((canvas.getWidth() * f.getxCenter()) - radius / 2, canvas.getHeight() * 0.885 - (radius * (f.getSizeOfSpike() -1)), radius, radius);
                                    gc.fillOval((canvas.getWidth() * t.getxCenter()) - radius / 2, canvas.getHeight() * 0.885 - (radius * (t.getSizeOfSpike())), radius, radius);

                                    t.addToSpike(f.removeFromSpike());

                                }
                                else {
                                    gc.clearRect((canvas.getWidth() * f.getxCenter()) - radius / 2, canvas.getHeight() * 0.05 + (radius * f.getSizeOfSpike() - 1), radius, radius);
                                    gc.fillOval((canvas.getWidth() * f.getxCenter()) - radius / 2, canvas.getHeight() * 0.05 + (radius * f.getSizeOfSpike()), radius, radius);
                                    t.addToSpike(f.removeFromSpike());
                                }
                            }
                        }
                        //draw(canvas);


                        infoPanel.appendText("\n" + commandPanel.getText());
                        System.out.println("The Size of Spike " + from + " is " + f.getSizeOfSpike());

                    }


                    commandPanel.clear();
                }
            }
        });

        vbox.getChildren().addAll(scorePanel, infoPanel);

        return vbox;
    }

    //TODO DRAW GAME BOARD
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

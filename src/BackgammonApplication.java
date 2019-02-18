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
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.awt.*;
import java.util.Optional;

public class BackgammonApplication extends Application {

    CommandPanel commandPanel = new CommandPanel();
    InformationPanel infoPanel = new InformationPanel();

    private Board board;
    private GraphicsContext gc;
    private Canvas canvas;
    private Spike f, t;

    private String playerOneName;
    private String playerTwoName;

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
        enterUserNames(stage);

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

            if(from < 0 || from > 26 || dest < 0 || dest > 26) {
                infoPanel.getInfoPanel().appendText("\n" + "Move Value out of bounds. No Corresponding Spike");
            } else {
                f = board.getSpike()[from ];
                t = board.getSpike()[dest ];

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

    public void enterUserNames(Stage mainStage) {

        final double INPUT_FIELD_WIDTH = 150;

        VBox vBox = new VBox();
        HBox playerOneHBox = new HBox();
        HBox playerTwoHBox = new HBox();
        HBox buttonHBox = new HBox();

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainStage);
        dialog.setTitle("ENTER PLAYER NAMES");


        BorderPane pane = new BorderPane();
        Scene dialogScene = new Scene(pane, 500,200);
        dialog.setScene(dialogScene);

        BackgroundImage image = new BackgroundImage(new Image("src/backgammon.jpg", dialogScene.getWidth(),dialogScene.getHeight(),false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);


        pane.setPadding(new Insets(10,10,10,10));

        Button enterButton = new Button("Enter");

        vBox.setSpacing(10);

        //Setting where different elements are positioned
        playerOneHBox.setAlignment(Pos.CENTER);
        playerTwoHBox.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.BOTTOM_CENTER);

        //Creation of input fields
        TextField playerOneTextField = new TextField();
        playerOneTextField.setPromptText("Enter Player One Name");
        TextField playerTwoTextField = new TextField();
        playerTwoTextField.setPromptText("Enter Player Two Name");

        //Setting widths
        playerOneTextField.setPrefWidth(INPUT_FIELD_WIDTH);
        playerTwoTextField.setPrefWidth(INPUT_FIELD_WIDTH);
        enterButton.setPrefWidth(INPUT_FIELD_WIDTH);

        //Adding components to border pane
        pane.setCenter(vBox);
        pane.setBottom(buttonHBox);

        //Layout is composes of HBoxes inside a VBox in the center and button is in the bottom section seperately
        playerOneHBox.getChildren().addAll(new Label("Player 1 Name: "), playerOneTextField);
        playerTwoHBox.getChildren().addAll(new Label("Player 2 Name: "), playerTwoTextField);
        buttonHBox.getChildren().add(enterButton);
        vBox.getChildren().addAll(playerOneHBox, playerTwoHBox);

        pane.setBackground(new Background(image));

        dialog.show();

        //When user presses enter the names in the textfields are put into variables and are displayed in information panel
        enterButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                playerOneName = playerOneTextField.getText();
                playerTwoName = playerTwoTextField.getText();

                System.out.println("Player One Name: " + playerOneName);
                System.out.println("Player Two Name: " + playerTwoName);


                infoPanel.getInfoPanel().appendText("Player 1: " + playerOneName + "\n");
                infoPanel.getInfoPanel().appendText("Player 2: " + playerTwoName + "\n");
                dialog.close();
            }
        });

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
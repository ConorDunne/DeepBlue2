/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/
package src.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartMenu {

    private Button enterButton;
    private TextField playerOneTextField;
    private TextField playerTwoTextField;
    private TextField maxScoreTextField;

    private Button arrowLeft, arrowRight;

    private int maxScore = 1;

    private Stage dialog;

    //Created a new stage which asks users to enter their names
    public void enterUserNames(Stage mainStage) {

        final double INPUT_FIELD_WIDTH = 150;

        VBox vBox = new VBox();
        HBox playerOneHBox = new HBox();
        HBox playerTwoHBox = new HBox();
        HBox maxScoreHBox = new HBox();
        HBox buttonHBox = new HBox();

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainStage);
        dialog.setTitle("ENTER PLAYER NAMES");
        dialog.setResizable(false);

        BorderPane pane = new BorderPane();
        Scene dialogScene = new Scene(pane, 500, 200);
        dialog.setScene(dialogScene);

        BackgroundImage image = new BackgroundImage(new Image("src/Resources/backgammon.jpg", dialogScene.getWidth(), dialogScene.getHeight(), false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        pane.setPadding(new Insets(10, 10, 10, 10));

        enterButton = new Button("Enter");
        arrowLeft = new Button("<-");
        arrowRight = new Button("->");
        enterButton.setFont(Font.font("Arial", 13));

        vBox.setSpacing(10);

        //Setting where different elements are positioned
        playerOneHBox.setAlignment(Pos.CENTER);
        playerTwoHBox.setAlignment(Pos.CENTER);
        maxScoreHBox.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.BOTTOM_CENTER);

        //Creation of input fields
        playerOneTextField = new TextField();
        playerOneTextField.setPromptText("Enter Player One Name");
        playerTwoTextField = new TextField();
        playerTwoTextField.setPromptText("Enter Player Two Name");
        maxScoreTextField = new TextField();

        Label enterPOneText = new Label("Player 1 Name: ");
        Label enterPTwoText = new Label("Player 2 Name: ");
        Label maxScoreText = new Label("Choose Max Score: ");

        enterPOneText.setFont(Font.font("Arial", 15));
        enterPTwoText.setFont(Font.font("Arial", 15));
        maxScoreText.setFont(Font.font("Arial", 15));

        enterPOneText.setTextFill(Color.WHITE);
        enterPTwoText.setTextFill(Color.WHITE);
        maxScoreText.setTextFill(Color.WHITE);

        maxScoreTextField.setText("" + maxScore);

        //Setting widths
        playerOneTextField.setPrefWidth(INPUT_FIELD_WIDTH);
        playerTwoTextField.setPrefWidth(INPUT_FIELD_WIDTH);
        maxScoreTextField.setPrefWidth(INPUT_FIELD_WIDTH-100);

        enterButton.setPrefWidth(INPUT_FIELD_WIDTH);
        buttonHBox.setPadding(new Insets(0,0,0,100));

        //Adding components to border pane
        pane.setCenter(vBox);
        pane.setBottom(buttonHBox);

        //Deals with the choosing of max score the player wants to play to
        arrowLeft.setOnMouseClicked(event -> {
            if(maxScore > 1){
                maxScore--;
            }
            maxScoreTextField.setText("" + maxScore);
        });
        arrowRight.setOnMouseClicked(event -> {
            maxScore++;
            maxScoreTextField.setText("" + maxScore);
        });
        maxScoreTextField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                maxScore = Integer.parseInt(maxScoreTextField.getText());
        });

        //Layout is composes of HBoxes inside a VBox in the center and button is in the bottom section seperately
        playerOneHBox.getChildren().addAll(enterPOneText, playerOneTextField);
        playerTwoHBox.getChildren().addAll(enterPTwoText, playerTwoTextField);
        maxScoreHBox.getChildren().addAll(maxScoreText, arrowLeft, maxScoreTextField, arrowRight);
        buttonHBox.getChildren().add(enterButton);
        vBox.getChildren().addAll(playerOneHBox, playerTwoHBox, maxScoreHBox);

        pane.setBackground(new Background(image));

        dialog.show();
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public Button getEnterButton() { return enterButton; }

    public TextField getPlayerOneTextField() {
        return playerOneTextField;
    }

    public TextField getPlayerTwoTextField() { return playerTwoTextField; }
    public Stage getDialog() { return dialog; }
    public TextField getMaxScoreTextField() { return maxScoreTextField; }
    public Button getArrowLeft() { return arrowLeft; }
    public Button getArrowRight() { return arrowRight; }
    public int getMaxScore() { return maxScore; }
}

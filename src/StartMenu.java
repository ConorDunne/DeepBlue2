package src;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StartMenu {



    InformationPanel infoPanel = new InformationPanel();


    private Button enterButton;
    private TextField playerOneTextField;
    private TextField playerTwoTextField;

    private Stage dialog;

    public StartMenu(){

    }

    public void enterUserNames(Stage mainStage) {




        final double INPUT_FIELD_WIDTH = 150;

        VBox vBox = new VBox();
        HBox playerOneHBox = new HBox();
        HBox playerTwoHBox = new HBox();
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

        vBox.setSpacing(10);

        //Setting where different elements are positioned
        playerOneHBox.setAlignment(Pos.CENTER);
        playerTwoHBox.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.BOTTOM_CENTER);

        //Creation of input fields
        playerOneTextField = new TextField();
        playerOneTextField.setPromptText("Enter Player One Name");
        playerTwoTextField = new TextField();
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



    }

    public Button getEnterButton() { return enterButton; }

    public TextField getPlayerOneTextField() {
        return playerOneTextField;
    }

    public TextField getPlayerTwoTextField() {
        return playerTwoTextField;
    }

    public Stage getDialog() {
        return dialog;
    }
}

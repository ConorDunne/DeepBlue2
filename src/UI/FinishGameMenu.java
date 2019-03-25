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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FinishGameMenu {

    private Stage endStage;

    private Button exitButton;
    private Button restartButton;
    private Label resultsLabel;

    public FinishGameMenu(){

    }

    //Created a new stage which asks users to enter their names
    public void endOfGame() {

        final double BUTTON_WIDTH = 100;

        endStage = new Stage();
        endStage.initModality(Modality.APPLICATION_MODAL);
        //endStage.initOwner(mainStage);
        endStage.setTitle("END OF GAME");
        endStage.setResizable(false);

        BorderPane pane = new BorderPane();
        Scene endScene = new Scene(pane, 500, 200);
        endStage.setScene(endScene);

        exitButton = new Button("Exit");
        exitButton.setPrefWidth(BUTTON_WIDTH);
        exitButton.setOnMouseClicked((event -> {
            System.exit(0);
        }));


        resultsLabel = new Label("PLACEHOLDER");
        resultsLabel.setFont(Font.font("Arial", 20));

        restartButton = new Button("New Game");
        restartButton.setPrefWidth(BUTTON_WIDTH);



        VBox buttonsVBox = new VBox();
        buttonsVBox.setAlignment(Pos.CENTER);
        buttonsVBox.setSpacing(10);
        buttonsVBox.getChildren().addAll(restartButton,exitButton);

        HBox resultsHBox = new HBox();

        resultsHBox.setAlignment(Pos.CENTER);
        resultsHBox.setPadding(new Insets(15,0,0,0));
        resultsHBox.getChildren().add(resultsLabel);

        pane.setCenter(buttonsVBox);
        pane.setTop(resultsHBox);


        endStage.show();
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Label getResultsLabel() {
        return resultsLabel;
    }
}

/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package src;

//import packages for handling the application, stage and scene
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BackgammonApplication extends Application {

    @Override
    //program is started
    public void start(Stage stage) {
        BackgammonLogic logic = new BackgammonLogic(stage);
    }
    //CALLED WHENEVER WE START MAIN JAVA PROGRAM
    public static void main(String[] args) {
        launch(args);
    }
}
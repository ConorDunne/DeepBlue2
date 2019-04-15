/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package Sprint1To4;

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

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class BackgammonApplication extends Application {
    BackgammonLogic logic;

    @Override
    //program is started
    public void start(Stage stage) {
         logic = new BackgammonLogic(stage);


    }
    //CALLED WHENEVER WE START MAIN JAVA PROGRAM
    public static void main(String[] args) throws IOException{
        launch(args);

    }

}
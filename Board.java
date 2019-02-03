import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import java.awt.*;

import javafx.stage.Screen;
import javafx.scene.canvas.Canvas;

public class Board
{
    public static GraphicsContext Board(GraphicsContext gc, double width, double height)
    {
        return base(gc, width, height);
    }

    public static GraphicsContext base(GraphicsContext gc, double width, double height)
    {
        gc.setFill(Color.rgb(208, 157, 93));
        gc.fillRect(0, 0, width, height);


        return gc;
    }
}

import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board
{
    public static void board(GraphicsContext gc, double width, double height)
    {
        background(gc, width, height, 208, 157, 93);
        squares(gc, width, height, Color.LAWNGREEN);
        bearOff(gc, width, height, Color.DARKRED);
    }

//  Methods for drawing background (Takes JavaFX colour OR RGB colour)
    public static void background(GraphicsContext gc, double width, double height, int r, int g, int b)
    {
        gc.setFill(Color.rgb(r, g, b));
        gc.fillRect(0, 0, width, height);
    }

    public static void background(GraphicsContext gc, double width, double height, Color c)
    {
        gc.setFill(c);
        gc.fillRect(0, 0, width, height);
    }

//  Methods for drawing squares (Takes JavaFX colour OR RGB colour)
    public static void squares(GraphicsContext gc, double width, double height, Color c)
    {
        gc.setFill(c);
            gc.fillRect(width*0.05, height*0.05, width*0.325, height*0.9);
            gc.fillRect(width*0.475, height*0.05, width*0.325, height*0.9);
    }

    public static void squares(GraphicsContext gc, double width, double height, int r, int g, int b)
    {
        gc.setFill(Color.rgb(r, g, b));
        gc.fillRect(width*0.05, height*0.05, width*0.325, height*0.9);
        gc.fillRect(width*0.475, height*0.05, width*0.325, height*0.9);
    }

//  Methods for drawing bear off areas (Takes JavaFX colour OR RGB colour)
    public static void bearOff(GraphicsContext gc, double width, double height, Color c)
    {
        gc.setFill(c);
            gc.fillRect(width*0.85, height*0.05, width*0.1, height*0.35);
            gc.fillRect(width*0.85, height*0.6, width*0.1, height*0.35);
    }

    public static void bearOff(GraphicsContext gc, double width, double height, int r, int g, int b)
    {
        gc.setFill(Color.rgb(r, g, b));
        gc.fillRect(width*0.85, height*0.05, width*0.1, height*0.35);
        gc.fillRect(width*0.85, height*0.6, width*0.1, height*0.35);
    }
}

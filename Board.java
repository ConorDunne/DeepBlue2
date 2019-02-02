import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board
{
    public static GraphicsContext board(GraphicsContext gc, double width, double height)
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

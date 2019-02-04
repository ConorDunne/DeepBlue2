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
        trianglesOne(gc, width, height, Color.BLACK);
        trianglesTwo(gc, width, height, Color.WHITE);
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

    public static void trianglesOne(GraphicsContext gc, double width, double height, Color c)
    {
        double i = 0.325/6;
        double b = 0.05;

        gc.setFill(c);

//  Upper Left Triangles
        gc.fillPolygon( new double[]{width*(b), width*(b+i), width*((2*b + i)/2)},
                        new double[]{height*0.05, height*0.05, height*0.4},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*2), width*(b+i*3), width*((2*b + 5*i)/2)},
                        new double[]{height*0.05, height*0.05, height*0.4},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*4), width*(b+i*5), width*((2*b + 9*i)/2)},
                        new double[]{height*0.05, height*0.05, height*0.4},
                     3);

//  Lower Left Triangles
        gc.fillPolygon( new double[]{width*(b+i), width*(b+i*2), width*((2*b + 3*i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*3), width*(b+i*4), width*((2*b + 7*i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*5), width*(b+i*6), width*((2*b + 11*i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);

//  Upper Right Triangles
        b = 0.475;

        gc.fillPolygon( new double[]{width*(b+i), width*(b+i*2), width*((2*b + 3*i)/2)},
                        new double[]{height*0.05, height*0.05, height*0.4},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*3), width*(b+i*4), width*((2*b + 7*i)/2)},
                        new double[]{height*0.05, height*0.05, height*0.4},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*5), width*(b+i*6), width*((2*b + 11*i)/2)},
                        new double[]{height*0.05, height*0.05, height*0.4},
                     3);

//  Lower Right Triangles
        gc.fillPolygon( new double[]{width*(b), width*(b+i), width*((2*b + i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*2), width*(b+i*3), width*((2*b + 5*i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*4), width*(b+i*5), width*((2*b + 9*i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);
    }

    public static void trianglesTwo (GraphicsContext gc, double width, double height, Color c)
    {
        double i = 0.325/6;
        double b = 0.05;

        gc.setFill(c);

//  Upper Left Triangles
        gc.fillPolygon( new double[]{width*(b+i), width*(b+i*2), width*((2*b + 3*i)/2)},
                new double[]{height*0.05, height*0.05, height*0.4},
                3);
        gc.fillPolygon( new double[]{width*(b+i*3), width*(b+i*4), width*((2*b + 7*i)/2)},
                new double[]{height*0.05, height*0.05, height*0.4},
                3);
        gc.fillPolygon( new double[]{width*(b+i*5), width*(b+i*6), width*((2*b + 11*i)/2)},
                new double[]{height*0.05, height*0.05, height*0.4},
                3);

//  Lower Left Triangles
        gc.fillPolygon( new double[]{width*(b), width*(b+i), width*((2*b + i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*2), width*(b+i*3), width*((2*b + 5*i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);
        gc.fillPolygon( new double[]{width*(b+i*4), width*(b+i*5), width*((2*b + 9*i)/2)},
                        new double[]{height*0.95, height*0.95, height*0.6},
                     3);

//  Upper Right Triangles
        b = 0.475;

        gc.fillPolygon( new double[]{width*(b), width*(b+i), width*((2*b + i)/2)},
                new double[]{height*0.05, height*0.05, height*0.4},
                3);
        gc.fillPolygon( new double[]{width*(b+i*2), width*(b+i*3), width*((2*b + 5*i)/2)},
                new double[]{height*0.05, height*0.05, height*0.4},
                3);
        gc.fillPolygon( new double[]{width*(b+i*4), width*(b+i*5), width*((2*b + 9*i)/2)},
                new double[]{height*0.05, height*0.05, height*0.4},
                3);

//  Lower Right Triangles
        gc.fillPolygon( new double[]{width*(b+i), width*(b+i*2), width*((2*b + 3*i)/2)},
                new double[]{height*0.95, height*0.95, height*0.6},
                3);
        gc.fillPolygon( new double[]{width*(b+i*3), width*(b+i*4), width*((2*b + 7*i)/2)},
                new double[]{height*0.95, height*0.95, height*0.6},
                3);
        gc.fillPolygon( new double[]{width*(b+i*5), width*(b+i*6), width*((2*b + 11*i)/2)},
                new double[]{height*0.95, height*0.95, height*0.6},
                3);
    }
}

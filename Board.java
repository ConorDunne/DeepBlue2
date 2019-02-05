<<<<<<< HEAD
=======
package deepBlue2r;

>>>>>>> 1
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

public class Board
{
    public static void board(GraphicsContext gc, double width, double height)
    {
        Color background = Color.rgb(208,157,93);
        Color gamingSquares = Color.DARKOLIVEGREEN;
        Color bearOffArea = Color.DARKRED;
        Color TrianglesPlayerOne = Color.BLACK;
        Color TrianglesPlayerTwo = Color.WHITE;
        Color LogoDiamond = Color.DEEPSKYBLUE;
        Color LogoText = Color.DARKBLUE;
<<<<<<< HEAD
=======
        Color counter1 = Color.DARKRED;//black
        Color counter2 = Color.RED;//white
        
>>>>>>> 1

        background(gc, width, height, background);
        squares(gc, width, height, gamingSquares);
        bearOff(gc, width, height, bearOffArea);
        trianglesOne(gc, width, height, TrianglesPlayerOne);
        trianglesTwo(gc, width, height, TrianglesPlayerTwo);
        logo(gc, width, height, LogoDiamond, gamingSquares, LogoText);
<<<<<<< HEAD
=======
        //logo(gc, width, height, diamond, background, text)
        countersBlack(gc, width, height, counter1);
        countersWhite(gc, width, height, counter2);
>>>>>>> 1
    }

//  Method for drawing background (Boarder)
    public static void background(GraphicsContext gc, double width, double height, Color c)
    {
        gc.setFill(c);
        gc.fillRect(0, 0, width, height);
    }

//  Method for drawing gaming square areas
    public static void squares(GraphicsContext gc, double width, double height, Color c)
    {
        gc.setFill(c);
            gc.fillRect(width*0.05, height*0.05, width*0.325, height*0.9);
            gc.fillRect(width*0.475, height*0.05, width*0.325, height*0.9);
    }

//  Method for drawing bear off areas
    public static void bearOff(GraphicsContext gc, double width, double height, Color c)
    {
        gc.setFill(c);
            gc.fillRect(width*0.85, height*0.05, width*0.1, height*0.35);
            gc.fillRect(width*0.85, height*0.6, width*0.1, height*0.35);
    }

//  Methods for drawing the spikes (Triangles)
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

//  Method for drawing the logo
    public static void logo(GraphicsContext gc, double width, double height, Color diamond, Color background, Color text)
    {
        gc.setFill(diamond);
        gc.fillPolygon( new double[]{width*0.2125, width*0.05, width*0.2125, width*0.375},
                        new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                     4);
        gc.fillPolygon( new double[]{width*0.6375, width*0.475, width*0.6375, width*0.8},
                        new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                     4);

        gc.setFill(background);
        gc.fillPolygon( new double[]{width*0.2125, width*0.1, width*0.2125, width*0.325},
                        new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                     4);
        gc.fillPolygon( new double[]{width*0.6375, width*0.525, width*0.6375, width*0.75},
                        new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                     4);

        gc.setStroke(text);
        gc.setFill(text);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font (60));
            gc.fillText("DB2", width*0.2125, height*0.5, width*0.325);
            gc.fillText("DB2", width*0.6375, height*0.5, width*0.325);
    }
<<<<<<< HEAD
=======
    
    //draw counters into their locations
    public static void countersBlack(GraphicsContext gc, double width, double height, Color counter1)
    {
    	final int radius = 29;
    	//width/height = radius
    	
    	//bottom right counters
    	gc.setFill(counter1);
    	gc.fillOval(371, 327, radius, radius);
    	gc.fillOval(371, 327-radius, radius, radius);
    	
    	//bottom left counters
    	gc.fillOval(25, 327, radius, radius);
    	gc.fillOval(25, 327-radius, radius, radius);
    	gc.fillOval(25, (327-(2*radius)), radius, radius);
    	gc.fillOval(25, (327-(3*radius)), radius, radius);
    	gc.fillOval(25, (327-(4*radius)), radius, radius);
    	
    	//top left counters
    	gc.fillOval(133, 19, radius, radius);
    	gc.fillOval(133, 19+radius, radius, radius);
    	gc.fillOval(133, (19+(2*radius)), radius, radius);
    	
    	//top right counters
    	gc.fillOval(237, 19, radius, radius);
    	gc.fillOval(237, 19+radius, radius, radius);
    	gc.fillOval(237, (19+(2*radius)), radius, radius);
    	gc.fillOval(237, (19+(3*radius)), radius, radius);
    	gc.fillOval(237, (19+(4*radius)), radius, radius);
    }
    
    public static void countersWhite(GraphicsContext gc, double width, double height, Color counter2)
    {
    	final int radius = 29;
    	//width/height = radius
    	
    	//top right counters
    	gc.setFill(counter2);
    	gc.fillOval(371, 19, radius, radius);
    	gc.fillOval(371, 19+radius, radius, radius);
    	
    	//top left counters
    	gc.fillOval(25, 19, radius, radius);
    	gc.fillOval(25, 19+radius, radius, radius);
    	gc.fillOval(25, (19+(2*radius)), radius, radius);
    	gc.fillOval(25, (19+(3*radius)), radius, radius);
    	gc.fillOval(25, (19+(4*radius)), radius, radius);
    	
    	//bottom left counters
    	gc.fillOval(133, 327, radius, radius);
    	gc.fillOval(133, 327-radius, radius, radius);
    	gc.fillOval(133, (327-(2*radius)), radius, radius);
    	
    	//bottom right counters
    	gc.fillOval(237, 327, radius, radius);
    	gc.fillOval(237, 327-radius, radius, radius);
    	gc.fillOval(237, (327-(2*radius)), radius, radius);
    	gc.fillOval(237, (327-(3*radius)), radius, radius);
    	gc.fillOval(237, (327-(4*radius)), radius, radius);
    }
>>>>>>> 1
}

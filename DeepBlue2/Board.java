package DeepBlue2;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

public class Board
{
    private Color background;
    private Color gamingSquares;
    private Color bearOffArea;
    private Color trianglesPlayerOne;
    private Color trianglesPlayerTwo;
    private Color counterPlayerOne;
    private Color counterPlayerTwo;

    private Color logoDiamond = Color.DEEPSKYBLUE;
    private Color logoText = Color.DARKBLUE;

    public Board(GraphicsContext gc, double width, double height)
    {
        setColors();
        drawBoard(gc, width, height);
    }

//  Methods for retrieving colours
    private Color getBackground() {return background;}
    private Color getGamingSquares() {return gamingSquares;}
    private Color getBearOffArea() {return bearOffArea;}
    private Color getTrianglesPlayerOne() {return trianglesPlayerOne;}
    private Color getTrianglesPlayerTwo() {return trianglesPlayerTwo;}
    private Color getCounterPlayerOne() {return counterPlayerOne;}
    private Color getCounterPlayerTwo() {return counterPlayerTwo;}
    private Color getLogoDiamond() {return logoDiamond;}
    private Color getLogoText() {return logoText;}

    private void drawBoard(GraphicsContext gc, double width, double height)
    {
        background(gc, width, height);
        squares(gc, width, height);
        bearOff(gc, width, height);
        trianglesOne(gc, width, height);
        trianglesTwo(gc, width, height);
        logo(gc, width, height);
        countersPlayerOne(gc, width, height);
        countersPlayerTwo(gc, width, height);
    }

    private void setColors()
    {
        background = Color.rgb(208,157,93);
        gamingSquares = Color.DARKOLIVEGREEN;
        bearOffArea = Color.DARKRED;

        trianglesPlayerOne = Color.BLACK;
        trianglesPlayerTwo = Color.WHITE;

        counterPlayerOne = Color.BLACK;
        counterPlayerTwo = Color.WHITE;
    }

//  Method for drawing background (Boarder)
    public void background(GraphicsContext gc, double width, double height)
    {
        gc.setFill(getBackground());
        gc.fillRect(0, 0, width, height);
    }

//  Method for drawing gaming square areas
    public void squares(GraphicsContext gc, double width, double height)
    {
        gc.setFill(getGamingSquares());
            gc.fillRect(width*0.05, height*0.05, width*0.325, height*0.9);
            gc.fillRect(width*0.475, height*0.05, width*0.325, height*0.9);
    }

//  Method for drawing bear off areas
    public void bearOff(GraphicsContext gc, double width, double height)
    {
        gc.setFill(getBearOffArea());
            gc.fillRect(width*0.85, height*0.05, width*0.1, height*0.35);
            gc.fillRect(width*0.85, height*0.6, width*0.1, height*0.35);
    }

    //  Methods for drawing the spikes (Triangles)
    public void trianglesOne(GraphicsContext gc, double width, double height)
    {
        double i = 0.325/6;
        double b = 0.05;

        gc.setFill(getTrianglesPlayerOne());

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

    public void trianglesTwo (GraphicsContext gc, double width, double height)
    {
        double i = 0.325/6;
        double b = 0.05;

        gc.setFill(getTrianglesPlayerTwo());

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
    //draw counters into their locations

    public void countersPlayerOne(GraphicsContext gc, double width, double height)
    {
    	final int radius = 29;
    	//width/height = radius

    	//bottom right counters
    	gc.setFill(getCounterPlayerOne());
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

    public void countersPlayerTwo(GraphicsContext gc, double width, double height)
    {
    	final int radius = 29;
    	//width/height = radius

    	//top right counters
    	gc.setFill(getCounterPlayerTwo());
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

    public void logo(GraphicsContext gc, double width, double height)
    {
        gc.setFill(getLogoDiamond());
        gc.fillPolygon( new double[]{width*0.2125, width*0.05, width*0.2125, width*0.375},
                new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                4);
        gc.fillPolygon( new double[]{width*0.6375, width*0.475, width*0.6375, width*0.8},
                new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                4);

        gc.setFill(getGamingSquares());
        gc.fillPolygon( new double[]{width*0.2125, width*0.1, width*0.2125, width*0.325},
                new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                4);
        gc.fillPolygon( new double[]{width*0.6375, width*0.525, width*0.6375, width*0.75},
                new double[]{height*0.4, height*0.5, height*0.6, height*0.5},
                4);

        gc.setFill(getLogoText());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.setFont(Font.font (60));
        gc.fillText("DB2", width*0.2125, height*0.5, width*0.325);
        gc.fillText("DB2", width*0.6375, height*0.5, width*0.325);
    }
}

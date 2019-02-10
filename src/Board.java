package src;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

public class Board {
    private Color background;
    private Color gamingSquares;
    private Color bearOffArea;
    private Color trianglesPlayerOne;
    private Color trianglesPlayerTwo;
    private Color counterPlayerOne;
    private Color counterPlayerTwo;

    private Color logoDiamond = Color.DEEPSKYBLUE;
    private Color logoText = Color.DARKBLUE;

    private Spike[] spike;
    private Counter[] playerOne;
    private Counter[] playerTwo;

    public Board(GraphicsContext gc, double width, double height) {
        setColors();

        spike = new Spike[24];
        Spike.initSpike(spike);

        playerOne = new Counter[15];
        playerTwo = new Counter[15];

        double[] loc = {spike[0].getxCenter(), spike[5].getxCenter(), spike[7].getxCenter(), spike[11].getxCenter()};
        Counter.initCounter(playerOne, loc, getCounterPlayerOne(), true);
        Counter.initCounter(playerTwo, loc, getCounterPlayerTwo(), false);

        initSpikeCounters();

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

    public void drawBoard(GraphicsContext gc, double width, double height) {
        background(gc, width, height);
        squares(gc, width, height);
        bearOff(gc, width, height);
        spikes(gc, width, height);
        logo(gc, width, height);
     }

    private void setColors() {
        background = Color.rgb(208,157,93);
        gamingSquares = Color.DARKOLIVEGREEN;
        bearOffArea = Color.DARKRED;

        trianglesPlayerOne = Color.BLACK;
        trianglesPlayerTwo = Color.WHITE;

        counterPlayerOne = Color.DARKSLATEGRAY;
        counterPlayerTwo = Color.INDIANRED;
    }

//  Method for drawing background (Boarder)
    public void background(GraphicsContext gc, double width, double height) {
        gc.setFill(getBackground());
        gc.fillRect(0, 0, width, height);
    }

//  Method for drawing gaming square areas
    public void squares(GraphicsContext gc, double width, double height) {
        gc.setFill(getGamingSquares());
            gc.fillRect(width*0.05, height*0.05, width*0.325, height*0.9);
            gc.fillRect(width*0.475, height*0.05, width*0.325, height*0.9);
    }

//  Method for drawing bear off areas
    public void bearOff(GraphicsContext gc, double width, double height) {
        gc.setFill(getBearOffArea());
            gc.fillRect(width*0.85, height*0.05, width*0.1, height*0.35);
            gc.fillRect(width*0.85, height*0.6, width*0.1, height*0.35);
    }

//  Method for drawing the spikes (Triangles)
    public void spikes(GraphicsContext gc, double width, double height) {
        for(int i=0; i<24; i++){
            if(i%2 == 0)
                gc.setFill(getTrianglesPlayerTwo());
            else
                gc.setFill(getTrianglesPlayerOne());

            spike[i].drawSpike(gc, width, height);
        }
    }

//  Method for drawing the counters
    public void drawPlayerCounters(GraphicsContext gc, double width, double height) {
        for (int i = 0; i < 15; i++) {
            playerOne[i].drawChecker(gc, width, height);
            playerTwo[i].drawChecker(gc, width, height);
        }
    }

//  Method for drawing the board logo
    public void logo(GraphicsContext gc, double width, double height) {
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

    public void initSpikeCounters() {
        for (int i = 0; i < 15; i++) {
            if (i < 2) {
                spike[0].addToSpike(playerOne[i]);
                spike[23].addToSpike(playerTwo[i]);
            }
            else if (i < 7) {
                spike[11].addToSpike(playerOne[i]);
                spike[12].addToSpike(playerTwo[i]);
            }
            else if (i < 10) {
                spike[16].addToSpike(playerOne[i]);
                spike[7].addToSpike(playerTwo[i]);
            }
            else {
                spike[18].addToSpike(playerOne[i]);
                spike[5].addToSpike(playerTwo[i]);
            }
        }
    }

    public Spike[] getSpike() { return spike; }
    public Counter[] getPlayerOne() { return playerOne; }
    public Counter[] getPlayerTwo() { return playerTwo; }
}

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

    public Board(GraphicsContext gc, double width, double height) {
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

    private void drawBoard(GraphicsContext gc, double width, double height) {
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

    //  Methods for drawing the spikes (Triangles)
    public void spikes(GraphicsContext gc, double width, double height) {
        spike = new Spike[24];

//  Initialize Object Array (Player 2)
        spike[0] = new Spike(1, 5);
        spike[2] = new Spike(3, 3);
        spike[4] = new Spike(5, 1);

        spike[6] = new Spike(7, 5);
        spike[8] = new Spike(9, 3);
        spike[10] = new Spike(11, 1);

        spike[12] = new Spike(13, 0);
        spike[14] = new Spike(15, 2);
        spike[16] = new Spike(17, 4);

        spike[18] = new Spike(19, 0);
        spike[20] = new Spike(21, 2);
        spike[22] = new Spike(23, 4);

//  Initialize Object Array (Player 1)
        spike[1] = new Spike(2, 4);
        spike[3] = new Spike(4, 2);
        spike[5] = new Spike(6, 0);

        spike[7] = new Spike(8, 4);
        spike[9] = new Spike(10, 2);
        spike[11] = new Spike(12, 0);

        spike[13] = new Spike(14, 1);
        spike[15] = new Spike(16, 3);
        spike[17] = new Spike(18, 5);

        spike[19] = new Spike(20, 1);
        spike[21] = new Spike(22, 3);
        spike[23] = new Spike(24, 5);

        for(int i=0; i<24; i++){
            if(i%2 == 0)
                gc.setFill(getTrianglesPlayerTwo());
            else
                gc.setFill(getTrianglesPlayerOne());

            spike[i].drawSpike(gc, width, height);
        }
    }

//  Method for drawing the logo
    //draw counters into their locations

    public void drawPlayerCounters(GraphicsContext gc, double width, double height) {
        Counter[] playerOne = new Counter[15];
        gc.setFill(getCounterPlayerOne());

        for(int i=0; i<15; i++) {
            if(i < 2)
                playerOne[i] = new Counter(0, 0.77291);
            else if(i < 7)
                playerOne[i] = new Counter(11, 0.07707);
            else if(i < 10)
                playerOne[i] = new Counter(16, 0.29367);
            else
                playerOne[i] = new Counter(18, 0.50207);
        }

        playerOne[0].drawChecker(gc, width, height, 0);
        playerOne[1].drawChecker(gc, width, height, 1);

        playerOne[2].drawChecker(gc, width, height, 0);
        playerOne[3].drawChecker(gc, width, height, 1);
        playerOne[4].drawChecker(gc, width, height, 2);
        playerOne[5].drawChecker(gc, width, height, 3);
        playerOne[6].drawChecker(gc, width, height, 4);

        playerOne[7].drawChecker(gc, width, height, 0);
        playerOne[8].drawChecker(gc, width, height, 1);
        playerOne[9].drawChecker(gc, width, height, 2);
        playerOne[10].drawChecker(gc, width, height, 0);
        playerOne[11].drawChecker(gc, width, height, 1);
        playerOne[12].drawChecker(gc, width, height, 2);
        playerOne[13].drawChecker(gc, width, height, 3);
        playerOne[14].drawChecker(gc, width, height, 4);

        Counter[] playerTwo = new Counter[15];
        gc.setFill(getCounterPlayerTwo());

        for(int i=0; i<15; i++) {
            if(i < 2)
                playerTwo[i] = new Counter(23, 0.77291);
            else if(i < 7)
                playerTwo[i] = new Counter(12, 0.07707);
            else if(i < 10)
                playerTwo[i] = new Counter(7, 0.29367);
            else
                playerTwo[i] = new Counter(5, 0.50207);
        }

        playerTwo[0].drawChecker(gc, width, height, 0);
        playerTwo[1].drawChecker(gc, width, height, 1);

        playerTwo[2].drawChecker(gc, width, height, 0);
        playerTwo[3].drawChecker(gc, width, height, 1);
        playerTwo[4].drawChecker(gc, width, height, 2);
        playerTwo[5].drawChecker(gc, width, height, 3);
        playerTwo[6].drawChecker(gc, width, height, 4);

        playerTwo[7].drawChecker(gc, width, height, 0);
        playerTwo[8].drawChecker(gc, width, height, 1);
        playerTwo[9].drawChecker(gc, width, height, 2);

        playerTwo[10].drawChecker(gc, width, height, 0);
        playerTwo[11].drawChecker(gc, width, height, 1);
        playerTwo[12].drawChecker(gc, width, height, 2);
        playerTwo[13].drawChecker(gc, width, height, 3);
        playerTwo[14].drawChecker(gc, width, height, 4);
    }

    public Spike[] getSpike() { return spike; }
}

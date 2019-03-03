/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/
package src;

//import packages for stage, color
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Counter {
    //fields holding counter info - colour of piece, player it belongs to, starting xy pos and finishing xy pos
    private int spikeLocation;
    private double xCenter;
    private int CounterNum;
    private Color colour;
    private int player;


    public Counter(int loc, double spikeX, int y, Color c, int player) {
    	//store parameters into local variables
        this.spikeLocation = loc;
        this.xCenter = spikeX;
        this.colour = c;
        this.CounterNum = y;
        this.player = player;
    }

  //initialise the counters for both players
    public static void initCounter(Counter[] c, double[] loc, Color c1, boolean t) {
        if(t) {
        	//player one's counters, at various starting positions around the board
            for (int i = 0; i < 15; i++) {
                if (i < 2)
                    c[i] = new Counter(1, loc[0], i, c1, 1);
                else if (i < 7)
                    c[i] = new Counter(12, loc[1], i-2, c1, 1);
                else if (i < 10)
                    c[i] = new Counter(17, loc[2], i-7, c1, 1);
                else
                    c[i] = new Counter(19, loc[3], i-10, c1, 1);
            }
        }
        else {
            for (int i = 0; i < 15; i++) {
            	//player two's counters, at various starting positions around the board
                if (i < 2)
                    c[i] = new Counter(24, loc[0], i, c1, 2);
                else if (i < 7)
                    c[i] = new Counter(13, loc[1], i-2, c1, 2);
                else if (i < 10)
                    c[i] = new Counter(8, loc[2], i-7, c1, 2);
                else
                    c[i] = new Counter(6, loc[3], i-10, c1, 2);
            }
        }
    }

  //setter methods for x value, spike locations and the counter number
    public void setX(double x) {
        this.xCenter = x;
    }
    public void setSpike(int x) {
        this.spikeLocation = x;
    }
    public void setCounterNum(int x) {
        CounterNum = x;
    }

  //draws the counters into their positions depending on the spike they are on top of
    public void drawChecker(GraphicsContext gc, double width, double height) {
        double radius = width*0.045;
        gc.setFill(this.colour);

        if(spikeLocation == 0) {
            gc.fillOval((width*xCenter) - radius/2, height*0.5 - (radius*(getCounterNum()+1)), radius, radius);
        } else if(spikeLocation == 27) {
            gc.fillOval((width*xCenter) - radius/2, height*0.5 + (radius*(getCounterNum()+1)), radius, radius);
        }
        else if(spikeLocation == 25) {
            gc.fillRect((width*xCenter) - radius/2, height*0.4 - ((getCounterNum()+1)*height*(0.35/15)) , radius, height*(0.35/15));
        }
        else if(spikeLocation == 26) {
            gc.fillRect((width*xCenter) - radius/2, height*0.95 - ((getCounterNum()+1)*height*(0.35/15)) , radius, height*(0.35/15));
        }
        else if(spikeLocation < 13)
            gc.fillOval((width*xCenter) - radius/2, height*0.89 - (radius*getCounterNum()), radius, radius);
        else
            gc.fillOval((width*xCenter) - radius/2, height*0.05 + (radius*getCounterNum()), radius, radius);

    }

  //accessor methods for spike location, x value, counter number, counter colour and the player 
    public int getSpikeLocation() { return spikeLocation; }
    public double getxCenter() { return xCenter; }
    public int getCounterNum() { return CounterNum; }
    public Color getCounterColour() { return this.colour; }
    public int getPlayer() { return this.player; }
}

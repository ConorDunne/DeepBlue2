package src;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Counter {
    //fields holding counter info - colour of piece, player it belongs to, starting xy pos and finishing xy pos
    private int spikeLocation;
    private double xCenter;
    private int CounterNum;
    private Color colour;


    public Counter(int loc, double spikeX, int y, Color c) {
        this.spikeLocation = loc;
        this.xCenter = spikeX;
        this.colour = c;
        this.CounterNum = y;
    }

    public static void initCounter(Counter[] c, double[] loc, Color c1, boolean t) {
        if(t) {
            for (int i = 0; i < 15; i++) {
                if (i < 2)
                    c[i] = new Counter(0, loc[0], i, c1);
                else if (i < 7)
                    c[i] = new Counter(11, loc[1], i-2, c1);
                else if (i < 10)
                    c[i] = new Counter(16, loc[2], i-7, c1);
                else
                    c[i] = new Counter(18, loc[3], i-10, c1);
            }
        }
        else {
            for (int i = 0; i < 15; i++) {
                if (i < 2)
                    c[i] = new Counter(23, loc[0], i, c1);
                else if (i < 7)
                    c[i] = new Counter(12, loc[1], i-2, c1);
                else if (i < 10)
                    c[i] = new Counter(7, loc[2], i-7, c1);
                else
                    c[i] = new Counter(5, loc[3], i-10, c1);
            }
        }
    }

    public void setX(double x) {
        this.xCenter = x;
    }

    public void setSpike(int x) {
        this.spikeLocation = x;
    }

    public void setCounterNum(int x) {
        CounterNum = x;
    }

    public void drawChecker(GraphicsContext gc, double width, double height) {
        double radius = height*0.065;
        gc.setFill(this.colour);

        if(spikeLocation < 13)
            gc.fillOval((width*xCenter) - radius/2, height*0.885 - (radius*getCounterNum()), radius, radius);
        else
            gc.fillOval((width*xCenter) - radius/2, height*0.05 + (radius*getCounterNum()), radius, radius);

    }

    public int getSpikeLocation() { return spikeLocation; }
    public double getxCenter() { return xCenter; }
    public int getCounterNum() { return CounterNum; }
}

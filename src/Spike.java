package src;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.Stack;

//java file containing the spike class
public class Spike {
    private int number;                     //  The spike number to be printed below the spike
    private int lm;                         //  Location Multiplier (to find position)
    private double xCenter;                 //  Center of the spike
    private double xChange = 0.325/12;      //  To find the edges of the Triangle
    private double yBase;                   //  Y-Coordinant for base of triangle
    private double yPoint;                  //  The increase or decrease of the Y Coordinant for the point


    private  int noCounters = 0;

    Stack<Counter> stack = new Stack<>();

    public Spike(int number, int LM, int noCounters) {
        this.number = number;
        this.lm = LM;
        this.noCounters = noCounters;

        if(number > 12) {
            yBase = 0.05;
            yPoint = 0.4;
        }
        else {
            yBase = 0.95;
            yPoint = 0.6;
        }

        if(number > 6 && number < 19)
            xCenter = 0.05 + ((0.325/6) * LM) + xChange;
        else
            xCenter = 0.475 + ((0.325/6) * LM) + xChange;
    }

    public static void initSpike(Spike[] spike) {
//  Initialize Object Array (Player 2)
        spike[0] = new Spike(1, 5, 2);
        spike[2] = new Spike(3, 3, 0);
        spike[4] = new Spike(5, 1,0);

        spike[6] = new Spike(7, 5,0);
        spike[8] = new Spike(9, 3,0);
        spike[10] = new Spike(11, 1,0);

        spike[12] = new Spike(13, 0,5);
        spike[14] = new Spike(15, 2,0);
        spike[16] = new Spike(17, 4,0);

        spike[18] = new Spike(19, 0,5);
        spike[20] = new Spike(21, 2, 0);
        spike[22] = new Spike(23, 4,0);

//  Initialize Object Array (Player 1)
        spike[1] = new Spike(2, 4,0);
        spike[3] = new Spike(4, 2,0);
        spike[5] = new Spike(6, 0,5);

        spike[7] = new Spike(8, 4,3);
        spike[9] = new Spike(10, 2,0);
        spike[11] = new Spike(12, 0,5);

        spike[13] = new Spike(14, 1,0);
        spike[15] = new Spike(16, 3,3);
        spike[17] = new Spike(18, 5,5);

        spike[19] = new Spike(20, 1,0);
        spike[21] = new Spike(22, 3,0);
        spike[23] = new Spike(24, 5,2);
    }

    public void drawSpike(GraphicsContext gc, double width, double height) {
            gc.fillPolygon( new double[]{width*(xCenter-xChange), width*(xCenter+xChange), width*(xCenter)},
                            new double[]{height*yBase, height*yBase, height*yPoint},
                         3);

            gc.setFill(Color.BLACK);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.setFont(Font.font (10));

            if(number < 13)
                gc.fillText(Integer.toString(number), width*xCenter, height*(yBase+0.025));
            else
                gc.fillText(Integer.toString(number), width*xCenter, height*(yBase-0.025));
    }

    public void addToSpike(Counter c) {
        stack.push(c);
        noCounters++;
    }

    public Counter removeFromSpike() {
        noCounters--;
        return stack.pop();
    }

    public int getNumber(){ return this.number; }
    public int getLm() {return this.lm; }
    public double getxCenter() { return xCenter; }
    public double getxChange() { return xChange; }
    public double getyBase() { return yBase; }
    public double getyPoint() { return yPoint; }

    public int getNoCounters() {return noCounters; }
}
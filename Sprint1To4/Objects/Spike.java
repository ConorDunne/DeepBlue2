/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/
package Sprint1To4.Objects;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Stack;

//java file containing the spike class
public class Spike {
    private int number;                     //  The spike number to be printed below the spike
    private int numberRev;                  //  The spike number to be printed below the spike (Player 2)
    private int lm;                         //  Location Multiplier (to find position)
    private double xCenter;                 //  Center of the spike
    private double xChange = 0.3645/12;      //  To find the edges of the Triangle
    private double yBase;                   //  Y-Coordinant for base of triangle
    private double yPoint;                  //  The increase or decrease of the Y Coordinant for the point

    Stack<Counter> stack = new Stack<>();

    public Spike(int number, int numberRev, int LM) {
        this.number = number;
        this.numberRev = numberRev;
        this.lm = LM;

        if(number > 12) {
            yBase = 0.05;
            yPoint = 0.4;
        }
        else {
            yBase = 0.95;
            yPoint = 0.6;
        }

        if(number > 6 && number < 19)
            xCenter = 0.05 + ((0.3645/6) * LM) + xChange;
        else
            xCenter = 0.4895 + ((0.3645/6) * LM) + xChange;
    }

    public Spike(int number, double xCenter) {
        this.xCenter = xCenter;
        this.number = number;
        this.numberRev = number;
    }

    public static void initSpike(Spike[] spike) {
//  Initialize spikes
        for(int i=1; i<7; i++) {
            spike[i] = new Spike(i, 25-i, 6-i);
        }
        for(int i=7; i<13; i++) {
            spike[i] = new Spike(i, 25-i, 12-i);
        }
        for(int i=13; i<19; i++) {
            spike[i] = new Spike(i, 25-i, i-13);
        }
        for(int i=19; i<25; i++) {
            spike[i] = new Spike(i, 25-i, i-19);
        }

//  Initialize Special Positions
        spike[0] = new Spike(1, 0.454);          //  Knocked out checkers
        spike[27] = new Spike(28, 0.454);
        spike[25] = new Spike(25, 0.9275);       //  Bear-Off 1
        spike[26] = new Spike(26, 0.9275);       //  Bear-Off 2
    }

    public void drawSpike(GraphicsContext gc, double width, double height, byte rev) {
            gc.fillPolygon( new double[]{width*(xCenter-xChange), width*(xCenter+xChange), width*(xCenter)},
                            new double[]{height*yBase, height*yBase, height*yPoint},
                         3);

            gc.setFill(Color.BLACK);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);
            gc.setFont(Font.font (10));
            if(rev == 0) {
                if (number < 13)
                    gc.fillText(Integer.toString(number), width * xCenter, height * (yBase + 0.025));
                else
                    gc.fillText(Integer.toString(number), width * xCenter, height * (yBase - 0.025));
            } else {
                if (numberRev < 13)
                    gc.fillText(Integer.toString(numberRev), width * xCenter, height * (yBase - 0.025));
                else
                    gc.fillText(Integer.toString(numberRev), width * xCenter, height * (yBase + 0.025));
            }
    }

    public void addToSpike(Counter c) {
        c.setX(getxCenter());
        c.setSpike(getNumber());
        c.setCounterNum(stack.size());

        stack.push(c);
    }

    public Counter topOfSpike(){
        return stack.peek();
    }
    public Counter removeFromSpike() { return stack.pop(); }
    public int getSizeOfSpike(){
        return stack.size();
    }
    public boolean isEmpty() {return stack.size() == 0;}
    public int getNumber(){ return this.number; }
    public Color getColour() { return stack.peek().getCounterColour(); }
    public int getCounterPlayer() { return stack.peek().getPlayer(); }
    public int getLm() {return this.lm; }
    public double getxCenter() { return xCenter; }
    public double getxChange() { return xChange; }
    public double getyBase() { return yBase; }
    public double getyPoint() { return yPoint; }
}
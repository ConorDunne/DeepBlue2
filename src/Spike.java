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
    public void addCounter(){
        noCounters++;
    }

    public int getNumber(){ return this.number; }
    public int getLm() {return this.lm; }
    public double getxCenter() { return xCenter; }
    public double getxChange() { return xChange; }
    public double getyBase() { return yBase; }
    public double getyPoint() { return yPoint; }

    public int getNoCounters() {return noCounters; }
}
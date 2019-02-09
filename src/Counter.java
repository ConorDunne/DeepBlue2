package src;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;


public class Counter {
    //fields holding counter info - colour of piece, player it belongs to, starting xy pos and finishing xy pos
    private int spikeLocation;
    private double xCenter;
    private double YCoordinant;


    public Counter(int loc, double spikeX) {
        this.spikeLocation = loc;
        this.xCenter = spikeX;
    }

    public void setX(double x) {
        this.xCenter = x;
    }

    public void drawChecker(GraphicsContext gc, double width, double height, int num) {
        double radius = height*0.065;

        if(spikeLocation < 12)
            gc.fillOval((width*xCenter) - radius/2, height*0.885 - (radius*num), radius, radius);
        else
            gc.fillOval((width*xCenter) - radius/2, height*0.05 + (radius*num), radius, radius);

    }
    public void moveCounter(GraphicsContext gc, double width, double height, int num){
        double radius = height*0.065;

        if(num < 12)
            gc.fillOval((width*xCenter) - radius/2, height*0.885 - (radius*num), radius, radius);
        else
            gc.fillOval((width*xCenter) - radius/2, height*0.05 + (radius*num), radius, radius);
    }

    public int getSpikeLocation() { return spikeLocation; }
    public double getxCenter() { return xCenter; }
    public double getYCoordinant() { return YCoordinant; }
}

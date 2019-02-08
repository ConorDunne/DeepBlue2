package src;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

import javax.xml.soap.Text;


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

    public void drawChecker(GraphicsContext gc, double x, double y, double radius) {
        gc.fillOval(x, y, radius, radius);
    }

    public void drawChecker(GraphicsContext gc, double width, double height, int num) {
        double radius = height*0.065;

        if(spikeLocation < 12)
            gc.fillOval((width*xCenter) - radius/2, height*0.885 - (radius*num), radius, radius);
        else
            gc.fillOval((width*xCenter) - radius/2, height*0.05 + (radius*num), radius, radius);

    }
}

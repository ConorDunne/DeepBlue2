package src;

import javafx.scene.canvas.GraphicsContext;


public class Counter {
    //fields holding counter info - colour of piece, player it belongs to, starting xy pos and finishing xy pos
    private boolean colour;
    private boolean player;
    private int startPosX;
    private int startPosY;
    private int finishPosX;
    private int finishPosY;

    public void drawChecker(GraphicsContext gc, double x, double y, double radius) {

        gc.fillOval(x, y, radius, radius);
    }
}

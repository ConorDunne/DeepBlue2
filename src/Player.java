package src;

import javafx.scene.paint.Color;

public class Player {

    private String name;
    private Color color;
    private int home;
    private int knockedOut;

    public Player(String name, Color color, int home, int knockedOut) {
        this.name = name;
        this.color = color;
        this.home = home;
        this.knockedOut = knockedOut;
    }

    @Override
    public String toString() {
        return name + "/" ;
    }

    public String getName() {
        return name;
    }
    public Color getColor() {
        return color;
    }
    public int getHomeLocation() {
        return home;
    }
    public int getKnockedOutLocation() {
        return knockedOut;
    }

}

package src;

import javafx.scene.paint.Color;

public class Player {

    private String name;
    private Color color;
    private boolean isTurn;

    public Player(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public Boolean setIsTurn(boolean isTurn){
        this.isTurn = isTurn;
        return this.isTurn;
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

    public boolean isTurn() {
        return isTurn;
    }

}

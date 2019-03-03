/*
    17379526    Conor Dunne
    17424866    Martynas Jagutis
    17379773    Ronan Mascarenhas
*/

package src;

//import package for scene color
import javafx.scene.paint.Color;

public class Player {

    private String name;
    private Color color;
    private int home;
    private int knockedOut;

  //player constructor that stores parameters into local variables
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

  //accessor methods for name, color, home location and knocked out status
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

package src;

//import packages for stage, image 
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Dice {
    Random rand = new Random();
    private int numberOfSides;
    private int diceNumber;

  //images for the dice sides
    private Image image1 = new Image("src/Resources/Dice/1.png");
    private Image image2 = new Image("src/Resources/Dice/2.png");
    private Image image3 = new Image("src/Resources/Dice/3.png");
    private Image image4 = new Image("src/Resources/Dice/4.png");
    private Image image5 = new Image("src/Resources/Dice/5.png");
    private Image image6 = new Image("src/Resources/Dice/6.png");

  //dice constructor
    public Dice(int diceNumber) {
        this.numberOfSides = 6;
        this.diceNumber = diceNumber;
    }

  //roll the dice, assigning a random number to "dice" and drawing the resulting roll
    public int rollDice(GraphicsContext gc, double width, double height) {
        int number = rand.nextInt(numberOfSides) + 1;
        drawDice(gc, width, height, number);

        return number;
    }

  //draws the dice depending on the number rolled
    public void drawDice(GraphicsContext gc, double width, double height, int number) {
        if(diceNumber == 1)
            gc.drawImage(getImage(number), width*0.6, height*0.4, width*0.05, width*0.05);
        else
            gc.drawImage(getImage(number), width*0.7, height*0.5, width*0.05, width*0.05);
    }

  //fetches the corresponding image for the number rolled
    private Image getImage(int i) {
        Image img = image1;
        switch (i) {
            case 1:
                img = image1;
                break;
            case 2:
                img = image2;
                break;
            case 3:
                img = image3;
                break;
            case 4:
                img = image4;
                break;
            case 5:
                img = image5;
                break;
            case 6:
                img = image6;
        }

        return img;
    }
}

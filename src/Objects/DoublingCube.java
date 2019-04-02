package src.Objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DoublingCube {
    private javafx.scene.paint.Color doublingCubeBack;
    private Color doublingCubeText;
    private int matchValue;
    private int cubeSide;       //  0 for middle, 1 for player 1, 2 for player 2

    public DoublingCube(Color back, Color text) {
        doublingCubeBack = back;
        doublingCubeText = text;
        matchValue = 1;
        cubeSide = 0;
    }

    public int canPass(int whoseGo) {
        if (whoseGo == cubeSide)
            return -1;
        else
            return 1;
    }

    public void passCube(int whoseGo) {
        cubeSide = whoseGo + 1;
        matchValue += matchValue;
    }

    public void drawCube(GraphicsContext gc, double width, double height) {
        gc.setFill(doublingCubeBack);
        gc.fillRect(width * 0.877, (height * 0.5 - width * 0.05), width * 0.1, width * 0.1);
        gc.setFill(doublingCubeText);

        if (matchValue > 100)
            gc.setFont(Font.font(20));
        else if (matchValue > 10)
            gc.setFont(Font.font(30));
        else
            gc.setFont(Font.font(50));
        gc.fillText(String.valueOf(matchValue), width * 0.927, height * 0.5);
    }

    public int getMatchValue() {
        return matchValue;
    }
}

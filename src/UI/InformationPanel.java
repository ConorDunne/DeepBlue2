package src.UI;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import src.Objects.Player;

public class InformationPanel {


    private TextArea infoPanel;
    private TextArea scorePanel;


    //Called when adding Vbox to border pane on right consisting of information panel and score panel
    public VBox addVBox() {

        VBox vbox = new VBox();
        infoPanel = new TextArea("Welcome to Backgammon!\n");
        scorePanel = new TextArea("SCORE\n(Blue) Player 1:\n(Red)  Player 2: ");

        infoPanel.setFont(Font.font("Arial", 13));
        scorePanel.setFont(Font.font("Arial", 13));

        VBox.setVgrow(infoPanel, Priority.ALWAYS);

        //Sizes are temp values and should probably be changed
        infoPanel.setPrefSize(200, 1000);
        scorePanel.setPrefSize(200, 800);

        infoPanel.setEditable(false);
        scorePanel.setEditable(false);

        vbox.getChildren().addAll(scorePanel, infoPanel);

        return vbox;
    }
    public void addPlayerInfo(Player pOne, Player pTwo){
        infoPanel.appendText("Player 1: " + pOne.toString() + "Red\n");
        infoPanel.appendText("Player 2: " + pTwo.toString() + "Blue\n");
    }

    public TextArea getInfoPanel() { return infoPanel; }
    public TextArea getScorePanel() { return scorePanel; }
}

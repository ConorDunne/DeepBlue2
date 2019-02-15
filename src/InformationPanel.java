package src;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class InformationPanel {

    private TextArea infoPanel;
    private TextArea scorePanel;

    public InformationPanel(){
    }

    //Called when adding Vbox to border pane on right consisting of information panel and score panel
    public VBox addVBox() {

        VBox vbox = new VBox();
        infoPanel = new TextArea("Welcome to Backgammon\n");
        scorePanel = new TextArea("SCORE: ");

        VBox.setVgrow(infoPanel, Priority.ALWAYS);

        //Sizes are temp values and should probably be changed
        infoPanel.setPrefSize(200, 1000);
        scorePanel.setPrefSize(200, 800);

        infoPanel.setEditable(false);
        scorePanel.setEditable(false);

        vbox.getChildren().addAll(scorePanel, infoPanel);

        return vbox;
    }

    public TextArea getInfoPanel() { return infoPanel; }
    public TextArea getScorePanel() { return scorePanel; }
}

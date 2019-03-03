package src;

//import packages for scene layout and text
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InformationPanel {


    private TextArea infoPanel;
    private TextArea scorePanel;


    //Called when adding Vbox to border pane on right consisting of information panel and score panel
    public VBox addVBox() {

        VBox vbox = new VBox();
        infoPanel = new TextArea("Welcome to Backgammon!\n");
        scorePanel = new TextArea("SCORE: ");

      //text settings for the panels
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
    
  //adds the player info to the info panel
    public void addPlayerInfo(Player pOne, Player pTwo){
        infoPanel.appendText("Player 1: " + pOne.toString() + "Red\n");
        infoPanel.appendText("Player 2: " + pTwo.toString() + "Blue\n");
    }

  //accessor methods for the info panel and score panel
    public TextArea getInfoPanel() { return infoPanel; }
    public TextArea getScorePanel() { return scorePanel; }
}

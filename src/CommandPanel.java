package src;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CommandPanel {

    private TextField commandPanel;
    private Button exportBtn;
    private Button helpBtn;

    public CommandPanel(){

    }

    //Called to add hbox to border pane on bottom consisting of command panel and export button
    public HBox addHBox() {
        HBox hbox = new HBox();
        commandPanel = new TextField();
        exportBtn = new Button("Export to .txt file");
        helpBtn = new Button("?");

        HBox.setHgrow(commandPanel, Priority.ALWAYS);
        exportBtn.setPrefWidth(200);

        commandPanel.setPromptText("Enter Command");

        hbox.getChildren().addAll(commandPanel, helpBtn, exportBtn);

        helpBtn.setOnMouseClicked((event) -> helpDialog());

        return hbox;
    }

    private void helpDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.getDialogPane().setPrefSize(450,200);

        alert.setContentText("\nEnter the instructions into the command panel to execute the moves\n" +
                "Move Counters: move [source] [destination]\n" +
                "Roll Dice: roll\n" +
                "Exit Program: quit\n" +
                "Change Player Turns: next");

        alert.showAndWait();
    }

    public TextField getCommandPanel(){ return this.commandPanel; }
    public Button getExportBtn() { return this.exportBtn; }
}

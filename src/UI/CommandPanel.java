package src.UI;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CommandPanel {

    private TextField commandPanel;
    private Button exportBtn;
    private Button helpBtn;

    //Called to add HBox to border pane on bottom consisting of command panel and export button
    public HBox addHBox() {
        HBox hbox = new HBox();

        commandPanel = new TextField();
        exportBtn = new Button("Export to .txt file");
        helpBtn = new Button("?");

        exportBtn.setFont(Font.font("Arial", 13));
        helpBtn.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        helpBtn.setStyle("-fx-background-color: #429ef4;");

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
                "Move Counters: Enter letter from possible moves\n" +
                "Exit Program: quit\n" +
                "Change Player Turns: next" +
                "End Game: finish");

        alert.showAndWait();
    }

    public TextField getCommandPanel(){ return this.commandPanel; }
    public Button getExportBtn() { return this.exportBtn; }
}

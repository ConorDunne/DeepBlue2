package src;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class CommandPanel {

    private TextField commandPanel;
    private Button exportBtn;

    public CommandPanel(){

    }

    //Called to add hbox to border pane on bottom consisting of command panel and export button
    public HBox addHBox() {
        HBox hbox = new HBox();
        commandPanel = new TextField();
        exportBtn = new Button("Export to .txt file");

        HBox.setHgrow(commandPanel, Priority.ALWAYS);
        exportBtn.setPrefWidth(200);

        commandPanel.setPromptText("Enter Command");

        hbox.getChildren().addAll(commandPanel, exportBtn);

        return hbox;
    }

    public TextField getCommandPanel(){ return this.commandPanel; }
    public Button getExportBtn() { return this.exportBtn; }
}

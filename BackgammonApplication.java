//Testing commit .

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import java.awt.Label;

import javafx.stage.Screen;

public class BackgammonApplication extends Application {
	
    @Override 
    public void start( Stage stage ) {
    	
    	double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    	double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    	
    	//Setting up Graphics, layout ect
    	stage.setTitle("Backgammon");
    	GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10)); //Sets padding of grid to main window
    	grid.setVgap(8); //Sets Padding Vertically
    	grid.setHgap(10);
    	
    	Scene scene = new Scene(grid, 700, 400);
    	stage.setScene(scene);
        stage.show();
               
        grid.setGridLinesVisible(true);
            
        //GRAPHICS GO HERE 
        TextField commandPanel = new TextField();
        TextArea infoPanel = new TextArea("Welcome to Backgammon!");
        TextArea scorePanel = new TextArea("SCORE: ");
        Button exportBtn = new Button("Export to .txt file");     
        
        //Sets minWidth, prefWidth and maxWidth for specified columns and rows. Used so items resize according to screen size
        ColumnConstraints column0 = new ColumnConstraints(Double.MIN_VALUE, grid.getWidth() *.75, Double.MAX_VALUE);
        ColumnConstraints column1 = new ColumnConstraints(grid.getWidth() *.05, grid.getWidth() *.25, Double.MAX_VALUE);
        RowConstraints row0 = new RowConstraints(grid.getHeight()*.05, grid.getHeight()*.15, Double.MAX_VALUE);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        
        //Determines whether row or columns expands when screen is expanded
        column0.setHgrow(Priority.ALWAYS);
        column1.setHgrow(Priority.ALWAYS);    
        row0.setVgrow(Priority.ALWAYS);
        row1.setVgrow(Priority.ALWAYS);
        row2.setVgrow(Priority.NEVER);
        
        grid.getColumnConstraints().add(0, column0);
        grid.getColumnConstraints().add(1, column1);
        grid.getRowConstraints().add(0,row0);
        grid.getRowConstraints().add(1, row1);
        
        //Setting the columns and rows of the different panels
        GridPane.setConstraints(commandPanel, 0, 2);
        GridPane.setConstraints(infoPanel, 1,1);
        GridPane.setConstraints(exportBtn, 1,2);
        GridPane.setConstraints(scorePanel, 1, 0);
        
        commandPanel.setPromptText("Enter Command");       
        infoPanel.setEditable(false); //Makes user unable to enter text into the information panel   
        scorePanel.setEditable(false);
        exportBtn.setPrefWidth(1000); //***temp*** As it is not following the constraints of the columns for some reason
        
        grid.getChildren().addAll(commandPanel, infoPanel, exportBtn, scorePanel); //Adding elements to grid
    
       
        
        //Text entered in command panel is appended to the information panel
        commandPanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				
				//Inserts text to the information panel after the user presses enter
				if(e.getCode() == KeyCode.ENTER) {
					infoPanel.appendText("\n" + commandPanel.getText());
					//Exit the program if the user enters 'quit'
					if(commandPanel.getText().equals("quit")) {
						System.exit(0);
					}	
					commandPanel.clear(); 
				}
			}
		}); 
          
        //TODO ADD A SCORES PANEL ABOVE THE INFORMATION PANEL Column 1 Row 0
        //TODO ADD THE BOARD TO THE GRID column 0 row 0
        System.out.println("Stage Height: " + stage.getHeight() + "Stage Width: " + stage.getWidth()
        + "\n" + "Grid Height: " + grid.getHeight() + "Grid Width: " + grid.getWidth()
        + "\n" + "Command Panel Height: " + commandPanel.getHeight() + "Command Panel Width: " + commandPanel.getWidth()  
        + "\n" + "InfoPanel Height: "+ infoPanel.getHeight() + "InfoPanel Height: " + infoPanel.getWidth() 
        + "\n" + "Button Height: " + exportBtn.getHeight() + "Button Width: " + exportBtn.getWidth());
  
    }
    
    //CALLED WHENEVER WE START MAIN JAVA PROGRAM
    public static void main(String[] args) {
		launch(args);
	}
}

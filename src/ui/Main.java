package ui;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.sikuli.script.Screen;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class Main extends Application {

	@Override
	  public void start(Stage primaryStage) throws Exception {
	    Button btn = new Button("Run example");
	    btn.setOnAction((e) -> {
	    	 DemoTest d = new DemoTest();
	    	 d.demo();
	    });
	    
	    Button capImage = new Button("Capture Images");
	    capImage.setOnAction((e) -> {
	    DemoTest d = new DemoTest();
		    try {
				d.snipTool();
			} catch (AWTException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    GridPane gridPane  = new GridPane();
	   /*
	    StackPane  root = new StackPane();
	    root.getChildren().add(btn);
	    root.getChildren().add(capImage);
	    root.setHga*/
	    gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        
        gridPane.add(btn, 0, 1);
        gridPane.add(capImage, 0, 2);
	    Scene scene = new Scene(gridPane, 300, 300);
	    primaryStage.setTitle("MyGuide Desktop Automator");
	    primaryStage.getIcons().add(new Image("file:resources\\ui\\ed-auto-cloud.png"));
	    primaryStage.setScene(scene);
	    primaryStage.show();
	  }
	 
	  public static void main(String[] args) {
	    launch(args);
	  }

}

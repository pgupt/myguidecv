package ui;

import java.awt.image.BufferedImage;

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
	 
	    StackPane  root = new StackPane();
	    root.getChildren().add(btn);
	 
	    Scene scene = new Scene(root, 300, 300);
	    primaryStage.setTitle("MyGuide Desktop Automator");
	    primaryStage.getIcons().add(new Image("file:resources\\ui\\ed-auto-cloud.png"));
	    primaryStage.setScene(scene);
	    primaryStage.show();
	  }
	 
	  public static void main(String[] args) {
	    launch(args);
	  }

}

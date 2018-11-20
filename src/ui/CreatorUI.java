/*
 * Add Creator UI code here
 */

package ui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import creator.ElememtCapture;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CreatorUI extends Application {
	Stage primaryStage;
	ElememtCapture elementCapture;
	static Point initial = new Point();
	static Rectangle rect = new Rectangle();
	int stepCount;
	Label guideTitleLabel;
	TextField guideTitleTF;
	String guideName;
	
	public CreatorUI(Stage stage) {
		this.primaryStage = stage;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
	}

	void createNewGuide() {
		guideTitleLabel = new Label("Guide title: ");
    	guideTitleTF = new TextField();
    	
    	Label guideDesc = new Label("Guide description: ");
    	TextField guideDescTF = new TextField();
    	
    	Button submit = new Button("Submit");
    	
    	GridPane homeGridPane  = new GridPane();
    	homeGridPane.add(guideTitleLabel, 0, 0);
    	homeGridPane.add(guideTitleTF, 1, 0);
    	
    	homeGridPane.add(guideDesc, 0, 1);
    	homeGridPane.add(guideDescTF, 1, 1);
    	
    	homeGridPane.add(submit, 1, 3);
    	
    	Scene homeScene = new Scene(homeGridPane, 300, 300);
    	primaryStage.setScene(homeScene);
        primaryStage.show();
        
        submit.setOnAction((f) -> {
        	guideName = guideTitleTF.getText();
        	new File("./"+guideName).mkdirs();
        	this.addStep();
        });
   }

	private void addStep() {
    	Button addStep = new Button("Add step");
    	GridPane addStepGridPane  = new GridPane();
    	addStepGridPane.add(addStep, 2, 1);
    	Scene addStepScene = new Scene(addStepGridPane, 300, 300);
    	primaryStage.setScene(addStepScene);
    	primaryStage.show();
    	
    	addStep.setOnAction((g) -> {
    		stepCount++;
    		this.captureAOI();	
    	});	        

    	primaryStage.setScene(addStepScene);
		primaryStage.show();
	
	}
	
	/*
	 * Capture Area of Interest
	 */
	private void captureAOI() {
		System.out.println("Start snipping tool...");
		primaryStage.hide();
		elementCapture = new ElememtCapture();
		try {
			Thread.sleep(500);
			elementCapture.screenshot();
		} catch (AWTException | IOException | InterruptedException e1) {
			e1.printStackTrace();
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//GridPane gridPane3 = new GridPane();	   
		AnchorPane stackPane = new AnchorPane();

		//Scene imageScene = new Scene(gridPane3,screenSize.getWidth(),screenSize.getHeight());
		Scene imageScene = new Scene(stackPane,screenSize.getWidth(),screenSize.getHeight());
		final Canvas canvas = new Canvas(screenSize.getWidth(),screenSize.getHeight());
		//GraphicsContext gc = canvas.getGraphicsContext2D();
		Image capture = new Image("File:screenshot.png");
		//gridPane3.add(new ImageView(capture),0,0);        

		stackPane.getChildren().addAll(canvas, new ImageView(capture),rect);
		primaryStage.setScene(imageScene);
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		//Point initial = new Point();
		imageScene.setOnMousePressed(event -> {       
			stackPane.getChildren().remove(rect);
			initial = MouseInfo.getPointerInfo().getLocation();
			System.out.println("detected: " + initial.getX() +", " + initial.getY());
			event.consume();                
		});

		imageScene.setOnMouseDragged(event -> {       
			stackPane.getChildren().remove(rect);
			Point dragging = MouseInfo.getPointerInfo().getLocation();
			System.out.println("!!dragging!!: " + dragging.getX() +", " + dragging.getY());
			//	gc.strokeRect(initial.getX(), initial.getY(), Math.abs(initial.getX()-dragging.getX()),Math.abs(initial.getY()-dragging.getY()) ); 			
			rect = new Rectangle((int)initial.getX(), (int)(initial.getY() - 30), (int)Math.abs(initial.getX()-dragging.getX()),(int)Math.abs(initial.getY()-dragging.getY()));
			rect.opacityProperty().set(0.1);
			//Rectangle rect = new Rectangle();
			//rect.setX(arg0);
			stackPane.getChildren().add(rect);
			event.consume();                
		});

		imageScene.setOnMouseReleased(event -> {
			Point finalPoint = MouseInfo.getPointerInfo().getLocation();
			System.out.println("released: " + finalPoint.getX() +", " + finalPoint.getY());   
			elementCapture.cropImage("screenshot.png",rect, guideName, stepCount);
			event.consume();
			addStep();
		});
	}

}

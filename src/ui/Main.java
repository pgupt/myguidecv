package ui;

import java.awt.AWTException;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.sikuli.guide.Guide;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.VPos;


import creator.*;

public class Main extends Application {
	
	private static Main main;
	static Point initial = new Point();
	static Rectangle rect = new Rectangle();
	ElememtCapture elementCapture;
	CreatorUI creatorUI;
	int stepCount;
	
	/*
	 * (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * Making this class singleton
	 */
	/*private Main() {
		
	}
	
	public Main getInstance() {
		if(main == null){
	        synchronized (Main.class) {
	            if(main == null){
	                main = new Main();
	            }
	        }
	    }
	    return main;
	}*/
	
	@SuppressWarnings("restriction")
	@Override
	  public void start(Stage primaryStage) throws Exception {

		Button capImage = new Button("Capture Images");
		/*capImage.setOnAction((e) -> {
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
				cropImage("screenshot.png",rect);
				event.consume();
			});


		});*/
		
    			    
	    Button createGuide = new Button("Create new guide");
	    createGuide.setOnAction((e) -> {
	    	creatorUI = new CreatorUI(primaryStage);
	    	creatorUI.createNewGuide();
	    });
	    
	    Button playGuide = new Button("Play Guide");
	    playGuide.setOnAction((f) -> {
	    	creatorUI = new CreatorUI(primaryStage);
	    	creatorUI.playGuide();
	    });
	    
	    GridPane gridPane  = new GridPane();
	   
	    gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

       
        // gridPane.add(capImage, 0, 2); // not adding capture Image button in UI
        gridPane.add(createGuide, 0, 3);
        GridPane.setValignment(createGuide, VPos.CENTER);
        gridPane.add(playGuide, 0, 5);
        GridPane.setValignment(playGuide, VPos.CENTER);
	    Scene scene = new Scene(gridPane, 300, 300);
	    primaryStage.setTitle("MyGuide Desktop Automator");
	    primaryStage.getIcons().add(new Image("file:resources\\ui\\ed-auto-cloud.png"));
	    primaryStage.setScene(scene);
	    primaryStage.show();
	  }
	 
	public static BufferedImage cropImage(String imageFile, Rectangle rect) {
		try {
			BufferedImage image = ImageIO.read(new File(imageFile));
			BufferedImage subImage = image.getSubimage((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
			Timestamp t = new Timestamp(System.currentTimeMillis());
			// String croppedImageFilename = "./"+ guideName+"/"+t.getTime()+".png";
			// ImageIO.write(subImage, "png", new File(croppedImageFilename));
			// System.out.println("File created: "+croppedImageFilename);
			ImageIO.write(subImage, "png", new File("test_cropped.png"));
			return subImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	  @SuppressWarnings("restriction")
	public static void main(String[] args) {
	    launch(args);
	  }

}

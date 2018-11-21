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

import creator.DB;
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
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;

@SuppressWarnings("restriction")
public class CreatorUI extends Application {
	Stage primaryStage;
	ElememtCapture elementCapture;
	static Point initial = new Point();
	static Rectangle rect = new Rectangle();
	int stepCount;
	int guideId;
	public String author = "pramod";
	Label guideTitleLabel;
	TextField guideTitleTF;
	String guideName;
	static boolean finished;
	Thread snipping;
	
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
            new File("./Guides/"+guideName).mkdirs();
            this.addStep();
            //use this part to link to database
            DB db = new DB();
            guideId = db.createGuide(guideName, author); //default name to Pramod

            //fornowguideid will be Math.random 100000 or
        });
   }

	private void addStep() {
    	Button addStep = new Button("Capture Screen");
    	AnchorPane addStepGridPane  = new AnchorPane();
    	addStepGridPane.getChildren().addAll(addStep);
    	AnchorPane.setLeftAnchor(addStepGridPane.getChildren().get(0), 90.0);
    	AnchorPane.setTopAnchor(addStepGridPane.getChildren().get(0), 135.0);
    	Scene addStepScene = new Scene(addStepGridPane, 300, 300);
    	primaryStage.setAlwaysOnTop(true);
    	primaryStage.setScene(addStepScene);
    	primaryStage.show();
    	
    	
    	addStep.setOnAction((g) -> {
    		stepCount++;
			//primaryStage.hide();
    		this.captureAOI();	        	    	  		    		
	    });    	
    	
    	primaryStage.setScene(addStepScene);
    	primaryStage.show();
    	
	}
	
	private void configureStep() {
		AnchorPane configureStepPane = new AnchorPane();
		//configureStepPane.setPadding(new Insets(20,20,20,20));
		//configureStepPane.setVgap(5);
		//configureStepPane.setHgap(5);		
    	ObservableList<String> stepOptionsList = FXCollections.observableArrayList(
    			"Click",
    			"Double Click",
    			"Right Click",
    			"Hover",
    			"Enter Text",
    			"Get Text"   			
    			);
    	
    	ComboBox stepOptionsBox = new ComboBox();
    	stepOptionsBox.getItems().addAll(stepOptionsList);
    	Button saveStep = new Button("Save Step");
    	Button addNewStep = new Button("Add New Step");
    	Button reCapture = new Button("Recapture Image");
    	Button back = new Button("Main Menu");
    	
    	Image croppedThumbnail = new Image("File:./Guides/"+guideName+"/"+stepCount+".png");
    	ImageView cropped = new ImageView(croppedThumbnail);
    	double maxDimension,minDimension;
    	if(croppedThumbnail.getHeight() > croppedThumbnail.getWidth()) {
        	cropped.setFitHeight(250);
        	cropped.setFitWidth(250*(croppedThumbnail.getWidth()/croppedThumbnail.getHeight()));    		
    	}
    	else {
    		cropped.setFitWidth(250);
        	cropped.setFitHeight(250*(croppedThumbnail.getHeight()/croppedThumbnail.getWidth()));
    	}
    	
    	configureStepPane.getChildren().addAll(cropped,stepOptionsBox,saveStep,addNewStep,reCapture,back);
    	//Position Image
    	AnchorPane.setLeftAnchor(configureStepPane.getChildren().get(0), 40.0);
    	AnchorPane.setTopAnchor(configureStepPane.getChildren().get(0), ((400-cropped.getFitHeight())/2) - 35);
    	//Position combobox
    	AnchorPane.setLeftAnchor(configureStepPane.getChildren().get(1), 375.0);
    	AnchorPane.setTopAnchor(configureStepPane.getChildren().get(1), 75.0);
    	//Position saveStep button
    	AnchorPane.setLeftAnchor(configureStepPane.getChildren().get(2), 350.0);
    	AnchorPane.setTopAnchor(configureStepPane.getChildren().get(2), 300.0);
    	//Position addNewStep button
    	AnchorPane.setLeftAnchor(configureStepPane.getChildren().get(3), 450.0);
    	AnchorPane.setTopAnchor(configureStepPane.getChildren().get(3), 300.0);
    	//Position reCapture button
    	AnchorPane.setLeftAnchor(configureStepPane.getChildren().get(4),80.0);
    	AnchorPane.setBottomAnchor(configureStepPane.getChildren().get(4), 40.0);
    	stepOptionsBox.getSelectionModel().getSelectedItem();
    	//Position cance; button
    	AnchorPane.setTopAnchor(configureStepPane.getChildren().get(5), 75.0);
    	AnchorPane.setRightAnchor(configureStepPane.getChildren().get(5), 40.0);
    	Scene configureStepScene = new Scene(configureStepPane,700,400);
    	primaryStage.setX(600);
    	primaryStage.setY(350);
    	primaryStage.setMaximized(false);
    	primaryStage.setScene(configureStepScene);
    	primaryStage.show();
    	
    	addNewStep.setOnAction((j) ->{
    		addStep();  		
    	});		
    	
    	saveStep.setOnAction((k) ->{
    		 //Save the step to database
            DB db = new DB();
            String action = (String) stepOptionsBox.getSelectionModel().getSelectedItem();
            db.createStep(guideId, stepCount, action); //guideId needs to be given  and action
    	});
    	
    	reCapture.setOnAction((l) ->{
    		stepCount--;
    		addStep();
    	});
    	
    	back.setOnAction((m)-> {
    		this.start();
    	});
	}
	
	/*
	 * Capture Area of Interest
	 */
	private void captureAOI() {
		
			System.out.println("Start snipping tool...");
	    	primaryStage.setAlwaysOnTop(false);
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
			
			rect = new Rectangle();
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
				configureStep();
			});
		}
	
	public void playGuide() {
		AnchorPane guideListPane = new AnchorPane();
		ObservableList<String> guideList = FXCollections.observableArrayList();
		//Get list of available guides
		File guidesFile = new File("./Guides/");
		File[] guideFiles = guidesFile.listFiles();
		for(File guide : guideFiles) {
			guideList.add(guide.getName());
			System.out.println(guide.getName());
		}
		
		ListView guideSelection = new ListView();
		guideSelection.setItems(guideList);
		Label guidesLabel = new Label("Select a guide:");
		Button playGuide = new Button("Play Guide");
		Scene guideListScene = new Scene(guideListPane,400,400);
		guideListPane.getChildren().addAll(guideSelection,playGuide);
		//Position listview
		AnchorPane.setLeftAnchor(guideListPane.getChildren().get(0),40.0);
		AnchorPane.setRightAnchor(guideListPane.getChildren().get(0),40.0);
		AnchorPane.setBottomAnchor(guideListPane.getChildren().get(0),120.0);
		AnchorPane.setTopAnchor(guideListPane.getChildren().get(0),40.0);

		//Position play button
		AnchorPane.setLeftAnchor(guideListPane.getChildren().get(1),150.0);
		AnchorPane.setBottomAnchor(guideListPane.getChildren().get(1),40.0);

		primaryStage.setScene(guideListScene);
		primaryStage.show();
		
	}
	
	public void start() {
		
	    Button createGuide = new Button("Create new guide");
	    createGuide.setOnAction((e) -> {
	    	this.createNewGuide();
	    });
	    
	    Button playGuide = new Button("Play Guide");
	    playGuide.setOnAction((f) -> {
	    	this.playGuide();
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
		
	
}

package ui;

import java.io.File;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import player.PlayGuide;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;

import common.*;

public class PlayerUI extends Application {
	Stage primaryStage;
	PlayGuide playGuide;
	String selectedGuide;
	
	public PlayerUI(Stage stage) {
		primaryStage = stage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		
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
		Button play = new Button("Play");
		
		guideSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        System.out.println("ListView selection changed from oldValue = " 
		                + oldValue + " to newValue = " + newValue);
		        selectedGuide  = newValue;
		    }

		});
		
		play.setOnAction((f) -> {
			playGuide = new PlayGuide();
			playGuide.fetchAndPlay(selectedGuide);
		});
		
		Scene guideListScene = new Scene(guideListPane,400,400);
		guideListPane.getChildren().addAll(guideSelection,play);
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

}

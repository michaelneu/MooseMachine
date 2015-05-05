package com.cs.moose;

import com.cs.moose.ui.IDE;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	private static final String VERSION = "0.0.0";
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		IDE.Stage = stage;
		
		stage.setTitle("MooseMachine " + VERSION);
		//Image icon = new Image(getClass().getResourceAsStream("icon.png"));
		//stage.getIcons().add(icon);
		
		// safely exit and kill all threads
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ui/IDE.fxml"));
		Scene scene = new Scene(root);
		stage.setMinWidth(600);
		stage.setMinHeight(400);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void launchNew() {
		// not detachable
	}
}

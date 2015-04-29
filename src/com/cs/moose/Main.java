package com.cs.moose;

import com.cs.moose.ui.IDE;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		IDE.Stage = stage;
		
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ui/IDE.fxml"));
		Scene scene = new Scene(root);
		stage.setMinWidth(600);
		stage.setMinHeight(400);
		
		stage.setScene(scene);
		stage.show();
	}
}

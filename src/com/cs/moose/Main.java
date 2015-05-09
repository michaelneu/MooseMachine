package com.cs.moose;

import java.io.File;
import java.util.ArrayList;

import com.cs.moose.locale.Locale;
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
	private static final String VERSION = "0.1";
	
	public static void main(String[] args) {
		// first of all generate locale
		Locale.generateLocale();
		
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
		try {
			// see http://stackoverflow.com/questions/4159802/how-can-i-restart-a-java-application
			
			final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

			final ArrayList<String> command = new ArrayList<String>();
			command.add(javaBin);
			command.add("-jar");
			command.add(currentJar.getPath());
	
			final ProcessBuilder builder = new ProcessBuilder(command);
			builder.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

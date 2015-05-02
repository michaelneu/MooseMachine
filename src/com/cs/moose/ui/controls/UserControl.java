package com.cs.moose.ui.controls;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public abstract class UserControl extends AnchorPane implements Initializable {
	public UserControl(String fxmlFile) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

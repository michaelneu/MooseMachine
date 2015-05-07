package com.cs.moose.ui.controls;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public class MessageBox {
	private static Alert getAlertBase(String message, String title, AlertType type) {
		Alert alert = new Alert(type);

		alert.initModality(Modality.APPLICATION_MODAL);
		
		alert.setWidth(400);
		alert.setHeight(175);
		
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(message);
		
		return alert;
	}
	
	
	public static void showError(String message, String title) {
		Alert alert = getAlertBase(message, title, AlertType.ERROR);
		alert.showAndWait();
	}
	
	public static boolean confirm(String message, String title) {
		Alert alert = getAlertBase(message, title, AlertType.CONFIRMATION);
		Optional<ButtonType> result = alert.showAndWait();
		
		return result.get() == ButtonType.OK;
	}
}

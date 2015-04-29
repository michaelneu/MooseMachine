package com.cs.moose.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.cs.moose.ui.controls.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings({"rawtypes", "unchecked"})
public class IDE implements Initializable {
	// reference required for filechooser
	public static Stage Stage;
	private static FileChooser fileChooser;
	
	@FXML
	private WebView editor, debugEditor;
	@FXML
	private AnchorPane debugView;
	@FXML
	private Label titlebarText;
	@FXML
	private Polygon titlebarPolygon;
	@FXML
	private TableView memoryTable;
	@FXML 
	private AnchorPane mainMenu;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		editor.setContextMenuEnabled(false);
		debugEditor.setContextMenuEnabled(false);
		
		memoryTable.setSelectionModel(new NullTableViewSelectionModel(memoryTable));
		
		fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("moose-Dateien", "*.moose");
		fileChooser.getExtensionFilters().add(filter);
		filter = new FileChooser.ExtensionFilter("Text-Dateien", "*.txt");
		fileChooser.getExtensionFilters().add(filter);
	}
	
	private void displayMemory(short[] memory) {
		Object[] columns = memoryTable.getColumns().toArray();
		
		for (int i = 0; i < columns.length; i++) {
			TableColumn column = (TableColumn)columns[i];
			
			column.setSortable(false);
			column.setCellValueFactory(new PropertyValueFactory<MemoryTableRow, String>("column" + i));
		}
		
		ObservableList<MemoryTableRow> data = FXCollections.observableArrayList();
		for (MemoryTableRow row : MemoryTableRow.getRows(memory)) {
			data.add(row);
		}
		
		memoryTable.setItems(data);
	}
	
	@FXML
	private void toggleModes(MouseEvent event) {
		if (!editor.isVisible()) {
			titlebarText.setText("Editor");
			titlebarPolygon.setFill(Color.CORAL);
		} else { 
			titlebarText.setText("Debug");
			titlebarPolygon.setFill(Color.DARKGRAY);
		}
		
		editor.setVisible(!editor.isVisible());
		debugView.setVisible(!debugView.isVisible());
	}
	
	
	
	@FXML
	private void hideMainMenu(MouseEvent event) {
		if (mainMenu.isVisible()) {
			mainMenu.setVisible(false);
		}
	}
	@FXML
	private void showMainMenu(MouseEvent event) {
		if (!mainMenu.isVisible()) {
			mainMenu.setVisible(true);
			event.consume();
		}
	}
	@FXML
	private void mainMenuOpenFile(MouseEvent event) {
		java.io.File file = fileChooser.showOpenDialog(Stage);
		
		if (file != null) {
			titlebarText.setText(file.getAbsolutePath());
		}
	}
	@FXML
	private void mainMenuSave(MouseEvent event) {
		if (titlebarText.getText().equals("Editor")) {
			this.mainMenuSaveAs(event);
		} else {
			
		}
	}
	@FXML
	private void mainMenuSaveAs(MouseEvent event) {
		java.io.File file = fileChooser.showSaveDialog(Stage);
		
		if (file != null) {
			try {
				com.cs.moose.io.File.writeAllText(file.getAbsolutePath(), "");
			} catch (Exception ex) {
				Dialog.showError("Fehler beim Speichern der Datei", "Fehler");
			}
		}
	}
}

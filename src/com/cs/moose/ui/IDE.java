package com.cs.moose.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.cs.moose.io.File;
import com.cs.moose.ui.controls.*;
import com.cs.moose.ui.controls.editor.CodeEditor;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings({"rawtypes", "unchecked"})
public class IDE implements Initializable {
	// reference required for filechooser
	public static Stage Stage;
	private static FileChooser fileChooser;
	
	@FXML
	private CodeEditor editor, debugEditor;
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
		// initialize filechooser
		fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("moose-Dateien", "*.moose");
		fileChooser.getExtensionFilters().add(filter);
		filter = new FileChooser.ExtensionFilter("Text-Dateien", "*.txt");
		fileChooser.getExtensionFilters().add(filter);

		
		// initialize tableview
		memoryTable.setSelectionModel(new NullTableViewSelectionModel(memoryTable));
		Object[] columns = memoryTable.getColumns().toArray();
		
		for (int i = 0; i < columns.length; i++) {
			TableColumn column = (TableColumn)columns[i];
			
			column.setSortable(false);
			column.setCellValueFactory(new PropertyValueFactory<MemoryTableRow, String>("column" + i));
		}
	}
	
	private void displayMemory(short[] memory) {

		ObservableList<MemoryTableRow> data = FXCollections.observableArrayList();
		for (MemoryTableRow row : MemoryTableRow.getRows(memory)) {
			data.add(row);
		}
		
		memoryTable.setItems(data);
	}
	
	private String titlebarTextValue;
	@FXML
	private void toggleModes(MouseEvent event) {
		if (!editor.isVisible()) {
			titlebarText.setText(titlebarTextValue);
			titlebarPolygon.setFill(Color.CORAL);
		} else { 
			titlebarTextValue = titlebarText.getText();
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
			String path = file.getAbsolutePath();
			
			try {
				String code = File.readAllText(path);
				
				editor.setCode(code);
				titlebarText.setText(path);
			} catch (Exception ex) {
				// display messagebox
			}
		}
	}
	@FXML
	private void mainMenuSave(MouseEvent event) {
		if (titlebarText.getText().equals("Editor")) {
			this.mainMenuSaveAs(event);
		} else {
			try {
				String filename = titlebarText.getText();
				String code = editor.getCode();
				
				File.writeAllText(filename, code);
			} catch (Exception ex) {
				// display messagebox
			}
		}
	}
	@FXML
	private void mainMenuSaveAs(MouseEvent event) {
		java.io.File file = fileChooser.showSaveDialog(Stage);
		
		if (file != null) {
			try {
				String path = file.getAbsolutePath(),
						code = editor.getCode();
				
				File.writeAllText(path, code);
				titlebarText.setText(path);
			} catch (Exception ex) {
				// display messagebox
			}
		}
	}
}

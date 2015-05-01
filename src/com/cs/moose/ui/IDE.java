package com.cs.moose.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.cs.moose.Main;
import com.cs.moose.exceptions.CompilerException;
import com.cs.moose.exceptions.JumpPointException;
import com.cs.moose.exceptions.SyntaxException;
import com.cs.moose.io.File;
import com.cs.moose.locale.*;
import com.cs.moose.machine.Compiler;
import com.cs.moose.machine.Lexer;
import com.cs.moose.machine.Machine;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings({"rawtypes", "unchecked"})
public class IDE implements Initializable {
	// reference required for filechooser
	public static Stage Stage;
	private static FileChooser fileChooser;
	private static ILocale locale = ILocale.getLocale();
	
	private String currentFile;
	private Machine currentMachine;
	
	@FXML
	private CodeEditor editor, debugEditor;
	@FXML
	private AnchorPane debugView, mainMenu, iconPause;
	@FXML
	private Label titlebarText;
	@FXML
	private Polygon titlebarPolygon, iconPlay;
	@FXML
	private TableView memoryTable;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initialize filechooser (set its filters)
		fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("moose-" + locale.getFiles(), "*.moose");
		fileChooser.getExtensionFilters().add(filter);
		filter = new FileChooser.ExtensionFilter("Text-" + locale.getFiles(), "*.txt");
		fileChooser.getExtensionFilters().add(filter);

		
		// initialize tableview
		memoryTable.setSelectionModel(new NullTableViewSelectionModel(memoryTable));
		Object[] columns = memoryTable.getColumns().toArray();
		
		for (int i = 0; i < columns.length; i++) {
			TableColumn column = (TableColumn)columns[i];
			
			column.setSortable(false);
			column.setCellValueFactory(new PropertyValueFactory<MemoryTableRow, String>("column" + i));
		}
		
		
		// initially display blank memory to decrease load times on first compilation
		displayMemory(new short[65536]);
	}
	
	private void displayMemory(short[] memory) {
		ObservableList<MemoryTableRow> data = FXCollections.observableArrayList();
		for (MemoryTableRow row : MemoryTableRow.getRows(memory)) {
			data.add(row);
		}
		
		memoryTable.setItems(data);
	}
	
	
	@FXML
	private void toggleModes(MouseEvent event) {
		// check for code editing beforehand
		if (currentFile == null || editor.getCodeEdited()) {
			// prompt user
			if (!Dialog.confirm(locale.getCompileFileNotSavedWarning(), locale.getCodeNotSaved())) {
				return;
			}
		}
		
		if (currentMachine == null) {
			String code = editor.getCode();
			try {
				Lexer lexer = new Lexer(code);
				currentMachine = Compiler.getMachine(lexer);
				
				displayMemory(currentMachine.toMachineState().getMemory());
				
				debugEditor.setCode(code);
				debugEditor.highlightLine(1);
				
				toggleView();
			} catch (SyntaxException ex) {
				ex.printStackTrace();
				Dialog.showError(locale.getSyntaxErrorInLine(ex.getLine() + 1), locale.getCompilerError());
			} catch (JumpPointException ex) {
				Dialog.showError(locale.getCompilerJumpPointError(), locale.getCompilerError());
			} catch (CompilerException ex) {
				Dialog.showError(locale.getCompilerUnknownError(), locale.getCompilerError());
			}
		} else {
			currentMachine = null;
			
			toggleView();
		}
	}
	
	private void toggleView() {
		if (currentMachine == null) {
			if (currentFile == null) {
				titlebarText.setText("Editor");
			} else {
				titlebarText.setText(currentFile);
			}
			
			titlebarPolygon.setFill(Color.CORAL);
		} else {
			titlebarText.setText("Debug");
			titlebarPolygon.setFill(Color.DARKGRAY);
		}
		
		editor.setVisible(!editor.isVisible());
		debugView.setVisible(!debugView.isVisible());

		iconPause.setVisible(!iconPause.isVisible());
		iconPlay.setVisible(!iconPlay.isVisible());
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
	private void mainMenuNew(MouseEvent event) {
		Main.launchNew();
	}
	@FXML
	private void mainMenuOpenFile(MouseEvent event) {
		boolean edited = editor.getCodeEdited();
		if (!edited || (edited && Dialog.confirm(locale.getOpenFileNotSavedWarning(), locale.getCodeNotSaved()))) {
			java.io.File file = fileChooser.showOpenDialog(Stage);
			
			if (file != null) {
				String path = file.getAbsolutePath();
				
				try {
					String code = File.readAllText(path);
					
					editor.setCode(code);
					titlebarText.setText(path);
					currentFile = path;
					
					editor.getCodeEdited();
				} catch (Exception ex) {
					// display messagebox
				}
			}
		}
	}
	@FXML
	private void mainMenuSave(MouseEvent event) {
		if (currentFile == null) {
			this.mainMenuSaveAs(event);
		} else {
			try {
				String code = editor.getCode();
				
				File.writeAllText(currentFile, code);
				editor.getCodeEdited();
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
				currentFile = path;
				
				editor.getCodeEdited();
			} catch (Exception ex) {
				// display messagebox
			}
		}
	}
	@FXML
	private void mainMenuExit(MouseEvent event) {
		boolean edited = editor.getCodeEdited();
		
		if (!edited || (edited && Dialog.confirm("Code nicht gespeichert. \nTrotzdem beenden?", "Code nicht gespeichert"))) {
			System.exit(0);
		}
	}
	
	@FXML
	private void keyTypedHandler(KeyEvent event) {
		if (event.isControlDown()) {
			switch (event.getCharacter().charAt(0)) {
				case 14: // ctrl + n
					mainMenuNew(null);
					break;
					
				case 15: // ctrl + o
					mainMenuOpenFile(null);
					break;
					
				case 19:  // ctrl + s
					mainMenuSave(null);
					break;
					
				case 83: // ctrl + shift + s
					mainMenuSaveAs(null);
					break;
					
				case 23: // ctrl + w
					mainMenuExit(null);
					break;
					
				default: 
					break;
			}
		}
	}
}

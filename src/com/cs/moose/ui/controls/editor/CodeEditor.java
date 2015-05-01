package com.cs.moose.ui.controls.editor;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CodeEditor extends AnchorPane implements Initializable {
	private static String editorTemplate;
	
	@FXML
	private WebView editor;
	private WebEngine engine;
	
	
	public CodeEditor() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CodeEditor.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (editorTemplate == null) {
			editorTemplate = getEditorTemplate();
		}
		
		editor.setContextMenuEnabled(false);
		engine = editor.getEngine();
		
		
		setCode("");
	}
	
	@SuppressWarnings("resource")
	private static String getEditorTemplate() {
		InputStream indexStream = CodeEditor.class.getResourceAsStream("index.html");
		Scanner reader = new Scanner(indexStream).useDelimiter("\\A");
		
		if (reader.hasNext()) {
			return reader.next();
		} else {
			return "<h1>Fehler beim Laden des Editors</h1>";
		}
	}
	
	@FXML
	private void keyPressHandler(KeyEvent event) {
		final double scaleFactor = 0.1;
		
		if (event.isControlDown()) {
			double currentScale = editor.getZoom();
			KeyCode code = event.getCode();
			
			if (code == KeyCode.PLUS) {
				currentScale += scaleFactor;
				editor.setZoom(currentScale);
			} else if (code == KeyCode.MINUS && currentScale > 0) {
				currentScale -= scaleFactor;
				editor.setZoom(currentScale);
			}
		}
	}

	private boolean autofocus;
	public boolean getAutofocus() {
		return this.autofocus;
	}
	public void setAutofocus(boolean value) {
		this.autofocus = value;
	}
	
	private boolean lineHighlight;
	public boolean getHighlightLine() {
		return this.lineHighlight;
	}
	public void setHighlightLine(boolean value) {
		this.lineHighlight = value;
	}

	
	public void setCode(String code) {
		String content = editorTemplate.replace("${autofocus}", this.autofocus ? " autofocus" : "");
		content = content.replace("${highlight-line}", this.lineHighlight ? "styleActiveLine: true," : "");
		content = content.replace("${code}", code);
		
		this.engine.loadContent(content);
	}
	
	public String getCode() {
		String code = (String)engine.executeScript("editor.getValue();");
		
		return code;
	}
	
	public void highlightLine(int line) {
		if (line > 0) {
			engine.executeScript("editor.setCursor({line: " + (line - 1) + ", ch:0});");
		}
	}
}

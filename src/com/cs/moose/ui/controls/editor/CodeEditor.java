package com.cs.moose.ui.controls.editor;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.cs.moose.machine.Lexer;
import com.cs.moose.ui.controls.UserControl;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CodeEditor extends UserControl {
	private static String editorTemplate;
	
	@FXML
	private WebView editor;
	private WebEngine engine;
	
	private boolean codeEdited;
	private String[] codeLines;
	
	public CodeEditor() {
		super("CodeEditor.fxml");
	}

	private void reloadEditor() {
		String code = "";
		
		try {
			code = getCode();
		} catch (Exception ex) {
			
		}
		
		setCode(code);
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
			return "<h1>Unable to load editor</h1>";
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
		} else {
			codeEdited = true;
		}
	}
	
	@FXML
	private void mouseReleasedHandler(MouseEvent event) {
		if (this.readonly && this.lineHighlight) {
			event.consume();
			highlightLine(currentLine);
		}
	}
	
	public boolean getCodeEdited() {
		boolean temp = this.codeEdited;
		this.codeEdited = false;
		
		return temp;
	}

	private boolean autofocus;
	public boolean getAutofocus() {
		return this.autofocus;
	}
	public void setAutofocus(boolean value) {
		this.autofocus = value;
		
		reloadEditor();
	}

	private boolean lineHighlight;
	public boolean getHighlightLine() {
		return this.lineHighlight;
	}
	public void setHighlightLine(boolean value) {
		this.lineHighlight = value;
		
		reloadEditor();
	}
	
	private boolean readonly;
	public boolean getReadonly() {
		return this.readonly;
	}
	public void setReadonly(boolean value) {
		this.readonly = value;
		
		reloadEditor();
	}
	
	private int initialLine;
	public int getInitialLine() {
		return this.initialLine;
	}
	public void setInitialLine(int line) {
		this.initialLine = line;
	}

	
	public void setCode(String code) {
		this.codeLines = Lexer.stripNonCommands(code).split("\n");
		
		String content = editorTemplate.replace("${autofocus}", this.autofocus ? "autofocus: true," : "");
		content = content.replace("${highlight-line}", this.lineHighlight ? "styleActiveLine: true," : "");
		content = content.replace("${readonly}", this.readonly ? "readOnly: true, cursorBlinkRate: -1," : "");
		
		if (this.initialLine > 0) {
			content = content.replace("${initial-line}", "editor.setCursor({line: " + (this.initialLine - 1) + ", ch:0});");
		} else {
			content = content.replace("${initial-line}", "");
		}
		
		content = content.replace("${code}", code);
		
		this.engine.loadContent(content);
	}
	
	public String getCode() {
		String code = (String)engine.executeScript("editor.getValue();");
		
		return code;
	}
	
	private int currentLine;
	public void highlightLine(int line) {
		if (line > 0) {
			currentLine = line;
			engine.executeScript("editor.setCursor({line: " + (line - 1) + ", ch:0});");
		}
	}
	
	public int findNextCommandLine(int targetLine) {
		int whitespaces = 0,
			nonWhitespaces = 0;
		
		for (String line : codeLines) {
			if (line.length() == 0) {
				whitespaces++;
			} else {
				if (nonWhitespaces == targetLine) {
					break;
				} else {
					nonWhitespaces++;
				}
			}
		}
		
		return whitespaces + nonWhitespaces + 1;
	}
	public void highlightNextCommandLine(int targetLine) {
		int line = findNextCommandLine(targetLine);
		
		highlightLine(line);
	}
}

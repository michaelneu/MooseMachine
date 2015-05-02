package com.cs.moose.ui.controls.debugger;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import com.cs.moose.exceptions.MachineException;
import com.cs.moose.machine.Lexer;
import com.cs.moose.machine.Machine;
import com.cs.moose.ui.controls.UserControl;
import com.cs.moose.ui.controls.editor.CodeEditor;
import com.cs.moose.ui.controls.memorytable.MemoryTable;

public class DebugView extends UserControl {
	@FXML
	private MemoryTable memoryTable;
	@FXML
	private CodeEditor editor;
	
	private Machine machine;
	private String[] strippedCode;
	
	public DebugView() {
		super("DebugView.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void startDebug(String code, Machine machine) {
		this.machine = machine;
		this.strippedCode = Lexer.stripNonCommands(code).split("\n");
		
		int initialLine = findCurrentLine();
		editor.setInitialLine(initialLine);
		editor.setCode(code);
	}
	
	private void updateDebugger() {
		this.memoryTable.setMemory(machine.getWorkingMemory());
		
		int currentLine = findCurrentLine();
		editor.highlightLine(currentLine);
	}
	
	private int findCurrentLine() {
		int targetLine = machine.getNextCommand() / 2,
				whitespaces = 0,
				nonWhitespaces = 0;
		
		for (String line : strippedCode) {
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
	
	
	public void goForwards() {
		try {
			this.machine.goForward();
			updateDebugger();
		} catch (MachineException ex) {
			// display error
		}
	}
	
	public void runCompleteProgram() {
		
	}
	
	public boolean goBackwards() {
		boolean successfull = this.machine.goBackwards();
		
		if (successfull) {
			updateDebugger();
		}
		
		return successfull;
	}
	
	public boolean canGoForwards() {
		return this.machine.isRunning();
	}
}

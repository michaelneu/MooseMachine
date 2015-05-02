package com.cs.moose.ui.controls.debugger;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import com.cs.moose.machine.Machine;
import com.cs.moose.types.MachineState;
import com.cs.moose.ui.controls.UserControl;
import com.cs.moose.ui.controls.editor.CodeEditor;
import com.cs.moose.ui.controls.memorytable.MemoryTable;

public class DebugView extends UserControl {
	@FXML
	private MemoryTable memoryTable;
	@FXML
	private CodeEditor editor;
	
	public DebugView() {
		super("DebugView.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void startDebug(String code, Machine machine) {
		editor.setCode(code);
		editor.highlightLine(1);
		
		MachineState state = machine.toMachineState();
		memoryTable.setMemory(state.getMemory());
	}
}

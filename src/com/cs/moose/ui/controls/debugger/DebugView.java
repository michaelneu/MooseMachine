package com.cs.moose.ui.controls.debugger;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import com.cs.moose.BackgroundWorker;
import com.cs.moose.exceptions.MachineException;
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
	@FXML
	private TextArea stdout;
	@FXML
	private CheckBox cbHold,cbOverflow, cbNegative, cbZero;
	@FXML
	private Label lblAccu;
	
	private volatile Machine machine;
	private volatile boolean paused = true;
	private BackgroundWorker debugWorker;
	
	public DebugView() {
		super("DebugView.fxml");
		
		debugWorker = new BackgroundWorker() {
			
			@Override
			public void onWorkerDone(BackgroundWorker.Finished args) {
				
			}
			
			@Override
			public void onProgressChanged(BackgroundWorker.ProgressChanged args) {
				switch (args.getProgress()) {
					case 0:
						((MachineException)args.getUserState()).printStackTrace();
						break;
						
					case 1: 
						pause();
						updateDebugger();
						break;
				}
			}
			
			@Override
			public void onDoWork(BackgroundWorker.Parameters args) {
				boolean guiNeedsUpdate = false;
				
				while (!args.isCancelled()) {
					if (machine != null) {
						boolean running = !machine.toMachineState().getFlagHold();
					
						if (!paused && running) {
							guiNeedsUpdate = true;
							
							try {
								machine.goForward();
							} catch (MachineException ex) {
								pause();
								debugWorker.reportProgress(0, ex);
							}
						} else if (guiNeedsUpdate) {
							guiNeedsUpdate = false;
							
							debugWorker.reportProgress(1);
						} else if (!paused && !running) {
							pause();
						} else {
							BackgroundWorker.sleep(100);
						}
					} else {
						BackgroundWorker.sleep(100);
					}
				}
			}
		};
		
		// start the debug worker
		debugWorker.runWorkerAsync();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void startDebug(String code, Machine machine) {
		pause();
		
		this.machine = machine;
		
		this.memoryTable.setMemory(machine.toMachineState().getMemory());

		this.editor.setCode(code);
		int initialLine = editor.findNextCommandLine(0);
		this.editor.setInitialLine(initialLine);
		

		this.stdout.setText("");
		this.lblAccu.setText("0");
		
		for (CheckBox box : new CheckBox[] { cbHold, cbOverflow, cbNegative, cbZero }) {
			box.setSelected(false);
		}
	}
	
	private void updateDebugger() {
		MachineState state = this.machine.toMachineState();
		
		this.memoryTable.setMemory(state.getMemory());
		this.editor.highlightNextCommandLine(state.getNextCommand() / 2);
		this.stdout.setText(state.getStdout());
		
		this.cbHold.setSelected(state.getFlagHold());
		this.cbOverflow.setSelected(state.getFlagV());
		this.cbNegative.setSelected(state.getFlagNegative());
		this.cbZero.setSelected(state.getFlagZero());
		
		this.lblAccu.setText(state.getAccumulator() + "");
	}
	
	
	public void next() {
		try {
			this.machine.goForward();
			updateDebugger();
		} catch (MachineException ex) {
			// display error
		}
	}
	public boolean prev() {
		boolean successfull = this.machine.goBackwards();
		
		if (successfull) {
			updateDebugger();
		}
		
		return successfull;
	}
	
	
	public void pause() {
		this.paused = true;
	}
	
	public void unpause() {
		this.paused = false;
	}
	
	
	public boolean isPlaying() {
		return !this.paused && (this.machine != null && !this.machine.toMachineState().getFlagHold());
	}
}

package com.cs.moose.ui.controls.debugger;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;

import com.cs.moose.BackgroundWorker;
import com.cs.moose.exceptions.MachineException;
import com.cs.moose.machine.Machine;
import com.cs.moose.ui.controls.UserControl;
import com.cs.moose.ui.controls.editor.CodeEditor;
import com.cs.moose.ui.controls.memorytable.MemoryTable;

public class DebugView extends UserControl {
	@FXML
	private MemoryTable memoryTable;
	@FXML
	private CodeEditor editor;
	
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
						boolean running = machine.isRunning();
					
						if (!paused && running) {
							guiNeedsUpdate = true;
							
							try {
								machine.goForward();
							} catch (MachineException ex) {
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
		
		this.memoryTable.setMemory(machine.getWorkingMemory());

		editor.setCode(code);
		int initialLine = editor.findNextCommandLine(0);
		editor.setInitialLine(initialLine);
	}
	
	private void updateDebugger() {
		this.memoryTable.setMemory(machine.getWorkingMemory());
		editor.highlightNextCommandLine(machine.getNextCommand() / 2);
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
		return !this.paused && (this.machine != null && this.machine.isRunning());
	}
}

package com.cs.moose.machine;

import com.cs.moose.types.*;

import java.util.ArrayList;

public class Machine {
	private int accumulator;
	private int[] memory;
	private int nextCommand, currentCommand;
	
	private ArrayList<MachineState> previousStates;
	
	public Machine(int[] memory) {
		previousStates = new ArrayList<MachineState>();
	}
	
	public void goForward() {
		MachineState currentState = toMachineState();
		this.previousStates.add(currentState);
	}
	
	public void goBackwards() {
		int length = this.previousStates.size();
		
		if (length > 0) {
			MachineState lastState = this.previousStates.get(length - 1);
			this.previousStates.remove(length - 1);
			
			fromMachineState(lastState);
		}
	}
	
	private void fromMachineState(MachineState state) {
		this.memory = state.getMemory();
		this.currentCommand = state.getCurrentCommand();
		this.nextCommand = state.getNextCommand();
		this.accumulator = state.getAccumulator();
	}
	
	private MachineState toMachineState() {
		return new MachineState(this.accumulator, this.memory.clone(), this.nextCommand, this.currentCommand);
	}
}

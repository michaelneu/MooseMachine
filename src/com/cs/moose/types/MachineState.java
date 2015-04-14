package com.cs.moose.types;

public class MachineState {
	private final int[] memory;
	private final int nextCommand, currentCommand, accumulator;

	public MachineState(int accumulator, int[] memory, int nextCommand, int currentCommand) {
		this.accumulator = accumulator;
		this.memory = memory;
		this.nextCommand = nextCommand;
		this.currentCommand = currentCommand;
	}
	
	public int[] getMemory() {
		return memory;
	}

	public int getNextCommand() {
		return nextCommand;
	}

	public int getCurrentCommand() {
		return currentCommand;
	}
	
	public int getAccumulator() {
		return accumulator;
	}
}

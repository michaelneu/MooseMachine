package com.cs.moose.types;

public class MachineState {
	private final int[] memory;
	private final int nextCommand, accumulator;

	public MachineState(int accumulator, int[] memory, int nextCommand) {
		this.accumulator = accumulator;
		this.memory = memory;
		this.nextCommand = nextCommand;
	}
	
	public int[] getMemory() {
		return memory;
	}

	public int getNextCommand() {
		return nextCommand;
	}
	
	public int getAccumulator() {
		return accumulator;
	}
}

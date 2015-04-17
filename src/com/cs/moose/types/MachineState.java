package com.cs.moose.types;

public class MachineState {
	private final short[] memory;
	private final short nextCommand, accumulator;
	private final boolean hold;

	public MachineState(short accumulator, short[] memory, short nextCommand, boolean hold) {
		this.accumulator = accumulator;
		this.memory = memory;
		this.nextCommand = nextCommand;
		this.hold = hold;
	}
	
	public short[] getMemory() {
		return memory;
	}

	public short getNextCommand() {
		return nextCommand;
	}
	
	public short getAccumulator() {
		return accumulator;
	}
	
	public boolean getHold() {
		return hold;
	}
}

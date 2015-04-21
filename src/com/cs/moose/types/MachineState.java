package com.cs.moose.types;

public class MachineState {
	private final short[] memory;
	private final short nextCommand, accumulator;
	private final boolean flagZero, flagNegative, flagHold, flagV;

	public MachineState(short accumulator, short[] memory, short nextCommand, boolean flagZero, boolean flagNegative, boolean flagHold, boolean flagV) {
		this.accumulator = accumulator;
		this.memory = memory;
		this.nextCommand = nextCommand;
		this.flagZero = flagZero;
		this.flagNegative = flagNegative;
		this.flagHold = flagHold;
		this.flagV = flagV;
	}
	
	public short[] getMemory() {
		return this.memory;
	}

	public short getNextCommand() {
		return this.nextCommand;
	}
	
	public short getAccumulator() {
		return this.accumulator;
	}

	public boolean getFlagHold() {
		return this.flagHold;
	}
	public boolean getFlagZero() {
		return this.flagZero;
	}
	public boolean getFlagNegative() {
		return this.flagNegative;
	}
	public boolean getFlagV() {
		return this.flagV;
	}
}

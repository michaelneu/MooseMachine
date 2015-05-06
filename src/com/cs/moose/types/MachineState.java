package com.cs.moose.types;

public class MachineState {
	private final short[] memory;
	private final short accumulator;
	private final int nextCommand;
	private final boolean flagZero, flagNegative, flagHold, flagV;
	private final String stdout;

	public MachineState(short accumulator, short[] memory, int nextCommand, boolean flagZero, boolean flagNegative, boolean flagHold, boolean flagV, String stdout) {
		this.accumulator = accumulator;
		this.memory = memory;
		this.nextCommand = nextCommand;
		this.flagZero = flagZero;
		this.flagNegative = flagNegative;
		this.flagHold = flagHold;
		this.flagV = flagV;
		this.stdout = stdout;
	}
	
	public short[] getMemory() {
		return this.memory;
	}

	public int getNextCommand() {
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
	
	public String getStdout() {
		return this.stdout;
	}
}

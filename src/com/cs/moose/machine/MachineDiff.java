package com.cs.moose.machine;

import com.cs.moose.types.MachineState;

class MachineDiff extends MachineState {
	private short affectedMemoryCell, memoryCellValue;
	private boolean memoryAffected;
	
	public MachineDiff(short accumulator, int nextCommand, boolean flagZero, boolean flagNegative, boolean flagHold, boolean flagV, String stdout) {
		this(accumulator, nextCommand, flagZero, flagNegative, flagHold, flagV, stdout, (short)0, (short)0);
		
		this.memoryAffected = false;
	}
	public MachineDiff(short accumulator, int nextCommand, boolean flagZero, boolean flagNegative, boolean flagHold, boolean flagV, String stdout, short affectedMemoryCell, short memoryCellValue) {
		super(accumulator, null, nextCommand, flagZero, flagNegative, flagHold, flagV, stdout);
		
		this.memoryAffected = true;
		this.affectedMemoryCell = affectedMemoryCell;
		this.memoryCellValue = memoryCellValue;
	}
	
	public boolean isMemoryAffected() {
		return this.memoryAffected;
	}
	
	public short getAffectedMemoryCell() {
		return this.affectedMemoryCell;
	}
	
	public short getMemoryCellValue() {
		return this.memoryCellValue;
	}
}

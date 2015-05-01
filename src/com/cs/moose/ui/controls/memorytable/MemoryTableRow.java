package com.cs.moose.ui.controls.memorytable;

public class MemoryTableRow {
	private final short[] values;
	private final int row;
	
	public MemoryTableRow(short[] values, int row) {
		this.values = values;
		this.row = row;
	}

	public int getColumn0() {
		return row;
	}
	public short getColumn1() {
		return values[0];
	}
	public short getColumn2() {
		return values[1];
	}
	public short getColumn3() {
		return values[2];
	}
	public short getColumn4() {
		return values[3];
	}
	public short getColumn5() {
		return values[4];
	}
	public short getColumn6() {
		return values[5];
	}
	public short getColumn7() {
		return values[6];
	}
	public short getColumn8() {
		return values[7];
	}
	public short getColumn9() {
		return values[8];
	}
	public short getColumn10() {
		return values[9];
	}
	
	public static MemoryTableRow[] getRows(short[] memory) {
		int rest = memory.length % 10, 
			count = memory.length / 10;
		
		if (rest > 0) {
			count++;
		}
		
		MemoryTableRow[] rows = new MemoryTableRow[count];
		for (int i = 0; i < count; i++) {
			short[] values = new short[10];
			for (int j = 0; j < 10; j++) {
				int index = i * 10 + j;
				if (index < memory.length) {
					values[j] = memory[index];
				}
			}
			
			MemoryTableRow row = new MemoryTableRow(values, i * 10);
			rows[i] = row;
		}
		
		return rows;
	}
}

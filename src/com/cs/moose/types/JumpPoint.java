package com.cs.moose.types;

public class JumpPoint {
	private final String name;
	private final int codeLine;
	private int memoryPosition;
	
	public JumpPoint(String name, int codeLine) {
		this.name = name;
		this.codeLine = codeLine;
	}

	public String getName() {
		return this.name;
	}
	
	public int getCodeLine() {
		return this.codeLine;
	}
	
	public int getMemoryPosition() {
		return this.memoryPosition;
	}
	
	public void setMemoryPosition(int position) {
		this.memoryPosition = position;
	}
}

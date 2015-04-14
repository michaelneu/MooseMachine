package com.cs.moose.types;

public class JumpPoint {
	private final String name;
	private final int codeLine, memoryPosition;
	
	public JumpPoint(String name, int codeLine, int memoryPosition) {
		this.name = name;
		this.codeLine = codeLine;
		this.memoryPosition = memoryPosition;
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
}

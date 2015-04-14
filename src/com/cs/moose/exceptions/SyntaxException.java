package com.cs.moose.exceptions;

public class SyntaxException extends Exception {
	private static final long serialVersionUID = 2312061659114478813L;
	private final int line;
	
	public SyntaxException(String message, int line) {
		super(message);
		
		this.line = line;
	}
	
	public int getLine() {
		return this.line;
	}
}

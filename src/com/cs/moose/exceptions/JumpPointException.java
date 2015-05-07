package com.cs.moose.exceptions;

public class JumpPointException extends Exception{
	private static final long serialVersionUID = -3529710801909450514L;
	private final String point;

	public JumpPointException(String message, String point) {
		super(message);
		this.point = point;
	}
	
	public String getPoint() {
		return this.point;
	}
}

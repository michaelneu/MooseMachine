package com.cs.moose.exceptions;

import com.cs.moose.types.*;

public class MachineException extends Exception {
	private static final long serialVersionUID = 1546025454222700505L;
	private final Command command;
	
	public MachineException(Command command, String message) {
		super(message);
		this.command = command;
	}
	
	public Command getCommand() {
		return this.command;
	}
}

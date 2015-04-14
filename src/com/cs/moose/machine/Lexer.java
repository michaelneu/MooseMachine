package com.cs.moose.machine;

import com.cs.moose.exceptions.*;
import com.cs.moose.types.*;

import java.util.Hashtable;
import java.util.List;

public class Lexer {
	private static final String regexComments = "\\#(.*)",
			regexJump = "^([A-Za-z]+\\:\\s*|)",
			regexCommand = "([A-Za-z])+",
			regexParameter = "\\s(\\d+|[A-Za-z]+)",
			regexJumpCommandNumber = regexJump + regexCommand + regexParameter,
			regexJumpCommand = regexJump + regexCommand;
	
	private String originalCode;
	private String[] processedCode;
	
	public Lexer(String code) throws SyntaxException, NullPointerException {
		if (code == null) {
			throw new NullPointerException("Can not analyze code if there is no code to analyze");
		} else {
			this.originalCode = code;
			
			prepareCode();
			checkSyntax();
		}
	}
	
	private void prepareCode() {
		String temp;
		
		// strip comments
		temp = this.originalCode.replaceAll(regexComments, "");
		
		// strip multiple whitespaces
		temp = temp.replaceAll("\\h", " ");
		
		// strip whitespaces
		temp = temp.trim();
		
		this.processedCode = temp.split("\n");
	}
	
	private static boolean isCommand(String command) {
		// iterate through compiler command set and check if command in set
		for (Command originalCommand : Compiler.commandSet) {
			if (command.equals(originalCommand.name())) {
				return true;
			}
		}
		
		return false;
	}
	
	private void checkSyntax() throws SyntaxException {
		for (int i = 0; i < this.processedCode.length; i++) {
			String line = this.processedCode[i].trim();
			
			if (line.length() == 0 || line.matches(regexJump)) { // line is either empty or just a jump endpoint
				continue;
			} else if (line.matches(regexJumpCommandNumber) || line.matches(regexJumpCommand)) { // line contains a command
				if (line.matches(regexJump + ".*")) { // starts with a jump command
					line = line.substring(line.indexOf(":") + 1);
					line = line.trim();
				}
				
				String[] parts = line.split(" ");
				if (parts.length > 0) {
					String command = parts[0];
					
					if (!isCommand(command.toUpperCase())) {
						throw new SyntaxException("Unknown command \"" + command + "\"", i);
					}
				}
			} else {
				throw new SyntaxException("Invalid syntax", i);
			}
		}
	}
	
	public String[] getProcessedCode() {
		return this.processedCode;
	}
	
	public Hashtable<String, JumpPoint> getJumpEndPoints() {
		Hashtable<String, JumpPoint> points = new Hashtable<String, JumpPoint>();
		
		// magic happens
		
		return points;
	}
}

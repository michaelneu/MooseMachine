package com.cs.moose.machine;

import com.cs.moose.exceptions.*;
import com.cs.moose.types.*;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Lexer {
	private static final String regexComments = "\\#(.*)",
			regexJump = "^([A-Za-z0-9\\-]+\\:\\s*)",
			regexCommand = "([A-Za-z]+)",
			regexParameter = "\\s(\\d+|[A-Za-z0-9\\-]+)",
			regexJumpCommand = regexJump + "?" + regexCommand, // [ JMP: ]   COMMAND   [ #COMMENT ]
			regexJumpCommandParameter = regexJumpCommand + regexParameter, // [ JMP: ]   COMMAND   [ CHARACTERS | NUMBER ]   [ #COMMENT ]
			regexJumpCall = "(i?)j[a-z]+\\s+[0-9a-z]+"; // JMP POINT
	
	static final Pattern patternJumpCommand = Pattern.compile(regexJumpCommand),
			patternJumpCommandParameter = Pattern.compile(regexJumpCommandParameter);

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
			} else if (line.matches(regexJumpCommandParameter) || line.matches(regexJumpCommand)) { // line contains a command
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
	
	public Hashtable<String, JumpPoint> getJumpPointDefinitions() {
		Hashtable<String, JumpPoint> points = new Hashtable<String, JumpPoint>();
		ArrayList<JumpPoint> waitForMemoryPosition = new ArrayList<JumpPoint>();
		
		int memoryIndex = 0;
		for (int i = 0; i < this.processedCode.length; i++) {
			String line = this.processedCode[i].trim();
			
			if (line.matches(regexJump + "(.*)")) {
				String[] parts = line.split("\\:");
				String name = parts[0]; // len(array) MUST be > 0, because regex matched it
				
				JumpPoint point = new JumpPoint(name, i);
				points.put(name, point);
				
				if (line.matches(regexJumpCommandParameter) || line.matches(regexJumpCommand)) {
					// command on same line --> set memory position
					
					point.setMemoryPosition(memoryIndex);
					memoryIndex += 2;
				} else {
					// command yet to come --> wait for memory position
					
					waitForMemoryPosition.add(point);
				}
			} else if (line.length() != 0) {
				if (waitForMemoryPosition.size() > 0) {
					for (JumpPoint point : waitForMemoryPosition) {
						point.setMemoryPosition(memoryIndex);
					}
					waitForMemoryPosition.clear();
				}
				
				memoryIndex += 2;
			}
		}
		
		// remove jumps pointing to nirvana
		for (JumpPoint point : waitForMemoryPosition) {
			points.remove(point.getName());
		}
		
		return points;
	}
	
	public ArrayList<String> getJumpCalls() {
		ArrayList<String> jumps = new ArrayList<String>();
		
		for (int i = 0; i < this.processedCode.length; i++) {
			String line = this.processedCode[i].trim();
			
			if (line.matches(regexJumpCall)) {
				String[] parts = line.split("\\s");
				String name = parts[1]; // parts > 1 because regex matched
				
				if (!jumps.contains(name)) {
					jumps.add(name);
				}
			}
		}
		
		return jumps;
	}
}

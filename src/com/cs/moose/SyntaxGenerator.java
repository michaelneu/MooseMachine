package com.cs.moose;

import java.util.ArrayList;

import com.cs.moose.types.Command;

public class SyntaxGenerator {
	public static String generateWords() {
		Command[] commands = Command.values();
		
		ArrayList<String> keywords = new ArrayList<String>(),
				builtin = new ArrayList<String>();
		
		for (Command command : commands) {
			String name = command.name(),
					formatted = String.format("\"%s\": true", name);
			
			if (name.startsWith("J")) {
				keywords.add(formatted);
			} else {
				builtin.add(formatted);
			}
		}
		
		String output = "var keywords = {%s},\n" + 
						"	builtin = {%s};";
		String keys = String.join(", ", keywords),
				builtins = String.join(", ", builtin);
		
		return String.format(output, keys, builtins);
	}
}

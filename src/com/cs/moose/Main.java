package com.cs.moose;

import com.cs.moose.io.*;
import com.cs.moose.types.*;
import com.cs.moose.machine.*;
import com.cs.moose.machine.Compiler;
import com.cs.moose.exceptions.*;

public class Main {
	public static void main(String[] args) throws Exception {
		String content = File.readAllText("/home/michael/Desktop/moosemachine/test.moose");
		try {
			Lexer code = new Lexer(content);
		} catch (SyntaxException ex) {
			System.out.println("SyntaxException: " + ex.getMessage() + " in line " + ex.getLine());
		} catch (Exception ex) {
			throw ex;
		}
	}
}

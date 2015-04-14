package com.cs.moose;

import com.cs.moose.exceptions.*;
import com.cs.moose.io.*;
import com.cs.moose.machine.*;
import com.cs.moose.machine.Compiler;

public class Main {
	public static void main(String[] args) throws Exception {
		String code = File.readAllText("/home/michael/Desktop/moosemachine/test.moose");
		try {
			Lexer lexer = new Lexer(code);

			Machine machine = Compiler.getMachine(lexer);
			machine.goForward();
		} catch (SyntaxException ex) {
			System.out.println("SyntaxException: " + ex.getMessage() + " in line " + ex.getLine());
		} catch (JumpPointException ex) {
			System.out.println("JumpPointException: " + ex.getMessage());
		} catch (CompilerException ex) {
			System.out.println("CompilerException: " + ex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
	}
}

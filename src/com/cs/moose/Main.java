package com.cs.moose;

import com.cs.moose.exceptions.*;
import com.cs.moose.io.*;
import com.cs.moose.machine.*;
import com.cs.moose.machine.Compiler;
import com.cs.moose.types.MachineState;

public class Main {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		
		String code = File.readAllText("/home/michael/Desktop/moosemachine/test.moose");

		try {
			Lexer lexer = new Lexer(code);

			Machine machine = Compiler.getMachine(lexer);
			System.out.println(machine);
			
			while (machine.isRunning()) {
				machine.goForward();
			}
			
			System.out.println(machine);
			
			while (machine.goBackwards()) {
				// go to the beginning
			}
			System.out.println(machine);

		} catch (SyntaxException ex) {
			System.out.println("SyntaxException: " + ex.getMessage() + " in line " + ex.getLine());
		} catch (JumpPointException ex) {
			System.out.println("JumpPointException: " + ex.getMessage());
		} catch (CompilerException ex) {
			System.out.println("CompilerException: " + ex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("\nTime elapsed: " + (end - start) + "ms");
	}
}

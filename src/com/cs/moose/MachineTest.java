package com.cs.moose;


import com.cs.moose.exceptions.*;
import com.cs.moose.machine.*;
import com.cs.moose.machine.Compiler;

public class MachineTest {
	static void runSimpleProgram() {
		long start = System.currentTimeMillis();
		
		/*
			# count to the number the machine's accumulator will overflow and subtract 1 --> max number
			LOADI 1
			STORE 101
			
			jmp: 
				NOOP
				ADDI 1
				STORE 101
				CMPI 0
				JGT jmp
			
			LOAD 101
			SUBI 1
			STORE 101
			
			HOLD
		 */
		
		String code = "LOADI 1\n" + 
				"STORE 101\n" + 
				"\n" + 
				"jmp: \n" + 
				"	NOOP\n" + 
				"	ADDI 1\n" + 
				"	STORE 101\n" + 
				"	CMPI 0\n" + 
				"	JGT jmp\n" + 
				"\n" + 
				"LOAD 101\n" + 
				"SUBI 1\n" + 
				"STORE 101\n" + 
				"\n" + 
				"HOLD";

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
			// usually no other exceptions should be thrown
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("\nTime elapsed: " + (end - start) + "ms");
	}
}

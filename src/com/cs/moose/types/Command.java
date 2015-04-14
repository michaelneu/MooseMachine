package com.cs.moose.types;

public enum Command {
	LOAD	(276),	// ax = valueof(var)
	LOADI	(532),	// ax = var
	
	STORE	(277),	// var = ax
	
	ADD		(266),	// ax += valueof(var)
	SUB		(267),	// ax -= valueof(var)
	MUL		(268),	// ax *= valueof(var)
	DIV		(269),	// ax /= valueof(var)
	MOD		(270),	// ax %= valueof(var)
	CMP		(271),	// ax == valueof(var)
	
	ADDI	(522),	// ax += var
	SUBI	(523),	// ax -= var
	MULI	(524),	// ax *= var
	DIVI	(525),	// ax /= var
	MODI	(526),	// ax %= var
	CMPI	(527),	// ax == var

	AND		(296),	// ax & valueof(var)
	OR		(297),	// ax | valueof(var)
	XOR		(298),	// ax ^ valueof(var)
	NOT		(46),  	// ~ax
	
	ANDI	(552),	// ax & var
	ORI		(553),	// ax | var
	XORI	(554),	// ax ^ var

	SHL		(299),	// ax << valueof(var)
	SHR		(300),	// ax >> valueof(var)
	SHRA	(301),	// cyclic (ax >> valueof(var))
	
	SHLI	(555),	// ax << var
	SHRI	(556),	// ax >> var
	SHRAI	(557),	// cyclic (ax >> var)

	JMPP	(286),	// result of last operation  > 0
	JMPNN	(287),	// 						    >= 0
	JMPN	(288),	// 							 < 0
	JMPNP	(289),	// 						    <= 0
	JMPZ	(290),	// 						    == 0
	JMPNZ	(291),	// 						    != 0

	JMP		(292),	// jmp var
	JMPV	(293),	// jmp if z-flag not set

	JGT		(286),	// alias
	JGE		(287),	// 
	JLT		(288),	// 
	JLE		(289),	// 
	JEQ		(290),	// 
	JNE		(291),	// 
	JOV		(293),	// 

	RESET	(1),	// resets the processor
	NOOP	(0),	// nothing
	HOLD	(99),	// stops the processor
	FAIL	(Integer.MIN_VALUE);	// used for compilation purpose

	private static final Command[] commands = Command.values();
	
	private final int numericCommand;
	private Command(int number) {
		this.numericCommand = number;
	}
	
	public int numeric() {
		return this.numericCommand;
	}
	
	public static Command fromNumeric(int numeric) {
		for (Command command : commands) {
			if (command.numeric() == numeric) {
				return command;
			}
		}
		
		return null;
	}
}

package com.cs.moose.machine;

import com.cs.moose.exceptions.*;
import com.cs.moose.types.*;

import java.util.ArrayList;

public class Machine {
	private short accumulator, nextCommand;
	private short[] memory;
	private boolean flagZero, flagNegative, flagV, flagHold;
	
	private ArrayList<MachineState> previousStates;
	
	public Machine(short[] memory) {
		this.memory = memory;
		this.previousStates = new ArrayList<MachineState>();
	}
	
	public void goForward() throws MachineException {
		if (!flagHold) {
			// save current state
			MachineState currentState = toMachineState();
			this.previousStates.add(currentState);
			
			// get command
			short memoryCommand = this.memory[this.nextCommand], 
					memoryParameter = this.memory[this.nextCommand + 1];
			
			Command command = Command.fromNumeric(memoryCommand);
			
			// run command
			try {
				runCommand(command, memoryParameter);
			} catch (IndexOutOfBoundsException ex) { // handle outside of memory exceptions
				throw new MachineException(null, "Invalid memory address");
			}
		}
	}
	
	private void runCommand(Command command, short parameter) throws MachineException, IndexOutOfBoundsException {
		boolean sequential = true; // used for jumps
		int tempAccumulator = this.accumulator; // used for detecting short overflow
		
		// move value from memory address into memoryParameter if there is another command CMD and CMDI (to not duplicate code)
		switch (command) {
			case LOAD:
			case ADD:
			case SUB:
			case MUL:
			case DIV:
			case MOD:
			case CMP:
			case AND:
			case OR:
			case XOR:
			case SHL:
			case SHR:
			case SHRA:
				parameter = this.memory[parameter];
				
			default:
				break;
		}
		
		// now run the command
		switch (command) {
			// memory actions
			case LOAD:
			case LOADI:
				this.accumulator = parameter;
				break;
				
			case STORE:
				this.memory[parameter] = this.accumulator;
				break;
			
			// arithmetic actions
			case ADD:
			case ADDI:
				this.accumulator += parameter;
				tempAccumulator += parameter;
				break;
				
			case SUB:
			case SUBI:
				this.accumulator -= parameter;
				tempAccumulator -= parameter;
				break;
				
			case MUL:
			case MULI:
				this.accumulator *= parameter;
				tempAccumulator *= parameter;
				break;
				
			case DIV:
			case DIVI:
				if (parameter == 0) {
					throw new MachineException(command, "Division by zero");
				} else {
					this.accumulator /= parameter;
					tempAccumulator /= parameter;
				}
				break;
				
			case MOD:
			case MODI:
				if (parameter == 0) {
					throw new MachineException(command, "Division by zero");
				} else {
					this.accumulator %= parameter;
					tempAccumulator %= parameter;
				}
				break;
				
			case CMP:
			case CMPI:
				parameter = (short)(parameter - this.accumulator);
				
				if (parameter < 0) {
					this.flagNegative = true;
				} else {
					this.flagZero = true;
				}
				break;
				
			// logic actions
			case AND:
			case ANDI: 
				this.accumulator &= parameter;
				break;
				
			case OR:
			case ORI:
				this.accumulator |= parameter;
				break;
				
			case XOR:
			case XORI:
				this.accumulator ^= parameter;
				break;
				
			case NOT:
				this.accumulator = (short)~this.accumulator;
				break;

			case SHL:
			case SHLI:
				if (parameter < 1) {
					throw new MachineException(command, "Invalid shifting parameter");
				} else {
					this.accumulator <<= parameter;
				}
				break;
				
			case SHR:
			case SHRI:
				if (parameter < 1) {
					throw new MachineException(command, "Invalid shifting parameter");
				} else {
					short antiShift = 0;
					for (int j = 0; j < parameter; j++) {
						antiShift += (short)(1 << 15 - j);
					}
					
					this.accumulator >>= parameter;
					this.accumulator ^= antiShift;
				}
				break;
				
			case SHRA:
			case SHRAI:
				if (parameter < 1) {
					throw new MachineException(command, "Invalid shifting parameter");
				} else {
					this.accumulator >>= parameter;
				}
				break;
			
				
				
			// jump action
			case JMPP:
			case JGT:
				if (!flagZero && !flagNegative) {
					sequential = false;
					this.nextCommand = parameter;
				}
				break;
			
			case JGE:
			case JMPNN:
				if (!flagNegative) {
					sequential = false;
					this.nextCommand = parameter;
				}
				break;
				
			case JLT:
			case JMPN:
				if (flagNegative) {
					sequential = false;
					this.nextCommand = parameter;
				}
				break;
			
			case JLE:
			case JMPNP:
				if (flagNegative || flagZero) {
					sequential = false;
					this.nextCommand = parameter;
				}
				break;
			
			case JEQ:
			case JMPZ:
				if (flagZero) {
					sequential = false;
					this.nextCommand = parameter;
				}
				break;
				
			case JNE:
			case JMPNZ:
				if (!flagZero) {
					sequential = false;
					this.nextCommand = parameter;
				}
				break;
				
			case JOV:
			case JMPV:
				if (flagV) {
					sequential = false;
					this.nextCommand = parameter;
				}
				break;
				
			case JMP:
				sequential = false;
				this.nextCommand = parameter;
				break;
				
				
			// machine actions
			case HOLD:
				this.flagHold = true;
				break;
			case RESET:
				this.memory = new short[this.memory.length];
				break;
			case NOOP:
				break;
			
			default:
				throw new MachineException(command, "Invalid command passed");
		}
		
		
		if (sequential) {
			this.nextCommand += 2;
		}
		
		if (tempAccumulator > Short.MAX_VALUE || tempAccumulator < Short.MIN_VALUE) {
			this.flagV = true;
		} else {
			this.flagV = false;
		}
	}
	
	public void goBackwards() {
		int length = this.previousStates.size();
		
		if (length > 0) {
			MachineState lastState = this.previousStates.get(length - 1);
			this.previousStates.remove(length - 1);
			
			fromMachineState(lastState);
		}
	}
	
	private void fromMachineState(MachineState state) {
		this.memory = state.getMemory();
		this.nextCommand = state.getNextCommand();
		this.accumulator = state.getAccumulator();
		this.flagZero = state.getFlagZero();
		this.flagNegative = state.getFlagNegative();
		this.flagHold = state.getFlagHold();
		this.flagV = state.getFlagV();
		
	}
	
	public MachineState toMachineState() {
		return new MachineState(this.accumulator, this.memory.clone(), this.nextCommand, this.flagZero, this.flagNegative, this.flagHold, this.flagV);
	}
	
	@Override
	public String toString() {
		final int elementsPerRow = 10;
		String out = "PC: " + this.nextCommand + "\t\t\tAKKU: " + this.accumulator + "\n\nZero-Flag: " + this.flagZero + "\tNegative-Flag: " + this.flagNegative + "\n" + "Overflow-Flag: " + this.flagV + "\tHold-Flag: " + this.flagHold + "\n\n\t";
		
		for (int i = 0; i < elementsPerRow; i++) {
			out += i + "\t";
		}
		
		
		for (int i = 0; i < 200; i++) {
			if (i % elementsPerRow == 0) {
				out += "\n" + i + "\t";
			}
			
			out += this.memory[i] + "\t";
		}
		
		return out;
	}
}

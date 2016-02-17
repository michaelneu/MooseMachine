package com.cs.moose;

import java.io.IOException;
import com.cs.moose.exceptions.*;
import com.cs.moose.io.File;
import com.cs.moose.machine.Lexer;
import com.cs.moose.machine.Compiler;
import com.cs.moose.machine.Machine;

public class MachineRunner {
    public static void run(String file) throws MachineRunnerException {
        try {
            String code = File.readAllText(file);
            Lexer lexer = new Lexer(code);
            Machine machine = Compiler.getMachine(lexer);

            while (!machine.toMachineState().getFlagHold()) {
                machine.goForward();
            }

            System.out.println(machine.toMachineState().getStdout());
        } catch (Exception ex) {
        	throw new MachineRunnerException("Error running '" + file + "'", ex);
        }
    }
}
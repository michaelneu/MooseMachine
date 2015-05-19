# Befehle
Im Folgenden wird der Akkumulator als `akku`, der Parameter der Methode als `parameter` und ein Zugriff auf den Speicher als `speicher[parameter]` bezeichnet. 
## Speicheroperationen
Befehl  | OpCode | Funktionsweise
--------|--------|--------------------------------
`LOAD`  |  276   | `akku = speicher[parameter]`
`LOADI` |  532   | `akku = parameter`
`STORE` |  277   | `speicher[parameter] = akku`


## Arithmetische Operationen
Das `VFLAG` (Overflow-Flag) wird bei jeder arithmetischen Operation dem Ergebnis entsprechend gesetzt. 

Befehl  | OpCode | Funktionsweise
--------|--------|------------------------------------
`ADD`   |  266   | `akku = akku + speicher[parameter]`
`SUB`   |  267   | `akku = akku - speicher[parameter]`
`MUL`   |  268   | `akku = akku * speicher[parameter]`
`DIV`   |  269   | `akku = akku / speicher[parameter]`
`MOD`   |  270   | `akku = akku % speicher[parameter]`
`CMP`   |  271   | `ZFLAG = akku == speicher[parameter]`
|       |        | `NFLAG = akku < speicher[parameter]`
`ADDI`  |  522   | `akku = akku + parameter`
`SUBI`  |  523   | `akku = akku - parameter`
`MULI`  |  524   | `akku = akku * parameter`
`DIVI`  |  525   | `akku = akku / parameter`
`MODI`  |  526   | `akku = akku % parameter`
`CMPI`  |  527   | `ZFLAG = akku == parameter`
|       |        | `NFLAG = akku < parameter`


## Logische Operationen

Befehl  | OpCode | Funktionsweise
--------|--------|------------------------------------
`AND`   |  296   | `akku = akku & speicher[parameter]`
`OR`    |  297   | `akku = akku | speicher[parameter]`
`XOR`   |  298   | `akku = akku ^ speicher[parameter]`
`NOT`   |  296   | `akku = ~akku`
`ANDI`  |  552   | `akku = akku & parameter`
`ORI`   |  553   | `akku = akku | parameter`
`XORI`  |  554   | `akku = akku ^ parameter`
`SHL`   |  299   | `akku = akku * pow(2, speicher[parameter])`
`SHR`   |  300   | `akku = abs(akku / pow(2, speicher[parameter]))`
`SHRA`  |  301   | `akku = akku / pow(2, speicher[parameter])`
`SHLI`  |  555   | `akku = akku * pow(2, parameter)`
`SHRI`  |  556   | `akku = abs(akku / pow(2, parameter))`
`SHRAI` |  557   | `akku = akku / pow(2, parameter)`


## Sprungbefehle
`pc` ist der Program-Counter, also die Adresse des nächsten auszuführenden Befehls. 

Befehl         | OpCode | Funktionsweise
---------------|--------|------------------------------------
`JMPP`, `JGT`  |  286   | `if (!NFLAG && !ZFLAG) pc = parameter`
`JMPNN`, `JGE` |  287   | `if (!NFLAG) pc = parameter`
`JMPN`, `JLT`  |  288   | `if (!ZFLAG && NFLAG) pc = parameter`
`JMPNP`, `JLE` |  289   | `if (ZFLAG || NFLAG) pc = parameter`
`JMPZ`, `JEQ`  |  290   | `if (ZFLAG) pc = parameter`
`JMPNZ`, `JNE` |  291   | `if (!ZFLAG) pc = parameter`
`JMP`          |  292   | `pc = parameter`
`JMPV`, `JOV`  |  293   | `if (VFLAG) pc = parameter`

## Maschinenbefehle

Befehl  | OpCode | Funktionsweise
--------|--------|------------------------------------
`RESET` |   1    | Maschine wird zurückgesetzt
`NOOP`  |   0    | Keine Aktion
`HOLD`  |   99   | Maschine wird angehalten


## Konsolenausgabe
Die Konsole (`stdout`) ist kein eigentlicher Teil der Maschine, dient aber der Visualisierung von Programmen. Strings, die `PUTS` übergeben werden, werden in einem separaten Speicher verwaltet, auf die der Programmierer keinen Zugriff hat. 

Der Befehl `PUTS` akzeptiert sowohl die Syntax `PUTS "string"` als auch `PUTS 'string'`. 

Befehl  | OpCode | Funktionsweise
--------|--------|------------------------------------
`PUT`   |  700   | `stdout.write(speicher[parameter])`
`PUTS`  |  701   | `stdout.write(string)`
`PUTA`  |  702   | `stdout.write(akku)`
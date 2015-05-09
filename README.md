#MooseMachine
[![Build Status](https://travis-ci.org/minedev/MooseMachine.svg?branch=master)](https://travis-ci.org/minedev/MooseMachine)

##Motivation
Der Informatikunterricht der gymnasialen Oberstufe beinhaltet einige Unterrichtseinheiten über die von-Neumann-Architektur und die Programmierung einer dieser Architektur ähnlichen Maschine mit der Java-Applikation [Minmaschine](http://schule.awiedemann.de/minimaschine.html). Diese Software visualisiert mit Hilfe einer vereinfachten Assembler-Sprache die Vorgänge innerhalb einer CPU, von der Befehlsholphase aus dem RAM bis hin zur Berechnung in der ALU. Trotz einiger Updates kann diese Applikation durch bestimmte Code-Konstellationen zum Absturz gebracht werden. Um dieses Problem zu beheben wurde die MooseMachine entwickelt. 

##Funktionsumfang
- Die MooseMachine basiert auf JavaFX 8, was auf Code-Basis eine höhere Flexibilität durch FXML und CSS im Design ermöglicht
- Das Problem eines "zufälligen" Absturzes bzw. einer blockierten Oberfläche wurde durch das Ausführen des Maschinencodes in einem separaten Thread umgangen
- Die MooseMachine ermöglicht das Debuggen des Codes in Einzelschritten, sowohl vorwärts als auch rückwärts. Die aktuelle Code-Position im Speicher wird im Code neben dem Speicher visualisiert
- Der Editor basiert auf dem CodeMirror Browser-Editor. Durch diesen bietet die MooseMachine wichtiges Syntax-Highlighting
- Die Applikation ist vollständig lokalisierbar, standardmäßig wird eine deutsche und englische Lokalisierung mitgeliefert. Sollte die aktuelle Systemsprache nicht mitgeliefert werden, so wird die englische Lokalisierung verwendet
- Die Syntax der Minimaschine wurde um das `DWORD`-Keyword gekürzt, dafür um die drei Befehle `PUT`, `PUTS`, `PUTA` erweitert. Diese Befehle ermöglichen die Ausgabe von Speicherzellen, Strings und dem Akkumulator zur Visualisierung von Daten
- Die MooseMachine wurde auf Geschwindigkeit optimiert. Ein Programm, das bis zu 2^15 - 1 zählt (der größten erreichbaren Zahl bei 16 Bit Integern) benötigt in der Minimaschine ~1.5 Sekunden. Die MooseMachine benötigt hierfür nur wenige Millisekunden, ohne Anzeige in der GUI benötigt das Programm für seine Abarbeitung und dem "Zurückspulen" auf den Anfangszustand nur ~70ms, ca. 200x so schnell wie die Minimaschine. 


##Schreiben von Programmen in der MooseMachine
Die Syntax der MooseMachine entspricht der [Syntax der Minimaschine](http://schule.awiedemann.de/manualmini/index.html), sie wurde lediglich um das `DWORD`-Keyword gekürzt. Beispielprogramme befinden sich im Unterordner `examples`. Ein solches Programm kann bspw. so aussehen: 

```
LOADI 	100 		// Zahl 100 in den Akkumulator laden
STORE 	100			// in Zelle 100 abspeichern

PUTS	"Zahl 1: "	// Ausgabe des Strings
PUT		100			// Zelle 100 ausgeben
PUTS	"\n"		// Zeilenumbruch ausgeben
LOADI 	200			// Zahl 200 in den Akkumulator laden
PUTS	"Zahl 2: "	
PUTA				// Akkumulator ausgeben
PUTS	"\n"

ADD		100			// Zelle 100 zu Akkumulator addieren
PUTS	"Summe:  "		
PUTA

HOLD				// Maschine anhalten
```
Die dazugehörige Konsolenausgabe sieht in diesem Fall so aus: 
```
Zahl 1: 100
Zahl 2: 200
Summe:  300
```

##Verwendung in Java
Die MooseMachine kann auch aus Java verwendet werden. Hierfür muss die .jar in den Klassenpfad eingebunden werden und das Package `com.cs.moose.machine` importiert werden, beispielsweise so: 

```java
import java.io.IOException;
import com.cs.moose.exceptions.*;
import com.cs.moose.io.File;
import com.cs.moose.machine.Lexer;
import com.cs.moose.machine.Compiler;
import com.cs.moose.machine.Machine;

public class Main {
    public static void main(String[] args) {
        // sanity check
        if (args.length != 2) {
            System.out.println("Bitte Dateinamen als 2. Parameter angeben");
            return;
        }

        try {
	        String code = File.readAllText(args[1]);        // Datei aus Aufrufparameter
	        Lexer lexer = new Lexer(code);                  // Code testen
	        Machine machine = Compiler.getMachine(lexer);   // Code kompilieren
	
	        for (int i = 0; i < 1000; i++) {
	            machine.goForward(); // Code ausführen
	        }
	        
	        System.out.println(machine.toString(20));                                    // die ersten 200 Speicherzellen ausgeben
	        System.out.println("\n\nSTDOUT: \n" + machine.toMachineState().getStdout()); // den Standard-Output ausgeben
        } catch (IOException ex) {
            System.out.println("Kann Datei nicht lesen");
        } catch (SyntaxException ex) {
            System.out.println("Inkorrekte Syntax in Zeile " + ex.getLine());
        } catch (CompilerException ex) {
            System.out.println("Compiler-Fehler: " + ex.getMessage());
        } catch (JumpPointException ex) {
            System.out.println("Punkt \"" + ex.getPoint() + "\" nicht definiert");
        } catch (MachineException ex) {
            System.out.println("Fehler beim Ausführen des Maschinencodes: " + ex.getMessage());
        }
    }
}
```




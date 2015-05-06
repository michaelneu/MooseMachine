CodeMirror.defineMode("moose", function (config, parserConfig) {
	var builtin = {"LOAD": true, "LOADI": true, "STORE": true, "ADD": true, "SUB": true, "MUL": true, "DIV": true, "MOD": true, "CMP": true, "ADDI": true, "SUBI": true, "MULI": true, "DIVI": true, "MODI": true, "CMPI": true, "AND": true, "OR": true, "XOR": true, "NOT": true, "ANDI": true, "ORI": true, "XORI": true, "SHL": true, "SHR": true, "SHRA": true, "SHLI": true, "SHRI": true, "SHRAI": true, "RESET": true, "NOOP": true, "HOLD": true, "FAIL": true},
		keywords = {"JMPP": true, "JMPNN": true, "JMPN": true, "JMPNP": true, "JMPZ": true, "JMPNZ": true, "JMP": true, "JMPV": true, "JGT": true, "JGE": true, "JLT": true, "JLE": true, "JEQ": true, "JNE": true, "JOV": true, "PUT": true, "PUTS": true, "PUTA": true};
	return {
		startStat: function () {
			return {
				inString: false
			};
		},
		token: function (stream, state) {
			var next = stream.next();
			if (/\d/.test(next)) {
				return "number";
			} else if (next == "#" || (next == "/" && stream.eat("/"))) {
				stream.skipToEnd();
				return "comment";
			}
			
			if (stream.skipTo(":")) {
				return "variable";
			}
			stream.eatWhile(/\w/);
			var current = stream.current();
			current = current.toUpperCase().trim();
			if (keywords.propertyIsEnumerable(current)) {
				return "keyword";
			} else if (builtin.propertyIsEnumerable(current)) {
				return "builtin";
			}
		}
	};
});
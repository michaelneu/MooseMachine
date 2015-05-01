package com.cs.moose.locale;

public class LocaleDE extends ILocale {
	@Override
	public String getCompileFileNotSavedWarning() {
		return "Datei wurde noch nicht gespeichert. \nIm Falle eines (zwar unmöglich verursachbaren, aber denkbaren) Absturzes wäre das nicht gut. \nTrotzdem weitermachen?";
	}

	@Override
	public String getCodeNotSaved() {
		return "Code nicht gespeichert";
	}

	@Override
	public String getSyntaxErrorInLine(int i) {
		return "Syntaxfehler in Zeile " + i;
	}

	@Override
	public String getCompilerError() {
		return "Compiler-Fehler";
	}

	@Override
	public String getCompilerJumpPointError() {
		return "Fehler beim Kompilieren. Unbekannter Sprungpunkt!";
	}

	@Override
	public String getCompilerUnknownError() {
		return "Unbekannter Fehler beim Kompilieren";
	}

	@Override
	public String getOpenFileNotSavedWarning() {
		return "Datei noch nicht gespeichert. \nDurch das Öffnen der neuen Datei gehen die Änderungen verloren. \nTrotzdem weitermachen?";
	}

	@Override
	public String getFiles() {
		return "Dateien";
	}
}

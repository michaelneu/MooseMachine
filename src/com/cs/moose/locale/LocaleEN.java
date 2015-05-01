package com.cs.moose.locale;

public class LocaleEN extends ILocale {
	@Override
	public String getCompileFileNotSavedWarning() {
		return "You didn't save your code yet. \nThe application might crash, are you sure you want to live dangerously?";
	}

	@Override
	public String getCodeNotSaved() {
		return "Code not saved";
	}

	@Override
	public String getSyntaxErrorInLine(int i) {
		return "Syntax error on line " + i;
	}

	@Override
	public String getCompilerError() {
		return "Compiler error";
	}

	@Override
	public String getCompilerJumpPointError() {
		return "Compiler error. Invalid jump point";
	}

	@Override
	public String getCompilerUnknownError() {
		return "Unknown compile error";
	}
	
	@Override
	public String getOpenFileNotSavedWarning() {
		return "File not saved. Opening a new file will destroy your changes to the current file. \nProceed?";
	}

	@Override
	public String getFiles() {
		return "files";
	}
}

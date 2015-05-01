package com.cs.moose.locale;


public abstract class ILocale {
	public abstract String getFiles();
	public abstract String getCompileFileNotSavedWarning();
	public abstract String getCodeNotSaved();
	public abstract String getSyntaxErrorInLine(int i);
	public abstract String getCompilerError();
	public abstract String getCompilerUnknownError();
	public abstract String getCompilerJumpPointError();
	public abstract String getOpenFileNotSavedWarning();
	
	public static ILocale getLocale() {
		String localeString = System.getProperty("user.language");
		
		switch (localeString) {
			case "de": 
			case "at":
				return new LocaleDE();
				
			default: 
				return new LocaleEN();
		}
	}
}

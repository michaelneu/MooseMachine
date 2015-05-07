package com.cs.moose.locale;


public abstract class Locale {
	public static String MENU_OPEN, MENU_SAVE, MENU_SAVE_AS, MENU_NEW, MENU_EXIT,
						  DIALOG_FILE,
						  
						  EXCEPTION_MACHINE_MESSAGE, EXCEPTION_MACHINE_TITLE,
						  EXCEPTION_COMPILER_MESSAGE, EXCEPTION_COMPILER_TITLE,
						  EXCEPTION_JUMPPOINT_MESSAGE,
						  EXCEPTION_SYNTAX_MESSAGE,
						  EXCEPTION_IO_MESSAGE, EXCEPTION_IO_TITLE,
						  
						  EDITOR_FILE_NOT_SAVED_MESSAGE, EDITOR_FILE_NOT_SAVED_TITLE;
	
	private Locale() {
		
	}
	
	public static void generateLocale() {
		String locale = System.getProperty("user.language");
		LocaleReader reader = new LocaleReader(locale);

		for (String key : reader.getKeys()) {
			String value = reader.getLocalized(key);
			
			switch (key) {
				// main menu
				case "menu-new": 
					Locale.MENU_NEW = value;
					break;
				case "menu-open": 
					Locale.MENU_OPEN = value;
					break;
				case "menu-save": 
					Locale.MENU_SAVE = value;
					break;
				case "menu-save-as": 
					Locale.MENU_SAVE_AS = value;
					break;
				case "menu-exit": 
					Locale.MENU_EXIT = value;
					break;
					
				// dialog
				case "dialog-file": 
					Locale.DIALOG_FILE = value;
					break;
					
				// exceptions
				case "exception-machine-message": 
					Locale.EXCEPTION_MACHINE_MESSAGE = value;
					break;
				case "exception-machine-title": 
					Locale.EXCEPTION_MACHINE_TITLE = value;
					break;
				case "exception-compiler-message":
					Locale.EXCEPTION_COMPILER_MESSAGE = value;
					break;
				case "exception-compiler-title": 
					Locale.EXCEPTION_COMPILER_TITLE = value;
					break;
				case "exception-jumppoint-message": 
					Locale.EXCEPTION_JUMPPOINT_MESSAGE = value;
					break;
				case "exception-syntax-message": 
					Locale.EXCEPTION_SYNTAX_MESSAGE = value;
					break;
				case "exception-io-message": 
					Locale.EXCEPTION_IO_MESSAGE = value;
					break;
				case "exception-io-title": 
					Locale.EXCEPTION_IO_TITLE = value;
					break;
					
				// editor
				case "editor-file-not-saved-message":
					Locale.EDITOR_FILE_NOT_SAVED_MESSAGE = value;
					break;
				case "editor-file-not-saved-title":
					Locale.EDITOR_FILE_NOT_SAVED_TITLE = value;
					break;
					
				default: 
					break;
			}
		}
	}
}

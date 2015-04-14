package com.cs.moose.io;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class File {
	public static String readAllText(String filename) throws IOException {
		FileInputStream input = new FileInputStream(filename);
		byte[] data = new byte[input.available()];
		
		input.read(data);
		input.close();
		
		return new String(data, "UTF-8");
	}
	
	public static void writeAllText(String filename, String text) throws IOException {
		FileOutputStream output = new FileOutputStream(filename);
		output.write(text.getBytes());
		output.close();
	}
}

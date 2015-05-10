package com.cs.moose.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class File {
	static String readAll(InputStream input) throws IOException, UnsupportedEncodingException  {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF8"));
		
		String contents = "", temp;
		
		while ((temp = reader.readLine()) != null) {
			contents += temp + "\n";
		}
		
		reader.close();
		
		return contents;
	}
	
	public static String readAllText(String filename) throws IOException {
		FileInputStream input = new FileInputStream(filename);
		
		return readAll(input);
	}
	
	public static void writeAllText(String filename, String text) throws IOException {
		FileOutputStream output = new FileOutputStream(filename);
		output.write(text.getBytes());
		output.close();
	}
}

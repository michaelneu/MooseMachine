package com.cs.moose.io;

import java.io.InputStream;
import java.util.Scanner;

@SuppressWarnings("resource")
public class Resource {
	public static String getContent(Class<?> caller, String path) {
		InputStream stream = caller.getResourceAsStream(path);
		
		if (stream == null) {
			return null;
		} else {
			Scanner reader = new Scanner(stream).useDelimiter("\\A");
			
			if (reader.hasNext()) {
				return reader.next();
			} else {
				return null;
			}
		}
	}
}

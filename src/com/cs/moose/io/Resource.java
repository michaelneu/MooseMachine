package com.cs.moose.io;

import java.io.IOException;
import java.io.InputStream;

public class Resource {
	public static String getContent(Class<?> caller, String path) {
		InputStream stream = caller.getResourceAsStream(path);
		
		try {
			return File.readAll(stream);
		} catch (IOException e) {
			return null;
		}
	}
}

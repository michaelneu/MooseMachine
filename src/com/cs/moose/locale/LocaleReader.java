package com.cs.moose.locale;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cs.moose.io.Resource;

class LocaleReader {
	private final String regexLine = "([A-Za-z0-9\\-]+)\\s\\=\\s(.*)";
	private final Pattern patternLine = Pattern.compile(regexLine);
	private HashMap<String, String> localizationTable;
	
	public LocaleReader(String language) {
		this.localizationTable = new HashMap<String, String>();
		
		// get the langueage definition
		String languageDefinition = Resource.getContent(LocaleReader.class, "locales/" + language + ".lang");
		
		if (languageDefinition == null) { // language not supported
			languageDefinition = Resource.getContent(LocaleReader.class, "locales/en.lang");
		}
		
		loadLocalizations(languageDefinition);
	}
	
	private void loadLocalizations(String languageDefinition) {
		// strip leading/trailing spaces on each line
		languageDefinition = languageDefinition.replaceAll("^\\h+", "");
		// replace whitespaces between words
		languageDefinition = languageDefinition.replaceAll("\\h+", " ");
		// strip comments
		languageDefinition = languageDefinition.replaceAll("\\#.*", "");
		// replace multiple new lines with single ones
		languageDefinition = languageDefinition.replaceAll("\n+", "\n").trim();
		
		String[] lines = languageDefinition.split("\n");
		
		for (String line : lines) {
			if (line.matches(regexLine)) {
				Matcher matcher = patternLine.matcher(line);
				
				matcher.find();
				String key = matcher.group(1),
						value = matcher.group(2);

				value = value.replaceAll("\\\\n", "\n").replaceAll("\\\\\n", "\\\\n");
				value = value.replaceAll("\\\\t", "\t").replaceAll("\\\\\t", "\\\\t");
				
				this.localizationTable.put(key, value);
			}
		}
	}
	
	public String getLocalized(String key) {
		if (this.localizationTable.containsKey(key)) {
			return this.localizationTable.get(key);
		} else {
			return "";
		}
	}
	
	public Set<String> getKeys() {
		return this.localizationTable.keySet();
	}
}

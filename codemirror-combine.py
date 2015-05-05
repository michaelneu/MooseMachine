#!/usr/bin/env python2

"""
This script allows combining the codemirror sources into a single file, so the 
editor can be run without an internet connection. Useful for schools running
restrictive software. Requires recompiling the .jar as the index.html is part
of it. 
The MooseMachine will fill variables like ${readonly} on its own, modifying or
deleting them might end in the editor not functioning as intended. 
"""

import requests


files = [{
		"name": "base",
		"files": {
			"js": ["http://codemirror.net/lib/codemirror.js"],
			"css": ["http://codemirror.net/lib/codemirror.css"],
		},
	}, {
		"name": "fullscreen",
		"files": {
			"js": ["http://codemirror.net/addon/display/fullscreen.js"],
			"css": ["http://codemirror.net/addon/display/fullscreen.css"]
		},
	}, {
		"name": "active-line",
		"files": {
			"js": ["http://codemirror.net/addon/selection/active-line.js"],
			"css": []
		}
	}, {
		"name": "moose",
		"files": {
			"js": ["moose-mod.js"],
			"css": ["moose-style.css"]
		}
	}
]


skeleton = """<!doctype html>
<html>
	<head>
		%s
	</head>
	<body>
		<textarea id="code" name="code">${code}</textarea>

		<script type="text/javascript">
			var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
				${highlight-line}
				${readonly}
				${autofocus}
				lineNumbers: true,
				indentWithTabs: true,
				mode: "moose"
			});
			
			editor.setOption("fullScreen", true);
			
			${initial-line}
		</script>
	</body>
</html>
"""

def download(url): 
	url = str(url)

	if url.startswith("http"): 
		response = requests.get(url)
		return response.content
	else: 
		with open(url, "r") as f: 
			return f.read()

includes = ""

for package in files: 
	print "Adding '%s'..."%package["name"]

	package_files = package["files"]
	js = package_files["js"]
	css = package_files["css"]

	for js_file in js: 
		print "\t", "downloading '%s'..."%js_file
		content = download(js_file)

		includes += "<script type=\"text/javascript\">%s</script>\n"%content

	for css_file in css: 
		print "\t", "downloading '%s'..."%css_file
		content = download(css_file)

		includes += "<style type=\"text/css\">%s</style>\n"%content


html = skeleton%includes

with open("index.html", "w") as f: 
	f.write(html);

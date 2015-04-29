package com.cs.moose.ui.controls;

import javax.swing.JOptionPane;

public class Dialog {
	public static void showError(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
	}
}

package ui;

import life.library.Library;

public class GaUI {

	public static void main(String[] args) {
		String patternName = args.length > 0 ? args[0] : "Loaf";
		final String pattern = Library.get(patternName);
		MainFrame f = new MainFrame(pattern, patternName);
		f.setResizable(false);
	}
}

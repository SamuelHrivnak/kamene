package nPuzzle;

import nPuzzle.consoleui.ConsoleUI;
import nPuzzle.core.Field;

// hlavna trieda projektu ktora je zaroven singletonom
public class nPuzzle {
	private static nPuzzle instance = null;
	private ConsoleUI consoleUI;
	
	/**
	 * @param consoleUI
	 */
	private nPuzzle() {
		instance = this;
		consoleUI = new ConsoleUI();
		Field field = new Field(4, 4);
		consoleUI.newGameStarted(field);
		consoleUI.update();
	}

	public static nPuzzle getInstance() {
		if (instance == null) {
			return instance = new nPuzzle();
		}
		return instance;
	}

	public static void main(String[] args) {
		new nPuzzle();	
	}
}

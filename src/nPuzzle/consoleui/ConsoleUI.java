package nPuzzle.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nPuzzle.BestTimes;
import nPuzzle.core.Field;
import nPuzzle.core.GameState;
import nPuzzle.core.Puzzle;

public class ConsoleUI {
	private Field field;
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	private BestTimes bestTimes = new BestTimes();
	
	// metoda ktora cita vstup klavesnice
	private String readLine() {
		try {
			String stringInput = input.readLine();
			stringInput.trim();
			stringInput.toUpperCase();
			return stringInput;
		} catch (IOException e) {
			return null;
		}
	}
	// metoda startu hry ktora inicializuje start novej hry a nacita hracie pole, zaroven ukoncuje hru ak sa splnia podmienky
	public void newGameStarted(Field field) {		
		this.field = Field.load();
		update();
		do {
			processInput();
			update();	
			if (field.getStateOfGame() == GameState.SOLVED) {
				int timeOfPlayer = (int)(System.currentTimeMillis() - field.getStartTime())/1000;
				bestTimes.addPlayerTime(System.getProperty("user.name"), timeOfPlayer);
				bestTimes.printListOfTimes();
				System.out.println("Game is Solved !");
				System.exit(0);
			}
		} while (true);
	}
	
	// metoda obnovujuca hracie pole a informacie na konzole
	public void update() {
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				Puzzle puzzle = field.getPuzzle(row, column);

				if (puzzle.getValue() == 0)
					System.out.print("X" + "  ");
				else if (puzzle.getValue() > 0) {
					if (puzzle.getValue() < 10) {
						System.out.print(puzzle.getValue() + "  ");
					} else {
						System.out.print(puzzle.getValue() + " ");
					}
				}
			}
			System.out.println();
		}
		// vypis casu hry a mena hraca
		long currentTime = System.currentTimeMillis();
		System.out.println("------------------------");
		System.out.println("Game time: "+(currentTime- field.getStartTime())/1000 + " s");		
		System.out.println("Name of player: " + System.getProperty("user.name")+"");
		System.out.println("------------------------");		
	}
	
	// rozhodovaci strom, kde sa urcuje ake metody sa budu volat po stlaceni klaves
	private void processInput() {
		System.out.println("\"new\"       -- for new game, \n" + "\"exit\" -- for quit/save game \n\"top\" -- for list of best ||"
				+ " \"w,s\" -- for push puzzle up and down || \"a,d\" -- for push puzzle left and right ");
		String input = readLine();
		try {
			handleInput(input);
		} catch (WrongFormatException e) {
			System.err.println(e.getMessage());
		}

		if (input.equals("new")) {
			newGameStarted(field);
		} else if (input.equals("exit")) {
			field.save();
			
			System.err.println("Quiting game");
			System.exit(0);
		}else if(input.equals("top")) {
			bestTimes.printListOfTimes();
		} 
		else if (input.equals("w")) {
			try {
				field.pushUp();
			} catch (MoveNotPosibleException e) {
				System.err.println(e.getMessage());
			}
			return;
		} else if (input.equals("s")) {
			try {
				field.pushDown();
			} catch (MoveNotPosibleException e) {
				System.err.println(e.getMessage());
			}
			return;
		} else if (input.equals("a")) {
			try {
				field.pushLeft();
			} catch (MoveNotPosibleException e) {
				System.err.println(e.getMessage());
			}
			return;
		} else if (input.equals("d")) {
			try {
				field.pushRight();
			} catch (MoveNotPosibleException e) {
				System.err.println(e.getMessage());
			}
			return;
		}

	}
	
	//osetrenie zlych vstupov
	private void handleInput(String input) throws WrongFormatException {
		if (input.length() == 1) {
			if (input.charAt(0) != 'a'&& input.charAt(0) != 'w' && input.charAt(0) != 'd' && input.charAt(0) != 's') {
				throw new WrongFormatException("wrong key to move with puzzles");
			}
		} else {
			if (!input.equals("new") && !input.equals("exit") && !input.equals("top")) {
				throw new WrongFormatException("wrong keys for new, or exit function");
			}
		}

	}
}

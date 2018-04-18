package nPuzzle.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import nPuzzle.consoleui.MoveNotPosibleException;
// trieda hracieho pola 
public class Field implements Serializable {
	private final Puzzle[][] puzzles;
	private final int rowCount;
	private final int columnCount;
	private int indexRowOfEmpty;
	private int indexColumnOfEmpty;
	private GameState stateOfGame = GameState.PLAYING; 
	private long startTime;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		puzzles = new Puzzle[rowCount][columnCount];
		generate();
		startTime = System.currentTimeMillis();
	}

	private void generate() {
		int numberOfPuzzle = rowCount * columnCount - 1;
		Random random = new Random();
		puzzles[0][0] = new Puzzle(0);
		indexRowOfEmpty = 0;
		indexColumnOfEmpty = 0;

		while (numberOfPuzzle > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (puzzles[row][column] == null) {
				puzzles[row][column] = new Puzzle(numberOfPuzzle);
				numberOfPuzzle--;
			}
		}

	}
	// metoda ktora urcuje ci hodnoty puzzle tvoria rastucu postupnost, 
	//  kde kazdy nasledujuci prvok musi byt prave o jedna vacsi ako predchadzajuci
	private boolean isSolved() {
		for (int row = 1; row < rowCount; row++) {
			for (int column = 1; column < columnCount; column++) {
				if (puzzles[rowCount - 1][columnCount - 1].getValue() == 0) {
					Puzzle prevPuzzle = puzzles[row - 1][column - 1];
					Puzzle puzzle = puzzles[row][column];
					if (prevPuzzle.getValue() + 1 != puzzle.getValue()) {
						return false;
					}
				} else
					return false;
			}
		}
		return true;

	}
	// blok metod ktore ovladaju posun puzzle na poziciu ktora je prazdna
	public void pushUp() throws MoveNotPosibleException {
		if (indexRowOfEmpty == rowCount - 1) {
			throw new MoveNotPosibleException("Cant push puzzle up");
		}
		puzzles[indexRowOfEmpty][indexColumnOfEmpty]
				.setValue(puzzles[indexRowOfEmpty + 1][indexColumnOfEmpty].getValue());
		puzzles[indexRowOfEmpty + 1][indexColumnOfEmpty].setValue(0);
		indexRowOfEmpty = indexRowOfEmpty + 1;
		if (isSolved()) {
			stateOfGame = GameState.SOLVED;
		}

	}

	public void pushDown() throws MoveNotPosibleException {
		if (indexRowOfEmpty == 0) {
			throw new MoveNotPosibleException("Cant push puzzle down");
		}
		puzzles[indexRowOfEmpty][indexColumnOfEmpty]
				.setValue(puzzles[indexRowOfEmpty - 1][indexColumnOfEmpty].getValue());
		puzzles[indexRowOfEmpty - 1][indexColumnOfEmpty].setValue(0);
		indexRowOfEmpty = indexRowOfEmpty - 1;

		if (isSolved()) {
			stateOfGame = GameState.SOLVED;
		}
	}

	public void pushRight() throws MoveNotPosibleException {
		if (indexColumnOfEmpty == 0) {
			throw new MoveNotPosibleException("Cant push puzzle to the right");
		}
		puzzles[indexRowOfEmpty][indexColumnOfEmpty]
				.setValue(puzzles[indexRowOfEmpty][indexColumnOfEmpty - 1].getValue());
		puzzles[indexRowOfEmpty][indexColumnOfEmpty - 1].setValue(0);
		indexColumnOfEmpty = indexColumnOfEmpty - 1;

		if (isSolved()) {
			stateOfGame = GameState.SOLVED;
		}
	}

	public void pushLeft() throws MoveNotPosibleException {
		if (indexColumnOfEmpty == columnCount - 1) {
			throw new MoveNotPosibleException("Cant push puzzle to the left");
		}
		puzzles[indexRowOfEmpty][indexColumnOfEmpty]
				.setValue(puzzles[indexRowOfEmpty][indexColumnOfEmpty + 1].getValue());
		puzzles[indexRowOfEmpty][indexColumnOfEmpty + 1].setValue(0);
		indexColumnOfEmpty = indexColumnOfEmpty + 1;

		if (isSolved()) {
			stateOfGame = GameState.SOLVED;
		}
	}

	public Puzzle getPuzzle(int row, int column) {
		return puzzles[row][column];
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public GameState getStateOfGame() {
		return stateOfGame;
	}

	public long getStartTime() {
		return startTime;
	}
	//ukladanie a nacitavanie ulozeneho hracieho pola. // pole sa uklada cez tlacidlo exit
	public void save() {
		File f = new File("field.bin");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("field.bin"))) {
			oos.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// nechapem preco vyhadzuje vynimku Field.bin (The system cannot find the file specified) pritom mi to normalne loadne ulozene pole
	public static Field load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("field.bin"))) {
			return (Field) ois.readObject();
		} catch (Exception e) {
			// e.printStackTrace();         koli tomu zakomentovane
		}
		return new Field(4, 4);

	}
}

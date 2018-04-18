package nPuzzle.core;

import java.io.Serializable;
// trieda zapuzdrujuca element puzzle 
public class Puzzle implements Serializable{
	private int value;
	
	
	public Puzzle(int value) {
		this.value = value;
	}


	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	
	
}

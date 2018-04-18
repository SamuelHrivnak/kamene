package nPuzzle;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();
	// private Map<String, Integer> playerTimesMap = new HashMap<>() ;

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name ot the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		PlayerTime playerTime = new PlayerTime(name, time);
		playerTimes.add(playerTime);		
		Collections.sort(playerTimes);	
		//playerTimesMap.put(name, time);
	}
	
	public void resetTimes() {
		playerTimes = new ArrayList<PlayerTime>();
	}
	
	// metoda pouzivana na vypis najlepsich casov hracov
	public void printListOfTimes() {
		System.out.println("<.........................BEST TIMES ..........................>");
		playerTimes.stream().forEach(x -> System.out.println(x.name +", "+ x.time));
		System.out.println("<...............................................................>");
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		return playerTimes.toString();
	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}
		
		//Metody pouzivane na porovnavanie casov
		@Override
		public boolean equals(Object obj) {
			PlayerTime pt = (PlayerTime) obj;
			return ((this.time == pt.getTime()) && (this.name.equals(pt.getName())));
		}

		@Override
		public int hashCode() {
			return name.hashCode() + time;
		}

		@Override
		public int compareTo(PlayerTime arg0) {
			return this.getTime() - arg0.getTime();
		}
	}
}
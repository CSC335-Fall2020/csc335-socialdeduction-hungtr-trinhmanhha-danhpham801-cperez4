package social_deduction;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Danh Pham
 * Course: CSC 335 Fall 2020
 * Created: Dec 4, 2020
 * File: EventCard.java
 * Desc: this is the class that will represent the event card
 * 		 it will handle reducing the total value and checking
 * 		 if players passed the event. 
 */
public class EventCard {
	private int totalValue;
	public EventCard(int players) {
		// total weight should be between 1*Total # of Players and 9*Total # of Players
		totalValue = new Random().nextInt(8)+1;
		totalValue *= players;
	}
	/**
	 * check if the players have pass the event or not.
	 * @return true if the event is passed.
	 */
	public boolean pass() {
		return totalValue<=0;
	}
	
	/**
	 * reduce the number by a single value 
	 * card are put down 1 at a time (sequential gameplay)
	 * @param value card number that the player would play
	 */
	public void reduce(int value) {
		totalValue -= value;
	}
	
	/**
	 * reduce the total by a collection of values
	 * all players put down cards at the same time (simultaneous/buffered gameplay)
	 */
	public void reduce(ArrayList<Integer> values) {
		for(int val : values) {
			reduce(val);
		}
	}
	public String toString() {
		String rep = "";
		rep += "---------\n";
		rep += "|  " + totalValue + "  |\n";
		rep += "---------";
		return rep;
	}
	/**
	 * Accesses the total value of the event card
	 * @return the total value of this event card
	 */
	public int getValue() {
		return this.totalValue;
	}
}

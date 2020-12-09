import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @author Danh Pham
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 4, 2020
 * File: Deck.java
 * Desc: this is the class that will represent a deck of card.
 * 		 the total number of cards in the deck will scale based
 * 		 on the number of players. this will simulate realistic
 * 		 probability. 
 */


public class Deck {
	private Queue<Integer> deck = new LinkedList<>();
	public Deck(int players) {
		ArrayList<Integer> aL = new ArrayList<>();
		// Make a deck
		for(int i = 1; i<=9 ; i++) {
			for(int j = 0; j<2*players; j++) {
				aL.add(i);
			}
		}
		// Shuffle the deck
		for(int p=0; p<players; p++) {
			// for each player
			int time = new Random().nextInt(players)+1;
			for(int s=0; s<time; s++) {
				Collections.shuffle(aL);
			}
		}
		deck.addAll(aL);
	}
	/**
	 * Determines if the deck is empty
	 * @return true if deck is empty
	 */
	public boolean isEmpty() {
		return deck.size()==0;
	}
	/**
	 * Draws a card from the deck
	 * @return the card number that is drawn
	 */
	public int draw() {
		if(deck.isEmpty()) {
			Queue<Integer> temp = new LinkedList<>();
			ArrayList<Integer> aL = new ArrayList<>();
			for(int i = 1; i<=9 ; i++) {
				for(int j = 0; j<2*players; j++) {
					aL.add(i);
				}
			}
			for(int p=0; p<players; p++) {
				int time = new Random().nextInt(players)+1;
				for(int s=0; s<time; s++) {
					Collections.shuffle(aL);
				}
			}
			temp.addAll(aL);
			this.deck = temp;
		}
		return deck.poll();
	}
	
	public String toString() {
		String rep = "[";
		for(int x: deck) {
			rep+= x + ", ";
		}
		return rep.substring(0, rep.length()-2) + "]";
	}
}

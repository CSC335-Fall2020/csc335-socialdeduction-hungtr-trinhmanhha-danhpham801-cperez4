import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 17, 2020
 * File: DeckFactory.java
 * Desc: This is the factory class to generate decks. This explores
 * different algorithms to generate decks accordingly to the user
 * settings
 */

/**
 * 
 *
 */
public class AbstractDeckFactory {	
	private DeckConfig myConfig;
	public static class OnDrawEmptyArgs {
		private DeckConfig conf;
		private Deque<Integer> deck;
	}
	public AbstractDeckFactory(DeckConfig config) {
		myConfig = config;
	}
	/**
	 * Creates a default implementation for onDrawEmpty().
	 * @return default impl for onDrawEmpty()
	 */
	public static SerializableFunc<Boolean, OnDrawEmptyArgs> 
		defaultOnDrawEmpty() {
		return (OnDrawEmptyArgs args)-> {
			if(!args.deck.isEmpty()) { return false; }
			// add more cards into deck
			
			return true;
		};
	}
	/**
	 * Creates a deck from config
	 * @return the deck object produced by this factory
	 */
	public Deck makeDeck() {
		Deque<Integer> deckNum = myConfig.makeDeck();
		Deck retval = new Deck(deckNum,);
	}
}

package social_deduction;
import java.io.Serializable;
import java.util.Deque;


/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 18, 2020
 * File: DeckConfig.java
 * Desc:
 */

/**
 * 
 *
 */
public class DeckConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Initial number of cards per hand
	 */
	private int initCardsHand;
	/**
	 * Initial number of cards on the deck (after
	 * each person has {@link #initCardsHand}
	 */
	private int initCardsDeck;
	private int nPlayers;
	private SerializableFunc<Deque<Integer>, DeckConfig> cardGenerator;
	private int minCardNum, maxCardNum;
	public DeckConfig(int initCardsHand, int initCardsDeck,
			int nPlayers, SerializableFunc<Deque<Integer>, DeckConfig> cardGenerator,
			int minCardNum, int maxCardNum) {
		this.initCardsHand = initCardsHand;
		this.initCardsDeck = initCardsDeck;
		this.nPlayers = nPlayers;
		this.cardGenerator = cardGenerator;
		this.minCardNum = minCardNum;
		this.maxCardNum = maxCardNum;
	}
	/**
	 * Calls the cardGenerator function with the configurations
	 * in this class
	 * @return the deck of card numbers requested
	 */
	public Deque<Integer> makeDeck() {
		return cardGenerator.run(this);
	}
}

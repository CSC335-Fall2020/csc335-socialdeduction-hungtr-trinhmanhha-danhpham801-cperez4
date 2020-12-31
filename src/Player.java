import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/*
 * models a player in the game
 */

public class Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private transient ArrayList<Integer> hand;
	private boolean isTraitor = false;
	private boolean isAlive = true;
	
	public Player() {
		this.name = "";
		this.hand = new ArrayList<Integer>();
	}
	public Player(String name, Deck deck) {
		this.name = name;
		this.hand = new ArrayList<Integer>();
		for(int x = 0; x < 5; x++) {
			hand.add(deck.draw());
		}
	}
	public Player(String name, boolean isTraitor) {
		this.name = name;
		this.isTraitor = isTraitor;
		this.isAlive = true;
	}
	
	public Player(String name, Deck deck, boolean isTraitor) {
		this.name = name;
		this.hand = new ArrayList<Integer>();
		for(int x = 0; x < 5; x++) {
			hand.add(deck.draw());
		}
		this.isTraitor = isTraitor;
	}
	/**
	 * Attempts to play (remove, rather) a card from the current deck.
	 * @param card the event card number that this player is playing
	 * @return true if there exists such card, false otherwise. False should
	 * never be returned.
	 */
	public boolean play(int card) {
		return hand.remove((Integer)card);
	}
	
	/**
	 * Adds a card from the deck to this player's hand
	 * @param deck
	 * @return
	 */
	public int draw(Deck deck) {
		hand.add(deck.draw());
		return 0;
	}
	
	/**
	 * check to see if a certain card exists in the player's hand
	 * @param card
	 * @return
	 */
	public boolean hasCard(int card) {
		if(hand.contains(card)) {
			return true;
		}
		return false;
	}
	/**
	 * eliminates a player from the game if they are
	 */
	public void eliminate() {
		this.isAlive = false;
	}
	
	public void setTraitor() {
		this.isTraitor = true;
	}
	
	public boolean isTraitor() {
		return this.isTraitor;
	}
	
	public boolean isAlive() {
		return this.isAlive;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public ArrayList<Integer> getHand() {
		return this.hand;
	}
	public String toString() {
		String rep = "";
		rep += "Player Name: " + this.name + "\n";
		rep += "Current Hand: " + hand.toString() + "\n";
		rep += "Traitor: " + this.isTraitor + "\n";
		rep += "Alive: " + this.isAlive;
		return rep;
	}
	private void writePlayerHand(ObjectOutputStream oos) throws Exception {
		oos.writeObject(hand.size()); // serialize only size
	}
	private void readPlayerHand(ObjectInputStream ois) throws Exception {
		Integer length = (Integer) ois.readObject();
		this.hand = new ArrayList<>();
		for(int i = 0 ; i < length ;++i) {
			this.hand.add(null);
		}
	}
	/**
	 * Tests whether this player is "self"
	 * @return whether this player is self. This method
	 * returns Optional.empty() (check: retval.isPresent())
	 * if the hand is empty.
	 */
	public Optional<Boolean> isSelf() {
		// use the fact that the values of the cards will be null if not self
		// (because other players have transient hand)
		return hand.isEmpty()? Optional.empty():
			Optional.of(hand.get(0)!=null);
	}
	private void writeObject(ObjectOutputStream oos) throws Exception {
		oos.defaultWriteObject(); // write the objects (non-transient)
		writePlayerHand(oos);
	}
	private void readObject(ObjectInputStream ois) throws Exception {
		ois.defaultReadObject();
		readPlayerHand(ois);
	}
}

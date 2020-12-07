/*
 * models a player in the game
 */

public class Player {
	private String name;
	private int[] hand;
	private boolean isTraitor = false;
	private boolean isAlive = true;
	
	public Player() {
		this.name = "";
		this.hand = new int[5];
		for(int x = 0; x < 5; x++) {
			hand[x] = 0;
		}
	}
	public Player(String name, Deck deck) {
		this.name = name;
		this.hand = new int[5];
		for(int x = 0; x < 5; x++) {
			hand[x] = deck.draw();
		}
	}
	public Player(String name, Deck deck, boolean isTraitor) {
		this.name = name;
		this.hand = new int[5];
		for(int x = 0; x < 5; x++) {
			hand[x] = deck.draw();
		}
		this.isTraitor = isTraitor;
	}
	
	public void play(int card) {
		int i = 0;
		for(int c : hand) {
			if(c == card) {
				hand[i] = 0;
			}
			i++;
		}
	}
	
	/**
	 * Adds a card from the deck to this player's hand
	 * @param deck
	 * @return
	 */
	public int draw(Deck deck) {
		for(int x = 0; x < 5; x++) {
			if(this.hand[x] == 0) {
				this.hand[x] = deck.draw();
			}
		}
		return 0;
	}
	
	/**
	 * check to see if a certain card exists in the player's hand
	 * @param card
	 * @return
	 */
	public boolean hasCard(int card) {
		for(int x = 0; x < 5; x++) {
			if (this.hand[x] == card) {
				return true;
			}
		}
		return false;
	}
	/**
	 * eliminates a player from the game if they are
	 */
	public void eliminate() {
		this.isAlive = false;
	}
	
	public boolean isTraitor() {
		return this.isTraitor;
	}
	
	public boolean isAlive() {
		return this.isAlive;
	}
	
	public String getName() {
		return this.name;
	}
	public int[] getHand() {
		return this.hand;
	}
	public String toString() {
		String rep = "";
		rep += "Player Name: " + this.name + "\n";
		rep += "Current Hand: [";
		for (int x = 0; x < 5; x++) {
			rep += hand[x] + ", ";
		}
		rep = rep.substring(0, rep.length()-2) +"]\n";
		rep += "Traitor: " + this.isTraitor + "\n";
		rep += "Alive: " + this.isAlive;
		return rep;
	}
}

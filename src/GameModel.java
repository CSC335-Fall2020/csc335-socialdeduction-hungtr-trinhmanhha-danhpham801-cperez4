import java.util.ArrayList;
import java.util.Random;

/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 2, 2020
 * File: GameModel.java
 * Desc: This file is a model class for the social deduction card-game
 */
public class GameModel {
	private Player[] players;
	private Deck sharedDeck;
	private int eventSuccess;
	private int eventFail;
	
	
	public GameModel(String[] playerNames) {
		Random random = new Random();
		int numPlayers = playerNames.length;
		int traitor = random.nextInt(numPlayers);
		this.players = new Player[numPlayers];
		this.sharedDeck = new Deck(numPlayers);
		this.eventSuccess = 0;
		this.eventFail = 0;
		int i = 0;
		for(String name: playerNames) {
			if(traitor == i) {
				this.players[i] = new Player(name, sharedDeck, true);
			}else {
				this.players[i] = new Player(name, sharedDeck);
			}
			i++;
		}
	}
	
	public boolean isGameOver() {
		
		return false;
	}
	
	public String toString() {
		String rep = "";
		rep += "-Placeholder-\n";
		rep += "Shared Deck:\n";
		rep += this.sharedDeck.toString() + "\n\n";
		rep += "Players:\n";
		rep +="--------------------------------------------------------\n";
		for(Player p: this.players) {
			rep += p.toString() + "\n";
			rep +="--------------------------------------------------------\n";
		}
		return rep;
	}
}

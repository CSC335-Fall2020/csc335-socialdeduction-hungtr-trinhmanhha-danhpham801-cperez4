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
	private EventCard curEvent;
	private ArrayList<Integer> playedCards;
	private int gameProgress;
	private int eventSuccess;
	private int eventFail;
	private int traitor;
	
	
	public GameModel(String[] playerNames) {
		Random random = new Random();
		int numPlayers = playerNames.length;
		this.traitor = random.nextInt(numPlayers);
		this.players = new Player[numPlayers];
		this.sharedDeck = new Deck(numPlayers);
		this.curEvent = new EventCard(numPlayers);
		this.playedCards = new ArrayList<Integer>();
		this.gameProgress = 5;
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
	
	public void generateEvent() {
		this.curEvent = new EventCard(this.players.length);
	}
	
	//returns the int value of the card played
	public int playCard(String name, int card) {
		for(Player p: players) {
			if(p.getName().equals(name) && p.hasCard(card)) {
				p.play(card);
				p.draw(sharedDeck);
				playedCards.add(card);
			}
		}
		return -1;
	}
	
	public boolean resolveEvent() {
		this.curEvent.reduce(playedCards);
		boolean result = curEvent.pass();
		if(result) {
			this.eventSuccess += 1;
		}else {
			this.eventFail += 1;
		}
		this.playedCards = new ArrayList<Integer>();
		return result;
	}
	
	/*
	 * eliminates a player form the game
	 * returns 0 if the player was eliminated successfully, -1 if the player was already eliminated
	 */
	public int eliminate(String name) {
		for(Player p: players) {
			if(p.getName().toLowerCase().equals(name)) {
				if(!p.isAlive()) {
					return -1;
				}
				p.eliminate();
			}
		}
		return 0;
	}
	
	/*
	 * checks to see if any of the end-game conditions have been met
	 * returns -1 if the game is not over, 0 if the group wins, 1 if the saboteur wins
	 */
	public int isGameOver() {
		//checks to see if the saboteur is voted out
		if (!players[traitor].isAlive()) {
			return 0;
		//checks to see if there are no more events to be played
		}else if(gameProgress == 0){
			//if more events where successful then the group wins
			if (eventSuccess > eventFail){
				return 0;
			//if more events fail, or if there is a tie then the saboteur wins
			}else {
				return 1;
			}
		}else {
			//checks to see if there are only two players left (sabotuer and a +1)
			int playersAlive = 0;
			for(Player p : players) {
				if (p.isAlive()) {
					playersAlive++;
				}
			}
			if(playersAlive == 2) {
				return 1;
			}
		}
		return -1;
	}
	
	public void progressGame() {
		this.gameProgress -= 1;
	}
	public Player[] getPlayers() {
		return this.players;
	}
	public EventCard getEvent() {
		return this.curEvent;
	}
	
	public String toString() {
		String rep = "";
		rep += "-Placeholder-\n";
		rep += "Players:\n";
		rep +="--------------------------------------------------------\n";
		for(Player p: this.players) {
			rep += p.toString() + "\n";
			rep +="--------------------------------------------------------\n";
		}
		rep += "Current Event:\n" + curEvent.toString();
		return rep;
	}
}

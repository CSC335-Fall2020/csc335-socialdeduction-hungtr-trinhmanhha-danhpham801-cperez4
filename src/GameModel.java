import java.util.ArrayList;
import java.util.Observable;
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
public class GameModel extends Observable {
	protected ArrayList<String> nameList;
	private Player player;
	private Deck sharedDeck;
	private EventCard curEvent;
	private ArrayList<Integer> playedCards;
	protected int numPlayers;
	private int turns;
	private ProgressBar progress;
	
	
	public GameModel() {
		numPlayers = 0;
		System.out.println("Number of players: " + numPlayers);
		//list of every player's name
		nameList = new ArrayList<>();
		//player of this model
		this.player = new Player();
		//game deck
		this.sharedDeck = new Deck(numPlayers);
		//first event
		this.curEvent = new EventCard(numPlayers);
		//will hold cards played on each turn
		this.playedCards = new ArrayList<Integer>();
		//default number of turns
		this.turns = 5;
		this.progress = new ProgressBar();
	}
	
	public void processMsg(GameMessage msg) {
		if(msg.enoughPlayer) {
			setChanged();
			notifyObservers(msg);
		}
	}
	
	public void regPlayer(String name, Deck sharedDeck, boolean isTraitor) {
		this.player = new Player(name, sharedDeck, isTraitor);
	}
	
	//updates the event once it has been resolved
	public void generateEvent() {
		this.curEvent = new EventCard(this.numPlayers);
	}
	
	/*
	//returns the int value of the card played
	public int playCard(String name, int card) {
		for(Player p: players) {
			if(p.getName().equals(name) && p.hasCard(card)) {
				p.play(card);
				p.draw(sharedDeck);
				playedCards.add(card);
			}
		}
		return card;
	}*/
	
	//resolves the current event, returns true if the event was a success or false if it failed
	public boolean resolveEvent() {
		this.curEvent.reduce(playedCards);
		boolean result = curEvent.pass();
		//adds to the success/fail counters
		if(result) {
			progress.makeProgress('p');
		}else {
			progress.makeProgress('f');
		}
		this.playedCards = new ArrayList<Integer>();
		return result;
	}
	
	/*
	 * eliminates a player form the game
	 * returns 0 if the player was eliminated successfully, -1 if the player was already eliminated
	 */
	public int eliminate(String name) {
		if(!player.isAlive()) return -1;
		player.eliminate();
		numPlayers--;
		return 0;
	}
	
	/*
	 * checks to see if any of the end-game conditions have been met
	 * returns -1 if the game is not over, 0 if the group wins, 1 if the saboteur wins
	 */
	public int isGameOver() {
		//checks to see if the saboteur is voted out
		if (this.player.isTraitor() && !this.player.isAlive()) {
			return 0;
		//checks to see if there are no more events to be played
		}else if(turns == 0){
			//if more events where successful then the group wins
			if (progress.winner() > 0){
				return 0;
			//if more events fail, or if there is a tie then the saboteur wins
			}else {
				return 1;
			}
		}else {
			//checks to see if there are only two players left (sabotuer and a +1)
			if(numPlayers == 2) return 0;
		}
		return -1;
	}
	
	public void nextTurn() {
		this.turns -= 1;
	}
	
	public ArrayList<String> getNamelist() {
		return nameList;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	public EventCard getEvent() {
		return this.curEvent;
	}
	public ProgressBar getProgress() {
		return this.progress;
	}
	
	public String toString() {
		String rep = "";
		rep += "-Placeholder-\n";
		rep += this.progress.toString() + "\n";
		rep += "Players:\n";
		rep +="--------------------------------------------------------\n";
		rep += player.toString() + "\n";
		rep +="--------------------------------------------------------\n";
		rep += "Current Event:\n" + curEvent.toString();
		return rep;
	}
	
	public Deck getDeck() {
		return this.sharedDeck;
	}
	
	public void add2PlayedC(Integer card) {
		this.playedCards.add(card);
	}
	
	public void emptyPlayed() {
		this.playedCards = new ArrayList<>();
	}
	public void makeProgress(char c) {
		if(c=='p') {
			this.progress.makeProgress(c);
		}
		else {
			this.progress.makeProgress(c);
		}
	}
	
	/*
	public void setServer() {
		isServer = true;
	}
	
	public boolean isServer() {
		return isServer;
	}*/
}

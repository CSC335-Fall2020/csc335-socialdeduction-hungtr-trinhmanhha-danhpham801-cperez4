package social_deduction;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * @author Hung Tran, cperez4
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 2, 2020
 * File: GameModel.java
 * Desc: This file is a model class for the social deduction card-game.
 * Supposedly, this class shows the data that the current "player" has
 * access to seeing
 */
public class GameModel extends Observable {
	protected static final int TEST_LIMIT = 4;
	// protected ArrayList<String> nameList;
	protected ArrayList<Player> players;
	// protected Player player;
	private Deck sharedDeck;
	protected EventCard curEvent;
	protected ArrayList<Integer> playedCards;
	protected int numPlayers;
	protected boolean isVotingPhase;
	private int turns;
	protected ProgressBar progress;
	
	
	public GameModel(String name) {
		numPlayers = 0;
		//list of every player's name
		nameList = new ArrayList<>();
		// game deck
		this.sharedDeck = new Deck(TEST_LIMIT);
		//eventCard
		curEvent = new EventCard(TEST_LIMIT);
		//player of this model
		this.player = new Player(name, sharedDeck);
		//will hold cards played on each turn
		this.playedCards = new ArrayList<Integer>();
		//default number of turns
		this.turns = 5;
		this.progress = new ProgressBar();
		isVotingPhase = false;
	}
	
	public void processMsg(GameMessage msg) {
		if(msg.enoughPlayer) {
			setChanged();
			notifyObservers(msg);
		}
		
		else if(msg.playCard) {
			playedCards.add(msg.latestCard);
			if (playedCards.size() == TEST_LIMIT)
				msg.markEnoughCard();
			setChanged();
			notifyObservers(msg);
		}
		
		else if(msg.eventCheck) {
			playedCards.clear();
			setChanged();
			notifyObservers(msg);
		}
		
		else if(msg.traitorSet) {
			this.player.setTraitor();
			setChanged();
			notifyObservers(msg);
		}
		
		else if(msg.eliminate) {
			player.eliminate();
			setChanged();
			notifyObservers(msg);
		}
		else if(msg.chatMsg != null) { // is chat msg
			setChanged();
			notifyObservers(msg);
		}
	}
	
	/*
	public void playCard(int cardValue) {
		player.play(cardValue);
	}*/
	
	public void regPlayer(String name, Deck sharedDeck, boolean isTraitor) {
		this.player = new Player(name, sharedDeck, isTraitor);
	}

	/**
	 * Creates an event in the game model. This forces the
	 * group of players to resolve the event.
	 */
	public void generateEvent() {
		this.curEvent = new EventCard(this.numPlayers);
	}
	/**
	 * Forcefully makes this game model "focus" on 'event',
	 * forces the group of players to resolve 'event'
	 * @param event the event to be forcefully "focused"
	 */
	public void generateEvent(EventCard event) {
		this.curEvent = event;
	}
	
	/**
	 * returns the int value of the card played
	 * @param name The name of the player that is playing the card
	 * @param card the card number
	 * @return The card number
	 */
	/*
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
	
	/**
	 * resolves the current event, 
	 * @return true if the event was a 
	 * success or false if it failed
	 */
	public boolean resolveEvent() {
		this.curEvent.reduce(playedCards);
		boolean result = curEvent.pass();
		//adds to the success/fail counters
		if(result) {
			progress.makeProgress('p');
		}else {
			progress.makeProgress('f');
		}
		//resets the cards that are played after the event has been resolved
		this.playedCards = new ArrayList<Integer>();
		return result;
	}
	/**
	 * eliminates a player form the game
	 * @return 0 if the player was eliminated successfully,
	 * -1 if the player was already eliminated
	 */
	public int eliminate(String name) {
		if(!player.isAlive()) return -1;
		player.eliminate();
		numPlayers--;
		return 0;
	}
	
	/**
	 * checks to see if any of the end-game conditions have been met
	 * @return -1 if the game is not over, 0 if the group wins, 1 if the saboteur wins
	 */
	public int isGameOver() {
		//checks to see if the saboteur is voted out
		if (this.player.isTraitor() && !this.player.isAlive()) {
			return 0;
		//checks to see if there are no more events to be played
		} else if(turns == 0){
			//if more events where successful then the group wins
			if (progress.winner() > 0){
				return 0;
			//if more events fail, or if there is a tie then the saboteur wins
			}else {
				return 1;
			}
		} else {
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
	
	/**
	 * Adds 'card' into played cards
	 * @param card the card to be added to the list of played cards
	 */
	public void add2PlayedC(Integer card) {
		this.playedCards.add(card);
	}
	/**
	 * Clears the played cards list
	 */
	public void emptyPlayed() {
		this.playedCards = new ArrayList<>();
	}
	/**
	 * Makes this game model to make progress.
	 * @param c 'p' if the event passed, otherwise, interpret as 'f' (falled)
	 */
	public void makeProgress(char c) {
		if(c=='p') {
			this.progress.makeProgress(c);
		}
		else {
			this.progress.makeProgress(c);
		}
	}
	
	public void enterVotingPhase() {
		isVotingPhase = true;
	}
	
	/*
	public void setServer() {
		isServer = true;
	}
	
	public boolean isServer() {
		return isServer;
	}*/
}

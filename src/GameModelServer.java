import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameModelServer extends GameModel {
	private boolean firstRun;
	private ArrayList<Player> playersList;
	private HashMap<String, Integer> hm;
	private int voteList[];
	private int voteCount;
	
	public GameModelServer(String name) {
		super(name);
		firstRun = true;
		playersList = new ArrayList<>();
		hm = new HashMap<>();
		voteList = new int[testLimit];
		voteCount = 0;
	}

	@Override
	public void processMsg(GameMessage msg) {
		if(msg.chatMsg != null) {
			// process chatMsg
			setChanged();
			notifyObservers(msg);
			return;
		}
		// If message notifies there's a new player
		// Add that player name to nameList
		if(msg.newPlayer) addPlayer(msg.playerName);

		// Test with 3 players first
		// Game will start when there's 3 players
		if(numPlayers == testLimit && firstRun) {
			firstRun = false;
			init();
			msg = new GameMessage(GameMessage.ENOUGHPLAYER, nameList);
			msg.setEventVal(curEvent.getValue());
			setChanged();
			notifyObservers(msg);
		}

		if(msg.playCard) {
			playedCards.add(msg.latestCard);
			if (playedCards.size() == testLimit) {
				msg.markEnoughCard();
			}
			setChanged();
			notifyObservers(msg);
		}
		
		if(msg.voting) {
			int index = hm.get(msg.voted);
			voteList[index]++;
			voteCount++;
			if(voteCount == numPlayers) {
				resolveVote();
				
			}
		}
	}
	
	public void resolveVote() {
		int eliminated = findMaxVoted();
		System.out.println("Eliminate " + eliminated);
		
		// Nobody eliminated
		if(eliminated == -1) return;
		
		playersList.get(eliminated).eliminate();
		numPlayers--;
		
		// Server got eliminated
		if(eliminated == 0) this.player.eliminate();
		else { // Client got eliminated
			setChanged();
			notifyObservers(new GameMessage(GameMessage.ELIMINATE, eliminated-1));
		}
	}
	
	public int checkWin() {
		for(Player p : playersList) {
			if(p.isTraitor() && !p.isAlive()) return 1;
		}
		if(numPlayers == 2) return -1;
		return 0;
	}
	
	public int findMaxVoted() {
		int maxVote = 0;
		int maxPerson = -1;
		for(int i = 0; i < numPlayers; i++) {
			if(voteList[i] > maxVote) {
				maxVote = voteList[i];
				maxPerson = i;
			}
			
			// Two people with same vote will count as no max
			else if(voteList[i] == maxVote) {
				maxPerson = -1;
			}
		}
		
		return maxPerson;
	}
	
	public void init() {
		int traitor = new Random().nextInt(numPlayers-1) + 1;
		boolean isTraitor = false;
		int counter = 0;
		for(String s : nameList) {
			if(counter == traitor) isTraitor = true;
			else isTraitor = false;
			Player p = new Player(s, isTraitor);
			playersList.add(p);
			hm.put(s, counter);
			counter++;
		}
		
		// if server is traitor
		if(traitor == 0) this.player.setTraitor();
		else { // traitor is one of the client
			setChanged();
			notifyObservers(new GameMessage(GameMessage.TRAITORSET, traitor-1));
		}
	}

	public void addPlayer(String name) {
		if(nameList.isEmpty() || !nameList.contains(name)) {
			nameList.add(name);
			numPlayers++;
		}
	}

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
		
		GameMessage msg = new GameMessage(
				GameMessage.EVENTCHECK, result);
		setChanged();
		notifyObservers(msg);


		return result;
	}
}

import java.util.ArrayList;

public class GameModelServer extends GameModel {
	private boolean firstRun;

	public GameModelServer(String name) {
		super(name);
		firstRun = true;
		//System.out.println("Even val: " + curEvent.getValue());
		//first event
		//curEvent = new EventCard(testLimit);
	}

	@Override
	public void processMsg(GameMessage msg) {
		// If message notifies there's a new player
		// Add that player name to nameList
		if(msg.newPlayer) addPlayer(msg.playerName);

		// Test with 3 players first
		// Game will start when there's 3 players
		if(numPlayers == testLimit && firstRun) {
			firstRun = false;
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
	}

	public void playCard(int card) {
		playedCards.add(card);
		player.play(card);
		System.out.println(playedCards.size());
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

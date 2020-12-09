
public class GameModelServer extends GameModel {
	public GameModelServer(String name) {
		super(name);
	}

	@Override
	public void processMsg(GameMessage msg) {
		// If message notifies there's a new player
		// Add that player name to nameList
		if(msg.newPlayer) addPlayer(msg.playerName);
		
		// Test with 3 players first
		// Game will start when there's 3 players
		if(numPlayers == 3) {
			setChanged();
			notifyObservers(new GameMessage(GameMessage.ENOUGHPLAYER, nameList));
		}
	}
	
	public void addPlayer(String name) {
		if(nameList.isEmpty() || !nameList.contains(name)) {
			nameList.add(name);
			numPlayers++;
		}
	}
}

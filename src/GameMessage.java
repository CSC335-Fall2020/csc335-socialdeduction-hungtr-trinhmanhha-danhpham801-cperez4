import java.io.Serializable;
import java.util.ArrayList;

public class GameMessage implements Serializable {
	public static final int NEWPLAYER = 1;
	public static final int ENOUGHPLAYER = 2; 
	public static final int PLAYCARD = 3;
	public static final int ENOUGHCARD = 4;
	public static final int EVENTCHECK = 5;
	public static final int VOTING = 6;
	public static final int TRAITORSET = 7;
	public static final int ELIMINATE = 8,
			CHAT_MESSAGE = 0xa0f1;
	public boolean newPlayer;
	public boolean enoughPlayer;
	public boolean playCard;
	public boolean enoughCard;
	public boolean eventCheck;
	public boolean voting;
	public boolean traitorSet;
	public boolean eliminate;
	public String playerName;
	public ArrayList<String> nameList;
	public int eventVal;
	public int playerID;
	int latestCard;
	public boolean eventPassed;
	public String voted;
	ChatServerMessage chatMsg;
	private static final long serialVersionUID = 1L;
	public GameMessage(ChatServerMessage chatMsg) {
		this.chatMsg = chatMsg;
	}
	public GameMessage(int type) {
		init();
		if(type == NEWPLAYER) {
			newPlayer = true;
		}
		if(type == ENOUGHPLAYER) {
			enoughPlayer = true;
		}
		if(type == ENOUGHCARD) {
			enoughCard = true;
		}
	}
	
	public GameMessage(int type, String name) {
		init();
		if(type == NEWPLAYER) {
			newPlayer = true;
			playerName = name;
		}
		else if(type == VOTING) {
			voting = true;
			voted = name;
		}
	}
	
	public GameMessage(int type, ArrayList<String> names) {
		init();
		if(type == ENOUGHPLAYER) {
			enoughPlayer = true;
			nameList = names;
		}
	}
	
	public GameMessage(int type, int val) {
		init();
		if(type == PLAYCARD) {
			playCard = true;
			latestCard = val;
		}
		else if(type == TRAITORSET) {
			traitorSet = true;
			playerID = val;
		}
		else if(type == ELIMINATE) {
			eliminate = true;
			playerID = val;
		}
	}
	
	public GameMessage(int type, boolean result) {
		init();
		if(type == EVENTCHECK) {
			eventCheck = true;
			eventPassed = result;
		}
	}
	
	public void markEnoughCard() {
		enoughCard = true;
	}
	
	public void setEventVal(int val) {
		eventVal = val;
	}
	
	public void checkEvent(boolean val) {
		eventPassed = val;
	}
	
	private void init() {
		newPlayer = false;
		enoughPlayer = false;
		playCard = false;
		enoughCard = false;
		eventCheck = false;
		voting = false;
		eventPassed = false;
		traitorSet = false;
		eliminate = false;
		eventVal = 0;
		chatMsg = null;
	}
}

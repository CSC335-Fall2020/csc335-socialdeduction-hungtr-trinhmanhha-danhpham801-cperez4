import java.io.Serializable;
import java.util.ArrayList;

public class GameMessage implements Serializable {
	public static final int NEWPLAYER = 1;
	public static final int ENOUGHPLAYER = 2; 
	public static final int PLAYCARD = 3;
	public static final int ENOUGHCARD = 4;
	public static final int EVENTCHECK = 5;
	public boolean newPlayer;
	public boolean enoughPlayer;
	public boolean playCard;
	public boolean enoughCard;
	public boolean eventCheck;
	public String playerName;
	public ArrayList<String> nameList;
	public int eventVal;
	int latestCard;
	public boolean eventPassed;
	private static final long serialVersionUID = 1L;
	
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
	}
	
	public GameMessage(int type, ArrayList<String> names) {
		init();
		if(type == ENOUGHPLAYER) {
			enoughPlayer = true;
			nameList = names;
		}
	}
	
	public GameMessage(int type, int card) {
		init();
		if(type == PLAYCARD) {
			playCard = true;
			latestCard = card;
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
		eventPassed = false;
		eventVal = 0;
	}
}

package social_deduction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The Message class to be passed around in runtime.
 * 
 * Author: WoodyMC
 * Modified by Hung Tran (Pegasust/hungtr-uofa) for minimum
 * data size and code beauty
 * 
 *
 */
public class GameMessage implements Serializable {
	/**
	 * opcode of this message
	 * treat this as the shift amount for the bitmask
	 * type of message
	 */
	public static final int 
			PLACEHOLDER = 0,
			NEWPLAYER = 1,
			ENOUGHPLAYER = 2, 
			PLAYCARD = 3,
			ENOUGHCARD = 4,
			EVENTCHECK = 5,
			VOTING = 6,
			TRAITORSET = 7,
			ELIMINATE = 8,
			CHAT_MESSAGE = 9;
//	public boolean newPlayer;
//	public boolean enoughPlayer;
//	public boolean playCard;
//	public boolean enoughCard;
//	public boolean eventCheck;
//	public boolean voting;
//	public boolean traitorSet;
//	public boolean eliminate;
	public static class Message<A extends Serializable> implements Serializable {
		/**
		 * Optional
		 */
		private A args;
		/**
		 * comparing short to integral type is "cheap",
		 * and we are minimizing the data size, so we're using short
		 * (or byte, maybe)
		 */
		private short opcode;
	}
	public String playerName;
	public ArrayList<String> nameList;
	public int eventVal;
	public int playerID;
	int latestCard;
	public boolean eventPassed;
	/**
	 * Player getting voted off
	 */
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

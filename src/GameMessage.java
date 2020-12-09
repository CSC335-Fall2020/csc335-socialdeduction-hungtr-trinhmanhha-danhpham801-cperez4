import java.io.Serializable;
import java.util.ArrayList;

public class GameMessage implements Serializable {
	public static final int NEWPLAYER = 1;
	public static final int ENOUGHPLAYER = 2; 
	public boolean newPlayer;
	public boolean enoughPlayer;
	public String playerName;
	public ArrayList<String> nameList;
	private static final long serialVersionUID = 1L;
	
	public GameMessage(int type) {
		newPlayer = false;
		enoughPlayer = false;
		if(type == NEWPLAYER) {
			newPlayer = true;
		}
		if(type == ENOUGHPLAYER) {
			enoughPlayer = true;
		}
	}
	
	public GameMessage(int type, String name) {
		newPlayer = false;
		enoughPlayer = false;
		if(type == NEWPLAYER) {
			newPlayer = true;
			playerName = name;
		}
	}
	
	public GameMessage(int type, ArrayList<String> names) {
		newPlayer = false;
		enoughPlayer = false;
		if(type == ENOUGHPLAYER) {
			enoughPlayer = true;
			nameList = names;
		}
	}
}

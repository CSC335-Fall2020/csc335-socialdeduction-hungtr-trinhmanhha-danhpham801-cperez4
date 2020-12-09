import java.io.Serializable;

public class GameMessage implements Serializable {
	public boolean newPlayer;
	public boolean enoughPlayer;
	private static final long serialVersionUID = 1L;
	
	public GameMessage(int type) {
		newPlayer = false;
		enoughPlayer = false;
		if(type == 1) {
			newPlayer = true;
		}
		if(type == 2) {
			enoughPlayer = true;
		}
		
	}
}

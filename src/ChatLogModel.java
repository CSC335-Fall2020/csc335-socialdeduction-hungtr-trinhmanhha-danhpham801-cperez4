import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.text.Text;

/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 8, 2020
 * File: ChatLogModel.java
 * Desc:
 */

/**
 * This class serves as a chat log (as client), and can be
 * used as a shared chat log (as server)
 *
 */
public class ChatLogModel extends Observable {
	private List<ChatServerMessage> log;
	
	public ChatLogModel(Observer obs) {
		log = new ArrayList<>();
		this.addObserver(obs);
	}
	/**
	 * Adds 'msg' into the back of the chat log. Notifies
	 * observers of this chat log model.
	 * @param msg the message to 
	 */
	public void pushBack(ChatServerMessage msg) {
		log.add(msg);
		this.setChanged();
		this.notifyObservers(msg);
	}
}

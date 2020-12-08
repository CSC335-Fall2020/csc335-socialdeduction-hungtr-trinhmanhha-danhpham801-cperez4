import java.util.LinkedList;
import java.util.Queue;
/**
 * @author Danh Pham
 * Course: CSC 335 Fall 2020
 * Created: Dec 5, 2020
 * File: ChatLog.java
 * Desc: the class will keep a log of what the group is saying.
 * 		 (the choice for a text based implementation)
 */
public class ChatLog {
	private Queue<String> log;
	private static final int LOG_CAP = 50;
	
	public ChatLog() {
		log = new LinkedList<>();
	}
	
	public void put(String s) {
		if(log.size()>LOG_CAP) {
			log.poll();
		}
		log.add(s);
	}
	
	public String toString() {
		String ret = "";
		for(String s: log) {
			ret+=s+"\n";
		}
		return ret;
	}
	
}

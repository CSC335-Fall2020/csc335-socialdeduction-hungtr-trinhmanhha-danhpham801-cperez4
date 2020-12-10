
 /**
   * @author Hung Tran
   * Course: CSC 335 Fall 2020
   * Created: Dec 5, 2020
   * File: GChatBox.java
   * Desc: This is a class for whatever the chat server sends
   * to the chat clients
   */
public class ChatServerMessage {
	public static class ChatMessage {
		public Player player;
		public String message;
		public Object additionalInfo;
		private String buf = null;
		public ChatMessage(Player p, String msg, Object info) {
			player = p; message = msg; additionalInfo = info;
		}
		public ChatMessage(Player p, String msg) {
			this(p, msg, null);
		}
		private String infoStr() {
			if(additionalInfo != null) {
				return additionalInfo.toString();
			}
			return "";
		}
		private String playerStr() {
			if(player == null) {
				return "LOCAL";
			}
			String deadStr = !player.isAlive()? "[x] " : "";
			return String.format("%s%s", deadStr, player.getName());
		}
		public String toString() {
			if(buf == null) {
				buf = String.format("%s%s: %s", 
						infoStr(), playerStr(), message);
			}
			return buf;
		}
	}
	public ChatMessage msg;
}

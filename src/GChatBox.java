  import java.util.ArrayList;
  import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
  import javafx.scene.Scene;
  import javafx.scene.control.ScrollPane;
  import javafx.scene.control.TextField;
  import javafx.scene.layout.BorderPane;
  import javafx.scene.layout.Pane;
  import javafx.scene.layout.VBox;
  import javafx.scene.text.Text;
  import javafx.stage.Stage;
  /**
   * @author Danh Pham, Hung Tran
   * Course: CSC 335 Fall 2020
   * Created: Dec 5, 2020
   * File: GChatBox.java
   * Desc: the class will keep a log of what the group is saying.
   * 		 (the choice for a Graphical implementation)
   * 		  (class is only meant for visualizing the chat box)
   */
public class GChatBox extends Application implements Observer {

	private Pane root = new Pane();
	private Scene scene;
	private BorderPane mainP = null;
	private final VBox chatBox = new VBox(5);
	private List<Text> messages = new ArrayList<>();
	// text field container
	private ScrollPane container = new ScrollPane();
	private TextField tf = new TextField();
	// to be attached to game view
	private GameController chatClient = null;
	
	private ChatLogModel model = new ChatLogModel(this);
	private Player self = null;
	@Override
	public void start(Stage stage) throws Exception{
		// tryAttach(300, 500);
		BorderPane bp = makeInstance(300, 500, null, null);
	    scene = new Scene(bp,500,450);
	    stage.setScene(scene);
	    stage.show();
	}
	/**
	 * Accesses the main pane ready to be rendered by javaFX.
	 * @return this.mainP
	 */
	public BorderPane getMainPane() {
		return mainP;
	}
	/**
	 * Creates a default instance of GChatBox (client)
	 * @param w the width reserved for chatbox
	 * @param h the height reserved for chatbox
	 * @param client the client class that uses chatbox
	 * @param p the player that controls this chatbox
	 * @return the BorderPane to be attached to the view
	 */
	public BorderPane makeInstance(double w, double h,
			GameController gc, Player p) {
		chatClient = gc;
		model.addObserver(this);
		model.attachController(gc);
		self = p;
		tryAttach(w, h);
		return mainP;
	}
	// attaches everything together into this.mainP.
	private void tryAttach(double w, double h) {
		if(mainP == null) { // not yet attached
			mainP = new BorderPane();
			mainP.setMaxWidth(w);
			mainP.setMaxHeight(h);
		    initChatBox(w, h);
		    root.getChildren().add(container);
		    mainP.setCenter(root);
		    mainP.setBottom(tf);
		}
	}
	private void initChatBox(double w, double h){
		double tfHeight = h / 5.0;
	    container.setPrefSize(w, h-tfHeight);
	    container.setContent(chatBox); 
	    tf.setMaxSize(w, tfHeight);
	    tf.setOnAction(evt->{
	    	sendMessage(tf.getText());
	        tf.clear();
	    });
	}
	
	public static void main(String []args){
		launch(args);
	}
	private boolean isServer() {
		return chatClient.getClientTuple() == null;
	}
	/**
	 * Handles the view listening from the net and
	 * gotten a message of type chat message from the server.
	 * Simply adds 'msg' into chat model
	 * @param msg the message listened from server
	 */
	public void handleMessage(ChatServerMessage msg) {
		model.pushBack(msg);
	}
	private Object sendMessage(String msg) {
		ChatServerMessage send = new ChatServerMessage();
		send.msg = new ChatServerMessage.ChatMessage(self, msg);
		if(isServer()) {
			model.pushBack(send);			
		}
		chatClient.send(new GameMessage(send));
		return null;
	}
	private void addText(String text) {
		Text t = new Text(text);
		t.setWrappingWidth(295);
		messages.add(t);
		// String value: t.getText()
		chatBox.getChildren().add(t);
	}
	@Override
	public void update(Observable o, Object arg) {
		// we know that observable is a type of chat server
		// so arg should be of type ChatMessage
		ChatServerMessage msg = (ChatServerMessage) arg;
		addText(msg.msg.toString());
	}
}


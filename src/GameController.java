import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class GameController {
	public static class ClientTuple {
		ObjectInputStream in;
		ObjectOutputStream out;
		Socket socket;
		public ClientTuple(ObjectInputStream i, 
				ObjectOutputStream o, Socket s) {
			in = i; out = o; socket = s;
		}
	}
	protected GameModel model;
	protected boolean isServer;
	/*
	private ServerSocket server;
	private Socket connection;
	private Socket client;*/
	
	public GameController(GameModel gM) {
		model = gM;
	}
	
	///////////////////////////////////////////////////////
	/////////////NETWORK CODE/////////////////////////////
	//////////////////////////////////////////////////////
	
	public abstract ClientTuple getClientTuple();
	/*
	public void makeServer(int user) {
		try {
			server = new ServerSocket(4000);
			connection = server.accept();
			isServer = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeClient(int user) {
		try {
			client = new Socket("localhost", 4000);
			isServer = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void shutNetwork() {
		if(isServer)
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		else if(!isServer)
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void sendMsg(int messageType) {
		Socket current = null;
		if(isServer) current = connection;
		else current = client;
		try {
			ObjectOutputStream output = new ObjectOutputStream(current.getOutputStream());
			output.writeObject(new GameMessage(messageType));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public GameMessage receiveMsg() {
		Socket current = null;
		if(isServer) current = connection;
		else current = client;
		try {
			ObjectInputStream input = new ObjectInputStream(current.getInputStream());
			return (GameMessage) input.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	///////////////////////////////////////
	////////NON-NETWORK///////////////////
	//////////////////////////////////////
	
	/**
	 * Checks whether the game is over by consulting
	 * to the state of the game from the model
	 * @return whether the game is over
	 */
	public boolean isGameOver() {
		return model.isGameOver()==-1;
	}
	
	/**
	 * Forces the model to generate an event. This
	 * is assuming that the player is making a legal
	 * move (throwing out an event card)
	 */
	public void generateEvent() {
		model.generateEvent();
	}
	
	/*
	public int playCard(String name, int card) {
		Player p = players.get(name);
		p.play(card);
		p.draw(model.getDeck());
		model.add2PlayedC(card);
		return card;
	}
	
	public boolean eliminate(String name) {
		Player p = players.get(name);
		if(!p.isAlive()) {
			return false;
		}
		p.eliminate();
		return true;
	}*/
	
	public boolean resolveEvent() {
		EventCard curE = model.getEvent();
		boolean result = curE.pass();
		if(result) {
			model.makeProgress('p');
		}else {
			model.makeProgress('f');
		}
		model.emptyPlayed();
		return result;
	}
	
	public GameModel getModel() {
		return model;
	}
}
package social_deduction;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;

public class GameControllerServer extends GameController {	
	private ArrayList<ConnectionToClient> clientList;
	private LinkedBlockingQueue<GameMessage> messages;
	private ServerSocket serverSocket;

	public GameControllerServer(GameModel gM) {
		super(gM);
		isServer = true;
		clientList = new ArrayList<ConnectionToClient>();
        messages = new LinkedBlockingQueue<GameMessage>();
		try {
			serverSocket = new ServerSocket(4000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Thread accept = new Thread() {
			public void run(){
				while(model.numPlayers != model.testLimit){
					try{
						Socket s = serverSocket.accept();
						// send info of server to all client
						sendToAll(new GameMessage(GameMessage.NEWPLAYER,
								  gM.getPlayer().getName()));
						System.out.println("Connection accepted!");
						clientList.add(new ConnectionToClient(s));
					}
					catch(IOException e){ e.printStackTrace(); }
				}
			}
		};

		accept.setDaemon(true);
		accept.start();

		Thread messageHandling = new Thread() {
			public void run(){
				while(true){
					try{
						GameMessage message = messages.take();
						// Do some handling here...
						System.out.println("Message Received: " + message);
						sendToAll(message);
						Platform.runLater(new Runnable() {
							@Override public void run() {
								model.processMsg(message);
							}
						});
					}
					catch(InterruptedException e){ }
				}
			}
		};

		messageHandling.setDaemon(true);
		messageHandling.start();
	}

	private class ConnectionToClient {
		ObjectInputStream in;
		ObjectOutputStream out;
		Socket socket;

		ConnectionToClient(Socket socket) throws IOException {
			this.socket = socket;
			
			out = new ObjectOutputStream(socket.getOutputStream());

			Thread read = new Thread(){
				public void run(){
					try {
						in = new ObjectInputStream(socket.getInputStream());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					while(true){
						try{
							GameMessage obj = (GameMessage) in.readObject();
							messages.put(obj);
							System.out.println("New Message received in Server");
						}
						catch(IOException 
							| ClassNotFoundException 
							| InterruptedException e)
						{ e.printStackTrace(); }
					}
				}
			};

			read.setDaemon(true); // terminate when main ends
			read.start();
		}

		public void write(Object obj) {
			try{
				out.writeObject(obj);
			}
			catch(IOException e){ e.printStackTrace(); }
		}
	}

	public void send(Object obj) {
		GameMessage msg = (GameMessage)obj;
		sendToAll(msg);
	}
	public void sendToOne(int index, Object message)throws IndexOutOfBoundsException {
		clientList.get(index).write(message);
	}

	public void sendToAll(GameMessage message){
		for(ConnectionToClient client : clientList)
			client.write(message);
	}

	@Override
	public GameController.ClientTuple getClientTuple() {
		return null;
	}
}

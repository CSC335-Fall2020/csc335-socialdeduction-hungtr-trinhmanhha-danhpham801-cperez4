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
		clientList = new ArrayList<ConnectionToClient>();
        messages = new LinkedBlockingQueue<GameMessage>();
		try {
			serverSocket = new ServerSocket(4000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Thread accept = new Thread() {
			public void run(){
				while(true){
					try{
						Socket s = serverSocket.accept();
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
						Platform.runLater(new Runnable() {
							@Override public void run() {
								gM.processMsg(message);
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
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

			Thread read = new Thread(){
				public void run(){
					while(true){
						try{
							GameMessage obj = (GameMessage) in.readObject();
							messages.put(obj);
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

	public void sendToOne(int index, Object message)throws IndexOutOfBoundsException {
		clientList.get(index).write(message);
	}

	public void sendToAll(Object message){
		for(ConnectionToClient client : clientList)
			client.write(message);
	}
}

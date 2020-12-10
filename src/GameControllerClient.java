import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;

public class GameControllerClient extends GameController {
    private ConnectionToServer server;
    private LinkedBlockingQueue<GameMessage> messages;
    private Socket socket;

    public GameControllerClient(GameModel gM) throws IOException{
    	super(gM);
    	isServer = false;
        socket = new Socket("localhost", 4000);
        messages = new LinkedBlockingQueue<GameMessage>();
        server = new ConnectionToServer(socket);
        
        // Send info of this new player to server
        server.write(new GameMessage
        		(GameMessage.NEWPLAYER, gM.getPlayer().getName()));

        Thread messageHandling = new Thread() {
            public void run(){
                while(true){
                    try{
                    	GameMessage message = messages.take();
                        // Do some handling here...
                        System.out.println("Message Received in client: " + message);
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

    private class ConnectionToServer {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToServer(Socket socket) throws IOException {
            this.socket = socket;
            
            out = new ObjectOutputStream(socket.getOutputStream());

            Thread read = new Thread(){
                public void run(){
                	try {
						in = new ObjectInputStream(socket.getInputStream());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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

            read.setDaemon(true);
            read.start();
        }

        private void write(Object obj) {
            try{
                out.writeObject(obj);
            }
            catch(IOException e){ e.printStackTrace(); }
        }


    }

    public void send(Object obj) {
        server.write(obj);
    }

	@Override
	public GameController.ClientTuple getClientTuple() {
		return new GameController.ClientTuple(server.in, server.out, server.socket);
	}
}

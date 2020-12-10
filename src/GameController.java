public class GameController {
	protected GameModel model;
	protected boolean isServer;
	/*
	private ServerSocket server;
	private Socket connection;
	private Socket client;*/
	
	public GameController(GameModel gM) {
		model = gM;
	}
	
	///////////////////////////////////////
	////////NON-NETWORK///////////////////
	//////////////////////////////////////
	
	public boolean isGameOver() {
		return model.isGameOver()==-1;
	}
	
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

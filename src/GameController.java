import java.util.HashMap;
import java.util.Map;

public class GameController {
	private GameModel model;
	private Map<String, Player> players;
	
	public GameController(GameModel gM) {
		model = gM;
		players = new HashMap<>();
		for(Player p : model.getPlayers()) {
			players.put(p.getName(), p);
		}
	}
	
	public boolean isGameOver() {
		return model.isGameOver()==-1;
	}
	
	public void generateEvent() {
		model.generateEvent();
	}
	
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
	}
	
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
}

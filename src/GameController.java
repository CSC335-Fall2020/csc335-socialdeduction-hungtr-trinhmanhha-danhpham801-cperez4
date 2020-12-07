import java.util.ArrayList;

public class GameController {
	private GameModel model;
	
	public GameController(GameModel gM) {
		model = gM;
	}
	
	public boolean pass(EventCard c, ArrayList<Integer> playedCards) {
		c.reduce(playedCards);
		this.makeProgress(c.pass());
		return c.pass();
	}
	
	private void makeProgress(boolean pass) {
		if(pass) {
			model.pass();
		}
		else {
			model.fail();
		}
	}
	
	public void genEventCard() {
		EventCard eV = new EventCard(model.numPlayers());
		model.setCurEventCard(eV);
	}
	
	
}

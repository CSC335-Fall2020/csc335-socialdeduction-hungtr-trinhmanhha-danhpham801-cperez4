import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class DeckTest {
	private int players;
	@Test
	public void Test() {
		players = 5;
		Deck d = new Deck(players);
		while(!d.isEmpty()) {
			d.draw();
		}
		assertTrue(d.isEmpty());
		d.draw();
		assertFalse(d.isEmpty());
		
	}
}

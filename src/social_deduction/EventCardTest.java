package social_deduction;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class EventCardTest {
	int players;
	static final int MAX = 9;
	@Test
	public void Test1() {
		players = 5;
		ArrayList<Integer> al = new ArrayList<>();
		for(int p=0; p<players; p++) {
			al.add(MAX);
		}
		EventCard eC = new EventCard(players);
		eC.reduce(al);
		assertTrue(eC.pass());
	}
	
	@Test
	public void Test2() {
		players = 10;
		ArrayList<Integer> al = new ArrayList<>();
		for(int p=0; p<players; p++) {
			al.add(MAX);
		}
		EventCard eC = new EventCard(players);
		eC.reduce(al);
		assertTrue(eC.pass());
	}
	
	@Test
	public void Test3() {
		players = 100;
		EventCard eC = new EventCard(players);
		System.out.print(eC.toString());
		eC.reduce(99);
		assertFalse(eC.pass());
	}
}

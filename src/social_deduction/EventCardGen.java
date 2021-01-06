package social_deduction;
import java.util.Random;
/**
 * @author Danh Pham
 * Course: CSC 335 Fall 2020
 * Created: Dec 5, 2020
 * File: EventCardGen.java
 * Desc: the class will help generate an event card and will scale based on the 
 * 		 number of players.
 */
public class EventCardGen {
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	private int turns;
	private static int nP;

	public EventCardGen(int numPlay) {
		turns = RANDOM.nextInt(numPlay)+(numPlay/2);
		nP = numPlay;
	}
	
	// call function at the beginning of every turns
	public EventCard gen() {
		if(turns==0) {
			turns = RANDOM.nextInt(nP)+(nP/2);
			return new EventCard(nP);
		}
		turns--;
		return null;
	}
	
}

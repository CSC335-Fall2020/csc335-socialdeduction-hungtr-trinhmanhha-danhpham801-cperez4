package social_deduction;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProgressTest {
	@Test
	public void Test1() {
		ProgressBar pB = new ProgressBar();
		pB.makeProgress('p');
		System.out.println(pB.toString());
		assertTrue(pB.winner()>0);
		pB.makeProgress('f');
		pB.makeProgress('e');
		System.out.println(pB.toString());
		assertFalse(pB.winner()>0);
		assertTrue(pB.winner()<0);
		pB.makeProgress('p');
		System.out.println(pB.toString());
		assertTrue(pB.winner()==0);
	}
}

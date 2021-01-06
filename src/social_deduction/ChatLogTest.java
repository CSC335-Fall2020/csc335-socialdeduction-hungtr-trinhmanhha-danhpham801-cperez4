package social_deduction;
import java.util.Random;

import org.junit.Test;

public class ChatLogTest {
	@Test
	public void Test1() {
		Random r = new Random();
		ChatLog cL = new ChatLog();
		int say = 0;
		while(say<=50) {
			cL.put("[ "+say +" ] :"+String.valueOf(r.nextLong()));
			say++;
		}
		System.out.println(cL);
		cL.put("[ "+say +" ] : newest String to be added to the log");
		System.out.println(cL);
	}
}

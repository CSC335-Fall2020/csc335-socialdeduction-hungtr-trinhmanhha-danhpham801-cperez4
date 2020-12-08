/**
 * @author Danh Pham
 * Course: CSC 335 Fall 2020
 * Created: Dec 4, 2020
 * File: ProgressBar.java
 * Desc: the class will keep track of the current progress of the group.
 * 		 it will determine if the group have reached the goal or not.
 *       (math for the goal will need to be adjusted for real game scenario.)
 */
public class ProgressBar {
	private static final int START = 50;
	private int pass;
	private int fail;
	public ProgressBar() {
		pass = START;
		fail = START;
	}
	
	 // allow the group to make progress toward the goal
	public void makeProgress(char e) {
		if(e=='p') {
			pass++;
			fail--;
		}
		else {
			fail++;
			pass--;
		}
	}
	
	// check if the group reached the goal
	public int winner() {
		return pass-fail;
	}
	
	// give a text view of the group current progress.(percentage based)
	public String toString() {
		String ret = new String();
		ret+="|";
		for(int i=0; i<=100; i++) {
			if(i>pass) {
				ret+="-";
			}
			else {
				ret+="+";
			}
		}
		return ret+="|";
	}
	
}

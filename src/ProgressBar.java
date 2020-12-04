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
	private int totalProgress;
	private int goal;
	public ProgressBar(int players) {
		totalProgress =0;
		//for now make the goal large allowing game to continue;
		goal = players*players*9*2;
	}
	
	 // allow the group to make progress toward the goal
	public void makeProgress(int moveVal) {
		totalProgress += moveVal;
	}
	
	// check if the group reached the goal
	public boolean finish() {
		return totalProgress >= goal;
	}
	
	// give a text view of the group current progress.(percentage based)
	public String toString() {
		String ret = new String();
		ret+="|";
		int percentage = ((totalProgress)*100)/goal;
		for(int i=0; i<=100; i++) {
			
			if(i>percentage) {
				ret+=" ";
			}
			else {
				ret+="#";
			}
		}
		return ret+="|";
	}
	
}

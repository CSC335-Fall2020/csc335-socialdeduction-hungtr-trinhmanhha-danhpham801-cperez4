import java.util.Scanner;

public class misc {
	public static void main(String[] args) {
		String[] names = new String[5];
		names[0] = "Shadow";
		names[1] = "Toasty";
		names[2] = "Carlos";
		names[3] = "Em";
		names[4] = "Maritza";
		Scanner testPlayer = new Scanner(System.in);
		///////////////////////////
		GameModel model = new GameModel(names);
		String command = "";
		while(model.isGameOver() == -1) {
			System.out.println(model);
			command = testPlayer.nextLine();
			String[] commandArr = command.split(" ");
			if (commandArr[0].equals("eliminate")) {
				model.eliminate(commandArr[1].toLowerCase());
			}
		}
		System.out.println("Game Over");
		if(model.isGameOver() == 0) {
			System.out.println("Group Wins!");
		}else {
			System.out.println("Saboteur Wins!");
		}
	}
}

import java.util.Scanner;

public class misc {
	public static void main(String[] args) {
		/////////////////////////
		//sample game players
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
		//sample game loop
		while(model.isGameOver() == -1) {
			System.out.println(model.getProgress());
			System.out.println(model.getEvent());
			for(Player p: model.getPlayers()) {
				if (p.isAlive()) {
					//displays current player (this version is turned based only for testing the model)
					System.out.println(p);
					command = testPlayer.nextLine();
					String[] commandArr = command.split(" ");
					//eliminates a player
					if (commandArr[0].equals("eliminate")) {
						model.eliminate(commandArr[1].toLowerCase());
					//plays a card
					}else if(commandArr[0].equals("play")) {
						model.playCard(p.getName(), Integer.parseInt(commandArr[1]));
					//prints out the current model
					}else if(commandArr[0].equals("print")) {
						System.out.println(model.toString());
					}
				}
			}
			//resolves the event
			boolean pass = model.resolveEvent();
			if(pass) {
				System.out.println("Group succeeds in the event");
			}else{
				System.out.println("Group failed the event");
			}
			model.nextTurn();
			model.generateEvent();
			
		}
		System.out.println("Game Over");
		if(model.isGameOver() == 0) {
			System.out.println("Group Wins!");
		}else {
			System.out.println("Saboteur Wins!");
		}
	}
}


public class misc {
	public static void main(String[] args) {
		String[] names = new String[5];
		names[0] = "Shadow";
		names[1] = "Toasty";
		names[2] = "Carlos";
		names[3] = "Em";
		names[4] = "Maritza";
		///////////////////////////
		GameModel model = new GameModel(names);
		System.out.println(model);
	}
}

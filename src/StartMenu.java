import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * @author Danh Pham
 * Course: CSC 335 Fall 2020
 * Created: Dec 7, 2020
 * File: StartMenu.java
 * Desc: the class will show the initial menu pop up (could be better).
 */
public class StartMenu extends Application {
	private int port;
	private String playerName;
	private String host;
	private int limit;
	private int turns;
	
	 public void start(Stage s) {
		 s.setTitle("Start Menu");
		 TilePane root = new TilePane();
		 Button start = new Button("Start");
		 String str[] = { "Create New Server", "Join existing Sever"};
		 VBox bigOWrapper = new VBox();
		 
		 
		 VBox wrapper00P = new VBox();
		 HBox wrapper0P = new HBox();
		 Label limits = new Label("Max Players: ");
		 TextField limitsTF = new TextField();
		 limitsTF.setOnKeyReleased((event) ->{
			 this.limit = Integer.parseInt(limitsTF.getText());
		 });
		 wrapper0P.getChildren().addAll(limits, limitsTF);
		 
		 HBox wrapper1P = new HBox();
		 Label turns = new Label("Max Turns: ");
		 TextField turnsTF = new TextField();
		 turnsTF.setOnKeyReleased((event)->{
			 this.turns = Integer.parseInt(turnsTF.getText());
		 });
		 wrapper1P.getChildren().addAll(turns, turnsTF);
		 
		 wrapper00P.getChildren().addAll(wrapper0P, wrapper1P);
		 wrapper00P.setVisible(false);
		 
				
		 HBox wrapper0 = new HBox();
		 Label choice = new Label("Choose where to play: ");
		 ChoiceBox<String> cB = new ChoiceBox<String>(FXCollections.observableArrayList(str));
		 cB.setOnAction((event)-> {
			if(cB.getValue().equals("Create New Server")) {
				wrapper00P.setVisible(true);
			}
			else {
				wrapper00P.setVisible(false);
			}
		 });
		 wrapper0.getChildren().addAll(choice, cB);
		 
		 HBox wrapper1 = new HBox();
		 Label hNL = new Label("Host Name: ");
		 TextField hostName = new TextField();
		 hostName.setOnKeyReleased((event) ->{
			if(hostName.getText()!=null) {
				this.host = hostName.getText();
			}
		 });
		 wrapper1.getChildren().addAll(hNL, hostName);
		 
		 HBox wrapper2 = new HBox();
		 Label pL = new Label("Port: ");
		 TextField port = new TextField();
		 port.setOnKeyReleased((event) -> {
			this.port = Integer.parseInt(port.getText()); 
		 });
		 wrapper2.getChildren().addAll(pL, port);
		 
		 HBox wrapper3 = new HBox();
		 Label uName = new Label("Display Name: ");
		 TextField uNTF = new TextField();
		 uNTF.setOnKeyReleased((event) -> {
			 this.playerName = uName.getText();
		 });
		 wrapper3.getChildren().addAll(uName, uNTF);
		 
		 bigOWrapper.getChildren().addAll(wrapper0,wrapper00P,wrapper1,wrapper2,wrapper3,start);
		 root.getChildren().add(bigOWrapper);
	     
		 start.setOnAction((event)-> {
			 //send information to create instance of the game
			 if(cB.getValue().equals("Create New Server")) {
				 // create new game Server (this.port, this.limit, this.turns);

			 }
			 //create new game Client(this.host, this.port, this.playerName);
			 s.close();
		 });
		 Scene scene = new Scene(root, 252, 200);
		 s.setScene(scene);
		 s.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }

}

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
/**
 * @author Danh Pham
 * Course: CSC 335 Fall 2020
 * Created: Dec 7, 2020
 * File: StartMenu.java
 * Desc: the class will show the initial menu pop up (could be better).
 */
public class StartMenu extends Application {
	
	 public void start(Stage s) {
		 s.setTitle("Start Menu");
		 TilePane root = new TilePane();
		 Button start = new Button("Start");
		 String str[] = { "Create New Server", "Join existing Sever"};
		 
		 start.setOnAction((event)-> {
			 //send information to create instance of the game
		 });
		 
		 HBox wrapper0 = new HBox();
		 Label choice = new Label("Choose where to play: ");
		 ChoiceBox cB = new ChoiceBox(FXCollections.observableArrayList(str));
		 wrapper0.getChildren().addAll(choice, cB);
		 
		 HBox wrapper1 = new HBox();
		 Label hNL = new Label("Host Name: ");
		 TextField hostName = new TextField();
		 wrapper1.getChildren().addAll(hNL, hostName);
		 
		 HBox wrapper2 = new HBox();
		 Label pL = new Label("Port: ");
		 TextField port = new TextField();
		 wrapper2.getChildren().addAll(pL, port);
		 
		 root.getChildren().add(wrapper0);
		 root.getChildren().add(wrapper1);
		 root.getChildren().add(wrapper2);
		 root.getChildren().add(start);
	        
		 Scene scene = new Scene(root, 252, 100);
		 s.setScene(scene);
		 s.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }

}

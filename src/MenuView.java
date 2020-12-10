import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuView extends Stage {
	boolean isServer;
	int players;
	String name;
	boolean isCompleted;
	private Scene scene;
	private GridPane window;
	private ToggleGroup createGrp;
	private Button okButton;
	private Button cancelButton;
	TextField nameText;
	TextField playerText; // player number
	Label testField;
	

	public MenuView() {
		isServer = true;
		
		// GridPane settings
		window = new GridPane();
		window.setHgap(10);
		window.setVgap(10);
		window.setPadding(new Insets(10,10,10,10));

		// Label settings
		window.add(new Label("Create: "), 0, 0);
		window.add(new Label("UserName"), 0, 2);
		Label playerLabel = new Label("Number of players");
		playerLabel.setVisible(true);
		window.add(playerLabel, 3, 2);

		// Textfield settings
		nameText = new TextField();
		playerText = new TextField();
		playerText.setPrefColumnCount(1);
		playerText.setVisible(true);
		GridPane.setColumnSpan(nameText, 2);
		window.add(nameText, 1, 2);
		window.add(playerText, 4, 2);

		// Button settings
		okButton = new Button("OK");
		cancelButton = new Button("Cancel");
		okButton.setOnMouseClicked(e -> {
			testField.setStyle("-fx-text-fill: red");
			name = nameText.getText();
			if(name == null || name.length() < 1) {
				testField.setText("Name is invalid.");
				return; // lambda magic
			} else if (isServer){
				String nPlayersStr = playerText.getText();
				try {
					players = Integer.parseInt(nPlayersStr);
				} catch (NumberFormatException except) {
					testField.setText("Check num players");
					return;
				}
			}
			//testField.setText(nameText.getText());
			this.close();
			isCompleted = true;
		});
		cancelButton.setOnMouseClicked(e -> {
			isCompleted = false;
			this.close();
		});
		window.add(okButton, 0, 3);
		window.add(cancelButton, 1, 3);
		
		// Debug
		testField = new Label("Nothing for now");
		testField.setStyle("-fx-text-fill: black");
		window.add(testField, 0, 4);

		this.initModality(Modality.APPLICATION_MODAL);
		scene = new Scene(window, 420, 150);
		this.setScene(scene);
		this.setTitle("Game Menu");
		
		// Radios toggle settings
		initCreateGrp(playerLabel, playerText);
	}
	
	private void initCreateGrp(Label pLabel, TextField pText) {
		createGrp = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Server");
		RadioButton rb2 = new RadioButton("Client");
		rb1.setToggleGroup(createGrp);
		rb2.setToggleGroup(createGrp);
		rb1.setSelected(true);
		rb1.setOnMouseClicked(e-> {
			isServer = true;
			pLabel.setVisible(true);
			pText.setVisible(true);
		});
		rb2.setOnMouseClicked(e-> {
			isServer = false;
			pLabel.setVisible(false);
			pText.setVisible(false);
		});
		window.add(rb1, 1, 0);
		window.add(rb2, 2, 0);
	}
}

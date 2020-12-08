import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class GameView extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Cardouts");
		// mainBoard contains topBoard + bottomBoard
		BorderPane mainBoard = new BorderPane();

		// eventBoard
		BorderPane eventBoard = new BorderPane();
		eventBoard.setPrefSize(200, 300);
		setEventCard(eventBoard, 10);
		
		// playField contains card being played
		// playerHand contains the unique hand of each player
		FlowPane playField = new FlowPane();
		playField.setPrefHeight(150);
		FlowPane playerHands = new FlowPane();
		playerHands.setPrefHeight(150);

		// cardBoard contains playField and playerHands
		BorderPane cardBoard = new BorderPane();
		cardBoard.setTop(playField);
		cardBoard.setBottom(playerHands);
		
		// topBoard contains eventBoard and cardBoard
		BorderPane topBoard = new BorderPane();
		topBoard.setLeft(eventBoard);
		topBoard.setRight(cardBoard);

		// bottomBoard contains playerBoard + chatBoard
		BorderPane bottomBoard = new BorderPane();
		FlowPane playerBoard = new FlowPane();
		// Place holder for chatboard
		Rectangle chatBoard = new Rectangle();
		chatBoard.setWidth(700);
		chatBoard.setHeight(320);
		chatBoard.setFill(Color.GRAY);

		// Card played
		playField.setHgap(10);
		setCard(playField, 3);
		setCard(playField, 4);
		setCard(playField, 7);
		setCard(playField, 5);

		// Card on player hand
		playerHands.setHgap(10);
		playerHands.setVgap(10);
		setCard(playerHands, 1);
		setCard(playerHands, 2);
		setCard(playerHands, 3);
		setCard(playerHands, 4);
		setCard(playerHands, 5);

		// Create player board
		setPlayer(playerBoard, "Alice");
		setPlayer(playerBoard, "Bob");
		setPlayer(playerBoard, "Carol");
		setPlayer(playerBoard, "Dereck");
		setPlayer(playerBoard, "Eric");

		// Configure
		playerBoard.setOrientation(Orientation.VERTICAL);
		bottomBoard.setLeft(playerBoard);
		bottomBoard.setRight(chatBoard);
		mainBoard.setTop(topBoard);
		mainBoard.setBottom(bottomBoard);
		configure(mainBoard);

		Scene scene = new Scene(mainBoard, 900, 600);
		stage.setScene(scene);
		stage.show();
	}

	// Player field
	public void setPlayer(FlowPane playerBoard, String player) {
		Label name = new Label(player);
		Button vote = new Button("Vote");
		FlowPane onePlayer = new FlowPane();
		//onePlayer.setBorder(new Border(new BorderStroke(Color.BLACK));
		onePlayer.getChildren().addAll(name, vote);
		onePlayer.setPrefWidth(200);
		onePlayer.setMaxHeight(30);
		onePlayer.setHgap(10);
		onePlayer.setPadding(new Insets(10, 50, 10, 20));
		onePlayer.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
		playerBoard.getChildren().add(onePlayer);
	}

	// Put a card of certain value on specified location
	public void setEventCard(BorderPane placement, int cardValue) {
		Rectangle card = new Rectangle();
		card.setFill(Color.BLACK);
		card.setStroke(Color.RED);
		card.setWidth(120);
		card.setHeight(180);
		Text text = new Text(" " + cardValue);
		text.setFill(Color.RED);
		text.setFont(new Font(100));
		text.setBoundsType(TextBoundsType.VISUAL);
		StackPane cardWithText = new StackPane();
		cardWithText.getChildren().addAll(card, text);
		placement.setCenter(cardWithText);
	}

	// Put a card of certain value on specified location
	public void setCard(FlowPane placement, int cardValue) {
		Rectangle card = new Rectangle();
		card.setFill(Color.WHITE);
		card.setStroke(Color.BLACK);
		card.setWidth(60);
		card.setHeight(90);
		Text text = new Text(" " + cardValue);
		text.setFill(Color.BLACK);
		text.setFont(new Font(50));
		text.setBoundsType(TextBoundsType.VISUAL);
		StackPane cardWithText = new StackPane();
		cardWithText.getChildren().addAll(card, text);
		placement.getChildren().add(cardWithText);
	}

	//
	public void configure(BorderPane mainBoard) {
		mainBoard.setPadding(new Insets(10, 10, 10, 10));
		mainBoard.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
	}
}

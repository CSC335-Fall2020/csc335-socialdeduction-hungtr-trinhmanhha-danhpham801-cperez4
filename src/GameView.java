import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class GameView extends Application implements Observer {
	private GameModel model;
	private GameController ctr;
	BorderPane mainBoard;
	BorderPane bottomBoard; // Child of mainBoard
	FlowPane playerBoard;   // Child of bottomBoard
	
	@Override
	public void update(Observable o, Object arg) {
		GameMessage msg = (GameMessage) arg;
		if(msg.enoughPlayer) {
			mainBoard.setVisible(true);
			for(String s : msg.nameList) {
				setPlayer(playerBoard, s);
			}
			if(ctr.isServer) 
				((GameControllerServer) ctr).sendToAll(msg);
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Cardouts");
		
		// Show a menu for necessary info from players
		MenuView menu = new MenuView();
		menu.showAndWait();
		
		if(!menu.isComplete) stage.close();
		
		// Use info acquired from menu to setup
		if(menu.isServer) {
			model = new GameModelServer(menu.name);
			model.addObserver(this);
			((GameModelServer) model).addPlayer(menu.name);
			ctr = new GameControllerServer(model);
			stage.setTitle("Cardouts Server");
		}
		else { // is client
			model = new GameModel(menu.name);
			model.addObserver(this);
			ctr = new GameControllerClient(model);
			stage.setTitle("Cardouts Client");
		}
		
		// mainBoard contains topBoard + bottomBoard
		mainBoard = new BorderPane();
		mainBoard.setVisible(false);

		// eventBoard
		BorderPane eventBoard = new BorderPane();
		eventBoard.setPrefSize(200, 300);
		setEventCard(eventBoard, 10);
		
		// playField contains card being played
		FlowPane playField = new FlowPane();
		playField.setPrefHeight(150);
		playField.setPadding(new Insets(30, 50, 10, 50));
		playField.setHgap(10);
		playField.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
		
		// playerHand contains the unique hand of each player
		FlowPane playerHands = new FlowPane();
		playerHands.setPrefHeight(150);
		playerHands.setPadding(new Insets(20, 100, 20, 100));
		playerHands.setHgap(20);
		playerHands.setVgap(20);
		playerHands.setBackground(new Background(new BackgroundFill(Color.TEAL, null, null)));


		// cardBoard contains playField and playerHands
		BorderPane cardBoard = new BorderPane();
		cardBoard.setTop(playField);
		cardBoard.setBottom(playerHands);
		
		// topBoard contains eventBoard and cardBoard
		BorderPane topBoard = new BorderPane();
		topBoard.setLeft(eventBoard);
		topBoard.setCenter(cardBoard);

		// bottomBoard contains playerBoard + chatBoard
		bottomBoard = new BorderPane();
		playerBoard = new FlowPane();
		playerBoard.setOrientation(Orientation.VERTICAL);
		// Place holder for chatboard
		GChatBox chatbox = new GChatBox();
		Object chatClient = null;
		Player thisPlayer = null;
		BorderPane chatBoard = 
				chatbox.makeInstance(700, 250, chatClient, thisPlayer);
		


		// Configure
		bottomBoard.setLeft(playerBoard);
		bottomBoard.setRight(chatBoard);
		mainBoard.setTop(topBoard);
		mainBoard.setBottom(bottomBoard);
		configure(mainBoard);
		
		///////////////////////////////////////////////////////
		//////////////INITIAL SETUP ENDS HERE//////////////////
		///////////////////////////////////////////////////////
		

		// Card played
		setCard(playField, 3);
		setCard(playField, 4);
		setCard(playField, 7);
		setCard(playField, 5);

		// Card on player hand
		ArrayList<Integer> currentHand = model.getPlayer().getHand();
		for(Integer i : currentHand) {
			setCard(playerHands, i);
		}
		
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

	// Put an event card of certain value on specified location
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
		card.setOnMouseClicked(e -> {
			if(ctr.isServer);
		});
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

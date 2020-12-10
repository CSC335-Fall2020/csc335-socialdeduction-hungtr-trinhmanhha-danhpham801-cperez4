import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	ArrayList<Button> voteButtons;
	Label footNote;
	BorderPane mainBoard;
	BorderPane eventBoard;
	FlowPane playField;
	BorderPane bottomBoard; // Child of mainBoard
	FlowPane playerBoard;   // Child of bottomBoard
	
	@Override
	public void update(Observable o, Object arg) {
		GameMessage msg = (GameMessage) arg;
		
		// First run when there's enough player
		if(msg.enoughPlayer && !mainBoard.isVisible()) {
			mainBoard.setVisible(true);
			for(String s : msg.nameList) {
				setPlayer(playerBoard, s);
				setEventCard(eventBoard, msg.eventVal);
			}
			//toggleButtons();
			if(ctr.isServer) 
				((GameControllerServer) ctr).sendToAll(msg);
		}
		
		// Check if an event succeeds or fails
		else if(msg.eventCheck) {
			if(msg.eventPassed) {
				playField.setBackground(
						new Background(
								new BackgroundFill(
										Color.LIGHTGREEN, null, null)));
				playerBoard.setDisable(false);
			}
			else 
				playField.setBackground(
						new Background(
								new BackgroundFill(
										Color.RED, null, null)));
			if(ctr.isServer) 
				((GameControllerServer) ctr).sendToAll(msg);
			model.enterVotingPhase();
		}
		
		// Run on new card played
		else if(msg.playCard) {
			setCard(playField, msg.latestCard);
			if(msg.enoughCard) {
				playField.setVisible(true);
				if(ctr.isServer) 
					((GameModelServer) model).resolveEvent();
			}
		}
		
		// Run on setting who is the traitor
		else if(msg.traitorSet) {
			if(ctr.isServer) {
				if(msg.playerID == -1) footNote.setText("You are Traitor");
				else ((GameControllerServer) ctr).sendToOne(msg.playerID, msg);
			}
			else footNote.setText("You are traitor");
		}
		
		// Determine a person to eliminate
		else if(msg.eliminate) {
			if(ctr.isServer) {
				if(msg.playerID == -1) {
					getEliminated();
				}
				else ((GameControllerServer) ctr).sendToOne(msg.playerID, msg);
				checkGameOver(((GameModelServer) model).checkWin());
			}
			else getEliminated();
		}
	}
	
	// End the game and notify the player if the game is over
	public void checkGameOver(int gameStatus) {
		if(gameStatus == 0) return;
		Alert noti = new Alert(AlertType.INFORMATION);
		if(gameStatus == 1) {
			noti.setContentText("Traitor eliminated! Players win!");
		}
		else if(gameStatus == -1) {
			noti.setContentText("2 players left. Traitor win!");
		}
		
		noti.showAndWait();
	}
	
	// Set the screen to notify a person is eliminated
	public void getEliminated() {
		footNote.setText("You are eliminated");
		mainBoard.setDisable(true);
		mainBoard.setBackground(new Background(
								new BackgroundFill(
										Color.RED, null, null)));
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
		
		// Voting button that would be implemented later
		voteButtons = new ArrayList<>();
		
		// Footnote for special noting
		footNote = new Label("");
		
		// mainBoard contains topBoard + bottomBoard
		mainBoard = new BorderPane();
		mainBoard.setVisible(false);

		// eventBoard
		eventBoard = new BorderPane();
		eventBoard.setPrefSize(200, 300);
		
		
		// playField contains card being played
		playField = new FlowPane();
		playField.setVisible(false);
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
		//playerBoard.setDisable(true);
		// Place holder for chatboard
		Rectangle chatBoard = new Rectangle();
		chatBoard.setWidth(700);
		chatBoard.setHeight(320);
		chatBoard.setFill(Color.GRAY);


		// Configure
		bottomBoard.setLeft(playerBoard);
		bottomBoard.setRight(chatBoard);
		mainBoard.setBottom(footNote);
		mainBoard.setTop(topBoard);
		mainBoard.setCenter(bottomBoard);
		configure(mainBoard);
		
		///////////////////////////////////////////////////////
		//////////////INITIAL SETUP ENDS HERE//////////////////
		///////////////////////////////////////////////////////

		// Card on player hand
		ArrayList<Integer> currentHand = model.getPlayer().getHand();
		for(Integer i : currentHand) {
			setCard(playerHands, i);
		}
		
		Scene scene = new Scene(mainBoard, 900, 700);
		stage.setScene(scene);
		stage.show();
	}

	// Player field
	public void setPlayer(FlowPane playerBoard, String player) {
		Label name = new Label(player);
		Button vote = new Button("Vote");
		vote.setOnMouseClicked(e -> {
			if(!model.isVotingPhase) {
				e.consume();
				return;
			}
			if(ctr.isServer) {
				model.processMsg(new GameMessage
						(GameMessage.VOTING, player));
			}
			else {
				((GameControllerClient) ctr).send(new GameMessage
						(GameMessage.VOTING, player));
			}
			playerBoard.setDisable(true);
		});
		voteButtons.add(vote);
		FlowPane onePlayer = new FlowPane();
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
		Text text = new Text(" " + cardValue);
		text.setFill(Color.BLACK);
		text.setFont(new Font(50));
		text.setBoundsType(TextBoundsType.VISUAL);
		StackPane cardWithText = new StackPane();
		cardWithText.getChildren().addAll(card, text);
		placement.getChildren().add(cardWithText);
		card.setOnMouseClicked(e -> {
			placement.getChildren().remove(cardWithText);
			placement.setDisable(true);
			if(ctr.isServer) {
				model.player.play(cardValue);
				GameMessage toSend = new GameMessage
						(GameMessage.PLAYCARD, cardValue);
				model.processMsg(toSend);
				((GameControllerServer) ctr).sendToAll(toSend);
				
			}
			else {
				model.player.play(cardValue);
				((GameControllerClient) ctr).send(new GameMessage
						(GameMessage.PLAYCARD, cardValue));
			}
		});
	}

	//
	public void configure(BorderPane mainBoard) {
		mainBoard.setPadding(new Insets(10, 10, 10, 10));
		mainBoard.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
	}

	
}

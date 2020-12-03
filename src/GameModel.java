import java.util.ArrayList;

/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Dec 2, 2020
 * File: GameModel.java
 * Desc: This file is a model class for the social deduction card-game
 */
/**
 * Game description (Modified):
 * It's going to be a card game where the main objective is to reach the end
 * or find the saboteur. Each player is given 5 number cards at random and
 * the number will determine how much progress the group makes towards the
 * goal. There would be an event that pops up at a random time with a random
 * total value that the group will have to match or exceed by putting down
 * high number cards. 
 * 
 * [Strategy]
 * Generally, the saboteur will want to play lower cards
 * to delay the group from the goal; they, however, may gain trust by playing
 * high number cards in the beginning. There will be a time limit to how long
 * the game will last. The saboteur will have to survive till time runs out
 * or the good group is reduced to 1 or 2 players. 
 * 
 * [Mechanics]
 * It will play like a combination of UNO and Cards Against Humanity.
 * Event cards are different color then player cards.
 * Failure to meet the required amount in the event will somehow benefit the saboteur. {
 * 	Hung Tran: A murder card, maybe?
 * }
 * 
 * [Voting the saboteur]
 * Each player puts down 1 card each turn, each player has a pause button to debate 
 * who the saboteur is. The pause will have a global cooldown for every player 
 * in the game. 
 */
public class GameModel {
	int players; // The number of players
	int progress; // The current progress of the gameboard, reach goal to win
	int goal; // Self-explained
	ArrayList<Card> hands; // The current hand of player
	Card latest; // The latest card that has been played to the board
}

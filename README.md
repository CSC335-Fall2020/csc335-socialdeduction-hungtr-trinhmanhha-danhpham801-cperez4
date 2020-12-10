# csc335-socialdeduction-hungtr-trinhmanhha-danhpham801-cperez4
csc335-socialdeduction-hungtr-trinhmanhha-danhpham801-cperez4 created by GitHub Classroom

How to Play:
Each player chooses a display name and joins the server

Each player is dealt a random hand, these cards are used to deal with the EventCard (the black and red card on the screen)
One player is the Saboteur wants the Group to lose as many turns as possible, but no one in the Group knows who the saboteur is.

Each turn a new Event Card is created. All players must play a card in order to deal with the event.

If the Group succeeds (total number of played cards is greater than the event card value) in the event 
then they are given the choice to eliminate one other player form the game. Player is eliminated through majority vote.

If the Group fails in the event (total number of played cards is less than or equal to the event card value)
then the Saboteur gets to eliminate anyone they want from the game.

Win Conditions:
Group Victory - Saboteur is eliminated, More events won than lost at the end of the game
Saboteur Victory - Only one member of the Group + Saboteur is alive, More failed events than won at the end of the game.

Limitation:
Unfortunately, due to being short on time, the game is in an incomplete state. It will still run, although not every gameplay elements described above is available.
What is in this build:
- A menu that will help create servers and connecting clients as players
- The main screen will be blank until there is enough players joining.
- On the main screen, the traitor will have the text "you are traitor" on the bottom of their screen
- A chatbox should also be available on the bottom right of the main screen
- Select the card from second row to play that card. All the played cards wont show until everyone had each played a card.
- After the played cards are shown, the background would either be green (event passed) or red (event failed)
- After event is resolved, the vote button should be able to be clicked on. If you clicked on it before, nothing will happen. But if you click on them once now,
all buttons will be automatically grayed out.
- After every vote is casted. The person with the most voted is eliminated and their screen becomes red. If the eliminated is the traitor, then player win message shows.
- If it isn't a traitor, the game is supposed to continue to round 2 with everything repeated. This is where the code stopped working, as we didn't implement it.

What is not in this build:
- As stated above, we couldn't implement round onward. Everything will stop after the first round.
- The freedom to choose the amount of players also isn't there. We had defaulted the game to 4 players. The game wont work unless you create 4 instances of game 
(with 1 server and 3 clients)

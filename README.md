### Peer-to-peer tic-tac-toe

Project for Computer Networking, fall 2016, Shahid Beheshti University.

For every pair of players, one should host the game (game server) and the other one can be
a free-rider! 

A tracker server will manage the system, and initially all players connect to the tracker. 
The tracker will know a list of game servers and free-riders ready to play.
Once a free-rider player selects an opponent, the tracker
will connect the two. The further requests for each move of the game will not pass through tracker 
anymore and it directly between the two players. The game server player will do the calculations 
of the game.


To play the game (obviously doesn't make much sense on one machine!):

**1.** Run the tracker server in Tracker_jar directory.

![](https://raw.githubusercontent.com/AsalJalilvand/peer_to_peer_tic_tac_toe/master/screenshots/tracker.PNG)

**2.** In the Peer_jar, each player will run the peer.jar file. Each player must select
a role, game server or free-rider. 

![](https://raw.githubusercontent.com/AsalJalilvand/peer_to_peer_tic_tac_toe/master/screenshots/peer.PNG)

![](https://raw.githubusercontent.com/AsalJalilvand/peer_to_peer_tic_tac_toe/master/screenshots/before-connection.PNG)

**3.** The free-rider will select an opponent from the game servers list ready to play (click New Game).

![](https://raw.githubusercontent.com/AsalJalilvand/peer_to_peer_tic_tac_toe/master/screenshots/server-list.PNG)

**4.** Tracker will connect the two players.

![](https://raw.githubusercontent.com/AsalJalilvand/peer_to_peer_tic_tac_toe/master/screenshots/after-connection.PNG)

**5.** Play!

![](https://raw.githubusercontent.com/AsalJalilvand/peer_to_peer_tic_tac_toe/master/screenshots/playing.PNG)


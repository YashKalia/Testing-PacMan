## Assignment 3

#### Exercise 1

 
#### Exercise 2


#### Exercise 3  

For scenario 2.4. it's harder to create system test cases because :

   Ghosts move around and the direction in which they
   move depend on their nextAIMove() method, thus we have less 
   controllability for getting/preparing/setting the right state
   that is specified in the scenario 2.4 (a test case where the player is
   next to a cell containing). It's hard to get a state where the player is next to a ghost,
   and even if the player is next to a ghost,
   we don't know if the ghost stays in it's square (depends on the specific ghost 
   (do we have Iny or Clyde etc? They have different movements), is it always guaranteed to be the same ghost?)
   when the player has moved to the square where the ghost is assumed to be. 
   Furthermore, there are different ghosts
   each with different implementations of nextAIMove() which further decreases 
   our controllability and repeatability of the test. 
   
For scenario 2.5. it's harder to create system test cases because :

If we start the test case where the level/map : 
1. still contains (more than) one pellet
2. has big with and length with lot of walls and differently shaped paths
etc,

then we will have to eat all pellets except the last pellet,
 find the exact location of the last pellet, while avoiding 
 collision with all the different ghost (each move differently) etc, 
 just so that we have the right condition/state needed for scenario 2.5.
 Thus, we can conclude that there is a lack of controllability which makes testing
 this scenario difficult (if using the provided level that is made using the  provided board.txt)
 
#### Exercise 4


#### Exercise 5

All the scenarios in user story 3 are hard to test because:
We don't have control over when "a tick event" occurs and 
it is hard to bring the game to the exact states that are specified in 
the "Given ( + and)" clauses of the scenarios  (Thus, a lack of controllability).
The explanation about the movement of ghost which makes scenario 2.4 (see that part in answer to exercise 3) 
difficult to test is also a valid reason for why writing test conditions for the scenario's of user story 3 
is difficult. 

#### Exercise 6

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/589122154134503455/unknown.png">



#### Exercise 9


#### Exercise 10

This is the user story we came up with for dealing with new levels 
(can also be found in doc/scenarios.md but that's not in the src folder):


As a player,
 I want to be able to play a game with multiple levels;
So  that I can spend more time on JPacMan.

Story 5 : Next Levels

Scenario 5.1
Given I have won a level of the game,
and   the level that is won is not the last playable level;
When  I click start/nextLevel
Then  The next playable level will be available for starting

Scenario 5.2
Given I have won the the last level of the game;
When  I click start button,
Then  No new levels will be available for playing.

#### Story 5 : Next Levels

```
As a player,
 I want to be able to play a game with multiple levels;
So  that I can spend more time on JPacMan.

Scenario 5.1
Given I have won a level of the game,
and   the level that is won is not the last playable level;
When  I click start/nextLevel
Then  The next playable level will be available for starting

Scenario 5.2
Given I have won the the last level of the game;
When  I click start button,
Then  No new levels will be available for playing.
```



#### Exercise 12


#### Exercise 13


#### Exercise 14


#### Exercise 15


#### Exercise 16


#### Exercise 17


#### Exercise 18


#### Exercise 19


#### Exercise 20


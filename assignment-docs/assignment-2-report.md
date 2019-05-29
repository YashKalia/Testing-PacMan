## Assginment 2 report

#### Exercise 4
Decision table :

<img src = "https://media.discordapp.net/attachments/546026199197941775/582917845260697600/unknown.png">

continuation the the decision table:

<img src = "https://media.discordapp.net/attachments/546026199197941775/582917937459888128/unknown.png?width=1017&height=202">



#### Exercise 5

We think that the collisions where "nothing should happen" and/or shouldn't happen at all  are (see decision table at exercise 4) :
 1. pellet collide with pellet (shouldn't happen at all)
 2. ghost collide with a ghost ("nothing should happen")
 3. player collide with a player (if playing multiplayer game) or (shouldn't happen at all in single player game)
 4. ghost collide with pellet ("nothing should happen")
 5. pellet collide with a ghost (shouldn't happen at all, because pellet should be able to move at least it isn't specified in user stories)
 
for the 5 collisions that shouldn't happen (see exercise 5) : because it is not specified in the user stories what should happen,
we have just tested what we assumed should/ shouldn't happen.

 #### Exercise 6
 
 We have made an abstract test class from which 2 other test classes 
 (one for PlayerCollisions and DefaultPlayerInteractionMap )inherit from 
 the collide method in both one for PlayerCollisions and DefaultPlayerInteractionMap are tested,
 by doing so all other methods in those classes are also tested indirectly according to
 the coverage report that IntelliJ gives. This test class can be found in the test folder at:
 java/nl.tudelft.jpacman/level/CollisionMapTest
  
 #### Exercise 7
 
 
 #### Exercise 14
 After we have change the PointCalculator of the game we have noticed the following strange behaviour of the game :
 1. After some time playing the game we have noticed the following runtime exception in the terminal : "Relax! You found one of the solutions!".
 2. While playing the game we have also noticed that at some point the player score start to decrease instead of increasing when eating a pellet.
 3. While playing the game the player suddenly dies/game over eventhough the player has not even collided with any ghosts.
 4. While playing the game at the beginning the score of the player increases a lot (around 2.1 billion) when the player's direction is West.
  
 #### Exercise 15 
 
 
 
 
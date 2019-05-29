## Assginment 2 report

#### Exercise 4
Decision table :

<img src = "https://media.discordapp.net/attachments/546026199197941775/582917845260697600/unknown.png">

continuation the the decision table:

<img src = "https://media.discordapp.net/attachments/546026199197941775/582917937459888128/unknown.png?width=1017&height=202">



#### Exercise 5

We think that the collisions where "nothing should happen" and/or shouldn't happen at all  are (see decision table at exercise 4) :
 1. pellet collide with pellet
 2. ghost collide with a ghost
 3. player collide with a player (if playing multiplayer game)
 4. ghost collide with pellet
 5. pellet collide with a ghost 
 
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
 
 
####Exercise-6

The two classes that are not well tested are PacmanConfigurationException and JPacmanfuzzer.The JPacmanfuzzer class is annotated with the @Disabled tag, therefore, all methods in it are disabled. So it is not covered in the LauncherSmokeTest coverage.Furthermore,the CollisionInteractionMap class and the
DefaultPlayerInteractionMap class are detailed classes and therefore there is no point testing them in the LauncherSmokeTest since they are not a critical part of the minimal system behavior.

####Exercise-7

Yes, it is covered in the smoke test (see image below the move method is hit once). The LauncherSmokeTest fails because if we comment out the line of code that invokes the move method in the Level class then
the unit never moves in the new direction and does not gain the points that he would have gained if he had gained-that is why the assertion failed as it was
expecting the score to now be equals to a 10 but it was still a 0. Given that the test assertion fails it implies that either the points/score of the unit is
not adding up properly or that the unit did not move on the board as expected.

<img src = "https://media.discordapp.net/attachments/546026199197941775/573954893296041990/SQT_exercise_7.PNG">

####Exercise-8

After making the getDeltaX method return dy instead of dx, running the LauncherSmokeTest with Jacoco coverage fails.
The coverage lets us see that the getDeltaX method is hit by the LauncherSmokeTest.

<img src = "https://media.discordapp.net/attachments/546026199197941775/573955038901305345/SQT_exercise_8_-_1.PNG"> 

We can also see that the createBoard method which calls getDeltaX in BoardFactory is hit. 
It seems that in the process of creating a board in the test, 
squares on the board will not have the correct neighbours, see line 49 of createBoard method (because getDeltaX returns dy). 

<img src = "https://media.discordapp.net/attachments/546026199197941775/573955077119803392/SQT_exercise_8_-_2.PNG" length = 75>

This causes the actual points to be 0 instead of the expected 10 which is the reason  why the test fails. 
The problem is easier to understand with the help of Jacoco and IntelliJ 
because with them we can check what methods are affected by the change in getDeltaX method.  

####Exercise-9

The board class has a double array of "Square" to simulate a board. 
A unit object can occupy at most one of the squares in the double array of Squares which is a field of board object.
Furthermore a unit object can also unoccupy a Square and to occupy another neighbouring Square. 
The extensions of Unit class are Player, Pellet and Ghost all extensions of the Ghost class.
The Level class has the following interesting attributes: 
"board" - which is a Board object which is used to check remaining pellets on the board of a Level object in remainingPellets() method.
"npcs" - which is a Map<Ghost, ScheduledExecutorService> is used for the movement of Ghost objects in stopNPCs() and startNPCs() methods.
"players" - which is List<Player> and is used for checking if a player is alive and registering a player in a Level object in isAnyPlayerAlive() and registerPlayer(Player player) methods.
The move() method in Level lets unit Object move on the board belonging to a level(npc and player) to another neighbouring Square.
The method move() in the Game class allows for moving a Player object which is an extension of Unit class.
All classes that extend from abstract Game class have an attribute which is an instance of the Level class 
and override the abstract getLevel method from Game class.

####Exercise-10

  1. Good weather cases.
  
  1.1 When Clyde is far from pacman's location-then he must move towards pacman's current location.
  
  1.2 When Clyde is close to pacman's location-then he must run away from pacman.
  
  2. Bad Weather Cases-
  
  2.1 When Clyde is far from pacman's location-then he must not  move away pacman's current location.
  
  2.2 When Clyde is close to pacman's location-then he must not move towards pacman.  
  
  3. Clyde extra bad weather cases-
  
  3.1 If Clyde is not on board then the findUnitInBoard method  in Navigation class return a null object.
  
  3.2 If the player is not on board then the nextAi move method cannot find a path and returns Optional.empty.
  
  3.3 If there is not path between Clyde and the Player then the nextAi move method cannot find a path and returns Optional.empty.
 


####Exercise-11

Part-2 
To make sure that the Optional<Direction> nextAiMove() method of Inky object works, the following *requirements/conditions* need to be met:

  1. a Player is on the same Board as Inky
  
  2. Blinky is on  the same  Board as Inky
  
  3. There exists a (shortest and not null) path that can be taken to get to the square that is 2 Squares of the Player from 
     the square that Blinky is occupying.
     
  4. There exists a (shortest and not null) path that Inky can take to the *destination* that is supposed to go to 
     (see image below for information about the *destination*) 

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/574250657280950273/SQT_a1e11.PNG" length = 75>

Some good weather behaviour cases are:

  1. All of the *requirements/conditions* are met and Inky is alongside Blinky, while Blinky is behind Player. 
     The expected behaviour of Inky is that Inky should move towards Player.
     
  2. All of the *requirements/conditions* are met and Inky is ahead of Player, while Blinky is behind Player.
     The expected behaviour of Inky is that Inky should move away Player

Bad weather behaviour cases are all cases where at least one or all of the conditions 1 upto and including 4 
from from *requirements/conditions* are False. :

Also some other bad weather cases :

  1. All of the *requirements/conditions* are met and Inky is alongside Blinky, while Blinky is behind Player. 
     The expected behaviour of Inky is that Inky should not move away from Player but try to approach player.
     
  2. All of the *requirements/conditions* are met and Inky is ahead of Player, while Blinky is behind Player. 
     The expected behaviour of Inky is that Inky should not move towards Player but move away from player.
     
  3. Case where Blinky is not on board.
  
  4. Case where Inky is not on board.
  
  5. Case where Player is not on board.
  
  6. Case where there is no path from Inky to the destination where Inky should be.
  
  7. Case where Inky is already on the destination where Inky should be.
  
   ####Excercise-12
   Width and height are 5 and 5 respectively.
   <img src="https://cdn.discordapp.com/attachments/546026199197941775/578578393054838795/unknown.png">
   

   ####Exercise-14

One solution is to add class attributes in the test class and use the @BeforeEach annotation and the setUp() method. 
The code that you see that is repeated in the Arrange part of tests can be in the setUp method definition which will be invoked before each test.

Another solution is to define some helper-function without @BeforeEach in the test class that handles the specific Arrange part of certain unit tests which will be invoked at the start of unit tests. The advantage of this: you can choose which tests will invoke this helper method, 
so you have more control than when using @BeforeEach. The disadvantage: there is minimal repetition in calling the helper method in tests.

   ####Exercise-15

The goal of unit tests is to test each component in isolation. 
You want clean instances for each test, so each of them should not depend on each other and not have to work with possibly changed test data.
So the tests shouldn't be interfered by, but should be independent of another test. 
This can be achieved by using the setUp() method to instantiate clean instances.  
A consequence of this independence is that tests will be more repeatable: it always returns the same result if you do not change anything in between runs.   
Furthermore, using setUp() helps to minimize code repetition as mention in the answer to exercise 14.


####Exercise-16

When a test fails assertEquals gives an error message that says that a test failed because an expected value/instance differs from 
what was the actual value/instance, which is more useful for debugging than the error message that assertTrue gives: expected True but was False.

Another advantage of assertEquals is that you can use it for both primitive and reference types, 
while for assertTrue there is the risk that you might use ""=="" instead of equals() for comparing reference types which might be wrong.

####Exercise-17

The private method in a class are called within public methods, 
so when you test public methods you are indirectly testing private methods. 
In most cases, there should not be a need to test a private method. 
Private methods are an implementation detail so usually private methods shouldn't exist in isolation. 
A private method is an implementation detail that should be hidden to the users of the class and cannot be called in a test class. 
Testing private methods breaks encapsulation. If private methods have enough complexity that must be tested, 
we should put them in another class to keep high cohesion, a class should have only one purpose.


####Excercise-18

All checkstyle and Spotbug violations were fixed with suppressions.We added tests classes for ghosts Clyde and Inky for the "nextAimove()" method,and also
we tested the boundaries of "withinBoarders()" method of the Board class using parameterized test annotation and domain tesing strategy.We tried to work on a seperate branch
for each excercise and then merge the branch with the Asignment-1 branch. The pipeline failed for some commits and merge requests because of the checkstyle violations because we forgot
to run gradle check beforehand.We commited 3 times on average per excercise and the work was evenly distributed as much as possible. 



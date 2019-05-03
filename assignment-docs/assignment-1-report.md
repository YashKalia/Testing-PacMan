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

Part-2
  Good Weather Cases-
  1. When Clyde is far from pacman's location-then he must move towards pacman's current location.
  2. When Clyde is close to pacman's location-then he must run away from pacman.
  
  Bad Weather Cases-
  1. When Clyde is far from pacman's location-then he must not move away pacman's current location.
  2. When Clyde is close to pacman's location-then he must not move towards pacman.


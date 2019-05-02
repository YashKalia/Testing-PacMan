Excercise-6

The two classes that are not well tested are PacmanConfigurationException and JPacmanfuzzer.The JPacmanfuzzer class is annotated with the @Disabled tag
therefore all methods in it are disabled.So it is not covered in the LauncherSmokeTest coverage.Furthermore,the CollisionInteractionMap class and the
DefaultPlayerInteractionMap class are detailed classes and therefore there is no point testing them in the LauncherSmokeTest since they are not a 
critical part of the minimal system behavior.

Excercise-7
Yes it is covered in the smoke test.The smokeTest fails because if we comment out the line of code that invokes the move method in the Level class then
the unit never moves in he new direction and does not gain the points that he would have gained if he had gained-that is why the assertion failed as it was
expecting the score to now be equals to a 10 but it was still a 0.Given that the test assertion fails it implies that either the points/score of the unit is
not adding up properly or that the unit dud not move on the board as expected.

Excercise-10
Part-2
  Good Weather Cases-
  1.When Clyde is far from pacman's location-then he must move towards pacman's current location.
  2.When Clyde is close to pacman's location-then he must run away from pacman.
  
  Bad Weather Cases-
  1.When Clyde is far from pacman's location-then he must not  move away pacman's current location.
  2.When Clyde is close to pacman's location-then he must not move towards pacman.


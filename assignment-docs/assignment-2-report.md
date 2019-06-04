## Assginment 2 report

#### Exercise 4

Decision table :

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/585505329186341001/unknown.png">

continuation the the decision table:

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/585505500314206225/unknown.png">

#### Exercise 5

We think that the collisions where "nothing should happen" and/or shouldn't happen at all  are (see decision table at exercise 4) :
 1. pellet collide with pellet (shouldn't happen at all)
 2. ghost collide with a ghost ("nothing should happen")
 3. player collide with a player (if playing multiplayer game) or (shouldn't happen at all in single player game)
 4. ghost collide with pellet ("nothing should happen")
 5. pellet collide with a ghost (shouldn't happen at all, because pellet should be able to move at least it isn't specified in user stories)
 
 Because pellets shouldn't be able to move (according to user stories) we have not included it as a collider
 in the decision table. Hence, we don't have test cases where the pellet is a collider.

 #### Exercise 6
 
 We have made an abstract test class from which 2 other test classes 
 (one for PlayerCollisions and DefaultPlayerInteractionMap )inherit from 
 the collide method in both one for PlayerCollisions and DefaultPlayerInteractionMap are tested,
 by doing so all other methods in those classes are also tested indirectly according to
 the coverage report that IntelliJ gives. This test class can be found in the test folder at:
 java/nl.tudelft.jpacman/level/CollisionMapTest
  
 #### Exercise 7
 
 
 #### Exercise 9 

 We see "Thread.sleep(500L)" in the smokeTest which make the test flaky,
 because it is possible that we have to wait more than "500L" such that what is waited for is actually done executing. 
 A solution for this can be to constantly check if the action we're waiting for is done.
 Other causes for flakiness are : 
 1. tests that are too large (uses much memory) (solved by making tests use less memory when possible)
 2. when tests have  to be executed in certain order to pass (solved by making test independent and isolated)
 3. tests are usually flaky because incorrect assumptions about the ordering of operations being performed by different threads
  (solved by enforcing certain orders instead of assuming)   
  
 #### Exercise 11
 When we use mocks for integration testing, mocks may oversimplify and tend to be unrealistic e.g. 
 integration problems might pass because they were oversimplified thus giving a false sense of code quality.
 Integration of the system-under-test with its collaborators is might not be properly tested as a result,
 the individual components may work as expected, while the interaction between them may still be flawed.
 Another disadvantage is that strong coupling of mocks with the class interferes/complicate with refactoring, 
 since implementation changes are much more likely to break tests that use mocks.

 
 #### Exercise 13
 Mocking is a technique that is used to make the relationships and interactions between objects visible.
 We want to mock dependencies to improve controllability and observability when testing a class in isolation and also mock
 interfaces that specify the business rules that our program uses.
 We shouldn’t  mock classes that are concrete implementations of domain rules of the system
 because maintaining the behavior of the mock compatible with the behavior of original class is hard 
 and that mocking increases the coupling between the test and the production code, which means that 
 Mocks will have to be changed to suit the changes of the concrete implementation (you may or may not have full control over it e.g third-party libraries).

 
  
 
 #### Exercise 14
 After we have change the PointCalculator of the game we have noticed the following strange behaviour of the game :
 1. After some time playing the game we have noticed the following runtime exception in the terminal : "Relax! You found one of the solutions!".
 2. While playing the game we have also noticed that at some point the player score start to decrease instead of increasing when eating a pellet.
 3. While playing the game the player suddenly dies/game over eventhough the player has not even collided with any ghosts.
 4. While playing the game at/around the beginning the score of the player,
  decreases a lot (around 2.1 billion) when the player's eats a pellet in direction is West, if the player eats another pellet again in the West direction
  the point become positive again. 
  
 #### Exercise 15 
 
 For this exercise we have quite a lot of snapshots so we advise our reviewer to two open two copies of this pdf to ease reviewing.
 
 First some snapshots of the plotLogs using defaultPointCalculator :
 
 DefaultCalculator Snapshot 1 (figure 1):
 
 <img src = "https://media.discordapp.net/attachments/546026199197941775/583563525524422657/unknown.png">
 
 
 DefaultCalculator Snapshot 2 (figure 2):
 
 <img src = "https://media.discordapp.net/attachments/546026199197941775/583563895185342483/unknown.png?width=913&height=676">
 
 
 Now we have some snapshots of the plotLogs of AmazingPointCalculator :
 
 AmazingCalculator Snapshot 1 (figure 3) :
  
 <img src = "https://media.discordapp.net/attachments/546026199197941775/583576992708821003/unknown.png?width=1296&height=676">
 
 AmazingCalculator Snapshot 2 (figure 4) :
  
 <img src = "https://media.discordapp.net/attachments/546026199197941775/583567252855717918/unknown.png?width=424&height=676">
 
 AmazingCalculator Snapshot 3 (figure 5) :
  
 <img src = "https://media.discordapp.net/attachments/546026199197941775/583561620446380033/unknown.png?width=841&height=676">
 
  
  Anomaly number 1 :
  
  After some time playing the game we have noticed the following runtime exception in the terminal : "Relax! You found one of the solutions!".
  
  When we have played the game with AmazingPointCalculator we always noticed the above mentioned exception at the end near the sudden death of pacman (even if pacman didn't collide with any ghost).
  If we look at figure 3, we see that pacman stays alive at the end (see right most part of the graph) when pacman faces North so it may be the case that facing North after playing the game for a while causes the exception to be thrown.
  This exception clearly isn't thrown when playing the game with the default calculator under the same conditions mentioned that maybe causes the exception.  
  
  Anomaly number 2 :
  
  While playing the game we have also noticed that at some point the player score start to decrease instead of increasing when eating a pellet.
  
  I we take a look at figure 1 or figure 2 we see that it is always the case that consuming a pellet causes the player's score to increase when the default calculator is used. 
  Now take a look at figure 4 which is a zoomed version of the plot that the fuzzer gives when the AmazingPoint calculator is used. 
  We can see that consuming a pellet after around ~ 30 pellets was already consumed makes the player's score decrease instead of increasing. 
  (see that the number of pellets eaten increases from 32 to 33 and that the points decrease from ~220 to 200 when x-axis value is around 263)
  
  Anomaly number 3 :
  
  While playing the game the player suddenly dies/game over eventhough the player has not even collided with any ghosts. 
  
  If we compare all of the figures we see that in the graphs (see rightmost part of the killedByGhost? and isAlive?) where the default calculator is used, 
  pacman is always killed by a ghost (so it is never the case that pacman dies without colliding with a ghost). We also see that when the default calculator is used,
  the pacman can collect any number of pellets before dying (at figure 1 the player ate more than 50 and at figure 2 we see the player has consumed around 20).
  In contrast to the graphs of default calculator, we see the following patterns occur in figures 3, 4 and 5 (amazing calculator is used in these graphs) :
  1. The pacman dies without ever having collided with a ghost (the killedByGhost graphs is always at False)
  2. We see that pacman always dies right after when he has consumed around  35 pellets.
   
   
  Anomaly number 4 :
  
  While playing the game at/around the beginning the score of the player,
  decreases a lot (around 2.1 billion) when the player's eats a pellet in direction is West, if the player eats another pellet again in the West direction
  the point become positive again. 
  
  If we take a look at figure 3 and 5 we see that the player's score always starts to drop to negative 2 billion after the player has consumed  around 15 pellets. 
  In figure 5 we can also see that the score drops to negative 2 billion and then increases by 2 billion again (see when the x-axis' value is around 80), furthermore we see that these 2 odd changes 
  in the score happens when the player has consumed a pellet while facing West (see the Direction graph when x-axis' value is around 80 in figure 5). 
  We obviously do not see massive increases and decreases in the score in the figures 1 and 2 where the default calculator is used. 
  
  #### Exercise 16
  
  We have run "gradle staticAnalysis" command, there were no security warnings 
    associated to the piece of code where the PointCalculator is dynamically loaded 
    reported by SpotBugs. We believe that the vulnerability from OWASP 2017 list that applies to the source
    code where the PointCalculator is dynamically loaded  is : "A1 Injection" because JPacman uses AmazingPointCalculator 
    which is a dynamically loaded plugin and we usually "don't have" access to the source code of such plugins.
    The reason for why SpotBugs didn't report warnings is because SpotBugs is a **static** analysis which means statically analyzing the code will not help since the code base for  the AmazingPointCalculator plugin is not available for static analysis tools (e.g. SpotBugs). 
    Furthermore the anomalies caused by the AmazingCalculator occur at runtime which means that SpotBugs won't be able to
    give warnings for these anomalies because static analysis tools test software without execution of software. 
    
 #### Exercise 17
 1. To not use plugins / dynamic class loading for classes, if you do not have access to
    source code for seeing if there are security vulnerabilities. 
 2. Use a custom defined  ClassLoader method to load the untrusted code. The class loader method would get called 
    for all classes which the untrusted code uses, so you can do things like disable access to individual JDK classes by using a strict white-list of allowed JDK classes. (less privilege/ access for dynamically loaded class) 
 3. Security vulnerabilities can arise when privileged code 
    coexists with unprivileged code (or less privileged code) that was loaded by the same class loader. 
    by separating code into multiple name-spaces and separating code in different name-spaces the security vulnerabilities can be prevented.
     
 #### Part 1.7
 All checkstyle and Spotbug violations were fixed with suppressions if  needed . 
 We added tests classes for the collisionMap and it's extensions, start method in Game class and good and bad weather cases for the parseMap methods.
 We tried to work on a separate branch for each exercise and then merge the branch with the Asignment-2 branch. The pipeline failed for some commits and merge requests because of the checkstyle violations because we forgot
 to run gradle check beforehand. We commited 3 times on average per exercise and the work was evenly distributed as much as possible. 
 
 

 
 
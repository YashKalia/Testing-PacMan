## Assignment 3

All txt-files used by the withMapFile method can be found in :
src\test\resources

#### Exercise 1
The tests written for this exercise can be found in the test class With the following path : 
src\test\java\nl\tudelft\jpacman\integration\suspension\SuspensionTest.java
 
#### Exercise 2
The tests written for this exercise can be found in the test class With the following path : 
src\test\java\nl\tudelft\jpacman\integration\playerMoves\PlayerMovesTest.java

#### Exercise 3  

For scenario 2.4. it's harder to create system test cases because :    
   If we were to use the board.txt that is provided then it would be difficult to 
   get the game to a state where there is a ghost next to the player (at the start of the game
   there are no ghost that are next to the player), such that we 
   can simulate the player going to the ghost's cell.
   Ghosts move around and the direction in which they
   move depend on their nextAIMove() method, thus we have less 
   controllability for getting/preparing/setting the right state
   that is specified in the scenario 2.4.
   Furthermore, there are different ghosts
   each with different implementations of nextAIMove() which further decreases 
   our controllability and repeatability of the test. 
   
For scenario 2.5. it's harder to create system test cases because :

If we start the test case where the level/map is made with board.txt : 
1. still contains (more than) one pellet
2. board has big with and length with lot of walls and differently shaped paths
etc,

 then we will have to eat all pellets except the last pellet,
 find the exact location of the last pellet, while avoiding 
 collision with all the different ghost (each move differently) etc, 
 just so that we have the right condition/state needed for scenario 2.5.
 Thus, we can conclude that there is a lack of controllability which makes testing
 this scenario difficult
 
#### Exercise 4

The tests written for this exercise can be found in the test class With the following path : 
src\test\java\nl\tudelft\jpacman\integration\playerMoves\PlayerMovesTest.java

#### Exercise 5

All the scenarios in user story 3 are hard to test because:
We don't have control over when "a tick event" occurs and 
it is hard to bring the game to the exact states that are specified in 
the "Given ( + and)" clauses of the scenarios  (Thus, a lack of controllability for making a ghost move 
into a  pellet/ another ghost / player that is next to a ghost).
Ghosts move around and the direction in which they
move depend on their nextAIMove() method, thus we have less 
controllability for getting/preparing/setting the right state
that is specified in the scenario 2.4 (a test case where the player is
next to a cell containing). Furthermore, there are different ghosts
each with different implementations of nextAIMove() which further decreases our
 controllability and repeatability of the test.  

#### Exercise 6

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/589122154134503455/unknown.png">

#### Exercise 7

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/589390412964298752/unknown.png">

#### Exercise 8

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/589124840091549713/unknown.png">

Test cases for (state, event) pairs not in the transition table above :

1. (Actually playing the game, Start button clicked)

2. (First time launched GUI, Stop button clicked)

3. (First time launched GUI, Press arrow key)

4. (First time launched GUI, Player eats last pellet)

5. (First time launched GUI, Collision with a ghost)

6. (Paused State, Stop button clicked)

7. (Paused State, Press arrow key)

8. (Paused State, Player eats last pellet)

9. (Paused State, Collision with a ghost)

10. (Game Lost, Start button clicked)

11. (Game Lost, Stop button clicked)

12. (Game Lost, Press arrow key)

13. (Game Lost, Player eats last pellet)

14. (Game Lost, Collision with a ghost)

15. (Game Won, Start button clicked)

16. (Game Won, Stop button clicked)

17. (Game Won, Press arrow key)

18. (Game Won, Player eats last pellet)

19. (Game Won, Collision with a ghost)

All of the state event pairs above are good for different sneaky path test cases.

#### Exercise 9
All of the non empty cells in  the transition table will be covered if we test all the paths in the transition tree therefore 
there is no need to test the non empty cells in  the transition table again.
Due to exercise 15 all of the tests written for this exercise can be found in the test class with the following path:
src\test\java\nl\tudelft\jpacman\game\GameAndLauncherTest.java

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


#### Exercise 11

The state machine model for multi-level game:

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/589122447085797420/unknown.png">

The transition tree for the state machine model above:

<img src = "https://cdn.discordapp.com/attachments/546026199197941775/589391515982233610/unknown.png">

The new transition table will be the following :

<img src = "https://cdn.discordapp.com/attachments/546025287939391508/589417542637060137/unknown.png">

#### Exercise 12

For easability of testing common behaviour of MultiLevelGame and SinglePlayerGame 
(such that we can make a test be usable for both MultiLevelGame and SinglePlayerGame (see exercise 15)):
The stateMachine for SinglePlayerGame (derived at exercise 6) will now be same as the state machine in  **exercise 11** but  with the following differences :
1. The event in the (Level Won, Player has won all levels) pair will have it's condition changed from
   "/Level == 3" to "/Level == 1"
2. The event in the (Level Won, Start button clicked) will not  be present in the state machine of SiniglePlayerGame.

This way the test cases that are derived from the red, green and black leaves in  the transition tree
in  **exercise 11** can be used for both SinglePlayer- and MultiLevelGame.

The test case derived from the yellow leaf will be specific for MultiLevelGame and not for SinglePlayerGame.
(This test called "yellowLeafPathTest()" can be found the in test class with the following path : 
src\test\java\nl\tudelft\jpacman\game\MultiLevelGameAndLauncherTest.java ) 

The test case derived from the blue leaf will be different for both MultiLevelGame and SinglePlayerGame because they have 
different number of levels (Multilevel needs 3 levels to be cleared). 

Find the specific test called "singlePlayerGameBlueLeafPathTest()"for SingelPlayerGame in : 
src\test\java\nl\tudelft\jpacman\game\SinglePlayerGameAndLauncherTest.java

Find the specific test called "multilevelBlueLeafPathTest()"for MultiLevelGame in : 
src\test\java\nl\tudelft\jpacman\game\MultiLevelGameAndLauncherTest.java


Due to the small changes in state machine model  the most of the empty cells (for sneaky path testing)
in the transition table in exercise  11 will apply to both version of games
, but **not** all. 

Single player game will have the same transition table as the one in **exercise 11** but without the (state, event) pair
(level won, start button clicked) this is a specific sneaky path test case for SinglePlayer Game but not for MultiLevelGame. (
This specific test can be found the in test class with the following path : 
src\test\java\nl\tudelft\jpacman\game\SinglePlayerGameAndLauncherTest.java )

The sneaky path test involving State = Level Won and other events for MultiLevel differs from SinglePlayerGame 
(because MultiLevel for (Level Won, start button) is possible whereas SinglePlayerGame doesn't)
therefore MultiLevelGame has it's own sneaky path method that involves Level Won in :
src\test\java\nl\tudelft\jpacman\game\MultiLevelGameAndLauncherTest.java

#### Exercise 13 & 14
You can verify that what is asked by this exercise is indeed done by taking a look at the 
MultiLevelLauncher class that has the following path :  src\main\java\nl\tudelft\jpacman\MultiLevelLauncher.java

#### Exercise 15
The tests written for this exercise can be found in the test class With the following path : 
src\test\java\nl\tudelft\jpacman\game\GameAndLauncherTest.java

#### Exercise 16
The tests specific to MultiLevelGame can be found the in test class with the following path :  
src\test\java\nl\tudelft\jpacman\game\MultiLevelGameAndLauncherTest.java

#### Exercise 17
Proof for the implementation that is asked
is that the implemented test that involve multiLevelLauncher and MultiLevelGame pass.
Furthermore the classes  multiLevelLauncher and MultiLevelGame can be inspected.

#### Exercise 18

intelliJ "tracing option coverage report" for multiLevelLauncher class:
<img src = "https://cdn.discordapp.com/attachments/546026199197941775/590960184218877954/unknown.png">

intelliJ "tracing option coverage report" for multiLevelGame class:
<img src = "https://cdn.discordapp.com/attachments/546026199197941775/590960381539778610/unknown.png">

seeing the coverage percentages (method, line, class, branch) of the multilevel code they are satisfactory :
100% for most of them, 85%, 95% for others. exeception : 42 % branch coverage for multilevel game class (I dont understand
this there is only one if-statement in that class so how can there even be 7 branches).
However from the lectures we have learned : Exhaustive testing is impossible and that we must maximize information gain
while minimizing cost of designing tests. Maybe the information gained  100% branch coverage fo multilevel game class 
will not be  worth it compared to the cost of making test for increasing branch coverage

#### Exercise 19

3 good things :

- we have used parallel test hierarchy which is very good for not repeating code
- we have added some methods that increase observability which eases testing e.g allLevelsWon(), lastLevelReached() etc.
- we have a relatively high coverage percentage compared to when  we first got the repository of JPacMan which means that
  debugging in the future in case of failures will be much easier 

3 bad things :

- Some of the name of my tests do not make clear at first glance what the test exercises. Specifically for the transition 
  tree tests because I didn't know how to call tests that belong to a certain path to the leaf. 
  Although I did add lengthy Java Docs to explain it, the reviewer might have to first read the report to understand the test.
  Alternative : come up with more self explanatory test names. 
- There tests in Game and Launcher test class are long-ish Solution  : make some general methods 
  that contain assertions that are used often.  
- The pacmanUi doesn't actually show the change of boards when we go to the next level for MultiLevelGame.
  I asked a TA , the TA said that it isn't necessary. Solution : adapt the UI for MultiLevelGame if I want to 
  in my free time. 


package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Abstract class to be used  for parallel testing.
 * Games and Launcher and their extensions.
 */
public abstract class GameAndLauncherTest {

    /**
     * game to test.
     */
    private Game game;
    private Launcher launcher;
    private Player player;
    private Level level;

    /**
     * Method that will be overridden by extensions of this class to return.
     * objects that are extensions of Launcher or Launcher itself.
     * @return Launcher instance to be tested.
     */
    public abstract Launcher getLauncherTestSubject();

    /**
     * setting up variables of this class.
     */
    @BeforeEach
    void setUp() {
        launcher = Mockito.spy(getLauncherTestSubject());
    }


    /**
     *  Observing if we are actually in the state "Actually Playing the game / Not Paused".
     * @param game to check.
     * @param level to check.
     * @param player to check.
     * @return  true is we are on  this state false otherwise.
     */
    public boolean checkAtActuallyPlayingState(Game game, Level level, Player player) {
        try {
            assertThat(game.isInProgress()).isTrue();
            assertThat(level.isInProgress()).isTrue();
            assertThat(level.isAnyPlayerAlive()).isTrue();
            assertThat(player.isAlive()).isTrue();
        } catch (AssertionError assertErr) {
            return false;
        }
        return true;
    }

    /**
     *  Observing if we are actually in the state "First Time Launched Gui".
     * @param launcher to check.
     * @param game to check.
     * @param level to check.
     * @param player to check.
     * @return  true is we are on  this state false otherwise.
     */
    public boolean checkFirstTimeLaunchedGuiState(Launcher launcher, Game game,
                                                  Level level, Player player) {
        try {
            Mockito.verify(launcher, Mockito.times(1)).launch();
            assertThat(game.isInProgress()).isFalse();
            assertThat(game.getLevel().isInProgress()).isFalse();
            assertThat(level.isAnyPlayerAlive()).isTrue();
            assertThat(player.isAlive()).isTrue();
        } catch (AssertionError assertionError) {
            return false;
        }
        return true;
    }

    /**
     *  Observing if we are actually in the state "Paused State".
     * @param game to check.
     * @param level to check.
     * @param player to check.
     * @return  true is we are on  this state false otherwise.
     */
    public boolean checkPausedState(Game game, Level level, Player player) {
        try {
            assertThat(game.isInProgress()).isFalse();
            assertThat(game.getLevel().isInProgress()).isFalse();
            assertThat(level.isAnyPlayerAlive()).isTrue();
            assertThat(player.isAlive()).isTrue();
        } catch (AssertionError assertionError) {
            return false;
        }
        return true;
    }

    /**
     *  Observing if we are actually in the state "Game Lost".
     * @param game to check.
     * @param level to check.
     * @param player to check.
     * @return  true is we are on  this state false otherwise.
     */
    public boolean checkGameLostState(Game game, Level level, Player player) {
        try {
            Mockito.verify(game).levelLost();
            assertThat(game.isInProgress()).isFalse();
            assertThat(game.getLevel().isInProgress()).isFalse();
            assertThat(level.isAnyPlayerAlive()).isFalse();
            assertThat(player.isAlive()).isFalse();
        } catch (AssertionError assertionError) {
            return false;
        }
        return true;
    }

    /**
     *  Observing if we are actually in the state "Level Won".
     * @param game to check.
     * @param level to check.
     * @param player to check.
     * @return  true is we are on  this state false otherwise.
     */
    public boolean checkLevelWonState(Game game, Level level, Player player) {
        try {
            assertThat(game.isInProgress()).isFalse();
            assertThat(game.getLevel().isInProgress()).isFalse();
            assertThat(level.isAnyPlayerAlive()).isTrue();
            assertThat(player.isAlive()).isTrue();
            assertThat(level.remainingPellets() == 0).isTrue();
        } catch (AssertionError assertionError) {
            return false;
        }
        return true;
    }

    /**
     *  Observing if we are actually in the state "Game Won".
     * @param game to check.
     * @return  true is we are on  this state false otherwise.
     */
    public boolean checkGameWonState(Game game) {
        try {
            assertThat(game.lastLevelReached()).isTrue();
            assertThat(game.allLevelsWon()).isTrue();
        } catch (AssertionError assertionError) {
            return false;
        }
        return true;
    }

    /**
     * This is the test case written for the test that is derived from "redLeaf" path.
     * Please take a look at the report of assignment 3 Exercise 12 for information,
     * that will make clear which specific path from the transition tree this test exercises.
     * The .....Leaf.txt file can be found in src\test\resources\redLeaf.txt
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void redLeafPathTest() {
        launcher.withMapFile("/redLeaf.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // assert that we are on the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isTrue();

        // start button clicked event will cause transition
        // from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed
        // at "Actually Playing the Game / Not Paused" state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        Mockito.verify(game).start();

        //collision with ghost event will cause the transition
        // from "Actually Playing the Game / Not Paused"
        // to "Game Lost"
        game.move(player, Direction.EAST);

        // observing properties of at "Game lost" state are indeed true
        assertThat(checkGameLostState(game, level, player)).isTrue();
    }

    /**
     * This is the test case written for the test that is derived from "greenLeaf" path.
     * Please take a look at the report of assignment 3 Exercise 12 for information,
     * that will make clear which specific path from the transition tree this test exercises.
     * The .....Leaf.txt file can be found in src\test\resources\greenLeaf.txt
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void greenLeafPathTest() {
        launcher.withMapFile("/greenLeaf.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // assert that we are on the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isTrue();

        // start button clicked event will cause transition
        // from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed
        // at "Actually Playing the Game / Not Paused" state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        Mockito.verify(game).start();

        //person clicking stop button will cause the transition
        // from "Actually Playing the Game / Not Paused"
        // to "Suspended/Paused"
        game.stop();

        // observing properties of at "Suspended/Paused" state are indeed true
        assertThat(checkPausedState(game, level, player)).isTrue();
        Mockito.verify(game).stop();

        // when the player of this game "clicks the start button" the transition
        // from "Suspended/Paused"
        //to "Actually Playing the Game / Not Paused"
        game.start();

        // observing properties of
        // at "Actually Playing the Game / Not Paused" state are indeed true
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        Mockito.verify(game, Mockito.times(2)).start();
    }


    /**
     * This is the test case written for the test that is derived from "blackLeaf" path.
     * Please take a look at the report of assignment 3 Exercise 12 for information,
     * that will make clear which specific path from the transition tree this test exercises.
     * The .....Leaf.txt file can be found in src\test\resources\blackLeaf.txt
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void blackLeafPathTest() {
        launcher.withMapFile("/blackLeaf.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // assert that we are on the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isTrue();

        // start button clicked event will cause transition
        // from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed
        // at "Actually Playing the Game / Not Paused" state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        Mockito.verify(game).start();

        //moving pacman  to the East to eat a pellet transitions
        // from "Actually Playing the Game / Not Paused"
        // to "Actually Playing the Game / Not Paused"
        game.move(player, Direction.EAST);
        assertThat(player.isAlive()).isTrue();
        assertThat(level.remainingPellets() != 0).isTrue();

        // observing properties of
        // at "Actually Playing the Game / Not Paused" state are indeed true
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //         Sneaky Path Testing                                                                //
    //         The sneaky path cells that both MultiLevelGame and SinglePlayerGame have in common //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * This test method tests all of the sneaky path cells corresponding to.
     * the (State, event) - pair:  (First Time GUI Launched, {"Stop button clicked",
     * "Press Arrow Key", "Player eats last Pellet", "Collision With a Ghost", "All levels won"})
     * It easier to test all of the events that should  lead to any other State in one method
     * rather than in lots of methods that test only one event,
     * because this minimizes repeating code.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void sneakyPathFirstTimeLaunchedGui() {
        launcher.withMapFile("/SmallMap.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // assert that we are on the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isTrue();

        // Event : "Stop button clicked" shouldn't cause change of states
        game.stop();
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isFalse();


        // checkPausedState(game, level, player) is going to return true
        // "First Time Launched GUI" has the same properties as PausedState
        // e.g. in both states the game is not in Progress etc,
        assertThat(checkPausedState(game, level, player)).isTrue();
        // check that we are still in the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isTrue();

        //Event:"Press Arrow Key" shouldn't cause change of states
        game.move(player, Direction.EAST);
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isFalse();


        // checkPausedState(game, level, player) is going to return true because
        // "First Time Launched GUI" has the same properties as PausedState
        // e.g. in both states the game is not in Progress etc,
        assertThat(checkPausedState(game, level, player)).isTrue();
        // check that we are still in the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isTrue();

        // the Event: "All levels won" caused by the allLevelsWon()
        // just checks whether all levels of a game are won or not;
        game.allLevelsWon();
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isFalse();


        // checkPausedState(game, level, player) is going to return true
        // "First Time Launched GUI" has the same properties as PausedState
        // e.g. in both states the game is not in Progress etc,
        assertThat(checkPausedState(game, level, player)).isTrue();
        // check that we are still in the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isTrue();

        // The events "Player eats last Pellet", "Collision With a Ghost" can only
        // take place when we are on "actually playing/ not paused/ started" state
        // and cannot take place  in the first time gui launched
        // we have already asserted that we are not in "actually playing/ not paused/ started" state
        // see "assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();"
    }

    /**
     * This test method tests all of the sneaky path cells corresponding to.
     * the (State, event) - pair:  (Playing the Game/Not Paused ,
     * {"Start button clicked, "All levels won"})
     * It easier to test all of the events that should  lead to any other State in one method
     * rather than in lots of methods that test only one event,
     * because this minimizes repeating code.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void sneakyPathPlayingGameNotPaused() {
        launcher.withMapFile("/SmallMap.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();


        //events that brings use to the correct state to test "Actually playing the game"
        game.start();

        // check that we are at Actually Playing/ Not Paused State
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();


        // event : "Start button clicked" doesn't cause state change
        game.start();

        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isFalse();
        assertThat(checkPausedState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isFalse();

        // check that we are at Actually Playing/ Not Paused State
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();

        // the Event: "All levels won" caused by the allLevelsWon()
        // just checks whether all levels of a game are won or not;
        game.allLevelsWon();
        assertThat(checkFirstTimeLaunchedGuiState(launcher, game, level, player)).isFalse();
        assertThat(checkPausedState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isFalse();

        // check that we are at Actually Playing/ Not Paused State
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();

    }

    /**
     * This test method tests all of the sneaky path cells corresponding to.
     * the (State, event) - pair:  (Suspended/Paused, {"Stop button clicked",
     * "Press Arrow Key", "Player eats last Pellet", "Collision With a Ghost", "All levels won"})
     * It easier to test all of the events that should  lead to any other State in one method
     * rather than in lots of methods that test only one event,
     * because this minimizes repeating code.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void sneakyPathSuspendedPausedState() {
        launcher.withMapFile("/SmallMap.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();


        //events that brings use to the correct state to test "Suspended/Paused"
        game.start();
        game.stop();

        // check that we are at Paused State
        assertThat(checkPausedState(game, level, player)).isTrue();

        // the Event: "All levels won" caused by the allLevelsWon()
        // just checks whether all levels of a game are won or not;
        game.allLevelsWon();
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isFalse();

        // check that we are still at Paused State
        assertThat(checkPausedState(game, level, player)).isTrue();

        // event : "Press Arrow Key" should caused any state change.
        game.move(player, Direction.EAST);
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isFalse();

        // check that we are still at Paused State
        assertThat(checkPausedState(game, level, player)).isTrue();

        // because we are in the paused state ghosts can't move, players can move to eat pellets
        // therefore "Player eats last Pellet", "Collision With a Ghost" events cannot take place
        // those events can only take place in "Actually playing stated / not paused"
        // we have checked already that we are not in that state with
        // "assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();"
    }

    /**
     * This test method tests all of the sneaky path cells corresponding to.
     * the (State, event) - pair:  (Game Lost, {"Start button clicked", "Stop button clicked",
     * "Press Arrow Key", "Player eats last Pellet", "Collision With a Ghost", "All levels won"})
     * It easier to test all of the events that should  lead to any other State in one method
     * rather than in lots of methods that test only one event,
     * because this minimizes repeating code.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void sneakyPathGameLost() {
        launcher.withMapFile("/GhostMap.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // events to bring us to the game elost state
        game.start();
        game.move(player, Direction.EAST);

        // check that we are at game lost state
        assertThat(checkGameLostState(game, level, player)).isTrue();

        //event: "Start button clicked"
        game.start();
        //still at game lost
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isTrue();

        //event: "Stop button clicked"
        game.stop();
        //still at game lost
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isTrue();

        //event : player moves arrow
        game.move(player, Direction.EAST);
        //still at game lost
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isTrue();

        // event: check if all levels are won
        game.allLevelsWon();
        //still at game lost
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameLostState(game, level, player)).isTrue();

        // because we are in the game lost state ghosts can't move, players can move to eat pellets
        // therefore "Player eats last Pellet", "Collision With a Ghost" events cannot take place
        // those events can only take place in "Actually playing stated / not paused"
        // we have checked already that we are not in that state with
        // "assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();"
    }

    /**
     * This test method tests all of the sneaky path cells corresponding to.
     * the (State, event) - pair:  (Game Won, {"Start button clicked", "Stop button clicked",
     * "Press Arrow Key", "Player eats last Pellet", "Collision With a Ghost", "All levels won"})
     * It easier to test all of the events that should  lead to any other State in one method
     * rather than in lots of methods that test only one event,
     * because this minimizes repeating code.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void sneakyPathGameWon() {
        launcher.withMapFile("/OnePelletMap.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // getting to the state Game Won
        game.start();
        game.move(player, Direction.EAST);

        //check that we are in Game won state
        assertThat(checkGameWonState(game)).isTrue();

        // event : check if all levels are won
        // this will be true because when the game is won all levels are cleared
        // but regardless we are still at game won state
        game.allLevelsWon();
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isTrue();

        // event player presses arrow key
        game.move(player, Direction.EAST);
        // still in game won state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isTrue();

        //event : press stop button
        game.stop();
        // still in game won state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isTrue();

        //event : press start button
        game.start();
        // still in game won state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkGameWonState(game)).isTrue();

        // because we are in the game won state ghosts can't move, players can move to eat pellets
        // therefore "Player eats last Pellet", "Collision With a Ghost" events cannot take place
        // those events can only take place in "Actually playing stated / not paused"
        // we have checked already that we are not in that state with
        // "assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();"
    }

}

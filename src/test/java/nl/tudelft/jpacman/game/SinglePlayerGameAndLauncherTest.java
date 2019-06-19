package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testing MultiLevelGame and MultiLevelLauncher.
 */
public class SinglePlayerGameAndLauncherTest extends GameAndLauncherTest {

    private Launcher launcher;
    private SinglePlayerGame game;
    private Level level;
    private Player player;

    /**
     * setting up.
     */
    void setUpSinglePlayerGame() {
        launcher = new Launcher();
        launcher.withMapFile("/yellowLeaf.txt");
        launcher = Mockito.spy(launcher);
        launcher.launch();
        game =  Mockito.spy((SinglePlayerGame)  launcher.getGame());
        level = game.getLevel();
        player = game.getPlayers().get(0);

    }


    /**
     * Overriding so that we will be able to test Launcher.
     * @return a Launcher to get tested.
     */
    @Override
    public Launcher getLauncherTestSubject() {
        return new Launcher();
    }


    /**
     * This is the test case written for the test that is derived from "blueLeaf" path.
     * version for SinglePlayerGame.
     * this test case can use the file that is also used for the yellow path of MultiLevelGame.
     * Please take a look at the report of assignment 3 Exercise 12 for information,
     * that will make clear which specific path from the transition tree this test exercises.
     * The .....Leaf.txt file can be found in src\test\resources\yellowLeaf.txt
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void singlePlayerGameBlueLeafPathTest() {
        setUpSinglePlayerGame();

        // assert that we are on the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(launcher,  game, level, player)).isTrue();

        // start button clicked event will cause transition
        // from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed
        // at "Actually Playing the Game / Not Paused" state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        Mockito.verify(game).start();

        //moving pacman  to the East to eat the last pellet transition
        // from "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(1)).levelWon();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // all levels are now cleared

        // observing properties of at "Game" state are indeed true
        assertThat(checkGameWonState(game)).isTrue();
    }

    /**
     * This test method tests all of the sneaky path cells corresponding to.
     * the (State, event) - pair:  (Level Won, {"Start button clicked", "Stop button clicked",
     * "Press Arrow Key", "Player eats last Pellet", "Collision With a Ghost", "All levels won"})
     * It easier to test all of the events that should  lead to any other State in one method
     *rather than in lots of methods that test only one event,
     * because this minimizes repeating code.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void sneakyPathLevelWonSinglePlayerGame() {
        setUpSinglePlayerGame();

        // actions for getting to levelWon State
        game.start();
        game.move(player, Direction.EAST);

        // check that we are at level Won state
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // event : check if all levels are won
        // this will be true because all 1 level of Single player Game is cleared
        // but regardless we are still at level won state
        game.allLevelsWon();
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // event player presses arrow key
        game.move(player, Direction.EAST);
        // still in level won state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        //event : press stop button
        game.stop();
        // still in level won state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        //event : press start button
        game.start();
        // still in level won state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // because we are in the level won state ghosts can't move (game not in progress)
        // , players can move to eat pellets
        // therefore "Player eats last Pellet", "Collision With a Ghost" events cannot take place
        // those events can only take place in "Actually playing stated / not paused"
        // we have checked already that we are not in that state with
        // "assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();"
    }
}

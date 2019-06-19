package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testing MultiLevelGame and MultiLevelLauncher.
 */
public class MultiLevelGameAndLauncherTest extends GameAndLauncherTest {

    private MultiLevelGame game;
    private MultiLevelLauncher multiLauncher;
    private Player player;
    private Level level;

    /**
     * setting up variables of this class.
     */
    void setUpMultiLevelGame() {
        multiLauncher = new MultiLevelLauncher();
        List<String> listOfLevels = new ArrayList<>();
        listOfLevels.add("/yellowLeaf.txt");
        listOfLevels.add("/yellowLeaf.txt");
        listOfLevels.add("/yellowLeaf.txt");
        multiLauncher.setFileNamesofLevels(listOfLevels);
        multiLauncher = Mockito.spy(multiLauncher);
        multiLauncher.launch();
        game =  Mockito.spy(multiLauncher.getGame());
        level = game.getLevel();
        player = game.getPlayers().get(0);
    }



    /**
     * Overriding so that we will be able to test MultiLevelLauncher.
     * @return a MultiLevelLauncher to get tested.
     */
    @Override
    public MultiLevelLauncher getLauncherTestSubject() {
        return new MultiLevelLauncher();
    }

    /**
     * This is the test case written for the test that is derived from "yellowLeaf" path.
     * that is specific for a MultiLevelGame.
     * Please take a look at the report of assignment 3 Exercise 12 for information,
     * that will make clear which specific path from the transition tree this test exercises.
     * The .....Leaf.txt file can be found in src\test\resources\yellowLeaf.txt
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void yellowLeafPathTest() {
        setUpMultiLevelGame();
        // assert that we are on the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(multiLauncher, game, level,  player)).isTrue();

        // start button clicked event will cause transition
        // from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed at "
        // Actually Playing the Game / Not Paused" state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        assertThat(game.getCurrentLevelNumber() == 0).isTrue();
        Mockito.verify(game).start();

        //moving pacman  to the east to eat the last pellet
        // "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(1)).levelWon();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // player clicking start button will transition "Level Won"
        // to "Actually Playing the Game / Not Paused"
        game.goToNextLevel();
        game.start();
        level = game.getLevel();

        // observing properties of at
        // "Actually Playing the Game / Not Paused" state are indeed true
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        assertThat(level.remainingPellets() != 0);
        assertThat(game.getCurrentLevelNumber() == 1).isTrue();
        Mockito.verify(game, Mockito.times(2)).start();

    }

    /**
     * (this method is quite long because the last level has to be reached)
     * This is the test case written for the test that is derived from "blueLeaf" path.
     * version for a MultiGame.
     * this test case can use the file that is also used for the yellow path of MultiLevelGame.
     * Please take a look at the report of assignment 3 Exercise 12 for information,
     * that will make clear which specific path from the transition tree this test exercises.
     * The .....Leaf.txt file can be found in src\test\resources\yellowLeaf.txt
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void multilevelBlueLeafPathTest() {
        setUpMultiLevelGame();

        // assert that we are on the state "First Time Launched GUI"
        assertThat(checkFirstTimeLaunchedGuiState(multiLauncher, game, level,  player)).isTrue();

        // start button clicked event will cause transition
        // from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed
        // at "Actually Playing the Game / Not Paused" state
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        assertThat(game.getCurrentLevelNumber() == 0).isTrue();
        Mockito.verify(game).start();

        //moving pacman  to the East to eat the last pellet transitions
        // from "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(1)).levelWon();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // player clicking start button will transition "Level Won"
        // to "Actually Playing the Game / Not Paused"
        game.goToNextLevel();
        game.start();
        level = game.getLevel();

        // observing properties of
        // at "Actually Playing the Game / Not Paused" state are indeed true
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        assertThat(level.remainingPellets() != 0);
        assertThat(game.getCurrentLevelNumber() == 1).isTrue();
        Mockito.verify(game, Mockito.times(2)).start();

        //moving pacman  to the East to eat the last pellet transition
        // from "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(2)).levelWon();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // player clicking start button will transition "Level Won"
        // to "Actually Playing the Game / Not Paused"
        game.goToNextLevel();
        game.start();
        level = game.getLevel();

        // observing properties of
        // at "Actually Playing the Game / Not Paused" state are indeed true
        assertThat(checkAtActuallyPlayingState(game, level, player)).isTrue();
        assertThat(level.remainingPellets() != 0);
        assertThat(game.getCurrentLevelNumber() == 2).isTrue();
        Mockito.verify(game, Mockito.times(3)).start();

        //moving pacman  to the East to eat the last pellet transitions
        // from "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(3)).levelWon();
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // all levels are now cleared

        // observing properties of at "Game" state are indeed true
        assertThat(checkGameWonState(game)).isTrue();

    }

    /**
     * This test method tests all of the sneaky path cells corresponding to.
     * the (State, event) - pair:  (Level Won, { "Stop button clicked",
     * "Press Arrow Key", "Player eats last Pellet", "Collision With a Ghost", "All levels won"})
     * It easier to test all of the events that should  lead to any other State in one method
     * rather than in lots of methods that test only one event,
     * because this minimizes repeating code.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void sneakyPathLevelWonMultiLevelGame() {
        setUpMultiLevelGame();

        // actions for getting to levelWon State
        game.start();
        game.move(player, Direction.EAST);

        // check that we are at level Won state
        assertThat(checkLevelWonState(game, level, player)).isTrue();

        // event : check if all levels are won
        // this will be false because only 1 level is cleared
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


        // because we are in the level won state ghosts can't move (game not in progress)
        // , players can move to eat pellets
        // therefore "Player eats last Pellet", "Collision With a Ghost" events cannot take place
        // those events can only take place in "Actually playing stated / not paused"
        // we have checked already that we are not in that state with
        // "assertThat(checkAtActuallyPlayingState(game, level, player)).isFalse();"
    }
}

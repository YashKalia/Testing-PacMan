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
    @SuppressWarnings({"magicnumber", "methodlength"})
    void singlePlayerGameBlueLeafPathTest() {
        setUpSinglePlayerGame();

        // assert that we are on the state "First Time Launched GUI"
        checkFirstTimeLaunchedGuiState(launcher);

        // start button clicked event will cause transition
        // from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed
        // at "Actually Playing the Game / Not Paused" state
        checkAtNotPausedState(game, level, player);
        Mockito.verify(game).start();

        //moving pacman  to the East to eat the last pellet transition
        // from "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(1)).levelWon();
        checkLevelWonState(game, level, player);

        // all levels are now cleared

        // observing properties of at "Game" state are indeed true
        checkGameWonState(game);
    }
}

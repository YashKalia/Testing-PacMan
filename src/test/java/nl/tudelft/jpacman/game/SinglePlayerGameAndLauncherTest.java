package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        launcher = new Launcher();
        launcher.withMapFile("/yellowLeaf.txt");
        launcher = Mockito.spy(launcher);
        launcher.launch();
        game =  Mockito.spy((SinglePlayerGame)  launcher.getGame());
        level = game.getLevel();
        player = game.getPlayers().get(0);

    }

    /**
     * clean up after tests.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }

    /**
     * Overriding so that we will be able to test Launcher.
     * @return a Launcher to get tested.
     */
    @Override
    public Launcher getLauncherTestSubject() {
        return new Launcher();
    }

    @Test
    void SinglePlayerBlueLeafPathTest() {
        // assert that we are on the state "First Time Launched GUI"
        checkFirstTimeLaunchedGuiState(launcher);

        // start button clicked event will cause transition from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed at "Actually Playing the Game / Not Paused" state
        checkAtNotPausedState(game, level, player);
        Mockito.verify(game).start();

        //moving pacman  to the east to eat the last pellet  "Actually Playing the Game / Not Paused"
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

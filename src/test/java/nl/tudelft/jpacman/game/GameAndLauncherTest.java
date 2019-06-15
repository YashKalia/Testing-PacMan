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
     * clean up after tests.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }

    public void checkAtNotPausedState(Game game, Level level, Player player) {
        assertThat(game.isInProgress()).isTrue();
        assertThat(level.isInProgress()).isTrue();
        assertThat(level.isAnyPlayerAlive()).isTrue();
        assertThat(player.isAlive()).isTrue();
    }

    public void checkFirstTimeLaunchedGuiState(Launcher launcher) {
        Mockito.verify(launcher, Mockito.times(1)).launch();
    }

    public void checkPausedState(Game game, Level level, Player player) {
        assertThat(game.isInProgress()).isFalse();
        assertThat(game.getLevel().isInProgress()).isFalse();
        assertThat(level.isAnyPlayerAlive()).isTrue();
        assertThat(player.isAlive()).isTrue();
    }

    public void checkGameLostState(Game game, Level level, Player player) {
        Mockito.verify(game).levelLost();
        assertThat(game.isInProgress()).isFalse();
        assertThat(game.getLevel().isInProgress()).isFalse();
        assertThat(level.isAnyPlayerAlive()).isFalse();
        assertThat(player.isAlive()).isFalse();
    }

    public void checkLevelWonState(Game game, Level level, Player player) {
        assertThat(game.isInProgress()).isFalse();
        assertThat(game.getLevel().isInProgress()).isFalse();
        assertThat(level.isAnyPlayerAlive()).isTrue();
        assertThat(player.isAlive()).isTrue();
    }

    public void checkGameWonState(Game game) {
        assertThat(game.lastLevelReached()).isTrue();
        assertThat(game.allLevelsWon()).isTrue();
    }
    @Test
    void redLeafPathTest() {
        launcher.withMapFile("/redLeaf.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // assert that we are on the state "First Time Launched GUI"
        checkFirstTimeLaunchedGuiState(launcher);

        // start button clicked event will cause transition from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed at "Actually Playing the Game / Not Paused" state
        checkAtNotPausedState(game, level, player);
        Mockito.verify(game).start();

        //collision with ghost event will cause the transition from "Actually Playing the Game / Not Paused"
        // to "Game Lost"
        game.move(player, Direction.EAST);

        // observing properties of at "Game lost" state are indeed true
        checkGameLostState(game, level, player);
    }

    @Test
    void greenLeafPathTest() {
        launcher.withMapFile("/greenLeaf.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // assert that we are on the state "First Time Launched GUI"
        checkFirstTimeLaunchedGuiState(launcher);

        // start button clicked event will cause transition from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed at "Actually Playing the Game / Not Paused" state
        checkAtNotPausedState(game, level, player);
        Mockito.verify(game).start();

        //person clicking stop button will cause the transition from "Actually Playing the Game / Not Paused"
        // to "Suspended/Paused"
        game.stop();

        // observing properties of at "Suspended/Paused" state are indeed true
        checkPausedState(game, level, player);
        Mockito.verify(game).stop();

        // when the player of this game "clicks the start button" the transition from "Suspended/Paused"
        //to "Actually Playing the Game / Not Paused"
        game.start();

        // observing properties of at "Actually Playing the Game / Not Paused" state are indeed true
        checkAtNotPausedState(game, level, player);
        Mockito.verify(game, Mockito.times(2)).start();
    }

    @Test
    void blackLeafPathTest() {
        launcher.withMapFile("/blackLeaf.txt");
        launcher.launch();
        game = Mockito.spy(launcher.getGame());
        player = game.getPlayers().get(0);
        level = game.getLevel();

        // assert that we are on the state "First Time Launched GUI"
        checkFirstTimeLaunchedGuiState(launcher);

        // start button clicked event will cause transition from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed at "Actually Playing the Game / Not Paused" state
        checkAtNotPausedState(game, level, player);
        Mockito.verify(game).start();

        //moving pacman  to the east to eat a pellet  "Actually Playing the Game / Not Paused"
        // to "Actually Playing the Game / Not Paused"
        game.move(player, Direction.EAST);
        assertThat(player.isAlive()).isTrue();
        assertThat(level.remainingPellets() != 0).isTrue();

        // observing properties of at "Actually Playing the Game / Not Paused" state are indeed true
        checkAtNotPausedState(game, level, player);
    }
}

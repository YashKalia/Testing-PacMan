package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    @BeforeEach
    void setUp() {
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
     * clean up after tests.
     */
    @AfterEach
    void tearDown() {
        multiLauncher.dispose();
    }

    /**
     * Overriding so that we will be able to test MultiLevelLauncher.
     * @return a MultiLevelLauncher to get tested.
     */
    @Override
    public MultiLevelLauncher getLauncherTestSubject() {
        return new MultiLevelLauncher();
    }

    @Test
    void yellowLeafPathTest() {
        // assert that we are on the state "First Time Launched GUI"
        checkFirstTimeLaunchedGuiState(multiLauncher);

        // start button clicked event will cause transition from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed at "Actually Playing the Game / Not Paused" state
        checkAtNotPausedState(game, level, player);
        assertThat(game.getCurrentLevelNumber() == 0).isTrue();
        Mockito.verify(game).start();

        //moving pacman  to the east to eat the last pellet  "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(1)).levelWon();
        checkLevelWonState(game, level, player);

        // player clicking start button will transition "Level Won" to "Actually Playing the Game / Not Paused"
        game.goToNextLevel();
        game.start();
        level = game.getLevel();

        // observing properties of at "Actually Playing the Game / Not Paused" state are indeed true
        checkAtNotPausedState(game, level, player);
        assertThat(level.remainingPellets() != 0);
        assertThat(game.getCurrentLevelNumber() == 1).isTrue();
        Mockito.verify(game, Mockito.times(2)).start();
    }

    @Test
    void multilevelBlueLeafPathTest() {
        // assert that we are on the state "First Time Launched GUI"
        checkFirstTimeLaunchedGuiState(multiLauncher);

        // start button clicked event will cause transition from "First Time Launched GUI"
        // to "Actually Playing the Game / Not Paused"
        game.start();

        //observing that we are indeed at "Actually Playing the Game / Not Paused" state
        checkAtNotPausedState(game, level, player);
        assertThat(game.getCurrentLevelNumber() == 0).isTrue();
        Mockito.verify(game).start();

        //moving pacman  to the east to eat the last pellet  "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(1)).levelWon();
        checkLevelWonState(game, level, player);

        // player clicking start button will transition "Level Won" to "Actually Playing the Game / Not Paused"
        game.goToNextLevel();
        game.start();
        level = game.getLevel();

        // observing properties of at "Actually Playing the Game / Not Paused" state are indeed true
        checkAtNotPausedState(game, level, player);
        assertThat(level.remainingPellets() != 0);
        assertThat(game.getCurrentLevelNumber() == 1).isTrue();
        Mockito.verify(game, Mockito.times(2)).start();

        //moving pacman  to the east to eat the last pellet  "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(2)).levelWon();
        checkLevelWonState(game, level, player);

        // player clicking start button will transition "Level Won" to "Actually Playing the Game / Not Paused"
        game.goToNextLevel();
        game.start();
        level = game.getLevel();

        // observing properties of at "Actually Playing the Game / Not Paused" state are indeed true
        checkAtNotPausedState(game, level, player);
        assertThat(level.remainingPellets() != 0);
        assertThat(game.getCurrentLevelNumber() == 2).isTrue();
        Mockito.verify(game, Mockito.times(3)).start();

        //moving pacman  to the east to eat the last pellet  "Actually Playing the Game / Not Paused"
        // to "Level Won"
        assertThat(level.remainingPellets() == 1).isTrue();
        game.move(player, Direction.EAST);
        assertThat(level.remainingPellets() == 0).isTrue();

        // observing properties of at "Level Won" state are indeed true
        Mockito.verify(game, Mockito.times(3)).levelWon();
        checkLevelWonState(game, level, player);

        // all levels are now cleared

        // observing properties of at "Game" state are indeed true
        checkGameWonState(game);
    }
}

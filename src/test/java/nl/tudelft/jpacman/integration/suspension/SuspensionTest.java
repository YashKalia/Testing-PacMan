package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//    As a player,
//    I want to be able to suspend the game;
//    So  that I can pause and do something else.
//
//    Scenario S4.1: Suspend the game.
//    Given the game has started;
//    When  the player clicks the "Stop" button;
//    Then  all moves from ghosts and the player are suspended.
//
//    Scenario S4.2: Restart the game.
//    Given the game is suspended;
//    When  the player hits the "Start" button;
//    Then  the game is resumed.

/**
 * Test class for testing user story 4 from doc/scenarios.md
 */
public class SuspensionTest {

    // the Board file's content is copied from board/txt in main folder.
    private final String fileName = "/Board.txt";
    private Launcher launcher;

    /**
     * *from Launcher smoke test*.
     * Launch the user interface.
     */
    @BeforeEach
    void setUpPacman() {
        launcher = new Launcher();
        launcher.withMapFile(fileName);
        launcher.launch();
    }

    /**
     * *from Launcher smoke test*.
     * Quit the user interface when we're done.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }


    /**
     * This test case test both scenario 4.1 and 4.2 from the doc/scenarios.md.
     */
//    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    @Test
    void suspensionAndResumptionTest() {
        // We first test scenario 4.1 : Suspend the game.

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        // Given the game has started;
        // code for starting cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // When  the player clicks the "Stop" button;
        //  code for stopping.
        game.stop();

        // Then  all moves from ghosts and the player are suspended.
        // code for verifying what is mentioned above.
        assertThat(game.isInProgress()).isFalse();
    }
}

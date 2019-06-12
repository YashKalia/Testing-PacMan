package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

//import nl.tudelft.jpacman.board.Direction;

/**
 * Test class for testing user story 4 from doc/scenarios.md.
 */
public class SuspensionTest {

    // the Board file's content is copied from board/txt in main folder.
    private static final String FILENAME = "/Board.txt";
    private Launcher launcher;

    /**
     * *from Launcher smoke test*.
     * Launch the user interface.
     */
    @BeforeEach
    void setUpPacman() {
        launcher = new Launcher();
        launcher.withMapFile(FILENAME);
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
     * there is a surpression of "methodlength" -> mostly comments that
     * cause the method length to exceed the standard length.
     */
    @SuppressWarnings({"methodlength"})
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
        // we know from  the source code that when stop() method
        // from Game class is invoked, stopNPCs() from Level class is also invoked
        // which means ghost moves are suspended
        // Furthermore the start() from game will not allow
        // player to move if isInProgress() returns FALSE (asserted below)
        // code for verifying what is mentioned above.
        assertThat(game.isInProgress()).isFalse();


//        Mockito.verify(game.getLevel(), Mockito.times(1)).

        // Now we test : Scenario S4.2: Restart the game.

        // Given the game is suspended;
        // We already know that the game is suspended
        // from the previous assertion see line 68

        // When  the player hits the "Start" button;
        game.start();

        // Then  the game is resumed.
        assertThat(game.isInProgress()).isTrue();
    }
}

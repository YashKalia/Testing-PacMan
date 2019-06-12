package nl.tudelft.jpacman.integration.playerMoves;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
//    Scenario S2.4: The player dies
//    Given the game has started,
//    and  my Pacman is next to a cell containing a ghost;
//    When  I press an arrow key towards that square;
//    Then  my Pacman dies,
//    and  the game is over.
//
//    Scenario S2.5: Player wins, extends S2.1
//    When  I have eaten the last pellet;
//    Then  I win the game.
/**
 * Test class for testing user story 2 : Move the Player.
 */
public class PlayerMovesTest {

    private Launcher launcher;

    // Here you can take a look at the content of
    // SmallMap.txt without going to resource folder :
    //     #####
    //     #####
    //     # P.#
    //     ##.##
    //     #####

    // Here you can take a look at the content of
    // GhostMap.txt without going to resource folder :

    // Here you can take a look at the content of
    // OnePelletMap.txt without going to resource folder :
    //     #####
    //     #####
    //     # P.#
    //     ## ##
    //     #####

    /**
     * Launch the user interface.
     */
    @BeforeEach
    void setUp() {
        launcher = new Launcher();
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
     * This tests :  Scenario S2.1: The player consumes a pellet.
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void playerEatsPelletTest() {
        launcher.withMapFile("/SmallMap.txt");
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Square playerStartSquare = player.getSquare();

        //    Given the game has started cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        //    and  my Pacman is next to a square containing a pellet;
        // --- In the SmallMap.txt, we can see that there are pellets next to
        //     the square that Pacman occupies (to the SOUTH & EAST)
        // assertions for "next to square containing a pellet".
        Square southSquare = playerStartSquare.getSquareAt(Direction.SOUTH);
        Pellet pellet = (Pellet) southSquare.getOccupants().get(0);
        assertThat(southSquare).isEqualTo(playerStartSquare.getSquareAt(Direction.SOUTH));
        assertThat(southSquare.getOccupants().contains(pellet)).isTrue();

        //    When  I press an arrow key towards that square;
        game.move(player, Direction.SOUTH);

        //    Then  my Pacman can move to that square,
        assertThat(southSquare.isAccessibleTo(player)).isTrue();
        assertThat(southSquare.getOccupants().contains(player)).isTrue();
        //    Pacman not on it's starting square anymore
        assertThat(playerStartSquare.getOccupants().contains(player)).isFalse();
        //    and  I earn the points for the pellet,
        assertThat(player.getScore()).isEqualTo(10);
        //    and  the pellet disappears from that square.
        assertThat(southSquare.getOccupants().contains(pellet)).isFalse();
    }

    /**
     * This tests :  Scenario S2.2: The player moves on empty square.
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void playerMoveToEmptySquareTest() {
        launcher.withMapFile("/SmallMap.txt");
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Square playerStartSquare = player.getSquare();

        //    Given the game has started,
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();

        //    and  my Pacman is next to an empty square;
        //--- We can see in the SmallMap.txt that to the WEST of
        //    the player's start square there is an empty square
        Square emptyWestSquare = playerStartSquare.getSquareAt(Direction.WEST);
        assertThat(emptyWestSquare.getOccupants().isEmpty()).isTrue();
        // following assertion needed to prove "and  my points remain the same"
        // when pacman moves to WEST later.
        assertThat(player.getScore()).isZero();

        //    When  I press an arrow key towards that square;
        game.move(player, Direction.WEST);

        //    Then  my Pacman can move to that square
        assertThat(emptyWestSquare.isAccessibleTo(player)).isTrue();
        assertThat(emptyWestSquare.getOccupants().contains(player)).isTrue();
        //    Pacman not on it's starting square anymore
        assertThat(playerStartSquare.getOccupants().contains(player)).isFalse();
        //    and  my points remain the same. The score was zero before Pacman moved to WEST
        //    now it is still the same i.e. score = 0.
        assertThat(player.getScore()).isEqualTo(0);
    }

    /**
     * This tests :    Scenario S2.3: The move fails when player tries to move to wall.
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void playerTriesToMoveToWallTest() {
        launcher.withMapFile("/SmallMap.txt");
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Square playerStartSquare = player.getSquare();

        //    Given the game has started,
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();

        //    and my Pacman is next to a cell containing a wall;
        //--- We can see in SmallMap.txt that there is a cell to the North
        //    of the player's square that contains a wall.
        Square wallNorthSquare = playerStartSquare.getSquareAt(Direction.NORTH);
        /**
         * HOW TO ASSERT WALL ? assert that the sprite up north is a wall ?
         */

        //    When  I press an arrow key towards that cell;
        game.move(player, Direction.NORTH);

        //    Then  the move is not conducted,
        //    pacman will still be on the square it occupied before moving in the direction of wall
        assertThat(wallNorthSquare.isAccessibleTo(player)).isFalse();
        assertThat(wallNorthSquare.getOccupants().contains(player)).isFalse();
        assertThat(playerStartSquare.getOccupants().contains(player)).isTrue();
    }
}

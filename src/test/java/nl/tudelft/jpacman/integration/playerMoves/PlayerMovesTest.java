package nl.tudelft.jpacman.integration.playerMoves;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    //     #####
    //     ## ##
    //     # PG#
    //     #####
    //     ##.##
    //     #####

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
        launcher = launcher.withMapFile("/SmallMap.txt");
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
        launcher = launcher.withMapFile("/SmallMap.txt");
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
        launcher = launcher.withMapFile("/SmallMap.txt");
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

        //    When  I press an arrow key towards that cell;
        game.move(player, Direction.NORTH);

        //    Then  the move is not conducted,
        //    pacman will still be on the square it occupied before moving in the direction of wall
        assertThat(wallNorthSquare.isAccessibleTo(player)).isFalse();
        assertThat(wallNorthSquare.getOccupants().contains(player)).isFalse();
        assertThat(playerStartSquare.getOccupants().contains(player)).isTrue();
    }

    /**
     * This tests : Scenario S2.4: The player dies.
     */
    @Test
    @SuppressWarnings({"methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void playerCollideWithGhostAndDiesTest() {
        // Here we use the GhostMap.txt where there is a ghost to the EAST of the Player.
        launcher = launcher.withMapFile("/GhostMap.txt");
        launcher.launch();
        Game game = launcher.getGame();

        Player player = game.getPlayers().get(0);

        Square playerStartSquare = player.getSquare();
        // the following square is the square next to the player
        Square ghostEastSquare = playerStartSquare.getSquareAt(Direction.EAST);
        Ghost ghost = (Ghost) ghostEastSquare.getOccupants().get(0);

        //    Given the game has started,
        game.start();

        //    and  my Pacman is next to a cell containing a ghost;
        assertThat(ghostEastSquare.getOccupants().get(0) instanceof Ghost).isTrue();
        assertThat(ghostEastSquare.getOccupants().get(0)).isEqualTo(ghost);

        //    When  I press an arrow key towards that square;
        game.move(player, Direction.EAST);

        //    Then  my Pacman dies,
        //    and  the game is over.
        assertThat(player.getKiller()).isEqualTo(ghost);
        assertThat(game.getLevel().isAnyPlayerAlive()).isFalse();
        assertThat(player.isAlive()).isFalse();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * This tests : Scenario S2.5: Player wins, extends S2.1.
     */
    @Test
    @SuppressWarnings({"magicnumber", "methodlength", "PMD.JUnitTestContainsTooManyAsserts"})
    void eatLastPelletAndWinTest() {
        launcher = launcher.withMapFile("/OnePelletMap.txt");
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Square playerStartSquare = player.getSquare();
        Square lastPelletEastSquare = playerStartSquare.getSquareAt(Direction.EAST);
        Pellet lastPellet = (Pellet) lastPelletEastSquare.getOccupants().get(0);

        //start the game
        game.start();

        //    When  I have eaten the last pellet;
        //--- We see in  the OnePelletMap.txt that the last pellet is to the EAST
        //    of the player. Hence the player should move to EAST/
        assertThat(game.getLevel().remainingPellets()).isEqualTo(1);
        assertThat(lastPelletEastSquare.getOccupants().contains(lastPellet)).isTrue();
        game.move(player, Direction.EAST);
        assertThat(game.getLevel().remainingPellets()).isZero();

        //    Then  I win the game.
        //    when the game is won the game stops
        assertThat(game.isInProgress()).isFalse();
        assertThat(player.isAlive()).isTrue();
        assertThat(game.getLevel().isAnyPlayerAlive()).isTrue();
        assertThat(player.getKiller()).isNull();
    }
}

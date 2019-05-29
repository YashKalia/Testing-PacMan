package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Test suite for CollisioMap extensions.
 */
public abstract class CollisionMapTest {

    /**
     * CollsionMap instance for testing their functionality.
     * pointcalculator, ghost, player and pellet,
     * that will be mocked to ease controllability,
     * and observability
     */
    private CollisionMap collisionMap;
    private PointCalculator calculator;
    private Pellet pellet;
    private Player player;
    private Ghost ghost;

    /**
     * Method that will be overridden by extensions of this class to return.git
     * objects that are extensions of CollisionMap.
     * @param calculator needed to check if points change.
     * @return collisionmap instance to be tested.
     */
    public abstract CollisionMap getTestSubject(PointCalculator calculator);

    /**
     * For setting up the necessary clean,
     * instances that will be used in tests.
     */
    @BeforeEach
    void setUp() {
        calculator = Mockito.mock(DefaultPointCalculator.class);
        collisionMap = getTestSubject(calculator);
        pellet = Mockito.mock(Pellet.class);
        player = Mockito.mock(Player.class);
        ghost = Mockito.mock(Ghost.class);
        Mockito.doCallRealMethod().when(player).setKiller(Mockito.any());
        Mockito.doCallRealMethod().when(player).addPoints(Mockito.anyInt());
        Mockito.doCallRealMethod()
            .when(calculator).collidedWithAGhost(Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod()
            .when(calculator).consumedAPellet(Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod()
            .when(calculator).pacmanMoved(Mockito.any(), Mockito.any());
        Mockito.doNothing().when(player).setAlive(Mockito.anyBoolean());
        Mockito.doNothing().when(pellet).leaveSquare();
    }

    /**
     * Test the outcome that should happen when player collides with a ghost.
     */
    @Test
    void playerCollideWithGhostTest() {
        collisionMap.collide(player, ghost);

        // player verification
        Mockito.verify(player, Mockito.times(1))
            .setKiller(ghost);
        Mockito.verify(player, Mockito.times(1))
            .setAlive(false);
        boolean isAlive = player.isAlive();
        Mockito.verify(player, Mockito.never()).addPoints(Mockito.anyInt());
        boolean score = player.getScore() == 0;

        // calculator verification
        Mockito.verify(calculator, Mockito.times(1))
            .collidedWithAGhost(player, ghost);
        Mockito.verify(calculator, Mockito.never())
            .consumedAPellet(player, pellet);

        // pellet verification
        Mockito.verify(pellet, Mockito.never()).leaveSquare();

        assertThat(isAlive).isFalse();
        assertThat(score).isTrue();
    }

    /**
     * Test the outcome that should happen when player collides with a pellet.
     */
    @Test
    void playerCollideWithPellet() {
        collisionMap.collide(player, pellet);

        // plaver verification
        Mockito.verify(player, Mockito.never()).setKiller(Mockito.any());
        Mockito.verify(player, Mockito.never()).setAlive(false);
        Mockito.verify(player, Mockito.times(1))
            .addPoints(Mockito.anyInt());

        // calculator verification
        Mockito.verify(calculator, Mockito.never())
            .collidedWithAGhost(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.times(1))
            .consumedAPellet(player, pellet);

        // pellet verification
        Mockito.verify(pellet, Mockito.times(1))
            .leaveSquare();
    }

    /**
     * Test the outcome that should happen when player collides with a player.
     */
    @Test
    void playerCollideWithPlayer() {
        collisionMap.collide(player, player);

        // plaver verification
        Mockito.verify(player, Mockito.never()).addPoints(Mockito.anyInt());
        Mockito.verify(player, Mockito.never()).setAlive(false);
        Mockito.verify(player, Mockito.never()).setKiller(ghost);

        // calculator verification
        Mockito.verify(calculator, Mockito.never())
            .consumedAPellet(player, pellet);
        Mockito.verify(calculator, Mockito.never())
            .collidedWithAGhost(player, ghost);

        // pellet verification
        Mockito.verify(pellet, Mockito.never()).leaveSquare();
    }


    /**
     * Test the outcome that should happen when ghost collides with a pellet.
     */
    @Test
    void ghostCollideWithPellet() {
        collisionMap.collide(ghost, pellet);

        // plaver verification
        Mockito.verify(player, Mockito.never()).addPoints(Mockito.anyInt());
        Mockito.verify(player, Mockito.never()).setAlive(false);
        Mockito.verify(player, Mockito.never()).setKiller(ghost);

        // calculator verification
        Mockito.verify(calculator, Mockito.never())
            .consumedAPellet(player, pellet);
        Mockito.verify(calculator, Mockito.never())
            .pacmanMoved(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.never())
            .collidedWithAGhost(player, ghost);

        // pellet verification
        Mockito.verify(pellet, Mockito.never()).leaveSquare();
    }

    /**
     * Test the outcome that should happen when ghost collides with a player.
     */
    @Test
    void ghostCollideWithPlayer() {
        collisionMap.collide(ghost, player);

        // plaver verification
        Mockito.verify(player, Mockito.times(1))
            .setKiller(ghost);
        Mockito.verify(player, Mockito.times(1))
            .setAlive(false);
        boolean isAlive = player.isAlive();
        Mockito.verify(player, Mockito.never()).addPoints(Mockito.anyInt());
        boolean score = player.getScore() == 0;

        // calculator verification
        Mockito.verify(calculator, Mockito.times(1))
            .collidedWithAGhost(player, ghost);
        Mockito.verify(calculator, Mockito.never()).pacmanMoved(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.never()).consumedAPellet(player, pellet);

        // pellet verification
        Mockito.verify(pellet, Mockito.never()).leaveSquare();

        assertThat(isAlive).isFalse();
        assertThat(score).isTrue();
    }

    /**
     * Test the outcome that should happen when ghost collides with a ghost.
     */
    @Test
    void ghostCollideWithGhost() {
        collisionMap.collide(ghost, ghost);

        // pellet verification
        Mockito.verify(pellet, Mockito.never()).leaveSquare();

        // plaver verification
        Mockito.verify(player, Mockito.never()).addPoints(Mockito.anyInt());
        Mockito.verify(player, Mockito.never()).setAlive(false);
        Mockito.verify(player, Mockito.never()).setKiller(ghost);

        // calculator verification
        Mockito.verify(calculator, Mockito.never()).consumedAPellet(player, pellet);
        Mockito.verify(calculator, Mockito.never()).pacmanMoved(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.never()).collidedWithAGhost(player, ghost);
    }

    /**
     * Test the outcome that should happen when pellet collides with a pellet.
     */
    @Test
    void pelletCollideWithPellet() {
        collisionMap.collide(pellet, pellet);

        // player verification
        Mockito.verify(player, Mockito.never()).addPoints(Mockito.anyInt());
        Mockito.verify(player, Mockito.never()).setAlive(false);
        Mockito.verify(player, Mockito.never()).setKiller(ghost);

        // calculator verification
        Mockito.verify(calculator, Mockito.never()).consumedAPellet(player, pellet);
        Mockito.verify(calculator, Mockito.never()).pacmanMoved(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.never()).collidedWithAGhost(player, ghost);
    }

    /**
     * Test the outcome that should happen when pellet collides with a player.
     */
    @Test
    void pelletCollideWithPlayer() {
        collisionMap.collide(pellet, player);

        // player verification
        Mockito.verify(player, Mockito.never()).setKiller(Mockito.any());
        Mockito.verify(player, Mockito.never()).setAlive(false);
        Mockito.verify(player, Mockito.times(1))
            .addPoints(Mockito.anyInt());

        // calculator verification
        Mockito.verify(calculator, Mockito.never())
            .collidedWithAGhost(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.never())
            .pacmanMoved(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.times(1))
            .consumedAPellet(player, pellet);

        // pellet verification
        Mockito.verify(pellet, Mockito.times(1))
            .leaveSquare();

    }

    /**
     * Test the outcome that should happen when pellet collides with a ghost.
     */
    @Test
    void pelletCollideWithGhost() {
        collisionMap.collide(pellet, ghost);

        // player verification
        Mockito.verify(player, Mockito.never()).addPoints(Mockito.anyInt());
        Mockito.verify(player, Mockito.never()).setAlive(false);
        Mockito.verify(player, Mockito.never()).setKiller(ghost);

        // calculator verification
        Mockito.verify(calculator, Mockito.never()).consumedAPellet(player, pellet);
        Mockito.verify(calculator, Mockito.never()).pacmanMoved(Mockito.any(), Mockito.any());
        Mockito.verify(calculator, Mockito.never()).collidedWithAGhost(player, ghost);
    }
}

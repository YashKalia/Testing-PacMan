package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.points.PointCalculator;

/**
 * Test suite for testing PlayerCollisions.
 */
public class PlayerCollisionsTest extends CollisionMapTest {

    /**
     * Returns a PlayerCollisions object that can be tested.
     * @param calculator
     * @return PlayerCollisions object.
     */
    @Override
    public PlayerCollisions getTestSubject(PointCalculator calculator) {
        return new PlayerCollisions(calculator);
    }
}

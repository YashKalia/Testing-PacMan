package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.points.PointCalculator;


/**
 * Test suite for testing DefaultPlayerInteractionMap.
 */
public class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    /**
     * Returns a DefaultPlayerInteractionMap object that can be tested.
     * @param calculator
     * @return DefaultInteractionMap object.
     */
    @Override
    public DefaultPlayerInteractionMap getTestSubject(PointCalculator calculator) {
        return new DefaultPlayerInteractionMap(calculator);
    }
}

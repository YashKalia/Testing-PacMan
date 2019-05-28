package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.points.PointCalculator;

public class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    @Override
    public DefaultPlayerInteractionMap getTestSubject(PointCalculator calculator) {
        return new DefaultPlayerInteractionMap(calculator);
    }
}

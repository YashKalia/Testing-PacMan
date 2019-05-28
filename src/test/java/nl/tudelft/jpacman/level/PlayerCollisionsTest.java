package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import org.mockito.Mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerCollisionsTest extends CollisionMapTest {

    @Override
    public PlayerCollisions getTestSubject(PointCalculator calculator) {
        return new PlayerCollisions(calculator);
    }
}

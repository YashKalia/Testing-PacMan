package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Abstract class to be used  for parallel testing.
 * Games and Launcher and their extensions.
 */
public abstract class GameAndLauncherTest {

    /**
     * game to test.
     */
    private Game game;
    /**
     * Method that will be overridden by extensions of this class to return.
     * objects that are extensions of Launcher or Launcher itself.
     * @return Launcher instance to be tested.
     */
    public abstract Launcher getLauncherTestSubject();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }


}

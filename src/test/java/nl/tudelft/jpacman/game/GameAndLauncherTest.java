package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.CollisionMap;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * Abstract class to be used  for parallel testing.
 * Games and Launcher and their extensions.
 */
public abstract class GameAndLauncherTest {

    /**
     * Method that will be overridden by extensions of this class to return.
     * objects that are extensions of Launcher or Launcher itself.
     * @return Launcher instance to be tested.
     */
    public abstract Launcher getLauncherTestSubject();

//    /**
//     * Method that will be overridden by extensions of this class to return.
//     * objects that are extensions of game.
//     * @return Game instance to be tested.
//     */
//    public abstract Game getGameTestSubject(Player player, Level level, PointCalculator calculator);
}

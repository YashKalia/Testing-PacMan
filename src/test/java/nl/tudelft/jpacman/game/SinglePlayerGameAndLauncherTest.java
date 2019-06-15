package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * Testing MultiLevelGame and MultiLevelLauncher.
 */
public class SinglePlayerGameAndLauncherTest extends GameAndLauncherTest {
    /**
     * Overriding so that we will be able to test Launcher.
     * @return a Launcher to get tested.
     */
    @Override
    public MultiLevelLauncher getLauncherTestSubject() {
        return new MultiLevelLauncher();
    }

//    /**
//     * Overriding so that we will be able to test SinglePlayerGame.
//     * @return a SinglePlayerGame to get tested.
//     */
//    @Override
//    public SinglePlayerGame getGameTestSubject(Player player, Level level, PointCalculator calculator) {
//        return new SinglePlayerGame(player, level, calculator);
//    }

}

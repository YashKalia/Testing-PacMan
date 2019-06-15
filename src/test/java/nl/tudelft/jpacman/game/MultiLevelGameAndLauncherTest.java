package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * Testing MultiLevelGame and MultiLevelLauncher.
 */
public class MultiLevelGameAndLauncherTest extends GameAndLauncherTest {

    /**
     * Overriding so that we will be able to test MultiLevelLauncher.
     * @return a MultiLevelLauncher to get tested.
     */
    @Override
    public MultiLevelLauncher getLauncherTestSubject() {
        return new MultiLevelLauncher();
    }

//    /**
//     * Overriding so that we will be able to test MultiLevelGame.
//     * @return a MultiLevelLauncher to get tested.
//     */
//    @Override
//    public MultiLevelGame getGameTestSubject(Player player, Level level, PointCalculator calculator) {
//        return new MultiLevelGame(player, level, calculator);
//    }

}

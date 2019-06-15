package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;

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


}

package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;

/**
 * Testing MultiLevelGame and MultiLevelLauncher.
 */
public class SinglePlayerGameAndLauncherTest extends GameAndLauncherTest {
    /**
     * Overriding so that we will be able to test Launcher.
     * @return a Launcher to get tested.
     */
    @Override
    public Launcher getLauncherTestSubject() {
        return new Launcher();
    }

}

package nl.tudelft.jpacman;


import nl.tudelft.jpacman.game.MultiLevelGame;

/**
 * Launcher that is used when we want to play Games with more than one Levels.
 */
public class MultiLevelLauncher extends Launcher {

    private MultiLevelGame multiGame;

    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }

}

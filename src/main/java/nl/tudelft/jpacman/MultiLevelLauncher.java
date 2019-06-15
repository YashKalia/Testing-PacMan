package nl.tudelft.jpacman;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

/**
 * Launcher that is used when we want to play Games with more than one Levels.
 */
public class MultiLevelLauncher extends Launcher {

    /**
     * the game of this launcher;
     */
    private MultiLevelGame multiGame;
    public static final List<String> DefaultFileNamesofLevels = MultiLevelLauncher.setDefault();
    private List<String> fileNamesofLevels = DefaultFileNamesofLevels;
    private List<Level> levels = new ArrayList<Level>();

    @SuppressFBWarnings(
        value = {"UWF_UNWRITTEN_FIELD"},
        justification = "This is just code that the assignment says to write"
    )

    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }


    @Override
    public MultiLevelGame makeGame() {
        for (String name : fileNamesofLevels) {
            super.withMapFile(name);
            levels.add(this.makeLevel());
        }
        GameFactory gf = getGameFactory();
        multiGame = (MultiLevelGame) gf.createMultiLevelGame(levels, loadPointCalculator());
        return multiGame;
    }

    public static ArrayList<String> setDefault() {
        ArrayList<String> list = new ArrayList<>();
        list.add("/board.txt");
        return list;
    }

    @Override
    public MultiLevelLauncher withMapFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        list.add(fileName);
        this.fileNamesofLevels = list;
        return this;
    }

    public static void main(String[] args) {
    new MultiLevelLauncher().launch();
    }

}

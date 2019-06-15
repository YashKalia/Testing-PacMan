package nl.tudelft.jpacman;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Launcher that is used when we want to play Games with more than one Levels.
 */
public class MultiLevelLauncher extends Launcher {

    /**
     * the game of this launcher.
     */
    private MultiLevelGame multiGame;
    public static final List<String> DEFAULTFILENAME = MultiLevelLauncher.setDefault();
    private List<String> fileNamesofLevels = DEFAULTFILENAME;
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

    /**
     * to always have a default filename.
     * @return an arraylist containing default filename.
     */
    public static List<String> setDefault() {
        List<String> list = new ArrayList<>();
        list.add("/board.txt");
        return list;
    }

    /**
     * provide the name of the files containing the maps.
     * @param fileNamesofLevels give the list of name of map for making levels.
     */
    public void setFileNamesofLevels(List<String> fileNamesofLevels) {
        this.fileNamesofLevels = fileNamesofLevels;
    }

    @Override
    public MultiLevelLauncher withMapFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        list.add(fileName);
        this.fileNamesofLevels = list;
        return this;
    }

    /**
     * launch main.
     * @param args empty.
     */
    public static void main(String[] args) {
        new MultiLevelLauncher().launch();
    }
}

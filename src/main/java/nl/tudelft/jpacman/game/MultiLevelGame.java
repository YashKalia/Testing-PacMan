package nl.tudelft.jpacman.game;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * A game with one player and a single level.
 *
 * @author Jeroen Roosen
 */
public class MultiLevelGame extends Game {

    /**
     * The player of this game.
     */
    private final Player player;

    /**
     * The level of this game.
     */
    private Level level;

    /**
     * list of all levels.
     */
    private final List<Level> levels;

    /**
     *current level.
     */
    private int currentLevelNumber;

    /**
     * total number of levels.
     */
    private int totalLevels;
    /**
     * Create a new single player game for the provided level and player.
     *
     * @param player
     *            The player.
     * @param level
     *            The level.
     * @param pointCalculator
     *            The way to calculate points upon collisions.
     */
    protected MultiLevelGame(Player player, Level level, PointCalculator pointCalculator) {
        super(pointCalculator);

        assert player != null;
        assert level != null;

        this.player = player;
        this.level = level;
        this.level.registerPlayer(player);
        List<Level> list = new ArrayList<>();
        list.add(level);
        this.levels = list;
    }

    protected MultiLevelGame(Player player, List<Level> levels, PointCalculator pointCalculator) {
        super(pointCalculator);

        assert player != null;
        assert level != null;

        this.levels = levels;
        this.currentLevelNumber = 0;
        this.totalLevels = levels.size();
        this.player = player;
        this.level = levels.get(0);
        this.level.registerPlayer(player);
    }


    /**
     * Method for going to next level.
     */
    public void goToNextLevel() {
        if((!lastLevelReached()) && level.remainingPellets() == 0) {
            this.level = levels.get(currentLevelNumber + 1);
            this.currentLevelNumber++;
        }
    }

    /**
     * Boolean signifying if we are at last level.
     * @return true for last level reached, false otherwise;
     */
    public boolean lastLevelReached() {
        return currentLevelNumber == totalLevels - 1;
    }

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }

    @Override
    public Level getLevel() {
        return level;
    }
}

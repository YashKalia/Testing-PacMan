package nl.tudelft.jpacman.game;


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
     * The current level of this game.
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
     * constructor for multilevel game.
     * @param player player that plays.
     * @param levels list of levels.
     * @param pointCalculator to calculate points.
     */
    protected MultiLevelGame(Player player, List<Level> levels, PointCalculator pointCalculator) {
        super(pointCalculator);

        assert player != null;
        assert levels != null;

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
        if ((!lastLevelReached()) && level.remainingPellets() == 0) {
            this.level = levels.get(currentLevelNumber + 1);
            this.currentLevelNumber++;
            this.level.registerPlayer(getPlayers().get(0));
        }
    }

    /**
     * Boolean signifying if we are at last level.
     * @return true for last level reached, false otherwise;
     */
    @Override
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

    @Override
    public void levelWon() {
        stop();
        this.goToNextLevel();
    }

    /**
     * the current level number.
     * @return number of level;
     */
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }


    @Override
    public boolean allLevelsWon() {
        return this.getLevel().remainingPellets() == 0 && this.lastLevelReached();
    }

}

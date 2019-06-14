package nl.tudelft.jpacman.game;


import java.util.List;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * Game with multiple levels.
 */
public class MultiLevelGame extends Game {

    /**
     * The player of this game.
     */
    private final Player player;

    /**
     * The level of this game.
     */
    private final Level level;

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

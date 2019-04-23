import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.RuntimeException;

/**
 * A brand new points calculator, that does more than
 * just adding points.
 */
public class AmazingPointCalculator implements PointCalculator {

    private int pelletsEaten;
       
    
    @Override
    public void collidedWithAGhost(Player player, Ghost ghost) {
        // no points for colliding with a ghost
    }

    @Override
    public void consumedAPellet(Player player, Pellet pellet) {
    
        final int maxPelletScore = 28;
        final int maxPelletCrash = 34;
        final int deductScore = -15;
        final int maxPelletOverflow = 15;
        
        pelletsEaten++;
        
        // 1. If 28 Pellets eaten and score is higher than 0, deduct 15, else add 10
        if (pelletsEaten >= maxPelletScore && player.getScore() > 0) {
            player.addPoints(deductScore);
        } else {
            player.addPoints(pellet.getValue());
        }

        // 2. If 35 Pellets eaten and move NORTH, crash.
        // 3. If 35 Pellets eaten, and direction is anything but North, die.
        if (pelletsEaten > maxPelletCrash && player.getDirection() == Direction.NORTH) {
            throw new RuntimeException("Found a crash!");
        } else if (pelletsEaten > maxPelletCrash) {
            player.setAlive(false); 
        }

        // 4. If sequence of moves is West, huge increment to score
        if (pelletsEaten >= maxPelletOverflow && player.getDirection() == Direction.WEST) {
            player.addPoints(Integer.MAX_VALUE);
        }

    }

    @Override
    public void pacmanMoved(Player player, Direction direction) {
        // no points for moving
    }
}

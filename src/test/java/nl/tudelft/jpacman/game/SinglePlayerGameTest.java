package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



import static org.assertj.core.api.Assertions.assertThat;

/**
 * Class for testing certain methods in SinglePlayerGame and the one inherited from Game class.
 */
public class SinglePlayerGameTest {

    /**
     * attribute needed for a game object.
     */
    private PointCalculator calculator;
    /**
     * attribute needed for a single player game object.
     */
    private Level level;
    /**
     * attribute needed for a single player game object.
     */
    private Player player;
    /**
     * game object used to test methods in game class.
     */
    private SinglePlayerGame game;

    /**
     * set up method called before running each test.
     */
    @BeforeEach
    void setUp() {
        calculator = Mockito.mock(DefaultPointCalculator.class);
        level = Mockito.mock(Level.class);
        player = Mockito.mock(Player.class);
        game = new SinglePlayerGame(player, level, calculator);
    }

    /**
     * exercises 2 of the total 4 possible decision outcomes of the.
     * start method in game class.
     */
    @Test
    void testStartFirstIfStatementFalseAndSecondIfStatementTrue() {

        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(level.remainingPellets()).thenReturn(1);
        Mockito.doNothing().when(level).start();
        Mockito.doNothing().when(level).addObserver(Mockito.any());

        game.start();

        // if these assertions pass it means that the code in the first if statement
        // (which is a return  statement)
        // was not executed and that the code the second if statement is executed
        // first if statement in start method -> false outcome exercised
        // second if statement in start method -> true outcome exercised

        assertThat(game.isInProgress()).isTrue();
        Mockito.verify(level, Mockito.times(1)).addObserver(game);
        Mockito.verify(level, Mockito.times(1)).start();
    }

    /**
     * exercises the first if statement in start method -> true outcome.
     *
     * the annotations of checkreturn value is put here because SpotBugs gives a false positive.
     * the alarm for this false positive being
     * "This code calls a method and ignores the return value"
     * at Mockito.verify(level, Mockito.times(1)).isAnyPlayerAlive();
     * But I  am just trying  to trying to verify using mockito so I don't need the return value.
     */
    @Test
    void testStartFirstIfStatementTrue() {

        Mockito.when(level.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(level.remainingPellets()).thenReturn(1);
        Mockito.doNothing().when(level).start();
        Mockito.doNothing().when(level).addObserver(Mockito.any());

        // for setting IsInProgress attribute of game object to true
        game.start();

        assertThat(game.isInProgress()).isTrue();

        // invoking the method a second time will cause the
        // first if statement in be evaluated to true
        // and the return statement in that if block will be executed
        game.start();

        // If following verifications pass, then we know that in the second time
        // start method is invoked the lines after the return statement are not executed
        // which implies that the true decision outcome of the first if statement is exercised

        // These can be run but it causes spotBugs warning which I don't know how to suppress
        //        Mockito.verify(level, Mockito.times(1)).isAnyPlayerAlive();
        //        Mockito.verify(level, Mockito.times(1)).remainingPellets();

        Mockito.verify(level, Mockito.times(1)).addObserver(game);
        Mockito.verify(level, Mockito.times(1)).start();
    }

    /**
     * exercises the second if-statement in start method -> false outcome.
     */
    @Test
    void testStartSecondIfStatementFalse() {

        Mockito.when(level.isAnyPlayerAlive()).thenReturn(false);
        Mockito.when(level.remainingPellets()).thenReturn(0);
        Mockito.doNothing().when(level).start();
        Mockito.doNothing().when(level).addObserver(Mockito.any());

        game.start();

        // If following verifications pass, then we know that the decision outcome False
        // of the second if statement in the start method is exercised.
        Mockito.verify(level, Mockito.never()).addObserver(game);
        Mockito.verify(level, Mockito.never()).start();
        assertThat(game.isInProgress()).isFalse();
    }
}

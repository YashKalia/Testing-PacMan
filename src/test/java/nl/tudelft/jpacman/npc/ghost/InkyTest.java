package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the next nextAiMove method of Inky.
 */
public class InkyTest {

    /**
     * GhostMapParser, Player and List of Strings for making a Level
     * which will be used in tests.
     */
    private GhostMapParser ghostMapParser;
    private Player player;
    private List<String> text;

    /**
     * Instantiates a ghostMapParser & player & text for making a level.
     */
    @BeforeEach
    void setUp() {
        PacManSprites pacManSprites = new PacManSprites();
        DefaultPointCalculator calculator = new DefaultPointCalculator();
        GhostFactory ghostFactory = new GhostFactory(pacManSprites);
        BoardFactory boardFactory = new BoardFactory(pacManSprites);
        LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory, calculator);
        ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
        player = playerFactory.createPacMan();
        text = new ArrayList<>();
    }

    /**
     * This test case belongs to the partition of Blinky is not on the same board as Inky.
     */
    @Test
    void testBlinkyNotOnBoard() {
        text.add("##P     I##");
        player.setDirection(Direction.EAST);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * This test case belongs to the partition of Inky is not on the board.
     */
    @Test
    void testInkyNotOnBoard() {
        text.add("##P     B##");
        player.setDirection(Direction.EAST);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNull();
    }

    /**
     * This test case belongs to the partition of Player is not on the same board as Inky.
     */
    @Test
    void testPlayerNotOnBoard() {
        text.add("##B     I##");
        Level level = ghostMapParser.parseMap(text);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * This test case belongs to the partition of:
     * Player and  Blinky is not on the same board as Inky.
     */
    @Test
    void testBlinkyAndPlayerNotOnBoard() {
        text.add("##....   I##");
        Level level = ghostMapParser.parseMap(text);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * This test case belongs to the partition of there exists no shortest path,
     * that Inky can take to reach the destination square that Inky must reah/occupy.
     */
    @Test
    void testNoPathBetweenInkyAndDestination() {
        text.add("##              P  B #   I##");
        player.setDirection(Direction.WEST);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * This test case belongs to the partition of:
     * Inky is alongside Blinky and Blinky is behind Player.
     * (And also the requirements/conditions mentioned in assignment 1 report exercise 11 are met).
     * Testing that Inky doest move away from Player.
     */
    @Test
    void testInkyDoesNotMoveAwayFromPlayer() {
        text.add("##        ..P....BI               ##");
        player.setDirection(Direction.WEST);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isNotEqualTo(Optional.of(Direction.EAST));
    }

    /**
     * This test case belongs to the partition of:
     * Inky is ahead of Player and Blinky is behind Player.
     * (And also the requirements/conditions mentioned in assignment 1 report exercise 11 are met).
     * Testing that Inky doesn't move towards Player.
     */
    @Test
    void testInkyDoesNotMoveTowardsPlayer() {
        text.add("##                ##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..I.....##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..P.....##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..B.....##");
        player.setDirection(Direction.NORTH);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isNotEqualTo(Optional.of(Direction.SOUTH));
    }



    /**
     * This test case belongs to the partition of:
     * Inky is alongside Blinky and Blinky is behind Player.
     * (And also the requirements/conditions mentioned in assignment 1 report exercise 11 are met).
     */
    @Test
    void testInkyAlongsideBlinkyBehindPlayer() {
        text.add("##        ..P....BI##");
        player.setDirection(Direction.WEST);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * This test case belongs to the partition of:
     * Inky is ahead of Player and Blinky is behind Player.
     * (And also the requirements/conditions mentioned in assignment 1 report exercise 11 are met).
     */
    @Test
    void testInkyAheadOfPlayer() {
        text.add("##                ##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..I.....##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..P.....##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..B.....##");
        player.setDirection(Direction.NORTH);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.NORTH));
    }
    /**
     * Bad weather case where there is
     * no path to inky's destination.
     */
    @Test
    void testInkyAlreadyOnDestination() {
        text.add("##                ##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..#.....##");
        text.add("##        ..I.....##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..P.....##");
        text.add("##        ........##");
        text.add("##        ..B.....##");

        player.setDirection(Direction.NORTH);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Bad weather case where Inky is already on the destination where Inky should be.
     */
    @Test
    void testNoPathToInkyDestination() {
        text.add("##                ##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..I.....##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ........##");
        text.add("##        ..P.....##");
        text.add("##        ........##");
        text.add("##        ..B.....##");

        player.setDirection(Direction.NORTH);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }
}

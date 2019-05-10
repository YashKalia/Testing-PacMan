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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test case for Clyde.
 */
public class ClydeTest {
    private GhostMapParser ghostMapParser;
    private Player player;
    private List<String> text;

    private LevelFactory levelFactory;
    private GhostFactory ghostFactory;
    private BoardFactory boardFactory;

    /**
     * SetUp method.
     */
    @BeforeEach
    void setUp() {
        boardFactory = new BoardFactory(new PacManSprites());
        ghostFactory = new GhostFactory(new PacManSprites());
        levelFactory = new LevelFactory(new PacManSprites(),
            new GhostFactory(new PacManSprites()),
            new DefaultPointCalculator());
        ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        PlayerFactory playerFactory = new PlayerFactory(new PacManSprites());
        player = playerFactory.createPacMan();
        text = new ArrayList<String>();
    }


    /**
     * Tests if Clyde moves toward PacMan when the distance is more than 8.
     */
    @Test
    void farawaygoodweatherTest() {
        text.add("##############");
        text.add("#P.........C #");
        text.add("##############");
        Level level = ghostMapParser.parseMap(text);
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);
        Clyde thisisclyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(Optional.of(Direction.WEST), thisisclyde.nextAiMove());

    }
    //###p c###


    /**
     * Tests if Clyde doesn't moves away from PacMan when the distance is more than 8.
     */
    @Test
    void farawaybadweatherTest() {
        text.add("#P          C#");
        Level level = ghostMapParser.parseMap(text);

        level.registerPlayer(player);
        Clyde thisisclyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotEquals(thisisclyde.nextAiMove(), Direction.EAST);

    }

    /**
     * Tests if Clyde runs away from PacMan when the distance is less than 8.
     */
    @Test
    void closebygoodweatherTest() {
        text.add("#########");
        text.add("#       #");
        text.add("#C      #");
        text.add("#       #");
        text.add("#P      #");
        text.add("#########");

        player.setDirection(Direction.NORTH);
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);

        Clyde thisisclyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(thisisclyde.nextAiMove(), Optional.of(Direction.NORTH));

    }

    /**
     * Belongs to the on case when distance b/w Player and Clyde is exactly 8.
     */
    @Test
    void exactly8SpacesApartGoodWeatherCase() {
        text.add("#P       C#");
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(clyde.nextAiMove(), Optional.of(Direction.EAST));
    }


    /**
     * Tests if Clyde does not move toward Pacman if distance is less than 8.
     */
    @Test
    void closebybadweatherTest() {
        text.add("#P    C#");
        Level level = ghostMapParser.parseMap(text);

        level.registerPlayer(player);
        Clyde thisisclyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNotEquals(Optional.of(Direction.WEST), thisisclyde.nextAiMove());

    }

    /**
     * Bad weather case tht Clyde is not on Board.
     */
    @Test
    void clydeNotOnBoardBadWeatherTest() {
        text.add("#P    I#");
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Clyde thisisclyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertNull(thisisclyde);
    }

    /**
     * Belongs to the partition in which player is not on board.
     */
    @Test
    void playerNotOnBoardBadWeatherTest() {
        text.add("##B     C##");
        Level level = ghostMapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(clyde.nextAiMove(), Optional.empty());

    }

    /**
     * Belongs to the partition in which there is no path between player and clyde.
     */
    @Test
    void thereisnopath() {
        text.add("##              P  B #   C##");
        Level level = ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Clyde thisisclyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(Optional.empty(), thisisclyde.nextAiMove());


    }


}

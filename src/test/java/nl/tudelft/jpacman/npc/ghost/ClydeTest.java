package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ClydeTest {
    GhostMapParser ghostMapParser;
    Player player;
    List<String> text;

    LevelFactory levelFactory;
    GhostFactory ghostFactory;
    BoardFactory boardFactory;

    @BeforeEach
    void SetUp(){
        boardFactory=new BoardFactory(new PacManSprites());
        ghostFactory=new GhostFactory(new PacManSprites());
        levelFactory = new LevelFactory(new PacManSprites(), new GhostFactory(new PacManSprites()), new DefaultPointCalculator());
        ghostMapParser=new GhostMapParser(levelFactory,boardFactory,ghostFactory);
        PlayerFactory playerFactory = new PlayerFactory(new PacManSprites());
        player = playerFactory.createPacMan();
        text=new ArrayList<String>();
    }

    /*
    Tests if Clyde moves toward PacMan when the distance is more than 8.
     */
    @Test
    void farawaygoodweatherTest(){
        text.add("##############");
        text.add("#P.........C #");
        text.add("##############");
        Level level=ghostMapParser.parseMap(text);
        System.out.println(level.getBoard().getHeight());
        System.out.println(level.getBoard().getWidth());
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);
        System.out.println(player.getSquare());
        Clyde thisisclyde=Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        assertEquals( Optional.of(Direction.WEST),thisisclyde.nextAiMove());

    }
    //###p c###

    /*
    Tests if Clyde doesn't moves away from PacMan when the distance is more than 8.
     */
    @Test
    void farawaybadweatherTest() {
        text.add("#P          C#");
        Level level=ghostMapParser.parseMap(text);

        level.registerPlayer(player);
        Clyde thisisclyde=Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        assertNotEquals(thisisclyde.nextAiMove(),Direction.EAST);

    }


    /*
    Tests if Clyde runs away from PacMan when the distance is less than 8.
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
        Level level=ghostMapParser.parseMap(text);
        level.registerPlayer(player);

        Clyde thisisclyde=Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        System.out.println(Navigation.findNearest(Player.class, thisisclyde.getSquare()));
        assertEquals(thisisclyde.nextAiMove(),Optional.of(Direction.NORTH));

    }

    @Test
    void Exactly8SpacesApartGoodWeatherCase() {
        text.add("#P       C#");
        Level level=ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(clyde.nextAiMove(),Optional.of(Direction.EAST));
    }

    /*
     Tests if Clyde doesn't move toward PacMan when the distance is less than 8.
      */
    @Test
    void closebybadweatherTest() {
        text.add("#P    C#");
        Level level=ghostMapParser.parseMap(text);

        level.registerPlayer(player);
        Clyde thisisclyde=Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        System.out.println(Navigation.findNearest(Player.class, thisisclyde.getSquare()));
        System.out.println(thisisclyde.nextAiMove());
        assertNotEquals(Optional.of(Direction.WEST),thisisclyde.nextAiMove());

    }

    /**
     * Bad weather case tht Clyde is not on Board.
     */
    @Test
    void ClydeNotOnBoardBadWeatherTest(){
        text.add("#P    I#");
        Level level=ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Clyde thisisclyde=Navigation.findUnitInBoard(Clyde.class,level.getBoard());
        assertNull(thisisclyde);
    }

    @Test
    void PlayerNotOnBoardBadWeatherTest() {
        text.add("##B     C##");
        Level level = ghostMapParser.parseMap(text);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(clyde.nextAiMove(),Optional.empty());

    }

    @Test
    void Thereisnopath() {
        text.add("##              P  B #   C##");
        Level level=ghostMapParser.parseMap(text);
        level.registerPlayer(player);
        Clyde thisisclyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertEquals(Optional.empty(),thisisclyde.nextAiMove());


    }








}

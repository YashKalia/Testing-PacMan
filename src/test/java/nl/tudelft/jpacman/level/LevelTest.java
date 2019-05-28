package nl.tudelft.jpacman.level;


import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;

import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * level test class.
 */
public class LevelTest {
    private Player player;
    private MapParser mapParser;
    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private PlayerFactory playerFactory;
    private GhostFactory ghostFactory;
    private char[][] map;


    /**
     * Getter for ghostfactory object.
     * @return GhostFactory object.
     */
    GhostFactory getGhostFactory() {
        return ghostFactory;
    }


    /**
     * SetUp method.
     */
    @BeforeEach
    void setUp() {
        boardFactory = Mockito.mock(BoardFactory.class);
        levelFactory = Mockito.mock(LevelFactory.class);
        mapParser = new MapParser(levelFactory, boardFactory);
        ghostFactory = Mockito.mock(GhostFactory.class);
        playerFactory = new PlayerFactory(new PacManSprites());
        Blinky b = Mockito.mock(Blinky.class);
        Pellet p = Mockito.mock(Pellet.class);
        Mockito.when(levelFactory.createGhost()).thenReturn(b);
        Mockito.when(levelFactory.createPellet()).thenReturn(p);
        player = playerFactory.createPacMan();



    }
    //Map is segregated into different characters and tested separately.
    // map = new char[][]{
    //            {'#'}, {' '}, {'G'}, {'.'}, {'P'}
    //        };

    /**
     * good weather test case for a map containing only the wall.
     */
    @Test
    void parsewallTest() {
        map = new char[][]{
            {'#'}
        };
        player.setDirection(Direction.WEST);
        mapParser.parseMap(map);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory).createBoard((Mockito.any()));
        Mockito.verify(boardFactory).createWall();


    }

    /**
     * good weather test case for a map containing only an empty space.
     */
    @Test
    void parseemptyspaceTest() {
        map = new char[][]{
            {' '}
        };
        player.setDirection(Direction.WEST);
        mapParser.parseMap(map);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory).createBoard((Mockito.any()));
        Mockito.verify(boardFactory).createGround();
    }

    /**
     * good weather test case for a map containing only a Ghost.
     */
    @Test
    void parseGhostTest() {
        map = new char[][]{
            {'G'}
        };
        player.setDirection(Direction.WEST);
        mapParser.parseMap(map);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory).createBoard((Mockito.any()));
        Mockito.verify(levelFactory).createGhost();
        Mockito.verify(boardFactory).createGround();

    }

    /**
     * good weather test case for a map containing only a pellet.
     */
    @Test
    void parsepelletTest() {
        map = new char[][]{
            {'.'}
        };
        player.setDirection(Direction.WEST);
        mapParser.parseMap(map);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory).createBoard((Mockito.any()));
        Mockito.verify(boardFactory).createGround();
        Mockito.verify(levelFactory).createPellet();
    }

    /**
     * good weather test case for a map containing only the player.
     */
    @Test
    void parsePlayerTest() {
        map = new char[][]{
            {'P'}
        };
        player.setDirection(Direction.WEST);
        mapParser.parseMap(map);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory).createBoard((Mockito.any()));
        Mockito.verify(boardFactory).createGround();

    }

}

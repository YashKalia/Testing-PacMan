package nl.tudelft.jpacman.level;


import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * level test class.
 */
public class MapParserTest {
    private Player player;
    private MapParser mapParser;
    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private PlayerFactory playerFactory;
    private GhostFactory ghostFactory;
    private Ghost ghost;
    private char[][] map;
    private List<String> stringmap;
    private Pellet p;

    private static final int NUM1 = 5;
    private static final int NUM2 = 2;
    private static final int NUM3 = 4;

    /**
     * Getter for ghostfactory object.
     *
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
        ghost = Mockito.mock(Ghost.class);
        Blinky b = Mockito.mock(Blinky.class);
        p = Mockito.mock(Pellet.class);
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
        Square square;
        square = Mockito.mock(Square.class);
        map = new char[][]{
            {'G'}
        };
        Mockito.when(levelFactory.createGhost()).thenReturn(ghost);
        Mockito.when(boardFactory.createGround()).thenReturn(square);
        player.setDirection(Direction.WEST);
        mapParser.parseMap(map);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory).createBoard((Mockito.any()));
        Mockito.verify(levelFactory).createGhost();
        Mockito.verify(boardFactory).createGround();
        Mockito.verify(ghost).occupy(square);
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

    /**
     * Test for alternate parseMap method-the one with List<String>
     * as parameter.
     */
    @Test
    void listStringparsemapTest() {
        stringmap = new ArrayList<>();
        stringmap.add("#");
        stringmap.add(" ");
        stringmap.add(".");
        stringmap.add("P");
        stringmap.add("G");
        player.setDirection(Direction.WEST);
        mapParser.parseMap(stringmap);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());


    }

    /**
     * Test for alternate parseMap method-the one with string input
     * as parameter.
     *
     * @throws IOException if IO exception is thrown.
     */
    @Test
    void stringparsemapTest() throws IOException {
        mapParser.parseMap("/mapFile");
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory, Mockito.times(NUM1)).createWall();
        Mockito.verify(boardFactory, Mockito.times(NUM2)).createGround();
    }

    /**
     * Test for alternate parseMap method-the one with the input stream
     * as parameter.
     *
     * @throws IOException if IO exception is thrown.
     */
    @Test
    void inputstreamparsemapTest() throws IOException {
        String map = "# .PG";
        InputStream inputStream = new ByteArrayInputStream(map.getBytes(Charset.forName("UTF-8")));
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        mapParser.parseMap(inputStream);
        Mockito.verify(levelFactory).createLevel(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(boardFactory, Mockito.times(1)).createWall();
        Mockito.verify(boardFactory, Mockito.times(NUM3)).createGround();
        Mockito.verify(levelFactory, Mockito.times(1)).createPellet();
        Mockito.verify(levelFactory, Mockito.times(1)).createGhost();

    }

    //bad weather cases-excericse 2 starts here.
    //Check map format bad weather cases.


    /**
     * Testing if exception is raised if null input is provided.
     */
    @Test
    void testnullinput() {
        List<String> thelist = null;
        try {
            mapParser.parseMap(thelist);
        } catch (Exception e) {
            assertThat(e instanceof PacmanConfigurationException);
            assertTrue((e.getMessage().contains("Input text cannot be null.")));
        }


    }

    /**
     * Testing if exception is raised if empty list is provided.
     */
    @Test
    void testemptyinput() {
        List<String> list = new ArrayList<>();
        try {
            mapParser.parseMap(list);
        } catch (Exception e) {
            assertThat(e instanceof PacmanConfigurationException);
            assertTrue((e.getMessage().contains("Input text must consist of at least 1 row.")));
        }
    }

    /**
     * Testing if exception is raised if input is "" .
     */
    @Test
    void testwidthinput() {
        List<String> list = new ArrayList<>();
        list.add("");
        try {
            mapParser.parseMap(list);
        } catch (Exception e) {
            assertThat(e instanceof PacmanConfigurationException);
            assertTrue((e.getMessage().contains("Input text lines cannot be empty.")));
        }

    }

    /**
     * Testing if exception is raised if input of unequal length is provided.
     */
    @Test
    void testunequallineinput() {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("abcdef");
        try {
            mapParser.parseMap(list);
        } catch (Exception e) {
            assertThat(e instanceof PacmanConfigurationException);
            assertTrue((e.getMessage().contains("Input text lines are not of equal width.")));
        }


    }
}

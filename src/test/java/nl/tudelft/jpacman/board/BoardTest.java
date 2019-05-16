package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * A class for testing the methods in the Board class.
 */
public class BoardTest {
    private Board board;
    private Square[][] grid;
    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;

    /**
     * Set up the board.
     */
    @BeforeEach
    void setUp() {
        grid = new BasicSquare[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                grid[i][j] = new BasicSquare();
            }
        }
    }


    /**
     * Test the constructor of Board class.
     */
    @Test
    void testBoardConstructor() {
        grid = new BasicSquare[1][1];
        grid[0][0] = new BasicSquare();
        Board board = new Board(grid);
        assertThat(board.squareAt(0, 0).equals(grid[0][0]));
    }

    /**
     * Parameterized test for testing that squares are withing game borders.x->Width,y->Height,expected->expected value.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     * @param expected expected boolean value.
     */
    @ParameterizedTest
    @CsvSource({"0,0,true", "-1,-1,false", "5,5,false", "4,4,true","1,0,true","2,-1,false","3,5,false","0,4,true"})
    void withinBordersTest(int x, int y, boolean expected) {
        board = new Board(grid);
        assertEquals(board.withinBorders(x, y), expected);

    }


}

package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * A class for testing the methods in the Board class.
 */
public class BoardTest {


    /**
     * Test the constructor of Board class.
     */
    @Test
    void testBoardConstructor() {
        Square[][] grid = new BasicSquare[1][1];
        grid[0][0] = new BasicSquare();
        Board board = new Board(grid);
        assertThat(board.squareAt(0, 0).equals(grid[0][0]));
    }
}

package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BoardTest {


    @Test
    void testBoardConstructor() {
        Square[][] grid = new BasicSquare[1][1];
        grid[0][0] = new BasicSquare();
        Board board = new Board(grid);
        assertThat(board.squareAt(0,0).equals(grid[0][0]));
    }

    @Test
    void testBoardConstructorWithNull() {
        Square[][] grid = new BasicSquare[1][1];
        Board board = new Board(grid);
        assertThat(board.squareAt(0,0)).isNull();
    }


}

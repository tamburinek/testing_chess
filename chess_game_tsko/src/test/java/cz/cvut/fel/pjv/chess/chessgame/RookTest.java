package cz.cvut.fel.pjv.chess.chessgame;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import cz.cvut.fel.pjv.chess.chessgame.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RookTest {

    private Board mockedBoard;

    @BeforeEach
    public void mockSetUp() {
        //arrange - mock construction of chess board
        mockedBoard = mock(Board.class);
        when(mockedBoard.getSquareArray()).thenReturn(fillBoard());
    }

    @ParameterizedTest(name = "Test if rook with coordinates x = {0} and y = {1} can step on square {2},{3} and cannot step on square {4},{5}.")
    @CsvSource({"0, 0, 0, 7, 4, 4","0, 4, 4, 4, 7, 7","4, 4, 4, 6, 0, 0"})
    public void rookMovesTest(int x, int y, int x1, int y1, int x2, int y2) {

        //arrange - assign piece to to specific position on the virtual board
        Square square = new Square(0, x, y, true);
        Rook rook = new Rook(0, square);
        square.put(rook);

        //act - create board fields from given coordinates
        LinkedList<Square> squareList = (LinkedList<Square>) rook.getLegalMoves(mockedBoard);
        Square rightMove = new Square(0, x1, y1, true);
        Square wrongMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(isIn(rightMove, squareList));
        assertFalse(isIn(wrongMove, squareList));
    }

    @ParameterizedTest(name = "Test how many squares can rook with coordinates x = {0} and y = {1} move to - expected = {2}")
    @CsvSource({"0, 0, 14","0, 4, 14","4, 4, 14"})
    public void knightMovesCountTest(int x, int y, int count) {

        //arrange - assign piece to to specific position on the virtual board
        Square square = new Square(0, x, y, true);
        Rook rook = new Rook(0, square);
        square.put(rook);

        //act - create board fields from given coordinates
        LinkedList<Square> squareList = (LinkedList<Square>) rook.getLegalMoves(mockedBoard);
        int result = squareList.size();

        //assert
        assertEquals(count, result);
    }

    private Square[][] fillBoard() {
        Square[][] board = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1))
                    board[x][y] = new Square(0, y, x, true);
                else
                    board[x][y] = new Square(1, y, x, true);
            }
        }
        return board;
    }

    private boolean isIn(Square square, LinkedList<Square> squareList) {
        for (Square square1 : squareList) {
            if (square.getXNum() == square1.getXNum() && square.getYNum() == square1.getYNum())
                return true;
        }
        return false;
    }
}

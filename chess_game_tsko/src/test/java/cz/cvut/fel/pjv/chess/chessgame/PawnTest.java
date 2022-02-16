package cz.cvut.fel.pjv.chess.chessgame;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import cz.cvut.fel.pjv.chess.chessgame.piece.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PawnTest {

    private Board mockedBoard;

    @BeforeEach
    public void mockSetUp() {
        //arrange - mock construction of chess board
        mockedBoard = mock(Board.class);
        when(mockedBoard.getSquareArray()).thenReturn(fillBoard());
    }

    @ParameterizedTest(name = "Test if pawn with color {0} and coordinates x = {1} and y = {2} can step on square {3},{4} and cannot step on square {5},{6}.")
    @CsvSource({"0, 0, 0, 0, 2, 0, 3", "0, 0, 4, 0, 6, 1, 7", "0, 4, 4, 4, 5, 4, 7", "1, 7, 7, 7, 5, 7, 4", "1, 7, 4, 7, 2, 7, 1", "1, 4, 4, 4, 2, 4, 1"})
    public void pawnMovesTest(int color, int x, int y, int x1, int y1, int x2, int y2) {

        //arrange - assign piece to specific position of the virtual board
        Square square = new Square(0, x, y, true);
        Pawn pawn = new Pawn(color, square);
        square.put(pawn);

        //act - create board fields from given coordinates
        LinkedList<Square> squareList = (LinkedList<Square>) pawn.getLegalMoves(mockedBoard);
        Square rightMove = new Square(0, x1, y1, true);
        Square wrongMove = new Square(0, x2, y2, true);

        //assert
        assertTrue(isIn(rightMove, squareList));
        assertFalse(isIn(wrongMove, squareList));
    }

    @ParameterizedTest(name = "Test how many squares can pawn with color {0} and coordinates x = {1} and y = {2} move to - expected = {3}. First move boolean depends on {4}.")
    @CsvSource({"0, 0, 0, 2, 0", "0, 0, 0, 1, 1", "1, 7, 7, 2, 0", "1, 7, 7, 1, 1"})
    public void pawnMovesCountTest(int color, int x, int y, int count, int firstMove) {

        //arrange - assign piece to specific position on the virtual board
        Square square = new Square(0, x, y, true);
        Pawn pawn = new Pawn(color, square);
        setIfWasMoved(pawn, firstMove);
        square.put(pawn);

        //act - create board fields from given coordinates
        LinkedList<Square> squareList = (LinkedList<Square>) pawn.getLegalMoves(mockedBoard);
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

    private void setIfWasMoved(Pawn pawn, int bool) {
        if (bool == 1)
            pawn.setWasMoved(true);
    }
}

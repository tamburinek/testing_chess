package cz.cvut.fel.pjv.chess.chessgame;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import cz.cvut.fel.pjv.chess.chessgame.piece.Bishop;
import cz.cvut.fel.pjv.chess.chessgame.piece.Knight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveTest {

    @Test
    public void MoveTest_PieceCanMakeMove(){

        //assert
        Board board = new Board();
        board.initializePieces();
        Square[][] squares = board.getSquareArray();

        Square square = new Square(0, 0, 0, true);
        Knight knight = new Knight(0, square);

        Square square2 = new Square(0, 2, 1, true);
        Bishop bishop2 = new Bishop(1, square2);
        square2.put(bishop2);

        assertTrue(knight.moveForTest(square2,board));
    }

    @Test
    public void MoveTest_PieceCannotMakeMove(){

        //assert
        Board board = new Board();
        board.initializePieces();
        Square[][] squares = board.getSquareArray();

        Square square = new Square(0, 0, 0, true);
        Knight knight = new Knight(0, square);

        Square square2 = new Square(0, 2, 1, true);
        Bishop bishop2 = new Bishop(0, square2);
        square2.put(bishop2);

        assertFalse(knight.moveForTest(square2,board));
    }
}

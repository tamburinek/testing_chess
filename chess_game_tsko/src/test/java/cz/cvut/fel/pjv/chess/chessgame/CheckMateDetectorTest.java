package cz.cvut.fel.pjv.chess.chessgame;

import cz.cvut.fel.pjv.chess.chessgame.board.CheckmateDetector;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import cz.cvut.fel.pjv.chess.chessgame.piece.Bishop;
import cz.cvut.fel.pjv.chess.chessgame.piece.King;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckMateDetectorTest {

    @Test
    public void TestMoveTest_pieceCanExecuteMove(){

        //assert
        CheckmateDetector checkmateDetector = new CheckmateDetector();
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(1, 1, 1, true);
        Bishop bishop2 = new Bishop(1, square2);
        square2.put(bishop2);

        //act + assert
        assertTrue(checkmateDetector.testMoveForTest(bishop,square2));

    }

    @Test
    public void TestMoveTest_pieceCannotExecuteMove(){

        //assert
        CheckmateDetector checkmateDetector = new CheckmateDetector();
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(0, 1, 1, true);
        Bishop bishop2 = new Bishop(0, square2);
        square2.put(bishop2);

        //act + assert
        assertFalse(checkmateDetector.testMoveForTest(bishop,square2));
    }

    @Test
    public void TestMoveTest_WhitePieceCannotTakeKing(){

        //assert
        CheckmateDetector checkmateDetector = new CheckmateDetector();
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(1, 2, 2, true);
        King king = new King(1, square2);
        square2.put(king);

        //act + assert
        assertFalse(checkmateDetector.testMoveForTest(bishop,square2));

    }

    @Test
    public void TestMoveTest_BlackPieceCannotTakeKing(){

        //assert
        CheckmateDetector checkmateDetector = new CheckmateDetector();
        Square square = new Square(0, 0, 0, true);
        Bishop bishop = new Bishop(0, square);
        square.put(bishop);

        Square square2 = new Square(0, 2, 2, true);
        King king = new King(0, square2);
        square2.put(king);

        //act + assert
        assertFalse(checkmateDetector.testMoveForTest(bishop,square2));
    }
}

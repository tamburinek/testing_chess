package cz.cvut.fel.pjv.chess.chessgame;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessTests {

    @Test
    public void processTest1_checkingIfBoardWasBuildSuccessfully(){

        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();

        Square[][] boxes = board.getSquareArray();

        //assert
        assertSame("R", boxes[0][0].getOccupyingPiece().toString());
        assertSame("R", boxes[7][7].getOccupyingPiece().toString());
        assertSame("P", boxes[1][1].getOccupyingPiece().toString());
        assertSame("P", boxes[6][1].getOccupyingPiece().toString());
        assertSame("B", boxes[0][2].getOccupyingPiece().toString());
        assertSame("B", boxes[7][5].getOccupyingPiece().toString());
        assertSame("N", boxes[0][1].getOccupyingPiece().toString());
        assertSame("N", boxes[7][6].getOccupyingPiece().toString());
        assertSame("K", boxes[0][4].getOccupyingPiece().toString());
        assertSame("K", boxes[7][4].getOccupyingPiece().toString());
        assertSame("Q", boxes[0][3].getOccupyingPiece().toString());
        assertSame("Q", boxes[7][3].getOccupyingPiece().toString());
    }
    @Test
    public void processTest2_checkingMovesCountWithPiecesOnBoard(){

        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();
        Square[][] boxes = board.getSquareArray();

        LinkedList<Square> moves = (LinkedList<Square>) boxes[0][0].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 0);
        moves = (LinkedList<Square>) boxes[1][0].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 2);
        moves = (LinkedList<Square>) boxes[0][1].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 2);
        moves = (LinkedList<Square>) boxes[0][2].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 0);
        moves = (LinkedList<Square>) boxes[0][3].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 0);
        moves = (LinkedList<Square>) boxes[0][4].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 0);
    }

    @Test
    public void processTest3_movesCountAfterMove(){

        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();
        Square[][] boxes = board.getSquareArray();

        boxes[1][0].getOccupyingPiece().move(boxes[3][0],board);
        boxes[3][0].getOccupyingPiece().setWasMoved(true);

        LinkedList<Square> moves = (LinkedList<Square>) boxes[0][0].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 2);
        moves = (LinkedList<Square>) boxes[3][0].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 1);
        moves = (LinkedList<Square>) boxes[0][1].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 2);
        moves = (LinkedList<Square>) boxes[0][2].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 0);
        moves = (LinkedList<Square>) boxes[0][3].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 0);
        moves = (LinkedList<Square>) boxes[0][4].getOccupyingPiece().getLegalMoves(board);
        assertEquals(moves.size(), 0);
    }

    @Test
    public void processTest4_makeMove_andCheckIfMoveWasExecuted(){

        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();

        Square[][] boxes = board.getSquareArray();

        boxes[1][1].getOccupyingPiece().move(boxes[2][1],board);
        assertTrue(boxes[2][1].getOccupyingPiece().toString()=="P");
        assertTrue(boxes[1][1].getOccupyingPiece() == null);
    }

    @Test
    public void processTest5_EnPassantMove(){
        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();
        Square[][] boxes = board.getSquareArray();

        boxes[1][1].getOccupyingPiece().move(boxes[3][1],board);
        boxes[3][1].getOccupyingPiece().setWasMoved(true);

        List<Square> moves = boxes[3][1].getOccupyingPiece().getLegalMoves(board);
        assertEquals(1,moves.size());

        boxes[6][0].getOccupyingPiece().move(boxes[4][0],board);
        boxes[3][1].getOccupyingPiece().move(boxes[4][1],board);
        boxes[6][2].getOccupyingPiece().move(boxes[4][2],board);

        assertTrue(boxes[0][0].getOccupyingPiece().isEnPassantPawn);
        assertTrue(boxes[0][0].getOccupyingPiece().getEnPassantPawnPosition()==boxes[4][2]);

        moves = boxes[4][1].getOccupyingPiece().getLegalMoves(board);
        assertEquals(2,moves.size());

    }

    @Test
    public void processTest6_Castling(){
        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();
        Square[][] boxes = board.getSquareArray();

        List<Square> moves = boxes[7][4].getOccupyingPiece().getLegalMoves(board);
        assertEquals(0,moves.size());

        boxes[7][5].getOccupyingPiece().move(boxes[4][5],board);
        boxes[7][6].getOccupyingPiece().move(boxes[4][6],board);

        moves = boxes[7][4].getOccupyingPiece().getLegalMoves(board);
        assertEquals(2,moves.size());
    }

    @Test
    public void processTest7_pawnPromotion(){
        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();
        Square[][] boxes = board.getSquareArray();

        boxes[0][0].removePiece();
        boxes[6][0].getOccupyingPiece().move(boxes[0][0],board);


        assertTrue(boxes[0][0].getOccupyingPiece().toString()=="Q");
    }

    @Test
    public void processTest8_check(){
        Board board = new Board();
        Square[][] squares = new Square[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    squares[x][y] = new Square(1, y, x, true);
                } else {
                    squares[x][y] = new Square(0, y, x, true);
                }
            }
        }
        board.setBoard(squares);
        board.initializePieces();
        Square[][] boxes = board.getSquareArray();

        boxes[7][1].getOccupyingPiece().move(boxes[2][3],board);
        LinkedList<Square> moves = (LinkedList<Square>) boxes[2][3].getOccupyingPiece().getLegalMoves(board);

        assertTrue(isIn(boxes[0][4],moves));
        assertTrue(board.checkmateDetector.blackInCheck());

    }

    private boolean isIn(Square square, LinkedList<Square> squareList) {
        for (Square square1 : squareList) {
            if (square.getXNum() == square1.getXNum() && square.getYNum() == square1.getYNum())
                return true;
        }
        return false;
    }
}

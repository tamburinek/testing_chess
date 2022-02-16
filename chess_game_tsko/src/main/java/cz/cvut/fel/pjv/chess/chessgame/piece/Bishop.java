package cz.cvut.fel.pjv.chess.chessgame.piece;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;

import java.util.List;

/**
 * Bishop can move diagonally
 * @author artomnorba
 */

public class Bishop extends Piece {

    public Bishop(int color, Square initSq) {
        super(color, initSq);
    }

    public Bishop(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    /**
     * method finds all squares where bishop can theoretically make move
     * @param board1 - board of current game
     * @return all legal moves bishop can make
     */

    @Override
    public List<Square> getLegalMoves(Board board1) {
        Square[][] board = board1.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        return getDiagonalOccupations(board, x, y);
    }

    /**
     * To string is used when move is being written in the text file
     * @return letter of the piece
     */

    @Override
    public String toString() {
        return "B";
    }
}

package cz.cvut.fel.pjv.chess.chessgame.piece;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;

import java.util.LinkedList;
import java.util.List;

/**
 * Rook is piece which can move only linear
 * @author artomnorba
 */

public class Rook extends Piece {

    public Rook(int color, Square initSq) {
        super(color, initSq);
    }

    public Rook(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    /**
     * method finds all squares where rook can theoretically make move
     * @param board1 - board of current game
     * @return all legal moves rook can make
     */

    @Override
    public List<Square> getLegalMoves(Board board1) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        int[] occupations = getLinearOccupations(board, x, y);
        for (int i = occupations[0]; i <= occupations[1]; i++) {
            if (i != y) legalMoves.add(board[i][x]);
        }
        for (int i = occupations[2]; i <= occupations[3]; i++) {
            if (i != x) legalMoves.add(board[y][i]);
        }
        return legalMoves;
    }

    /**
     * To string is used when move is being written in the text file
     * @return letter of the piece
     */

    @Override
    public String toString() {
        return "R";
    }
}

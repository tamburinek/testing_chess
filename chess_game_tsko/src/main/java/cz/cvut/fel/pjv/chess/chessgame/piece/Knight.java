package cz.cvut.fel.pjv.chess.chessgame.piece;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;

import java.util.LinkedList;
import java.util.List;

/**
 * Knight is the only one piece which can jump over other pieces
 * @author artomnorba
 */

public class Knight extends Piece {


    public Knight(int color, Square initSq) {
        super(color, initSq);
    }

    public Knight(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    /**
     * method finds all squares where knight can theoretically make move
     * @param board1 - board of current game
     * @return all legal moves knight can make
     */

    @Override
    public List<Square> getLegalMoves(Board board1) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 2; i > -3; i--) {
            for (int k = 2; k > -3; k--) {
                if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
                    if (k != 0 && i != 0) {
                        try {
                            if (board[y + k][x + i].getOccupyingPiece()!=null && board[y + k][x + i].getOccupyingPiece().getColor()==this.getColor())
                            continue;
                            else legalMoves.add(board[y + k][x + i]);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }
        
        return legalMoves;
    }


    /**
     * To string is used when move is being written in the text file
     * @return letter of the piece
     */

    @Override
    public String toString() {
        return "N";
    }

}

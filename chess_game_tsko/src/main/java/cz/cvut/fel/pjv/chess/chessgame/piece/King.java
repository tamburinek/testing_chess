package cz.cvut.fel.pjv.chess.chessgame.piece;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import java.util.LinkedList;
import java.util.List;

import static cz.cvut.fel.pjv.chess.chessgame.board.CheckmateDetector.blackInCheck;
import static cz.cvut.fel.pjv.chess.chessgame.board.CheckmateDetector.whiteInCheck;

/**
 * King is the most important piece in chess game
 * u cannot play without him
 * @author artomnorba
 */

public class King extends Piece {

    public King(int color, Square initSq) {
        super(color, initSq);
    }

    public King(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    /**
     * method finds all squares where king can theoretically make move
     * @param board1 - board of current game
     * @return all legal moves king can make
     */

    @Override
    public List<Square> getLegalMoves(Board board1) {

        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
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

        if (!blackInCheck){
            if(getColor()==0 && !getWasMoved() && board [0][7].getOccupyingPiece() != null && board[0][7].getOccupyingPiece().getPieceNumber()==0  && !board[0][7].getOccupyingPiece().getWasMoved()){
                if (!board[0][6].isOccupied()&&!board[0][5].isOccupied())
                {legalMoves.add(board[0][6]);}
            }
            if(getColor()==0 && !getWasMoved() && board [0][0].getOccupyingPiece() != null && board[0][0].getOccupyingPiece().getPieceNumber()==0  && !board[0][0].getOccupyingPiece().getWasMoved()){
                if (!board[0][1].isOccupied()&&!board[0][2].isOccupied()){
                legalMoves.add(board[0][2]);}
            }
        }

        if (!whiteInCheck){
            if(getColor()==1 && !getWasMoved() && board [7][7].getOccupyingPiece() != null && board[7][7].getOccupyingPiece().getPieceNumber()==0  && !board[7][7].getOccupyingPiece().getWasMoved()){
                if (!board[7][6].isOccupied()&&!board[7][5].isOccupied())
                {legalMoves.add(board[7][6]);}
            }
            if(getColor()==1 && !getWasMoved() && board [7][0].getOccupyingPiece() != null && board[7][0].getOccupyingPiece().getPieceNumber()==0  && !board[7][0].getOccupyingPiece().getWasMoved()){
                if (!board[7][1].isOccupied()&&!board[7][2].isOccupied())
                {legalMoves.add(board[7][2]);}
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
        return "K";
    }

}

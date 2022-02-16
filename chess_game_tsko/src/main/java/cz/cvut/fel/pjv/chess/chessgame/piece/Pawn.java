package cz.cvut.fel.pjv.chess.chessgame.piece;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;

import java.util.List;
import java.util.LinkedList;

/**
 * Pawn is very weak piece but very import soldier in game
 * @author artomnorba
 */

public class Pawn extends Piece {

    public Pawn(int color, Square initSq) {
        super(color, initSq);
    }

    public Pawn(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }


    /**
     * method finds all squares where pawn can theoretically make move
     * @param board1 - board of current game
     * @return all legal moves pawn can make
     */

    @Override
    public List<Square> getLegalMoves(Board board1) {
        LinkedList<Square> legalMoves = new LinkedList<>();
        Square[][] board = board1.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int c = this.getColor();
        
        if (c == 0) {
            if (!getWasMoved()) {
                if (!board[y+2][x].isOccupied() && !board[y+1][x].isOccupied()) {
                    legalMoves.add(board[y+2][x]);
                }
            }
            
            if (y+1 < 8) {
                if (!board[y+1][x].isOccupied()) {
                    legalMoves.add(board[y+1][x]);
                }
            }
            
            if (x+1 < 8 && y+1 < 8) {
                if (board[y+1][x+1].isOccupied()) {
                    legalMoves.add(board[y+1][x+1]);
                }
            }
                
            if (x-1 >= 0 && y+1 < 8) {
                if (board[y+1][x-1].isOccupied()) {
                    legalMoves.add(board[y+1][x-1]);
                }
            }

            if (this.isEnPassantPawn()){
                if (this.getEnPassantPawnPosition().getYNum() == y && (this.getEnPassantPawnPosition().getXNum() == (x + 1) || (this.getEnPassantPawnPosition().getXNum() == (x - 1)))){
                    if (this.getEnPassantPawnPosition().getXNum() == (x+1) && !board[y+1][x+1].isOccupied()){
                        legalMoves.add(board[y+1][x+1]);
                    }
                    else if( this.getEnPassantPawnPosition().getXNum() == (x-1) && !board[y+1][x-1].isOccupied()){
                        legalMoves.add(board[y+1][x-1]);
                    }
                }
            }
        }
        
        if (c == 1) {
            if (!getWasMoved()) {
                if (!board[y-2][x].isOccupied() && !board[y-1][x].isOccupied()) {
                    legalMoves.add(board[y-2][x]);
                }
            }
            
            if (y-1 >= 0) {
                if (!board[y-1][x].isOccupied()) {
                    legalMoves.add(board[y-1][x]);
                }
            }
            
            if (x+1 < 8 && y-1 >= 0) {
                if (board[y-1][x+1].isOccupied()) {
                    legalMoves.add(board[y-1][x+1]);
                }
            }
                
            if (x-1 >= 0 && y-1 >= 0) {
                if (board[y-1][x-1].isOccupied()) {
                    legalMoves.add(board[y-1][x-1]);
                }
            }

            if (this.isEnPassantPawn()){
                if (this.getEnPassantPawnPosition().getYNum() == y && (this.getEnPassantPawnPosition().getXNum() == (x + 1) || (this.getEnPassantPawnPosition().getXNum() == (x - 1)))){
                    if (this.getEnPassantPawnPosition().getXNum() == (x+1) && !board[y-1][x+1].isOccupied()){
                        legalMoves.add(board[y-1][x+1]);
                    }
                    else if( this.getEnPassantPawnPosition().getXNum() == (x-1) && !board[y-1][x-1].isOccupied()){
                        legalMoves.add(board[y-1][x-1]);
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
        return "P";
    }
}

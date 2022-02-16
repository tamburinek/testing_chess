package cz.cvut.fel.pjv.chess.chessgame.players;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import cz.cvut.fel.pjv.chess.chessgame.piece.Piece;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Computer class is used when user selected Computer as black player name
 * Computer player is not smart - only finds random move
 * @author artomnorba
 */

public class Computer {

    /**
     * Logger used when computer failed to find move
     */

    public static final Logger LOGGER = Logger.getLogger(Computer.class.getName());

    /**
     * method tries to find random legal move
     * @param pieces - linkedList of all pieces
     * @param board - board to make move to specific square
     * @return - piece which was used to make move or null if there was problem
     */

    public Piece makeMove(LinkedList<Piece> pieces, Board board){

        for (int i = 0; i < 60; i++) {
            try {
                int pieceNumbers = pieces.size();
                Random rand = new Random();
                int upperbound = pieceNumbers;
                int int_random = rand.nextInt(upperbound) + 1;
                Piece currentPiece = pieces.get(int_random);
                List<Square> moves = currentPiece.getLegalMoves(board);
                upperbound = moves.size();
                int_random = rand.nextInt(upperbound);
                Square currentSquare = moves.get(int_random);
                if (currentPiece.move(currentSquare, board)){
                    return currentPiece;
                }
            } catch (Exception e) {
                LOGGER.info("computer failed to find right move");
            }
        }return null;
    }
}

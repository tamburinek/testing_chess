package cz.cvut.fel.pjv.chess.chessgame.board;

import cz.cvut.fel.pjv.chess.chessgame.piece.Bishop;
import cz.cvut.fel.pjv.chess.chessgame.piece.King;
import cz.cvut.fel.pjv.chess.chessgame.piece.Piece;
import cz.cvut.fel.pjv.chess.chessgame.piece.Queen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

/**
 * CheckmateDetector class is used to find game situation
 * @author artomnorba
 */

public class CheckmateDetector {

    /**
     * Logger used when player is in check
     */

    public static final Logger LOGGER = Logger.getLogger(CheckmateDetector.class.getName());

    private  Board b;
    private  LinkedList<Piece> wPieces;
    private  LinkedList<Piece> bPieces;
    private  LinkedList<Square> movableSquares;
    private  LinkedList<Square> squares;
    private  King bKing;
    private  King wKing;
    private  HashMap<Square,List<Piece>> wMoves;
    private  HashMap<Square,List<Piece>> bMoves;

    public static boolean whiteInCheck = false;
    public static boolean blackInCheck = false;



    public CheckmateDetector(){
        this.b = null;
        this.wPieces = null;
        this.bPieces = null;
        this.bKing = null;
        this.wKing = null;
    }

    public CheckmateDetector(Board board, LinkedList<Piece> wPieces,
            LinkedList<Piece> bPieces, King wKing, King bKing) {
        this.b = board;
        this.wPieces = wPieces;
        this.bPieces = bPieces;
        this.bKing = bKing;
        this.wKing = wKing;

        squares = new LinkedList<>();
        movableSquares = new LinkedList<>();
        wMoves = new HashMap<>();
        bMoves = new HashMap<>();
        
        Square[][] brd = board.getSquareArray();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares.add(brd[y][x]);
                wMoves.put(brd[y][x], new LinkedList<>());
                bMoves.put(brd[y][x], new LinkedList<>());
            }
        }
        update();
    }

    /**
     * updates situation on board after move or another change
     */

    public void update() {
        Iterator<Piece> wIter = wPieces.iterator();
        Iterator<Piece> bIter = bPieces.iterator();

        for (List<Piece> pieces : wMoves.values()) {
            pieces.removeAll(pieces);
        }
        
        for (List<Piece> pieces : bMoves.values()) {
            pieces.removeAll(pieces);
        }
        movableSquares.removeAll(movableSquares);

        while (wIter.hasNext()) {
            Piece p = wIter.next();

            if (!p.getClass().equals(King.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }

                List<Square> mvs = p.getLegalMoves(b);
                for (Square mv : mvs) {
                    List<Piece> pieces = wMoves.get(mv);
                    pieces.add(p);
                }
            }
        }
        
        while (bIter.hasNext()) {
            Piece p = bIter.next();
            
            if (!p.getClass().equals(King.class)) {
                if (p.getPosition() == null) {
                    wIter.remove();
                    continue;
                }
                
                List<Square> mvs = p.getLegalMoves(b);
                for (Square mv : mvs) {
                    List<Piece> pieces = bMoves.get(mv);
                    pieces.add(p);
                }
            }
        }
    }

    /**
     * method finds if black king is in check position
     * @return true if black is in check position
     */

    public boolean blackInCheck() {
        update();
        Square sq = bKing.getPosition();

        if (wMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            blackInCheck = false;
            return false;
        } else {
            blackInCheck = true;
            LOGGER.info("black is now in check");
            return true;
        }
    }

    /**
     * method finds if white king is in check position
     * @return true if white is in check position
     */

    public boolean whiteInCheck() {
        update();
        Square sq = wKing.getPosition();
        List<Square> opponentsMoves;

        for (Piece piece : bPieces) {
            opponentsMoves = piece.getLegalMoves(b);
            if (opponentsMoves.contains(sq)){
                whiteInCheck = true;
                LOGGER.info("white is now in check");
                return true;
            }
        }
        whiteInCheck = false;
        return false;
    }

    /**
     * checks if there is stalemate situation on board
     * @param b - boolean for color - true = white
     * @return true if there is stalemate position
     */

    public boolean checkStaleMate(boolean b){
         if(b){
             return wMoves.isEmpty();
         }else{
             return bMoves.isEmpty();
         }
    }

    /**
     * checks if black lost game
     * @return true if black is in check mate
     */

    public boolean blackCheckMated() {
        boolean checkmate = true;
        if (!this.blackInCheck()) return false;
        if (canEvade(wPieces, bKing)) checkmate = false;
        List<Piece> threats = wMoves.get(bKing.getPosition());
        if (canCapture(bMoves, threats, bKing)) checkmate = false;
        if (canBlock(threats, bMoves, bKing)) checkmate = false;
        return checkmate;
    }

    /**
     * checks if white lost game
     * @return true if white is in check mate
     */

    public boolean whiteCheckMated() {
        boolean checkmate = true;
        if (!this.whiteInCheck()) return false;
        if (canEvade(bPieces, wKing)) checkmate = false;
        List<Piece> threats = bMoves.get(wKing.getPosition());
        if (canCapture(wMoves, threats, wKing)) checkmate = false;
        if (canBlock(threats, wMoves, wKing)) checkmate = false;
        return checkmate;
    }

    /**
     * method tries to find out if there is square to escape from check situation
     * method don't try to capture the attacking piece
     * @param pieces - active pieces
     * @param tKing - king instance (black or white depends on turn)
     * @return true if king can escape from check
     */

    private boolean canEvade(LinkedList<Piece> pieces, King tKing) {
        int whitePiecesNum = pieces.size();
        List<Square> kingsMoves = tKing.getLegalMoves(b);
        List<Square> opponentMoves;

        for (Square square : kingsMoves) {
            int helper = 1;
            if (square.getOccupyingPiece()!=null){
                continue;
            }

            for (Piece piece : pieces) {
                opponentMoves = piece.getLegalMoves(b);
                if (opponentMoves.contains(square)){
                    break;
                }
                helper += helper;
                if (helper == whitePiecesNum){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * methods tries to find out if there is chance to capture the attacking piece
     * @param poss
     * @param threats - list of threats
     * @param king - king instance (color depends on turn)
     * @return true if attacking piece can be captured
     */

    private boolean canCapture(Map<Square,List<Piece>> poss, 
            List<Piece> threats, King king) {
        
        boolean capture = false;
        if (threats.size() == 1) {
            Square sq = threats.get(0).getPosition();
            
            if (king.getLegalMoves(b).contains(sq)) {
                movableSquares.add(sq);
                if (testMove(king, sq)) {
                    capture = true;
                }
            }
            
            List<Piece> caps = poss.get(sq);
            ConcurrentLinkedDeque<Piece> captures = new ConcurrentLinkedDeque<>(caps);
            
            if (!captures.isEmpty()) {
                movableSquares.add(sq);
                for (Piece p : captures) {
                    if (testMove(p, sq)) {
                        capture = true;
                    }
                }
            }
        }
        return capture;
    }

    /**
     * method tries to find out if there is way to block the check situation
     * @param threats - all threats
     * @param blockMoves - map of block moves
     * @param king - king instance (color depends on current turn)
     * @return true if there is way to block the check situation
     */

    private boolean canBlock(List<Piece> threats, 
            Map <Square,List<Piece>> blockMoves, King king) {
        boolean blockAble = false;
        
        if (threats.size() == 1) {
            Square ts = threats.get(0).getPosition();
            Square ks = king.getPosition();
            Square[][] brdArray = b.getSquareArray();
            
            if (ks.getXNum() == ts.getXNum()) {
                int max = Math.max(ks.getYNum(), ts.getYNum());
                int min = Math.min(ks.getYNum(), ts.getYNum());
                
                for (int i = min + 1; i < max; i++) {
                    List<Piece> blocks =
                            blockMoves.get(brdArray[i][ks.getXNum()]);
                    ConcurrentLinkedDeque<Piece> blockers =
                            new ConcurrentLinkedDeque<>(blocks);
                    
                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[i][ks.getXNum()]);
                        
                        for (Piece p : blockers) {
                            if (testMove(p,brdArray[i][ks.getXNum()])) {
                                blockAble = true;
                            }
                        }
                    }
                }
            }
            
            if (ks.getYNum() == ts.getYNum()) {
                int max = Math.max(ks.getXNum(), ts.getXNum());
                int min = Math.min(ks.getXNum(), ts.getXNum());
                
                for (int i = min + 1; i < max; i++) {
                    List<Piece> blocks =
                            blockMoves.get(brdArray[ks.getYNum()][i]);
                    ConcurrentLinkedDeque<Piece> blockers =
                            new ConcurrentLinkedDeque<>(blocks);
                    
                    if (!blockers.isEmpty()) {
                        
                        movableSquares.add(brdArray[ks.getYNum()][i]);
                        
                        for (Piece p : blockers) {
                            if (testMove(p, brdArray[ks.getYNum()][i])) {
                                blockAble = true;
                            }
                        }
                    }
                }
            }
            
            Class<? extends Piece> tC = threats.get(0).getClass();
            
            if (tC.equals(Queen.class) || tC.equals(Bishop.class)) {
                int kX = ks.getXNum();
                int kY = ks.getYNum();
                int tX = ts.getXNum();
                int tY = ts.getYNum();
                
                if (kX > tX && kY > tY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY++;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }
                
                if (kX > tX && tY > kY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY--;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }
                
                if (tX > kX && kY > tY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY++;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }
                
                if (tX > kX && tY > kY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY--;
                        List<Piece> blocks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers =
                                new ConcurrentLinkedDeque<>(blocks);
                        
                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);
                            
                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    blockAble = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockAble;
    }

    /**
     * @return list of movable squares
     */

    public List<Square> getAllowableSquares() {
        movableSquares.removeAll(movableSquares);
        if (whiteInCheck()) {
            whiteCheckMated();
        } else if (blackInCheck()) {
            blackCheckMated();
        }
        return movableSquares;
    }

    /**
     * method tests if move can be executed later
     * @param piece - piece which is being moved
     * @param square - square where piece is being moved
     * @return true if move is valid
     */

    public boolean testMove(Piece piece, Square square) {
        Piece c = square.getOccupyingPiece();
        boolean moveTest = true;

        if (c != null){
            if (c.getPieceNumber() == 2){
            return false;
            }
        }

        if (piece.getColor() == 1 && blackInCheck()) return false;
        else if (piece.getColor() == 0 && whiteInCheck()) return  false;

        Square init = piece.getPosition();

        piece.move(square, b);
        update();

        if (piece.getColor() == 0 && blackInCheck()) moveTest = false;
        else if (piece.getColor() == 1 && whiteInCheck()) moveTest =  false;

        piece.move(init, b);
        if (c != null) square.put(c);
        update();
        movableSquares.addAll(squares);
        return moveTest;
    }

    public boolean testMoveForTest(Piece piece, Square square){
        Piece c = square.getOccupyingPiece();
        if (c != null){
            if (c.toString() == "K"){
                return false;
            }
            if (c.getColor()== piece.getColor()){
                return false;
            }
        }
        return true;
    }
}

package cz.cvut.fel.pjv.chess.chessgame.piece;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import cz.cvut.fel.pjv.chess.chessgame.board.Square;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * abstract class Piece
 * @author artomnorba
 */

public abstract class Piece {

    /**
     * Logger used when move is made
     */

    public static final Logger LOGGER = Logger.getLogger(Piece.class.getName());

    private final int color;
    private Square currentSquare;
    private BufferedImage img;
    private boolean wasMoved = false;
    private int pieceNumber;

    public static boolean isEnPassantPawn = false;
    public static Square enPassantPawnPosition;



    public Piece(int color, Square initSq){
        this.color = color;
        this.currentSquare = initSq;
    }

    public Piece(int color, Square initSq, String img_file) {
        this.color = color;
        this.currentSquare = initSq;
        try {
              this.img = ImageIO.read(getClass().getResource(img_file));
          } catch (IOException e) {
            LOGGER.info("file not found");
          }

        if(img_file.equals("/wrook.png") || img_file.equals("/brook.png")){
            this.pieceNumber = 0;
        }
        if(img_file.equals("/wpawn.png") || img_file.equals("/bpawn.png")){
            this.pieceNumber = 1;
        }
        if(img_file.equals("/wking.png") || img_file.equals("/bking.png")){
            this.pieceNumber = 2;
        }
    }

    /**
     * Move method is the most important method in chess game
     * @param fin - square where is piece being moved
     * @param board1 - current board
     * @return boolean representation if move was successful
     */

    public boolean move(Square fin, Board board1) {
        Piece occup = fin.getOccupyingPiece();
        Square[][] board = board1.getSquareArray();

        //if piece is same color - return false and dont execute move
        if (occup != null) {
            if (occup.getColor() == this.color) return false;
            else fin.capture(this);
        }

        // executing en passant move
        if (isEnPassantPawn){
            int yPos = currentSquare.getYNum();
            int xPos = currentSquare.getXNum();
            int enPassX = enPassantPawnPosition.getXNum();
            int enPassY = enPassantPawnPosition.getYNum();
                if (enPassY == yPos && (enPassX == (xPos + 1) || (enPassX == (xPos - 1)))){
                    if (pieceNumber == 1){
                        if (color == 0){
                            if (!fin.isOccupied() && fin.getYNum() != 7){
                              board[enPassY][enPassX].removePiece();
                            }
                        }
                        else {
                            if (!fin.isOccupied() && fin.getYNum() != 0){
                                board[enPassY][enPassX].removePiece();
                        }
                    }
                }
            }
        }

        //resetting en passant pawn
        enPassantPawnPosition = null;
        isEnPassantPawn = false;

        //setting en passant pawn if necessary
        if (pieceNumber == 1 && (currentSquare.getYNum() == fin.getYNum()-2 || currentSquare.getYNum() == fin.getYNum()+2)){
            isEnPassantPawn = true;
            enPassantPawnPosition = fin;
        }

        //executing move and removing captured piece
        currentSquare.removePiece();
        this.currentSquare = fin;
        currentSquare.put(this);

        //queen promotion
        if (color==0 && currentSquare.getYNum()==7 && pieceNumber == 1) {
            currentSquare.removePiece();
            currentSquare.put(new Queen(0, currentSquare, "/bqueen.png"));
            wasMoved = true;
            return true;
        }
        else if (color==1 && currentSquare.getYNum()==0 && pieceNumber == 1) {
            currentSquare.removePiece();
            currentSquare.put(new Queen(1, currentSquare, "/wqueen.png"));
            wasMoved = true;
            return true;
        }

        //castling move
        else if (color==0 && currentSquare.getXNum()==6 && pieceNumber == 2 && !wasMoved){
            board[0][7].removePiece();
            Piece rook = new Rook(0,board[0][5],"/brook.png");
            board[0][5].put(rook);
            rook.setWasMoved(true);
        }
        else if (color==0 && currentSquare.getXNum()==2 && pieceNumber == 2 && !wasMoved){
            board[0][0].removePiece();
            Piece rook = new Rook(0,board[0][3],"/brook.png");
            board[0][3].put(rook);
            rook.setWasMoved(true);
        }
        else if (color==1 && currentSquare.getXNum()==6 && pieceNumber == 2 && !wasMoved){
            board[7][7].removePiece();
            Piece rook = new Rook(1,board[7][5],"/wrook.png");
            board[7][5].put(rook);
            rook.setWasMoved(true);
        }
        else if (color==1 && currentSquare.getXNum()==2 && pieceNumber == 2 && !wasMoved){
            board[7][0].removePiece();
            Piece rook = new Rook(1,board[7][3],"/wrook.png");
            board[7][3].put(rook);
            rook.setWasMoved(true);
        }
        return true;
    }

    public boolean moveForTest(Square fin, Board board1) {
        Piece occup = fin.getOccupyingPiece();
        Square[][] board = board1.getSquareArray();

        //if piece is same color - return false and dont execute move
        if (occup != null) {
            if (occup.getColor() == this.color) return false;
            else return true;
        }
        return true;
    }

    //getters and setters
    public boolean isEnPassantPawn() {
        return isEnPassantPawn;
    }
    public Square getEnPassantPawnPosition() {
        return enPassantPawnPosition;
    }
    public Square getPosition() {
        return currentSquare;
    }
    public void setPosition(Square sq) {
        this.currentSquare = sq;
    }
    public int getColor() {
        return color;
    }
    public Image getImage() {
        return img;
    }
    public boolean getWasMoved() {
        return wasMoved;
    }
    public void setWasMoved(boolean bool){
        this.wasMoved = bool;
    }
    public int getPieceNumber(){
        return pieceNumber;
    }

    //drawing piece in board
    public void draw(Graphics g) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        g.drawImage(this.img, x, y, null);
    }

    /**
     * method finds all linear occupations
     * @param board - current board
     * @param x - current x coordination
     * @param y - current y coordination
     * @return max squares - being used by rook
     */

    public int[] getLinearOccupations(Square[][] board, int x, int y) {
        int lastYabove = 0;
        int lastXright = 7;
        int lastYbelow = 7;
        int lastXleft = 0;
        
        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYabove = i;
                } else lastYabove = i + 1;
            }
        }

        for (int i = 7; i > y; i--) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYbelow = i;
                } else lastYbelow = i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXleft = i;
                } else lastXleft = i + 1;
            }
        }

        for (int i = 7; i > x; i--) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXright = i;
                } else lastXright = i - 1;
            }
        }

        return new int[]{lastYabove, lastYbelow, lastXleft, lastXright};
    }

    /**
     * method finds all diagonal occupations
     * @param board - current board
     * @param x - current x coordination
     * @param y - current y coordination
     * @return all diagonal occupations
     */

    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        LinkedList<Square> diagOccup = new LinkedList<>();
        
        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;
        
        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied()) {
                if (board[yNW][xNW].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[yNW][xNW]);
                }
                break;
            } else {
                diagOccup.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }
        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied()) {
                if (board[ySW][xSW].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[ySW][xSW]);
                }
                break;
            } else {
                diagOccup.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }
        while (xSE < 8 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                if (board[ySE][xSE].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[ySE][xSE]);
                }
                break;
            } else {
                diagOccup.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }
        while (xNE < 8 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                if (board[yNE][xNE].getOccupyingPiece().getColor() != this.color) {
                    diagOccup.add(board[yNE][xNE]);
                }
                break;
            } else {
                diagOccup.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }
        return diagOccup;
    }

    /**
     * abstract method is implemented by each piece type
     * @param board - current board
     * @return - all legal moves
     */

    public abstract List<Square> getLegalMoves(Board board);
}
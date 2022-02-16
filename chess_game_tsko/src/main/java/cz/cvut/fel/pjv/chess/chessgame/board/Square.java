package cz.cvut.fel.pjv.chess.chessgame.board;

import cz.cvut.fel.pjv.chess.chessgame.piece.Piece;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

/**
 * Square class represents square on board
 * @author artomnorba
 */

public class Square extends JComponent {
    private Board board;
    private final int color;
    private Piece occupyingPiece;
    private boolean disPiece;
    
    private final int xNum;
    private final int yNum;

    public Square(int color, int x, int y, boolean bool){
        this.color = color;
        this.xNum = x;
        this.yNum = y;
    }

    public Square(Board board, int color, int xNum, int yNum) {
        
        this.board = board;
        this.color = color;
        this.disPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    //setters and getters
    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }
    
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
    
    public int getXNum() {
        return this.xNum;
    }
    
    public int getYNum() {
        return this.yNum;
    }
    
    public void setDisplay(boolean v) {
        this.disPiece = v;
    }

    /**
     * puts piece on current square
     * @param piece - which piece is being placed
     */

    public void put(Piece piece) {
        this.occupyingPiece = piece;
        this.setDisplay(true);
        piece.setPosition(this);
    }

    /**
     * removing piece from square
     */

    public void removePiece() {
        this.occupyingPiece = null;
    }

    /**
     * capture piece from current square
     * @param piece - replacing piece
     */

    public void capture(Piece piece) {
        Piece occupyingPiece = getOccupyingPiece();
        if (occupyingPiece.getColor() == 0) board.bPieces.remove(occupyingPiece);
        if (occupyingPiece.getColor() == 1) board.wPieces.remove(occupyingPiece);
        this.occupyingPiece = piece;
    }

    /**
     * painting squares
     * @param g - graphics
     */

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.color == 1) {
            g.setColor(new Color(235,235, 208));
        } else {
            g.setColor(new Color(119, 148, 85));
        }
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        if(occupyingPiece != null && disPiece) {
            occupyingPiece.draw(g);
        }
    }
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xNum;
        result = prime * result + yNum;
        return result;
    }
    
}

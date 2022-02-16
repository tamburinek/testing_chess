package cz.cvut.fel.pjv.chess.chessgame.board;

import cz.cvut.fel.pjv.chess.chessgame.GUI.GameWindow;
import cz.cvut.fel.pjv.chess.chessgame.piece.*;
import cz.cvut.fel.pjv.chess.chessgame.players.Computer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;

/**
 * Board class represents chess board
 * @author artomnorba
 */

public class Board extends JPanel implements MouseListener, MouseMotionListener {

    /**
     * Logger used when moved was executed
     */

    public static final Logger LOGGER = Logger.getLogger(Board.class.getName());

    //string representation of images
    private static final String RESOURCES_WBISHOP_PNG = "/wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "/bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "/wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "/bknight.png";
	private static final String RESOURCES_WROOK_PNG = "/wrook.png";
	private static final String RESOURCES_BROOK_PNG = "/brook.png";
	private static final String RESOURCES_WKING_PNG = "/wking.png";
	private static final String RESOURCES_BKING_PNG = "/bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "/bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "/wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "/wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "/bpawn.png";

    public static boolean blackIsComputer;
	public static void setBlackIsComputer(boolean b){
	    blackIsComputer = b;
    }

    private final Computer computer = new Computer();
	private final Utility utility = new Utility();

	// Logical and graphical representations of board
	private Square[][] board;
    private GameWindow gameWindow;
    
    // List of pieces and whether they are movable
    public LinkedList<Piece> bPieces;
    public LinkedList<Piece> wPieces;
    public List<Square> movable;

    private boolean whiteTurn;
    private int movesCount = 1;
    private Piece currPiece;
    private int currX;
    private int currY;
    public static String whiteName;
    public static String blackName;
    public CheckmateDetector checkmateDetector;



    public Board(){
        board = new Square[8][8];
        bPieces = new LinkedList<>();
        wPieces = new LinkedList<>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
            }
        }
    }

    public Board(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        board = new Square[8][8];
        bPieces = new LinkedList<>();
        wPieces = new LinkedList<>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                }
                this.add(board[x][y]);
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
        utility.writeNewGame();

    }

    public void initializePieces() {
    	
        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(0, board[1][x], RESOURCES_BPAWN_PNG));
            board[6][x].put(new Pawn(1, board[6][x], RESOURCES_WPAWN_PNG));
        }
        
        board[7][3].put(new Queen(1, board[7][3], RESOURCES_WQUEEN_PNG));
        board[0][3].put(new Queen(0, board[0][3], RESOURCES_BQUEEN_PNG));
        
        King bk = new King(0, board[0][4], RESOURCES_BKING_PNG);
        King wk = new King(1, board[7][4], RESOURCES_WKING_PNG);
        board[0][4].put(bk);
        board[7][4].put(wk);

        board[0][0].put(new Rook(0, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(0, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(1, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new Rook(1, board[7][7], RESOURCES_WROOK_PNG));

        board[0][1].put(new Knight(0, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new Knight(0, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new Knight(1, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new Knight(1, board[7][6], RESOURCES_WKNIGHT_PNG));

        board[0][2].put(new Bishop(0, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new Bishop(0, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new Bishop(1, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(1, board[7][5], RESOURCES_WBISHOP_PNG));
        
        
        for(int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                bPieces.add(board[y][x].getOccupyingPiece());
                wPieces.add(board[7-y][x].getOccupyingPiece());
            }
        }
        checkmateDetector = new CheckmateDetector(this, wPieces, bPieces, wk, bk);
    }

    //getters
    public Square[][] getSquareArray() {
        return this.board;
    }
    public void setBoard(Square[][] squares){
        this.board = squares;
    }
    public boolean getTurn() {
        return whiteTurn;
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                sq.paintComponent(g);
            }
        }

        if (currPiece != null) {
            if ((currPiece.getColor() == 1 && whiteTurn)
                    || (currPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currPiece.getImage();
                g.drawImage(i, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    /**
     * mouse released method called when player tried to finish his move
     * @param e - event
     */

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));
        Timer timeHelper = new Timer();
        timeHelper.start();

        if (currPiece != null) {
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;

            List<Square> legalMoves = currPiece.getLegalMoves(this);
            movable = checkmateDetector.getAllowableSquares();

            if (legalMoves.contains(sq) && movable.contains(sq)
                    && checkmateDetector.testMove(currPiece, sq)) {

                sq.setDisplay(true);
                currPiece.move(sq, this);
                currPiece.setWasMoved(true);
                checkmateDetector.update();
                utility.writeMove(sq,currPiece,whiteTurn,movesCount);

                LOGGER.info("player moved: " + currPiece.toString() + " " + sq.getXNum() + " " + sq.getYNum());

                if (!whiteTurn){
                    movesCount++;
                }

                if (checkmateDetector.blackCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    utility.writeMate();

                    LOGGER.info("black checkmated");

                    gameWindow.checkmateOccurred(0);

                } else if (checkmateDetector.whiteCheckMated()) {
                    currPiece = null;
                    repaint();
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    utility.writeMate();

                    LOGGER.info("white checkmated");

                    gameWindow.checkmateOccurred(1);

                } else if (checkmateDetector.checkStaleMate(whiteTurn)){
                    gameWindow.checkmateOccurred(3);

                    LOGGER.info("stalemate");
                }

                else {
                    currPiece = null;
                    whiteTurn = !whiteTurn;
                    movable = checkmateDetector.getAllowableSquares();
                }

                if (blackIsComputer){
                    Piece compPiece = computer.makeMove(bPieces, this);
                    compPiece.setWasMoved(true);
                    compPiece.getPosition().setDisplay(true);
                    movable = checkmateDetector.getAllowableSquares();
                    utility.writeMove(compPiece.getPosition(),compPiece, whiteTurn,movesCount);
                    whiteTurn = !whiteTurn;
                    movesCount++;

                    LOGGER.info("computer made move: " + compPiece.toString() + " " + compPiece.getPosition().getXNum() + " "+ compPiece.getPosition().getYNum());
                }

            } else {
                currPiece.getPosition().setDisplay(true);
                currPiece = null;
            }
        }
        int timeSpend = timeHelper.getTime();
        LOGGER.info("this move was executed in " + timeSpend + " ns");
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    // Irrelevant methods, do nothing for these mouse behaviors
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
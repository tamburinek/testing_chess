package cz.cvut.fel.pjv.chess.chessgame.board;

import cz.cvut.fel.pjv.chess.chessgame.piece.Piece;
import java.io.FileWriter;
import java.io.IOException;
import static cz.cvut.fel.pjv.chess.chessgame.board.Board.blackName;
import static cz.cvut.fel.pjv.chess.chessgame.board.Board.whiteName;

/**
 * Utility class is used when move is being written in text file
 * @author artomnorba
 */

public class Utility {

    private int whiteKingMovesCount = 0;
    private int blackKingMovesCount = 0;

    /**
     * writing new game statement with names of players
     */

    public void writeNewGame(){
        try {
            FileWriter writer = new FileWriter("GameMoves.txt", true);
            writer.write("\r\n");
            writer.write("----------NEW GAME----------");
            writer.write("\r\n");
            writer.write("White Player: " + whiteName);
            writer.write("\r\n");
            writer.write("Black Player: " + blackName);
            writer.write("\r\n");
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * writing # to set end of the game
     */

    public void writeMate(){
        try {
            FileWriter writer = new FileWriter("GameMoves.txt", true);
            writer.write("#");
            writer.close();
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * writing move in the text file
     * @param square - where was piece placed
     * @param piece - which piece type was moved
     * @param whiteTurn - if it is whites turn
     * @param movesCount - number of move
     */

    public void writeMove(Square square, Piece piece, boolean whiteTurn, int movesCount){
        int y = square.getYNum();
        int x = square.getXNum();
        String writeX = xToLetter(x);
        int writeY = yInvert(y);
        String pieceChar = piece.toString();
        String message = pieceChar + writeX + writeY + " ";

        //castling moves
        if (pieceChar.equals("K") && whiteKingMovesCount==0 && writeX.equals("g") && whiteTurn){
            message = "0-0 ";
        }
        else if (pieceChar.equals("K") && blackKingMovesCount==0 && writeX.equals("g") && !whiteTurn){
            message = "0-0 ";
        }

        else if (pieceChar.equals("K") && whiteKingMovesCount==0 && writeX.equals("c") && whiteTurn){
            message = "0-0-0 ";
        }

        else if (pieceChar.equals("K") && blackKingMovesCount==0 && writeX.equals("c") && !whiteTurn){
            message = "0-0-0 ";
        }

        if (pieceChar.equals("K")){
            if (whiteTurn){
                whiteKingMovesCount++;
            }else blackKingMovesCount++;
        }

        try {
            FileWriter writer = new FileWriter("GameMoves.txt", true);
            if (whiteTurn){
            writer.write(movesCount + " " + message);
            }else {
                writer.write(message);
                writer.write("\r\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * because in text file must be letter representation of column
     * @param x - x coordinate
     * @return letter representation of column
     */

    public String xToLetter(int x){
        if (x == 0){
            return "a";
        }
        else if (x == 1){
            return "b";
        }
        else if (x == 2){
            return "c";
        }
        else if (x == 3){
            return "d";
        }
        else if (x == 4){
            return "e";
        }
        else if (x == 5){
            return "f";
        }
        else if (x == 6){
            return "g";
        }
        else {
            return "h";
        }
    }

    /**
     * because board is implemented from 0 - 7 this method has to invert number
     * @param y - y coordinate
     * @return new representation
     */

    public int yInvert(int y){
        if (y == 0){
            return 8;
        }
        else if (y == 1){
            return 7;
        }
        else if (y == 2){
            return 6;
        }
        else if (y == 3){
            return 5;
        }
        else if (y == 4){
            return 4;
        }
        else if (y == 5){
            return 3;
        }
        else if (y == 6){
            return 2;
        }
        else {
            return 1;
        }
    }
}


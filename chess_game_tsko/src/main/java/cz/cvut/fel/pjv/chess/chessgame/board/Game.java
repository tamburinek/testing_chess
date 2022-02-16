package cz.cvut.fel.pjv.chess.chessgame.board;
import cz.cvut.fel.pjv.chess.chessgame.GUI.StartMenu;

import javax.swing.*;

/**
 * Main class which starts whole game
 * @author artomnorba
 */

public class Game implements Runnable {
    public void run() {
        SwingUtilities.invokeLater(new StartMenu());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}



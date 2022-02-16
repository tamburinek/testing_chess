package cz.cvut.fel.pjv.chess.chessgame.GUI;

import cz.cvut.fel.pjv.chess.chessgame.board.Board;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * GameWindow class represents GUI
 */

public class GameWindow {

    /**
     * Logger used when file failed to find
     */

    public static final Logger LOGGER = Logger.getLogger(GameWindow.class.getName());

    private final JFrame gameWindow;
    public Clock blackClock;
    public Clock whiteClock;
    private Timer timer;
    private final Board board;
    

    public GameWindow(String blackName, String whiteName, int hh, int mm, int ss) {
        
        blackClock = new Clock(hh, ss, mm);
        whiteClock = new Clock(hh, ss, mm);
        gameWindow = new JFrame("Chess");

        try {
            Image whiteImg = ImageIO.read(getClass().getResource("/wp.png"));
            gameWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            LOGGER.info("file not found");
        }

        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20,20));
       
        // Game Data window
        JPanel gameData = gameDataPanel(blackName, whiteName, hh, mm, ss);
        gameData.setSize(gameData.getPreferredSize());
        gameWindow.add(gameData, BorderLayout.NORTH);

        this.board = new Board(this);
        
        gameWindow.add(board, BorderLayout.CENTER);
        
        gameWindow.add(buttons(), BorderLayout.SOUTH);
        
        gameWindow.setMinimumSize(gameWindow.getPreferredSize());
        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setResizable(false);
        
        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel gameDataPanel(final String bn, final String wn, 
            final int hh, final int mm, final int ss) {
        
        JPanel gameData = new JPanel();
        gameData.setLayout(new GridLayout(3,2,0,0));

        JLabel w = new JLabel(wn);
        JLabel b = new JLabel(bn);
        
        w.setHorizontalAlignment(JLabel.CENTER);
        w.setVerticalAlignment(JLabel.CENTER);
        b.setHorizontalAlignment(JLabel.CENTER);
        b.setVerticalAlignment(JLabel.CENTER);
        
        w.setSize(w.getMinimumSize());
        b.setSize(b.getMinimumSize());
        
        gameData.add(w);
        gameData.add(b);
        
        // CLOCKS
        final JLabel bTime = new JLabel(blackClock.getTime());
        final JLabel wTime = new JLabel(whiteClock.getTime());
        
        bTime.setHorizontalAlignment(JLabel.CENTER);
        bTime.setVerticalAlignment(JLabel.CENTER);
        wTime.setHorizontalAlignment(JLabel.CENTER);
        wTime.setVerticalAlignment(JLabel.CENTER);
        
        if (!(hh == 0 && mm == 0 && ss == 0)) {
            timer = new Timer(1000, null);
            timer.addActionListener(e -> {
                boolean turn = board.getTurn();

                if (turn) {
                    whiteClock.decr();
                    wTime.setText(whiteClock.getTime());

                    if (whiteClock.outOfTime()) {
                        timer.stop();
                        int n = JOptionPane.showConfirmDialog(
                                gameWindow,
                                bn + " wins by time! Play a new game? \n" +
                                "Choosing \"No\" quits the game.",
                                bn + " wins!",
                                JOptionPane.YES_NO_OPTION);

                        if (n == JOptionPane.YES_OPTION) {
                            new GameWindow(bn, wn, hh, mm, ss);
                        }
                        gameWindow.dispose();
                    }
                } else {
                    blackClock.decr();
                    bTime.setText(blackClock.getTime());

                    if (blackClock.outOfTime()) {
                        timer.stop();
                        int n = JOptionPane.showConfirmDialog(
                                gameWindow,
                                wn + " wins by time! Play a new game? \n" +
                                "Choosing \"No\" quits the game.",
                                wn + " wins!",
                                JOptionPane.YES_NO_OPTION);

                        if (n == JOptionPane.YES_OPTION) {
                            new GameWindow(bn, wn, hh, mm, ss);
                        }
                        gameWindow.dispose();
                    }
                }
            });
            timer.start();
        } else {
            wTime.setText("Untimed game");
            bTime.setText("Untimed game");
        }
        
        gameData.add(wTime);
        gameData.add(bTime);
        
        gameData.setPreferredSize(gameData.getMinimumSize());
        
        return gameData;
    }

    //buttons
    private JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 10, 0));
        
        final JButton quit = new JButton("Quit");
        
        quit.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to quit?",
                    "Confirm quit", JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                if (timer != null) timer.stop();
                gameWindow.dispose();
            }
        });
        
        final JButton nGame = new JButton("New game");
        
        nGame.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to begin a new game?",
                    "Confirm new game", JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        });
        
        final JButton instr = new JButton("How to play");
        
        instr.addActionListener(e -> JOptionPane.showMessageDialog(gameWindow,
                "Move the chess pieces by drag and drop \n"
                + "system. You can win by time or by checkmate \n"
                + "\nGood luck, hope you enjoy my game!",
                "How to play",
                JOptionPane.PLAIN_MESSAGE));
        
        buttons.add(instr);
        buttons.add(nGame);
        buttons.add(quit);
        buttons.setPreferredSize(buttons.getMinimumSize());
        return buttons;
    }

    /**
     * method called when mate occurred and screen has to be changed
     * @param c - case 0 - white won, 1 - black won, else - stalemate
     */

    public void checkmateOccurred (int c) {
        if (c == 0) {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "White wins by checkmate! Set up a new game? \n" +
                    "Choosing \"No\" lets you look at the final situation.",
                    "White wins!",
                    JOptionPane.YES_NO_OPTION);
            
            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        } else if(c==1) {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Black wins by checkmate! Set up a new game? \n" +
                    "Choosing \"No\" lets you look at the final situation.",
                    "Black wins!",
                    JOptionPane.YES_NO_OPTION);
            
            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        }else{
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "It's a stalemate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "It's a tie!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        }
    }
}

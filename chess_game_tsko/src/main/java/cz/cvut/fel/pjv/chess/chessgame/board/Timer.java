package cz.cvut.fel.pjv.chess.chessgame.board;

/**
 * Timer class is being called when move is being made and returns time in ns
 * it is very import to know code performance
 * @author artomnorba
 */

public class Timer extends Thread{

    int count = 0;
    boolean exit = false;

    public void run(){
        while(!exit){
            count++;
            try { sleep(0,1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * stops counter
     */

    public void stopThread(){
        exit = true;
    }

    /**
     * @return - time required to make move
     */

    public int getTime(){
        stopThread();
        return count;
    }
}

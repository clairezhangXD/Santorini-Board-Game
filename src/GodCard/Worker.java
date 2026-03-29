package GodCard;

import Board.Board;
import Board.Square;

/***
 * Worker class represents players game pieces
 * Created by:
 * @author joyceleung
 * @author clairezhang
 */
public class Worker {
    public int workerId; //associated player number
    public int xpos;
    public int ypos;
    public int level;

    /**
     * Worker constructor.
     * @param workerId ID of worker, identifies worker to a player
     * @param xpos x position of worker
     * @param ypos y position of worker
     * @param level level of worker
     */
    public Worker(int workerId, int xpos, int ypos, int level) {
        this.workerId = workerId;
        this.xpos = xpos;
        this.ypos = ypos;
        this.level = level;
    }

    /**
     * Setter for x position of worker
     */
    public void setXpos(int newXpos){this.xpos = newXpos;}

    /**
     * Setter for y position of worker
     */
    public void setYpos(int newYpos){this.ypos = newYpos;}

    /**
     * Setter for level of worker
     */
    public void setLevel(int newLevel){this.level = newLevel;}

    /**
     * Getter for worker location key "x,y"
     * @return Location key string
     */
    public String getLocationKey(){
        return (xpos + "," + ypos);
    }

    /**
     * Getter for worker ID
     * @return worker ID
     */
    public int getWorkerId(){
        return workerId;
    }


    /**
     * Move logic
     * @param board the game board
     * @param square the square being moved to
     */
    public void move (Board board, Square square){
        // Implement move
        board.move(getLocationKey(), square, this);
        setXpos(square.getXpos());
        setYpos(square.getYpos());
        setLevel(square.getLevel());
    }

    /**
     * Build logic
     * @param board the game board
     * @param square the square being built on
     */
    public void build (Board board, Square square){
        //Implement build
        if (!(square == null)){
            board.build(square);
        }
    }

}


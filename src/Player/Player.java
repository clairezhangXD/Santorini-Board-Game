package Player;

import Board.Board;
import Board.Square;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GodCard.GodCard;
import GodCard.Worker;
import UI.ClickHandler;
import UI.GameUI;
import UI.UI_update;


/***
 * Player Class
 * Created by:
 * @author joyceleung
 * @author clairezhang
 */
public class Player implements IPlayer{
    private int playerNumber; //auto increment based on num of players - potensh implement in overall GameEngine
    private GodCard godCard; //randomly assigned


    public Player(int playerNumber, GodCard godCard) {
        this.playerNumber = playerNumber;
        this.godCard = godCard;
    }

    /**
     * Randomly place a worker on the board.
     * @param workerID the id of the worker being placed
     * @param board the board that the workers are being placed on.
     */
    public void placeWorker(int workerID, Board board) throws IOException {
        //RANDOM placement
        Random rand = new Random();
        int maxX = board.getXsize();
        int maxY = board.getYsize();

        boolean found = false;
        while (!found) {
            int xpos = rand.nextInt(maxX);
            int ypos = rand.nextInt(maxY);

            Square square = board.board.get(xpos + "," + ypos);
            if (!square.containsWorker) {
                found = true;
                Worker worker = new Worker(workerID, xpos, ypos, 0);
                board.addWorker(worker);
                square.addWorker(worker);
                System.out.println(worker.getLocationKey());
                square.containsWorker = true;
                square.update_UI(UI_update.MOVE_WORKER_INTO);
                GameUI.updateUI(square);
                return;
            }
        }
    }


    /**
     * Player Turn logic - transfers turn to god card to handle
     * @param board the game board
     * @param clickHandler handles mouse click during turn
     * @see GodCard
     */
    public void playTurn(Board board, ClickHandler clickHandler) {
        Worker selectedWorker = selectWorker(board, clickHandler);
        godCard.executeTurn(board, selectedWorker);
    }

    /**
     * Select worker
     * @param board the game board
     * @param clickHandler handles click of worker selection
     * @return returns the worker that has being selected
     */
    public Worker selectWorker(Board board, ClickHandler clickHandler){

        List<Worker> playersWorkers = board.getPlayerWorkers(playerNumber);
        List<Square> validWorkerSquares = new ArrayList<Square>();
        for (Worker worker : playersWorkers) {
            validWorkerSquares.add(board.getWorkerSquare(worker));
        }

        //prompt
        GameUI.updatePrompter("Player " + this.getPlayerNumber() + " : Select worker!");
        Square selectedSquare = clickHandler.listenForSquare(validWorkerSquares);
        return selectedSquare.getWorker();
    }

    @Override
    public void startTurnTimer() {

    }

    @Override
    public void pauseTurnTimer() {

    }


    @Override
    public void addTime(int seconds) {

    }

    @Override
    public void checkProphecyStoneBenefit(Board board) {

    }

    @Override
    public void placeProphecyStone(Board board, ClickHandler clickHandler) {

    }


    /**
     * Getter for player number
     * @return int player number
     */
    public int getPlayerNumber() {return playerNumber;
    }

    /**
     * Getter for God Card
     * @return God Card
     */
    public GodCard getGodCard() {
        return godCard;
    }
}


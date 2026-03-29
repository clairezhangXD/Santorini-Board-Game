package GodCard;

import Board.Board;
import Board.Square;
import UI.ClickHandler;
import UI.HighlightUI;
import Game.WinCondition;

import java.util.ArrayList;

/***
 * Class representing God: Artemis
 * @see GodCard
 * Created by:
 * @author joyceleung
 * @author clairezhang
 */
public class Artemis extends GodCard{

    private static final String NAME = "Artemis";
    private static final String POWER_DESCRIPTION = "Your Worker may move one additional time, but not back to its initial space.";

    /***
     * Constructor for God Cards
     */
    public Artemis(){
        super(NAME, POWER_DESCRIPTION);
    }

    /**
     * Overrides Move functionality in GodCard. Allows a worker to move twice on their turn. Optionally can click on worker to cancel this.
     * @param board the game board
     * @param worker the worker being moved
     * @return Either null, or the location key of the selected move square
     */
    @Override
    public String moveWorker(Board board, Worker worker) {
        Square avoidSquare = board.getSquare(super.moveWorker(board, worker)); //move worker once

        ArrayList<Square> validMoves = board.getAvailableMoves(worker);
        validMoves.remove(avoidSquare); //move worker twice
        ArrayList<Square> validClicks = new ArrayList<>(validMoves);

        if (validMoves.isEmpty()) { //checking if worker has second available move
            return null; //terminate without moving a second time.
        }
        validClicks.add(board.getWorkerSquare(worker));

        HighlightUI.highlightMovesUI(validMoves);
        //listen for valid move selection
        ClickHandler clickHandler = new ClickHandler();
        Square selectedSquare = clickHandler.listenForSquare(validClicks);
        HighlightUI.removeHighlightsUI(validMoves);

        if (selectedSquare.containsWorker) { //allows for clicking on worker to cancel use of God power
            return null;
        }

        Square currSquare = board.getWorkerSquare(worker);
        worker.move(board, selectedSquare);

        WinCondition.checkWinConditions(board);

        return currSquare.getKey(); //returns square worker moved out of
    }

}

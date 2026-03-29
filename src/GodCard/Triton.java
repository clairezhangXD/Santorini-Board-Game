package GodCard;

import Board.Board;
import Board.Square;
import UI.ClickHandler;
import UI.HighlightUI;
import Game.WinCondition;

import java.util.ArrayList;

/***
 * Class representing God: Triton
 * @see GodCard
 * Created by:
 * @author clairezhang
 */
public class Triton extends GodCard {

    private static final String NAME = "Triton";
    private static final String POWER_DESCRIPTION = "Each time your Worker moves into a perimeter space, it may " +
            "immediately move again";

    /***
     * Constructor for God Cards
     */
    public Triton() {
        super(NAME, POWER_DESCRIPTION);
    }

    /**
     * Overrides Move functionality in GodCard. Allows a worker to move again if they move into a perimeter.
     * Optionally can click on worker to cancel this.
     *
     * @param board  the game board
     * @param worker the worker being moved
     * @return Either null, or the location key of the selected move square
     */
    @Override
    public String moveWorker(Board board, Worker worker) {

        super.moveWorker(board, worker); //default first move

        //check if perimeter square then move again, if they want to
//        Square currLocation = board.getWorkerSquare(worker);
        while (board.isPerimeterSquare(board.getWorkerSquare(worker))) {

            ArrayList<Square> validMoves = board.getAvailableMoves(worker);
            ArrayList<Square> validClicks = new ArrayList<>(validMoves);
            validClicks.add(board.getWorkerSquare(worker)); //can click worker to cancel use of God power
            HighlightUI.highlightMovesUI(validMoves);

            //listen for valid click selection
            ClickHandler clickHandler = new ClickHandler();
            Square selectedSquare = clickHandler.listenForSquare(validClicks);
            HighlightUI.removeHighlightsUI(validMoves);

            if (selectedSquare.containsWorker) { //check if player wishes to skip extra move
                return null;
            }

            worker.move(board, selectedSquare);

            WinCondition.checkWinConditions(board);
        }

        return null;
    }
}
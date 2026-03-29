package GodCard;

import Board.Board;
import Board.Square;
import UI.ClickHandler;
import UI.HighlightUI;

import java.util.ArrayList;

/***
 * Class representing God: Demeter
 * @see GodCard
 * Created by:
 * @author joyceleung
 * @author clairezhang
 */
public class Demeter extends GodCard {

    private static final String NAME = "Demeter";
    private static final String POWER_DESCRIPTION = "Your Worker may build one additional time, but not on the same space.";

    /***
     * Constructor for Demeter
     */
    public Demeter() {
        super(NAME, POWER_DESCRIPTION);
    }

    /**
     * Overrides BuildBlock method in GodCard. Allows worker to build twice on their turn. Optionally can click on worker to cancel this.
     * @param board the game board
     * @param worker the worker whose turn it is
     * @return either null, or the location key of the selected build square
     */
    @Override
    public String buildBlock(Board board, Worker worker) {

        Square avoidSquare = board.getSquare(super.buildBlock(board, worker)); //build first block

        ArrayList<Square> validBuilds = board.getAvailableBuilds(worker);
        validBuilds.remove(avoidSquare); //build second block
        ArrayList<Square> validClicks = new ArrayList<>(validBuilds);

        if (validBuilds.isEmpty()) { //checking if worker has second available build
            return null; //terminate without building a second time
        }
        validClicks.add(board.getWorkerSquare(worker));

        HighlightUI.highlightBuildsUI(validBuilds);
        //listen for valid build selection
        ClickHandler clickHandler = new ClickHandler();
        Square selectedSquare = clickHandler.listenForSquare(validClicks);
        HighlightUI.removeHighlightsUI(validBuilds);

        if (selectedSquare.containsWorker) { //allows for clicking on worker to cancel use of God power
            return null;
        }

        worker.build(board, selectedSquare);
        return selectedSquare.getKey();
    }
}



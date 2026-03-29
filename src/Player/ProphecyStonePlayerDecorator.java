package Player;

import Board.Board;
import Board.Square;
import GodCard.Worker;
import UI.ClickHandler;
import UI.GameUI;
import UI.HighlightUI;


import javax.swing.text.Highlighter;
import java.util.ArrayList;

public class ProphecyStonePlayerDecorator extends PlayerDecorator{
    private String prophecyStoneKey;

    public ProphecyStonePlayerDecorator(IPlayer decoratedPlayer) {
        super(decoratedPlayer); // Call the constructor of the abstract PlayerDecorator
    }

    //--- Override ProphecyStone related methods from IPlayer ---
    @Override
    public void placeProphecyStone(Board board, ClickHandler clickHandler) {
        //highlight all square UI and add clicklistener to each;
        HighlightUI.highlightProphecyStoneUI(board, decoratedPlayer.getPlayerNumber());
        ArrayList<Square> allSquaresList = new ArrayList<>(board.getAllSquares());

        //prompt
        GameUI.updatePrompter("Player " + decoratedPlayer.getPlayerNumber() + " : Place prophecy stone!");

        //receive selected square via ClickHandler, store in prophecyStoneKey
        Square selectedSquare = clickHandler.listenForSquare(allSquaresList);
        this.prophecyStoneKey = selectedSquare.getKey();

        //remove prophecy stone highlights from UI
        HighlightUI.removeHighlightsUI(allSquaresList);

        //prompt
        GameUI.updatePrompter("Player " + decoratedPlayer.getPlayerNumber() + " : Click prophecy stone to hide!");

        //add prophecy stone to UI, click again to hide
        ArrayList<Square> squareList = new ArrayList<>(); // Create an empty ArrayList
        squareList.add(selectedSquare);
        HighlightUI.revealProphecyStone(selectedSquare, decoratedPlayer.getPlayerNumber());
        clickHandler.listenForSquare(squareList);
        HighlightUI.removeHighlightsUI(squareList);
    }

    /**
     * reveal prophecy stone and check benefit, click stone to activate bonus or continue
     * @param board The game board.
     */
    @Override
    public void checkProphecyStoneBenefit(Board board) {
        Square prophecyStoneSquare = board.getSquare(prophecyStoneKey);
        ArrayList<Square> squareList = new ArrayList<>(); // Create an empty ArrayList
        squareList.add(prophecyStoneSquare);
        ClickHandler clickHandler = new ClickHandler();
        //reveal
        HighlightUI.revealProphecyStone(prophecyStoneSquare, decoratedPlayer.getPlayerNumber());
        //check against opp worker
        if (prophecyStoneSquare.containsWorker) {
            Worker worker = prophecyStoneSquare.getWorker();
            if (worker.getWorkerId() != decoratedPlayer.getPlayerNumber()) {
                //prompt
                int TIME_BENEFIT = 30;
                GameUI.updatePrompter("Player " + decoratedPlayer.getPlayerNumber() + " : Prophecy fulfilled! Click stone to receive "+ TIME_BENEFIT + "s benefit");
                clickHandler.listenForSquare(squareList);
                decoratedPlayer.addTime(TIME_BENEFIT);
            } else {
                //prompt
                GameUI.updatePrompter("Player " + decoratedPlayer.getPlayerNumber() + " : No prophecy benefit. Click stone to collect and continue.");
                clickHandler.listenForSquare(squareList);
            }
        } else {
            //prompt
            GameUI.updatePrompter("Player " + decoratedPlayer.getPlayerNumber() + " : No prophecy benefit. Click stone to collect and continue.");
            clickHandler.listenForSquare(squareList);
        }

        //remove prophecy stone from UI
        HighlightUI.removeHighlightsUI(squareList);

        //remove prophecy stone
        this.prophecyStoneKey = null;

    }

    @Override
    public void playTurn(Board board, ClickHandler clickHandler){
        if (prophecyStoneKey != null){
        checkProphecyStoneBenefit(board);
        }
        decoratedPlayer.playTurn(board, clickHandler);
        placeProphecyStone(board, clickHandler);
    }
}

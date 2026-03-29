package GodCard;

import Board.Board;
import Board.Square;
import UI.ClickHandler;
import UI.GameUI;
import UI.HighlightUI;
import java.util.ArrayList;

import Game.WinCondition;

/***
 * Abstract Class for God Cards and their logic
 * Created by:
 * @author joyceleung
 * @author clairezhang
 */
public abstract class GodCard {

    private String name;
    private String powerDescription;

    /***
     * Constructor for God Cards
     * @param name - name of the God
     * @param powerDescription - description of how God power alters player actions.
     */
    public GodCard(String name, String powerDescription) {
        this.name = name;
        this.powerDescription = powerDescription;
    };

    /***
     * Getter for God's name
     * @return name of God
     */
    public String getName() {
        return name;
    };

    /***
     * Getter for God's power
     * @return God Power description string
     */
    public String getPowerDescription() {
        return powerDescription;
    }

    /***
     * Execute steps of the Player's turn
     */
    public void executeTurn(Board board, Worker worker) {
        //prompt
        GameUI.updatePrompter("Move!");
        moveWorker(board, worker);
        //prompt
        GameUI.updatePrompter("Build!");
        buildBlock(board, worker);

    }

    /**
     * Move worker logic
     * @param board the game board
     * @param worker the worker being moved
     */
    public String moveWorker(Board board, Worker worker) {
        ArrayList<Square> validMoves = board.getAvailableMoves(worker);

        HighlightUI.highlightMovesUI(validMoves);
        //listen for valid move selection
        ClickHandler clickHandler = new ClickHandler();
        Square selectedSquare = clickHandler.listenForSquare(validMoves);
        HighlightUI.removeHighlightsUI(validMoves);

        Square currSquare = board.getWorkerSquare(worker);
        worker.move(board, selectedSquare);

        WinCondition.checkWinConditions(board);

        return currSquare.getKey(); //returns square worker moved out of
    }

    public String buildBlock(Board board, Worker worker) {

        //get list of valid builds and highlight them
        ArrayList<Square> validBuilds = board.getAvailableBuilds(worker);

        HighlightUI.highlightBuildsUI(validBuilds);
        //listen for valid build selection
        ClickHandler clickHandler = new ClickHandler();
        Square selectedSquare = clickHandler.listenForSquare(validBuilds);
        HighlightUI.removeHighlightsUI(validBuilds);

        worker.build(board, selectedSquare);
        return selectedSquare.getKey(); //returns square worker built on
    }

    /***
     * Gets string description of God and Power.
     * @return String description
     */
    public String toString() {
        return name + ": " + powerDescription;
    }


}


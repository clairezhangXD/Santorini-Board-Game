package Player;

import Board.Board;
import GodCard.GodCard;
import GodCard.Worker;
import UI.ClickHandler;
import java.io.IOException;

/**
 * Defines the contract for any player object in the game,
 * including base player functionality and optional decorated behaviors.
 */
public interface IPlayer {

    // --- Core Player Methods ---

    /**
     * Gets the unique identifier for this player.
     * @return The player's number (e.g., 1 or 2).
     */
    int getPlayerNumber();

    /**
     * Gets the GodCard assigned to this player.
     * @return The GodCard object.
     */
    GodCard getGodCard();

    /**
     * Places a worker on the game board.
     * @param workerID The ID of the worker to place (usually matches playerNumber).
     * @param board The game board.
     * @throws IOException If there's an issue with worker UI updates (e.g., image loading).
     */
    void placeWorker(int workerID, Board board) throws IOException;

    /**
     * Executes a full turn for the player, including selecting a worker,
     * moving, and building, potentially with GodCard powers.
     * Decorators will wrap this method to add pre/post turn logic.
     * @param board The game board.
     * @param clickHandler The handler for user clicks.
     */
    void playTurn(Board board, ClickHandler clickHandler);

    /**
     * Allows the player to select one of their workers on the board.
     * @param board The game board.
     * @param clickHandler The handler for user clicks.
     * @return The selected Worker object.
     */
    Worker selectWorker(Board board, ClickHandler clickHandler);


    // --- Timer Related Methods (for TimedPlayerDecorator) ---

    /**
     * Starts the turn timer for the current player.
     */
    void startTurnTimer();

    /**
     * Pauses the turn timer for the current player.
     */
    void pauseTurnTimer();

    /**
     * Adds a specified number of seconds to the player's turn timer.
     * @param seconds The amount of time in seconds to add.
     */
    void addTime(int seconds);


    // --- Prophecy Stone Related Methods (for ProphecyStonePlayerDecorator) ---

    /**
     * Guides the player to secretly place a Prophecy Stone on the board.
     * This might involve UI interaction to select a square.
     * @param board The game board.
     * @param clickHandler The handler for user clicks.
     */
    void placeProphecyStone(Board board, ClickHandler clickHandler);

    /**
     * Reveals a previously placed Prophecy Stone and checks if the player
     * receives a benefit based on the square's current state (opponent worker).
     * @param board The game board.
     */
    void checkProphecyStoneBenefit(Board board);

}
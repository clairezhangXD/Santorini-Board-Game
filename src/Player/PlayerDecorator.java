package Player;

import Board.Board; // Import Board class
import GodCard.GodCard; // Import GodCard class
import GodCard.Worker; // Import Worker class
import UI.ClickHandler; // Import ClickHandler class
import java.io.IOException; // For methods that might throw IOException

/**
 * Abstract base class for all player decorators.
 * Implements the IPlayer interface and delegates all calls to the
 * decorated IPlayer object by default. Concrete decorators will
 * extend this class and override specific methods to add functionality.
 */
public abstract class PlayerDecorator implements IPlayer {
    protected IPlayer decoratedPlayer; // The IPlayer object being decorated

    /**
     * Constructs a PlayerDecorator.
     *
     * @param decoratedPlayer The IPlayer object to be decorated.
     */
    public PlayerDecorator(IPlayer decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    // --- Core Player Methods (Delegated) ---

    @Override
    public int getPlayerNumber() {
        return decoratedPlayer.getPlayerNumber();
    }

    @Override
    public GodCard getGodCard() {
        return decoratedPlayer.getGodCard();
    }

    @Override
    public void placeWorker(int workerID, Board board) throws IOException {
        decoratedPlayer.placeWorker(workerID, board);
    }

    @Override
    public void playTurn(Board board, ClickHandler clickHandler) {
        // override to add pre/post turn logic
        // By default, it delegates to the next layer.
        decoratedPlayer.playTurn(board, clickHandler);
    }

    @Override
    public Worker selectWorker(Board board, ClickHandler clickHandler) {
        return decoratedPlayer.selectWorker(board, clickHandler);
    }


    // --- Timer Related Methods (Delegated) ---

    @Override
    public void startTurnTimer() {
        decoratedPlayer.startTurnTimer();
    }

    @Override
    public void pauseTurnTimer() {
        decoratedPlayer.pauseTurnTimer();
    }


    @Override
    public void addTime(int seconds) {
        decoratedPlayer.addTime(seconds);
    }


    // --- Prophecy Stone Related Methods (Delegated) ---

    @Override
    public void placeProphecyStone(Board board, ClickHandler clickHandler) {
        decoratedPlayer.placeProphecyStone(board, clickHandler);
    }

    @Override
    public void checkProphecyStoneBenefit(Board board) {
        decoratedPlayer.checkProphecyStoneBenefit(board);
    }
}
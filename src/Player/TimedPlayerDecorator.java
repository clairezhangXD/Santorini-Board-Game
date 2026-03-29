package Player;

import javax.swing.JLabel; // For passing to PlayerTimer

/**
 * A concrete decorator that adds turn-based timing functionality to an IPlayer.
 * It manages a PlayerTimer instance to track and display the player's remaining time.
 */
public class TimedPlayerDecorator extends PlayerDecorator {

    private PlayerTimer playerTimer; // The actual timer instance
    private static final int INITIAL_TIME_MINUTES = 2;

    /**
     * Constructs a TimedPlayerDecorator.
     *
     * @param decoratedPlayer The IPlayer object to be decorated with timer functionality.
     * @param timerLabel The JLabel on the UI where this player's timer will be displayed.
     */
    public TimedPlayerDecorator(IPlayer decoratedPlayer, JLabel timerLabel) {
        super(decoratedPlayer); // Call the constructor of the abstract PlayerDecorator
        // Initialise the PlayerTimer for this decorated player
        this.playerTimer = new PlayerTimer(INITIAL_TIME_MINUTES, timerLabel, decoratedPlayer.getPlayerNumber());
    }

    // --- Override Timer Related Methods from IPlayer ---
    // These methods now delegate to the internal PlayerTimer instance

    @Override
    public void startTurnTimer() {
        playerTimer.start();
    }

    @Override
    public void pauseTurnTimer() {
        playerTimer.pause();
    }

    @Override
    public void addTime(int seconds) {
        playerTimer.addTime(seconds);
    }

    // All other IPlayer methods  are inherited from
    // PlayerDecorator and automatically delegated to decoratedPlayer.
}
package Player;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.text.DecimalFormat; // For formatting time

import UI.GameUI;

import Game.WinCondition;

/**
 * Manages a player's countdown timer during their turn.
 * Updates a JLabel on the UI with the remaining time.
 */
public class PlayerTimer {
    private int remainingTimeSeconds; // Total seconds remaining
    private Timer swingTimer;         // The javax.swing.Timer instance
    private JLabel timerLabel;        // The UI label to update
    private final int playerId;       // The ID of the player this timer belongs to
    private boolean isPaused;         // To track if the timer is paused

    // Constants
    private static final int TIMER_DELAY_MS = 1000; // Update every second
    private static final DecimalFormat TIME_FORMATTER = new DecimalFormat("00"); // For MM:SS format

    /**
     * Constructs a PlayerTimer.
     *
     * @param initialTimeMinutes The initial time in minutes for the timer.
     * @param timerLabel The JLabel on the UI that will display the time.
     * @param playerId The ID of the player associated with this timer.
     */
    public PlayerTimer(int initialTimeMinutes, JLabel timerLabel, int playerId) {
        this.remainingTimeSeconds = initialTimeMinutes * 60; // Convert minutes to seconds
        this.timerLabel = timerLabel;
        this.playerId = playerId;
        this.isPaused = true; // Timer starts paused

        // Initialise the Swing Timer
        this.swingTimer = new Timer(TIMER_DELAY_MS, e -> {
            if (!isPaused) {
                remainingTimeSeconds--;
                updateLabel();
                if (remainingTimeSeconds <= 0) {
                    stop(); // Stop the timer when time runs out
                }
            }
        });

        updateLabel(); // Initial display of the time
    }

    /**
     * Starts the timer countdown.
     * If the timer is already running and not paused, this does nothing.
     */
    public void start() {
        if (isPaused && remainingTimeSeconds > 0) {
            isPaused = false;
            if (!swingTimer.isRunning()) {
                swingTimer.start();
            }
        }
    }

    /**
     * Pauses the timer countdown.
     */
    public void pause() {
        if (!isPaused) {
            isPaused = true;
        }
    }

    /**
     * Stops the timer completely and initiate win condition for opponent
     */
    public void stop() {
        if (swingTimer.isRunning()) {
            swingTimer.stop();
        }
        isPaused = true;

        WinCondition.winCondition(playerId == 1 ? 2 : 1);
    }

    /**
     * Adds a specified number of seconds to the remaining time.
     *
     * @param seconds The amount of time in seconds to add.
     */
    public void addTime(int seconds) {
        remainingTimeSeconds += seconds;
        GameUI.updateTimerDisplay(playerId, formatTime(remainingTimeSeconds));
    }

    /**
     * Updates the text of the JLabel with the current formatted time.
     */
    private void updateLabel() {
        if (timerLabel != null) {
            timerLabel.setText("Player " + playerId + " Time: " + formatTime(remainingTimeSeconds));
        }
    }

    /**
     * Formats the time from seconds into MM:SS format.
     *
     * @param totalSeconds The total number of seconds.
     * @return Formatted time string (MM:SS).
     */
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return TIME_FORMATTER.format(minutes) + ":" + TIME_FORMATTER.format(seconds);
    }
}
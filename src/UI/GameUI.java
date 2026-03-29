package UI; // Create a new package for UI if you don't have one

import Board.Board;
import Board.Square;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Manages the graphical user interface for the Santorini game.
 * Responsible for initialising the UI and providing methods to update it.
 */
public class GameUI {
    private JFrame frame;
    private static int xsize;
    private static JPanel gameBoardPanel; // This will be the 'panel' from your original code
    private JTextArea player1GodCardText;
    private JTextArea player2GodCardText;
    private static JLabel player1TimerLabel;
    private static JLabel player2TimerLabel;
    private static JTextArea prompterText;

    public GameUI(Board board, int xsize, int ysize) {
        this.xsize = xsize;
        initialise_UI(board, xsize, ysize);
    }

    private void initialise_UI(Board board, int xsize, int ysize) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Santorini");
        frame.setSize(1200, 850);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Game board panel
        gameBoardPanel = new JPanel(new GridLayout(ysize, xsize, 5, 5));
        gameBoardPanel.setPreferredSize(new Dimension(800, 800)); // Use preferredSize for layout managers
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                gameBoardPanel.add(board.getBoard().get(j + "," + i)); // Assuming board.get(key) returns a Square (which is a JLabel)
            }
        }

        // Player info panel
        JPanel playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding around the panel

        //Human value explanation;
        JLabel humanValueLabel = new JLabel("Human Value: Wisdom");
        humanValueLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        JTextArea humanValueText = new JTextArea("At the end of each player's turn, they will have the opportunity to place a prophecy stone anywhere on the board." +
                " If after the opponent's turn, the prophecy stone is on the same square as an opponent worker, the player will receive a time benefit!");
        humanValueText.setEditable(false);
        humanValueText.setLineWrap(true);
        humanValueText.setWrapStyleWord(true);
        humanValueText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        humanValueText.setMaximumSize(new Dimension(300, 100)); // Limit height
        humanValueText.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visibility

        // --- PLAYER 1 INFO ---
        JLabel player1Label = new JLabel("Player 1");
        player1Label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        player1GodCardText = new JTextArea("Your God Card is: ");
        player1GodCardText.setEditable(false);
        player1GodCardText.setLineWrap(true);
        player1GodCardText.setWrapStyleWord(true);
        player1GodCardText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        player1GodCardText.setMaximumSize(new Dimension(300, 60)); // Limit height
        player1GodCardText.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visibility

        ImageIcon player1Image = loadImageIcon("worker1_level0.png", 100, 100);
        JLabel player1ImageLabel = new JLabel(player1Image);
        player1ImageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // --- PLAYER 1 TIMER LABEL ---
        player1TimerLabel = new JLabel(" ");
        player1TimerLabel.setFont(player1TimerLabel.getFont().deriveFont(Font.BOLD, 36.0f)); // Bold and larger
        player1TimerLabel.setForeground(new Color(127, 50, 222));
        player1TimerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // --- PROMPTER ---
        prompterText = new JTextArea("Welcome to Santorini!");
        prompterText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        prompterText.setEditable(false);
        prompterText.setLineWrap(true);
        prompterText.setWrapStyleWord(true);
        prompterText.setFont(prompterText.getFont().deriveFont(Font.BOLD, 20.0f)); // Bold and larger
        prompterText.setBackground(Color.black);
        prompterText.setForeground(Color.WHITE);
        prompterText.setMaximumSize(new Dimension(300, 80)); // Limit height
        prompterText.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add border for visibility

        // --- PLAYER 2 INFO ---
        JLabel player2Label = new JLabel("Player 2");
        player2Label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        player2GodCardText = new JTextArea("Your God Card is:");
        player2GodCardText.setEditable(false);
        player2GodCardText.setLineWrap(true);
        player2GodCardText.setWrapStyleWord(true);
        player2GodCardText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        player2GodCardText.setMaximumSize(new Dimension(300, 80)); // Limit height
        player2GodCardText.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visibility

        ImageIcon player2Image = loadImageIcon("worker2_level0.png", 100, 100);
        JLabel player2ImageLabel = new JLabel(player2Image);
        player2ImageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // --- PLAYER 2 TIMER LABEL ---
        player2TimerLabel = new JLabel(" ");
        player2TimerLabel.setFont(player2TimerLabel.getFont().deriveFont(Font.BOLD, 36.0f));
        player2TimerLabel.setForeground(new Color(204, 73, 145));
        player2TimerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);


        // --- Add components to playerInfo panel in order ---
        playerInfoPanel.add(humanValueLabel);
        playerInfoPanel.add(humanValueText);
        playerInfoPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        playerInfoPanel.add(player1Label);
        playerInfoPanel.add(player1GodCardText);
        playerInfoPanel.add(player1ImageLabel);
        playerInfoPanel.add(player1TimerLabel);
        playerInfoPanel.add(Box.createRigidArea(new Dimension(0, 40))); // More spacing between player and prompter

        playerInfoPanel.add(prompterText);

        playerInfoPanel.add(Box.createRigidArea(new Dimension(0, 40))); // More spacing between player and prompter
        playerInfoPanel.add(player2Label);
        playerInfoPanel.add(player2GodCardText);
        playerInfoPanel.add(player2ImageLabel);
        playerInfoPanel.add(player2TimerLabel);

        // Main content panel to hold game board and player info side-by-side
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.X_AXIS));
        mainContentPanel.add(gameBoardPanel);
        mainContentPanel.add(playerInfoPanel);

        frame.add(mainContentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // Helper method to load images
    private ImageIcon loadImageIcon(String path, int width, int height) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            if (is == null) {
                System.err.println("Could not find resource: " + path);
                throw new IOException("Resource not found");
            }
            Image imageRaw = ImageIO.read(is);
            return new ImageIcon(imageRaw.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    /**
     * Updates the text for a player's God Card description.
     * @param playerNumber The player ID (1 or 2).
     * @param godCardText The text description of the God Card.
     */
    public void updateGodCardText(int playerNumber, String godCardText) {
        if (playerNumber == 1) {
            player1GodCardText.setText("Your God Card is:\n" + godCardText);
        } else if (playerNumber == 2) {
            player2GodCardText.setText("Your God Card is:\n" + godCardText);
        }
    }

    /**
     * Updates the displayed time for a player's timer.
     * This method will be called by PlayerTimer.
     * @param playerNumber The player ID (1 or 2).
     * @param timeString The formatted time string (e.g., "MM:SS").
     */
    public static void updateTimerDisplay(int playerNumber, String timeString) {
        if (playerNumber == 1) {
            player1TimerLabel.setText(timeString);
        } else if (playerNumber == 2) {
            player2TimerLabel.setText(timeString);
        }
    }

    /**
     * Update prompter message
     * @param message to display
     */
    public static void updatePrompter(String message) {
        prompterText.setText(message);
    }


    // Getter for the game board panel, useful if other classes need to add/remove components from it
    public static JPanel getGameBoardPanel() {
        return gameBoardPanel;
    }

    /**
     * getter for player 1 timer label
     * @return player1 timer jlabel
     */
    public JLabel getPlayer1TimerLabel() {
        return player1TimerLabel;
    }

    /**
     * getter for player 2 timer label
     * @return player2 tiemr jlabel
     */
    public JLabel getPlayer2TimerLabel() {
        return player2TimerLabel;
    }

    /**
     * Updates the UI image of a particular square
     * @param square the square to be updated
     */
    public static void updateUI(Square square) {
        int x = square.getXpos();
        int y = square.getYpos();
        ImageIcon imageIcon = Square.uiDisplay;
        JLabel label = (JLabel) GameUI.getGameBoardPanel().getComponent(y * xsize + x);
        label.setIcon(imageIcon);
    }

}
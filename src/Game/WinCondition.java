package Game;

import Board.Board;
import GodCard.Worker;
import javax.swing.*;

public class WinCondition {
    /**
     * Checks if a win condition is encountered before or after a turn starts
     * @return bool determining if a win has occurred
     */
    public static boolean checkWinConditions(Board board) {
        int losers1 = 0;
        int losers2=0;
        for (Worker worker: board.getWorkers()) {
            if (worker.level ==3){
                winCondition(worker.workerId);
                return true;
            }
            if (board.getAvailableMoves(worker).size() == 0) {
                if (worker.workerId == 1){
                    losers1++;
                }
                else {
                    losers2++;
                }
            }
        }
        if (losers1 == 2) {
            winCondition(2);
            return true;
        }
        if (losers2 == 2){
            winCondition(1);
            return true;
        }
        return false;
    }

    /**
     * Performs pop up on a win occurring
     * @param playerNumber the player who has won
     */
    public static void winCondition(int playerNumber) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("You win!");
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        JLabel winMessage = new JLabel();
        winMessage.setText("Game Over! Player "+playerNumber+" wins! Congratulations :)");
        frame.add(winMessage);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

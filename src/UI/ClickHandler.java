package UI;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import Board.Square;

/**
 * Click Handler Class. Responsible for watching for mouse clicks.
 * Created by:
 * @clairezhang
 */
public class ClickHandler {
    private List<Square> validSquares;
    private CountDownLatch countDownLatch;
    protected Square selectedSquare;


    /**
     * Listener for Square Click
     * @param validSquares Clickable squares
     * @return Square that is clicked
     */
    public Square listenForSquare(List<Square> validSquares){
        clearListeners();

        countDownLatch = new CountDownLatch(1);

        this.validSquares = validSquares;
        for (Square square : validSquares) {
            square.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            square.addMouseListener(new SquareClickListener(square));
        }

        try{
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Startup interrupted");
        }

        return selectedSquare;
    }

    /**
     * Removes listeners for squares once no longer needed
     */
    public void clearListeners() {
        if (validSquares != null) {
            for (Square square : validSquares) {
                // Remove ALL mouse listeners
                for (var listener : square.getMouseListeners()) {
                    square.removeMouseListener(listener);
                }
                square.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
        validSquares = null;
    }

    /**
     * Listens for clicks and apply them to a specific square
     */
    private class SquareClickListener extends MouseAdapter {
        private final Square square;

        public SquareClickListener(Square square) {
            this.square = square;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("clicked!");
            countDownLatch.countDown();
            selectedSquare = this.square;
            clearListeners();
        }
    }
}
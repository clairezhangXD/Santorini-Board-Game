package UI;

import Board.Board;
import Board.Square;

import java.util.ArrayList;

public class HighlightUI {

    public static void highlightMovesUI(ArrayList<Square> validMoves) {
        //get list of valid moves and highlight them

        for (Square moveSquare : validMoves) {
            moveSquare.update_UI(UI_update.HIGHLIGHT_MOVE);
            GameUI.updateUI(moveSquare);
        }
    }

    public static void removeHighlightsUI(ArrayList<Square> highlightedSquares) {

        //remove highlights
        for (Square moveSquare: highlightedSquares){
            moveSquare.update_UI(UI_update.REMOVE_HIGHLIGHTS);
            GameUI.updateUI(moveSquare);
        }
    }

    public static void highlightBuildsUI( ArrayList<Square> validBuilds) {
        //get list of valid squares and highlight them
        for (Square buildSquare: validBuilds){
            buildSquare.update_UI(UI_update.HIGHLIGHT_BUILD);
            GameUI.updateUI(buildSquare);
        }
    }

    public static void highlightProphecyStoneUI(Board board, int playerNumber) {
        //get all squares and highlight them
        for (Square prophecyStoneSquare: board.getAllSquares()){
            prophecyStoneSquare.update_prophecy_stone_UI(playerNumber, UI_prophecy_stone_update.HIGHLIGHT_PROPHECYSTONE);
            GameUI.updateUI(prophecyStoneSquare);
        }
    }

    public static void revealProphecyStone(Square prophecyStoneSquare, int playerNumber){
        prophecyStoneSquare.update_prophecy_stone_UI(playerNumber, UI_prophecy_stone_update.REVEAL_PROPHECYSTONE);
        GameUI.updateUI(prophecyStoneSquare);
    }

}

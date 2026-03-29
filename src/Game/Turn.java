package Game;

import Board.Board;
import Player.Player;
import UI.ClickHandler;
import Player.IPlayer;

/***
 * Turn Class - holds logic for each single turn (move and build)
 * Created by:
 * @author joyceleung
 * @author clairezhang
 */
public class Turn {

    public void playTurn(Board board, IPlayer player, ClickHandler clickHandler){
        player.playTurn(board, clickHandler);
    }

}



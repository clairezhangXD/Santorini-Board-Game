package Game;

import Board.Board;
import GodCard.Artemis;
import GodCard.Demeter;
import GodCard.Triton;
import GodCard.GodCard;
import Player.IPlayer;
import Player.Player;
import Player.TimedPlayerDecorator;
import Player.ProphecyStonePlayerDecorator;
import UI.ClickHandler;
import UI.GameUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Game engine to run Santorini
 * @author JMAC
 */
public class Game {

    protected Board board;
    protected ArrayList<IPlayer> players = new ArrayList<>(); // Change to IPlayer
    protected ArrayList<GodCard> godCards = new ArrayList<>();
    protected ArrayList<Integer> randomAssign = new ArrayList<>();
    protected ClickHandler clickHandler;
    private GameUI GameUI;

    //Constants for board size
    int XSIZE = 5;
    int YSIZE = 5;

    /**
     * Runs the game
     *
     * @throws IOException if game errors
     */
    public void run() throws IOException {
        godCards.add(new Artemis());
        godCards.add(new Demeter());
        godCards.add(new Triton());

        setupGameBoard();

        // Initial worker placement (this needs to use IPlayer now)
        IPlayer currentPlayer = players.get(0);
        currentPlayer.placeWorker(1, board);
        currentPlayer.placeWorker(1, board);

        IPlayer opponentPlayer = players.get(1);
        opponentPlayer.placeWorker(2, board);
        opponentPlayer.placeWorker(2, board);

        // loop between player 1 turn, then player 2 turn
        while (!WinCondition.checkWinConditions(board)) {
            for (IPlayer player : players) { // Iterate over IPlayer
                if (WinCondition.checkWinConditions(board)) {
                    return;
                }

                player.startTurnTimer(); // Start the timer for the current player

                Turn turn = new Turn();
                turn.playTurn(board, player, clickHandler);

                player.pauseTurnTimer(); // Pause the timer after the turn is complete
            }
        }
    }

    /**
     * Initialise the board, randomly assigns player to board
     */
    protected void setupGameBoard() {
        this.board = new Board(XSIZE, YSIZE); //Initialises 5x5 board
        this.clickHandler = new ClickHandler();
        this.GameUI = new GameUI(this.board, XSIZE, YSIZE); //Initialises 5x5 board UI
        int numPlayers = 2; //Initialises 2 players for now
        System.out.println("start menu successfully loaded");


        //random god card assignment
        for (int j = 0; j < godCards.size(); j++) {
            randomAssign.add(j);
        }
        Collections.shuffle(randomAssign); //randomising order

        for (int i = 0; i < numPlayers; i++) {
            GodCard card = godCards.get(randomAssign.get(i));
            Player basePlayer = new Player(i + 1, card); // Create the concrete Player

            IPlayer decoratedPlayer;
            // Decorate the player with the TimedPlayerDecorator and ProphecyStonePlayerDecorator
            if (i == 0) { // For Player 1
                decoratedPlayer = new TimedPlayerDecorator(basePlayer, GameUI.getPlayer1TimerLabel());
                decoratedPlayer = new ProphecyStonePlayerDecorator(decoratedPlayer);
            } else { // For Player 2
                decoratedPlayer = new TimedPlayerDecorator(basePlayer, GameUI.getPlayer2TimerLabel());
                decoratedPlayer = new ProphecyStonePlayerDecorator(decoratedPlayer);
            }

            this.GameUI.updateGodCardText(basePlayer.getPlayerNumber(), card.toString());
            players.add(decoratedPlayer); // Add the decorated player to the list
        }
    }
}





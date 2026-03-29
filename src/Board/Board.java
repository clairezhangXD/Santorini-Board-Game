package Board;

import GodCard.Worker;
import UI.UI_update;

import java.util.*;
import java.util.List;
import UI.GameUI;

/**
 * Board Class represents game board.
 */
public class Board {
    public int size;
    public int xsize;
    public int ysize;
    public HashMap<String, Square> board = new HashMap<>();
    public ArrayList<Worker> workers = new ArrayList<>();


    /**
     * Constructor for the board class.
     * Creates a new board based on the specified x and y dimensions.
     * Uses nested for loops to iterate through all coordinates and:
     *  1. Creates a new Ground object for each position
     *  2. Generates a string key by combining x,y coordinates with a comma separator
     *  3. Adds each position to the board HashMap using the String Key and the new Board.Square as the value
     */
    public Board(int xsize, int ysize){
        this.xsize = xsize;
        this.ysize = ysize;
        for (int i = 0; i < xsize; i++){
            for(int j = 0; j < ysize; j++){
                String key = i + "," + j;
                Square square = new Ground(i, j);
                board.put(key, square);
            }
        }
        System.out.println("Board loaded successfully");
    }

    public HashMap<String, Square> getBoard(){
        return board;
    }


    /**
     * Get Available Moves
     * @param worker the worker on the square that available moves are being calculated for
     * @return moves: list of available moves, formatted as list of squares
     */
    public ArrayList<Square> getAvailableMoves(Worker worker){

        Square square = getWorkerSquare(worker);
        ArrayList<String> adjSquares = square.getSurroundingSquares();
        int currLvl = square.getLevel();
        ArrayList<Square> moves = new ArrayList<>();

        // For loop checking whether a player can move to the possible adjacent square
        for (String key : adjSquares){
            Square moveSquare = board.get(key);
            if (moveSquare == null) { continue; }
            if (!moveSquare.canMoveTo){continue;}
            if (moveSquare.containsWorker){continue;}
            if (moveSquare.getLevel() - currLvl > 1){continue;}
            moves.add(board.get(key));
        }

        return moves;
    }

    /**
     * Get Available Builds
     * @param worker the worker on the square that available builds are being calculated for
     * @return builds: list of available builds, formatted as list of squares
     */
    public ArrayList<Square> getAvailableBuilds(Worker worker){

        Square square = getWorkerSquare(worker);
        ArrayList<String> adjSquares = square.getSurroundingSquares();
        ArrayList<Square> builds = new ArrayList<>();

        // For loop checking whether a player can build on the possible adjacent square
        for (String key : adjSquares){
            Square buildSquare = board.get(key);
            if (buildSquare == null) { continue; }
            if (!buildSquare.canBuildOn){continue;}
            if (buildSquare.containsWorker){continue;}
            builds.add(board.get(key));
        }

        return builds;
    }

    /**
     * Updates boolean of whether square can be moved to/built on
     *
     * @param currKey Key of Square
     * @param square  Square being updated
     */
    public void move(String currKey, Square square, Worker worker){

        board.get(currKey).containsWorker = false;
        square.removeWorker();
        String moveKey = square.getKey();
        board.get(moveKey).containsWorker = true;
        square.addWorker(worker);

        int xposC = board.get(currKey).xpos;
        int yposC = board.get(currKey).ypos;

        int xposN = board.get(moveKey).xpos;
        int yposN = board.get(moveKey).ypos;

        Square currSquare = board.get(currKey);
        int componentIndex = yposC * xsize + xposC;
        GameUI.getGameBoardPanel().remove(componentIndex);
        GameUI.getGameBoardPanel().add(board.get(currKey),componentIndex);
        GameUI.getGameBoardPanel().revalidate();
        GameUI.getGameBoardPanel().repaint();
        currSquare.update_UI(UI_update.MOVE_WORKER_OUT);
        GameUI.updateUI(currSquare);

        //update UI - add worker to new square
        Square newSquare = board.get(moveKey);
        int componentIndex1 = yposN * xsize + xposN;
        GameUI.getGameBoardPanel().remove(componentIndex1);
        GameUI.getGameBoardPanel().add(newSquare,componentIndex1);
        GameUI.getGameBoardPanel().revalidate();
        GameUI.getGameBoardPanel().repaint();
        newSquare.update_UI(UI_update.MOVE_WORKER_INTO);
        GameUI.updateUI(newSquare);

    }

    /**
     * Gets all Square objects from the board.
     * @return A collection of all squares.
     */
    public Collection<Square> getAllSquares() {
        return board.values();
    }

    /**
     * Build replaces current square with a new square at next level. Removes the old square from board, adds the new square, and updates UI
     * @param square the square being built on
     */
    public void build(Square square){
        String key = square.getKey();
        int currLvl = square.getLevel();
        int xpos = square.xpos;
        int ypos = square.ypos;

        switch (currLvl){
            case 0:
                board.put(key, new LevelOne(xpos, ypos));
                break;
            case 1:
                board.put(key, new LevelTwo(xpos, ypos));
                break;
            case 2:
                board.put(key, new LevelThree(xpos, ypos));
                break;
            case 3:
                board.put(key, new Dome(xpos, ypos));
                break;
        }
        int componentIndex = ypos * xsize + xpos;
        GameUI.getGameBoardPanel().remove(componentIndex);
        GameUI.getGameBoardPanel().add(board.get(key),componentIndex);
        GameUI.getGameBoardPanel().revalidate();
        GameUI.getGameBoardPanel().repaint();
        GameUI.updateUI(board.get(key));
    }

    /**
     * Adds worker to list of workers
     * @param worker the worker being added
     */
    public void addWorker(Worker worker){
        workers.add(worker);
    }

    /**
     * Gets a player's associated workers
     * @param playerNumber player number
     * @return list of player's workers
     */
    public List<Worker> getPlayerWorkers(int playerNumber) {
        List<Worker> playerWorkers = new ArrayList<>();
        for (Worker worker: workers){
            if (worker.workerId == playerNumber){
                playerWorkers.add(worker);
            }
        }
        return playerWorkers;

    }

    /**
     * Gets the X dimension of the board
     * @return x-size of the board
     */
    public int getXsize() {
        return xsize;
    }

    /**
     * Gets the Y dimension of the board
     * @return y-size of the board
     */
    public int getYsize() {
        return ysize;
    }

    /**
     * Getter for Square
     * @param squareKey the key of the square
     * @return Square
     */
    public Square getSquare(String squareKey) {
        return board.get(squareKey);
    }

    /**
     * Getter for worker location
     * @param worker the worker being searched for
     * @return the Square worker is on.
     */
    public Square getWorkerSquare(Worker worker){
        return board.get(worker.getLocationKey());
    }

    /**
     * Getter for workers
     * @return List of workers
     */
    public ArrayList<Worker> getWorkers(){return workers;}

    /**
     *checks if the square is on the perimeter of the board
     * @return true if on the perimeter
     */
    public boolean isPerimeterSquare(Square square){
        int xpos = square.getXpos();
        int ypos = square.getYpos();

        return xpos == (xsize - 1) || xpos == 0 || ypos == (ysize - 1) || ypos == 0;
    }

}

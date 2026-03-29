package Board;

import GodCard.Worker;
import UI.UI_prophecy_stone_update;
import UI.UI_update;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/***
 * Abstract class that defines the Board.Square on the Board
 * Created by:
 * @author May McGrath
 */
public abstract class Square extends JLabel {
    public int xpos;
    public int ypos;
    public int level;
    public boolean canMoveTo;
    public boolean canBuildOn;
    public boolean containsWorker;
    private Worker worker;
    public static ImageIcon uiDisplay;

    private static final int ICON_SIZE = 156;

    private static final int[][] DIRECTIONS = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1},
            {-1, 0}, {-1, -1}, {0, -1}, {1, -1}
    };

    /***
     * Initialise the Board.Square
     * @param xpos the x-position of the square
     * @param ypos the y-position of the square
     */
    public Square(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.containsWorker = false;
    }

    private ImageIcon loadAndScaleIcon(String path) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IOException("Resource not found: " + path);
            Image imageRaw = ImageIO.read(is);
            return new ImageIcon(imageRaw.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }


    public void update_prophecy_stone_UI(int playerNumber, UI_prophecy_stone_update update) {
        switch (update) {
            case HIGHLIGHT_PROPHECYSTONE:
                uiDisplay = loadAndScaleIcon("player" + playerNumber + "_prophecystone_highlight.png");
                break;
            case REVEAL_PROPHECYSTONE:
                uiDisplay = loadAndScaleIcon("player" + playerNumber + "_prophecystone.png");
                break;
        }
    }

    public void update_UI(UI_update update) {
        switch (update) {
            case HIGHLIGHT_MOVE:
                uiDisplay = loadAndScaleIcon("move_level" + level + ".png");
                break;
            case REMOVE_HIGHLIGHTS:
                String imagePath = containsWorker
                        ? String.format("worker%d_level%d.png", worker.getWorkerId(), level)
                        : "level" + level + ".png";
                uiDisplay = loadAndScaleIcon(imagePath);
                break;
            case HIGHLIGHT_BUILD:
                uiDisplay = loadAndScaleIcon("build_level" + level + ".png");
                break;
            case MOVE_WORKER_INTO:
                uiDisplay = loadAndScaleIcon("worker" + worker.getWorkerId() + "_level" + level + ".png");
                break;
            case MOVE_WORKER_OUT:
                uiDisplay = loadAndScaleIcon("level" + level + ".png");
                break;
        }
    }

    /***
     * Getter for the level of the square
     * @return level
     */
    public int getLevel () {
        return level;
    }

    /**
     * Getter for x position of square
     * @return x position
     */
    public int getXpos() { return xpos;}

    /**
     * Getter for y position of square
     * @return y position
     */
    public int getYpos() { return ypos;}

    /**
     * Getter for surrounding squares
     * @return List of Squares
     */
    public ArrayList<String> getSurroundingSquares() {
        ArrayList<String> squares = new ArrayList<String>();

        for (int[] dir: DIRECTIONS){
            int newX = xpos + dir[0];
            int newY = ypos + dir[1];

            String key = newX + "," + newY;
            squares.add(key);
        }
        return squares;
    }

    /**
     * Getter for square key
     * @return key
     */
    public String getKey(){
        return xpos + "," + ypos;
    }

    public void addWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker(){
        return worker;
    }

    public void removeWorker() {
        this.worker = null;
    }
}

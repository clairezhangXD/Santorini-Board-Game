package Board;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/***
 * Class defining the third level built by players on the board
 * Created by:
 * @author May McGrath
 */
public class LevelThree extends Square{
    
    /***
     * Constructor of Level Three
     * @param xpos x coordinate of Dome
     * @param ypos y coordinate of Dome
     */
    public LevelThree (int xpos, int ypos) {
        super(xpos, ypos);
        this.level = 3;
        this.canMoveTo = true;
        this.canBuildOn = true;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("level3.png");
            if (is == null) {
                throw new IOException("Resource not found");
            }
            ImageIcon imageRaw = new ImageIcon(ImageIO.read(is));
            Square.uiDisplay = new ImageIcon(imageRaw.getImage().getScaledInstance(156, 156, Image.SCALE_SMOOTH));
            this.setIcon(Square.uiDisplay);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
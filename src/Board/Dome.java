package Board;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/***
 * Class for the Dome on top of a building
 * Created by:
 * @author May McGrath
 */
public class Dome extends Square{

    /**
     * Dome constructor
     * @param xpos x coordinate of Dome
     * @param ypos y coordinate of Dome
     */
    public Dome (int xpos, int ypos) {
        super(xpos, ypos);
        this.level = 4;
        this.canMoveTo = false;
        this.canBuildOn = false;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("level4.png");
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

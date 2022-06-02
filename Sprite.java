import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;

/**
 * This class loads a sprite sheet and draws the appropriate frame for 
 * animations. Each sprite in the sprite sheet should be stacked on top 
 * of each other.
 */
public class Sprite implements Drawable {
    private Vector position;
    private int width;
    private int height;

    private BufferedImage image;
    
    public Sprite(int x, int y, String picName) {
        this.position = new Vector(x, y);
        
        // Load the image from file.
        this.image = tryLoadImage(picName);
    }

    public Sprite(int x, int y, BufferedImage pic) {
        this.position = new Vector(x, y);
        this.image = pic;

        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    public Sprite(Vector position, BufferedImage pic) {
        this.position = position;
        this.image = pic;

        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
    }

    public static BufferedImage tryLoadImage(String picName) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(picName));
        } catch (IOException ex) {
            System.out.println("Error: Image file not found [" + picName + "]");
            return null;
        }

        return image;
    }

    public int getX() {
        return (int) this.position.getX();
    }

    public int getY() {
        return (int) this.position.getY();
    }

    public Vector getPos() {
        return this.position;
    }
    
    public int getWidth() {
        return this.width;
    }   
    
    public int getHeight() {
        return this.height;
    }

    public void setX(int x) {
        this.position.setX(x);
    }

    public void setY(int y) {
        this.position.setY(y);
    }

    public void setPos(Vector newPos) {
        this.position = newPos;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(this.image, this.getX(), this.getY(), null);
    }
}

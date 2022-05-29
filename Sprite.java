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
public class Sprite {
    private int x;
    private int y;
    private int width;
    private int height;

    private int numFrames;
    private int curIndex;

    private BufferedImage spriteSheet;
    private BufferedImage activeFrame;
    
    public Sprite(int x, int y, int numFrames, String picName) {
        this.x = x;
        this.y = y;
        
        // Load the image from file.
        this.tryLoadImage(picName);

        this.numFrames = numFrames;
        this.width = this.spriteSheet.getWidth();
        this.height = this.spriteSheet.getHeight() / numFrames;

        this.curIndex = 0;
        this.setActiveFrame(this.curIndex);
    }

    public Sprite(int x, int y, int numFrames, BufferedImage pic) {
        this.x = x;
        this.y = y;

        this.spriteSheet = pic;

        this.numFrames = numFrames;
        this.width = this.spriteSheet.getWidth();
        this.height = this.spriteSheet.getHeight() / numFrames;

        this.curIndex = 0;
        this.setActiveFrame(this.curIndex);
    }

    public void tryLoadImage(String picName) {
        try {
            this.spriteSheet = ImageIO.read(new File(picName));
        } catch (IOException ex) {
            System.out.println("Error: Image file not found [" + picName + "]");
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }   
    
    public int getHeight() {
        return this.height;
    }

    public int getNumFrames() {
        return this.numFrames;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * This method loads a frame in the sprite sheet. If the index is higher than the
     * number of frames in the sprite sheet, it uses the modulus of the index.
     * @param index The index of the sprite in the spirte sheet.
     */
    public void setActiveFrame(int index) {
        this.curIndex = index % this.numFrames;
        this.activeFrame = this.spriteSheet.getSubimage(0, this.height * this.curIndex, this.width, this.height);
    }

    /**
     * This method loads the next frame in the sprite sheet. If the current frame is the 
     * last frame, it loops to the first one.
     */
    public void setNextFrame() {
        this.curIndex++;
        this.setActiveFrame(this.curIndex);
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(this.activeFrame, this.x, this.y, null);
    }
}

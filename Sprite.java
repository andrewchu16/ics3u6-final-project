import java.awt.Graphics;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.IOException;

/**
 * This class loads an image into memory for use in a Java GUI.
 */
public class Sprite implements Drawable {
    private Vector position;
    private Vector relPosition;
    private int width;
    private int height;

    private BufferedImage image;
    private BufferedImage originalImage;
    private BufferedImage reflectedImage;
    
    /**
     * This constructs a new {@code Sprite} object.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param picName The file name of the image.
     */
    public Sprite(int x, int y, String picName) {
        this.position = new Vector(x, y);
        this.relPosition = Vector.VECTOR_ZERO.clone();
        
        // Load the image from file.
        this.originalImage = tryLoadImage(picName);
        this.reflectedImage = reflectHorizontally(this.originalImage);
        this.image = originalImage;

        this.width = this.originalImage.getWidth();
        this.height = this.originalImage.getHeight();
    }

    /**
     * This constructs a new {@code Sprite} object.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param pic The image object.
     * @see BufferedImage
     */
    public Sprite(int x, int y, BufferedImage pic) {
        this.position = new Vector(x, y);
        this.relPosition = Vector.VECTOR_ZERO.clone();

        this.originalImage = pic;
        this.reflectedImage = reflectHorizontally(this.originalImage);
        this.image = originalImage;

        this.width = this.originalImage.getWidth();
        this.height = this.originalImage.getHeight();
    }

    /**
     * This constructs a new {@code Sprite} object. If the reference to the position
     * gets changed, the {@code Sprite}'s position also changes.
     * @param position The position of the top-left of the Sprite.
     * @param pic The image object.
     * @see BufferedImage
     */
    public Sprite(Vector position, BufferedImage pic) {
        this.position = position;
        this.relPosition = Vector.VECTOR_ZERO.clone();

        this.originalImage = pic;
        this.reflectedImage = reflectHorizontally(this.originalImage);
        this.image = this.originalImage;

        this.width = this.originalImage.getWidth();
        this.height = this.originalImage.getHeight();
    }

    /**
     * This method attempts to create a {@code BufferedImage} object.
     * @param picName The file name of the image.
     * @return A {@code BufferedImage} object of the image if no errors occur, {@code null} otherwise.
     */
    public static BufferedImage tryLoadImage(String picName) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(picName));
        } catch (IOException ex) {
            System.out.println("Error: Image file not found. [" + picName + "]");
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

    public int getRelX() {
        return (int) this.relPosition.getX();
    }

    public int getRelY() {
        return (int) this.relPosition.getY();
    }

    /**
     * This method gets a copy of the position of the {@code Sprite} object.
     * @return A copy of the position of the {@code Sprite} object.
     */
    public Vector getPos() {
        return this.position.clone();
    }

    /**
     * This method gets a reference of the position of the {@code Sprite} object.
     * If the reference of the position gets modified elsewhere, the position of
     * this {@code Sprite} will change.
     * @return A reference to the position of this {@code Sprite} object.
     */
    public Vector getRefPos() {
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

    /**
     * This method draws this {@code Sprite} onto a surface.
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.drawImage(this.image, this.getX() + this.getRelX(), 
                this.getY() + this.getRelY(), null);
    }

    /**
     * This method draws this {@code Sprite} onto a surface at a coordinate.
     * @param graphics The {@code Graphics} of the surface to draw on.
     * @param position The top-left coordinate to draw this {@code Sprite}.
     */
    public void draw(Graphics graphics, Vector position) {
        graphics.drawImage(this.image, (int) position.getX(), (int) position.getY(), null);
    }

    public boolean checkReflectedHorizontally() {
        return this.image == this.reflectedImage;
    }

    public void reflectHorizontally(int xLine) {
        if (!this.checkReflectedHorizontally()) {
            this.image = this.reflectedImage;
        } else {
            this.image = this.originalImage;
        }
        xLine -= this.getX();
        this.relPosition.reflectHorizontally(xLine);
        this.relPosition.setX(this.getRelX() - this.getWidth());
    }

    public static BufferedImage reflectHorizontally(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int colour = image.getRGB(x, y);
                newImage.setRGB(image.getWidth() - x - 1, y, colour);
            }
        }

        return newImage;
    }
}

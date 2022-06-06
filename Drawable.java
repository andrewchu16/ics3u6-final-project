import java.awt.Graphics;

/**
 * This interface contains the required methods for an object to be drawn onto a surface.
 * @see java.awt.Graphics
 */
public interface Drawable {
    /**
     * This method draws the object onto the {@code Graphics} of a drawable surface.
     * @param graphics The {@code Graphics} of the surface to draw on.
     */
    public void draw(Graphics graphics);
}

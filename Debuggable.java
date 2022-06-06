import java.awt.Graphics;

/**
 * This interface contains the required methods for an object to be debuggable.
 */
public interface Debuggable {
    /**
     * This method draws the debug info of an object onto a surface.
     * @param graphics The graphics of the surface to be drawn on.
     */
    public void drawDebugInfo(Graphics graphics);
}

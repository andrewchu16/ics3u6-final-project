/**
 * THis interface contains the required methods for an object that can be moved.
 */
public interface Moveable {
    /**
     * This method moves the object upwards or sets its direction to upwards.
     */
    public void moveUp();

    /**
     * THis method moves the object left or sets its direction to left.
     */
    public void moveLeft();

    /**
     * This method moves the object down or sets its direction to down.
     */
    public void moveDown();

    /**
     * This method moves the object right or sets its direction to right.
     */
    public void moveRight();
}

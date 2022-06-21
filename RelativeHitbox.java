import java.awt.Graphics;

/**
 * This class represents a hitbox with an anchor and relative position. The 
 * hitbox is at the position which is the sum of the two. When the anchor position
 * changes, the hitbox keeps its position relative to the anchor.
 */
public class RelativeHitbox extends Hitbox {
    private Vector anchorPosition;
    private Vector relativePosition;

    /**
     * This constructs a {@code RelativeHitbox} object with a position and dimensions.
     * @param anchorPosition The anchor position.
     * @param relativePosition The relative position of the top-left of this hitbox.
     * @param width The width of this hitbox.
     * @param height The height of this hitbox.
     */
    public RelativeHitbox(Vector anchorPosition, Vector relativePosition, int width, 
            int height) {
        super(Vector.sum(anchorPosition, relativePosition), width, height);
        this.anchorPosition = anchorPosition;
        this.relativePosition = relativePosition;
    }

    public int getAnchorX() {
        return (int) this.anchorPosition.getX();
    }

    public int getAnchorY() {
        return (int) this.anchorPosition.getY();
    
    }
    public Vector getAnchorPos() {
        return this.anchorPosition.clone();
    }

    public int getRelX() {
        return (int) this.relativePosition.getX();
    }

    public int getRelY() {
        return (int) this.relativePosition.getY();
    }

    public Vector getRelPos() {
        return this.relativePosition.clone();
    }
    
    public void setAnchorPos(Vector newAnchorPos) {
        this.anchorPosition = newAnchorPos;
        super.setPos(Vector.sum(this.anchorPosition, this.relativePosition));
    }

    public void setRelPos(Vector newRelPos) {
        this.relativePosition = newRelPos;
        super.setPos(Vector.sum(this.anchorPosition, this.relativePosition));
    }

    /**
     * This method sets the anchor to a new position.
     * @param newPos The new anchor position of this {@code RelativeHitbox}.
     */
    @Override
    public void setPos(Vector newPos) {
        this.anchorPosition = newPos;
        super.setPos(Vector.sum(this.anchorPosition, this.relativePosition));
    }

    /**
     * This method draws the outline of the hitbox as well as the anchor position.
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        super.drawDebugInfo(graphics);

        graphics.setColor(this.getColor());
        graphics.fillOval((int) this.anchorPosition.getX(), (int) this.anchorPosition.getY(), 2, 2);
    }

    @Override
    public RelativeHitbox clone() {
        return new RelativeHitbox(this.anchorPosition.clone(), this.relativePosition.clone(), 
                this.getWidth(), this.getHeight());
    }

    /**
     * This method reflects this {@code RelativeHitbox} over a vertical line. It sets
     * the anchor and relative positions so that if this method is called twice,
     * the positions end up in the same spot.
     * @param xLine The vertical line to reflect across.
     */
    public void reflectHorizontally(int xLine) {
        xLine -= this.getAnchorX();
        Vector newRelPos = this.relativePosition.clone();
        newRelPos.reflectHorizontally(xLine);
        newRelPos.setX(newRelPos.getX() - this.getWidth());

        this.setRelPos(newRelPos);
    }
}

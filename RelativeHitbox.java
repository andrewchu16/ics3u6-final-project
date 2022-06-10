import java.awt.Graphics;

public class RelativeHitbox extends Hitbox {
    private Vector anchorPosition;
    private Vector relativePosition;

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
        return this.relativePosition;
    }
    
    public void setAnchorPos(Vector newAnchorPos) {
        this.anchorPosition = newAnchorPos;
        this.setPos(Vector.sum(this.anchorPosition, this.relativePosition));
    }

    public void setRelPos(Vector newRelPos) {
        this.relativePosition = newRelPos;
        this.setPos(Vector.sum(this.anchorPosition, this.relativePosition));
    }

    @Override
    public void setPos(Vector newPos) {
        this.anchorPosition = newPos;
        super.setPos(Vector.sum(this.anchorPosition, this.relativePosition));
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        super.draw(graphics);

        graphics.setColor(this.getColor());
        graphics.fillOval((int) this.anchorPosition.getX(), (int) this.anchorPosition.getY(), 1, 1);
    }

    @Override
    public RelativeHitbox clone() {
        return new RelativeHitbox(this.anchorPosition, this.relativePosition, 
                this.getWidth(), this.getHeight());
    }
}

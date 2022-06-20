import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/*
 * This class represents a single frame in the animation cycle of an entity. It
 * contains both the sprite and the hitboxes associated with it.
 */
public class AnimationFrame implements Drawable, Debuggable, Collidable {
    private Vector position;
    private Sprite sprite;
    private ArrayList<RelativeHitbox> hitboxes;

    /**
     * This constructs an AnimationFrame object with a single hitbox around the image.
     * @param x The x-coordinate of the AnimationFrame.
     * @param y The y-coordinate of the AnimationFrame.
     * @param pic The image to use for the sprite.
     */
    public AnimationFrame(int x, int y, BufferedImage pic) {
        this.position = new Vector(x, y);
        this.sprite = new Sprite(x, y, pic);
        this.hitboxes = new ArrayList<RelativeHitbox>();

        RelativeHitbox hitbox = new RelativeHitbox(this.position, Vector.VECTOR_ZERO.clone(), 
                this.sprite.getWidth(), this.sprite.getHeight());
        this.hitboxes.add(hitbox);
    }

    /**
     * This constructs an {@code AnimationFrame} object with preset {@code Hitbox}es.
     * @param position The top-left coordinate of this {@code AnimationFrame}.
     * @param pic The image to use for the sprite.
     * @param hitboxes The hitboxes for the frame, with the position relative to the sprite.
     */
    public AnimationFrame(Vector position, BufferedImage pic, ArrayList<RelativeHitbox> hitboxes) {
        this.position = position;
        this.sprite = new Sprite(position, pic);
        this.hitboxes = hitboxes;
        this.setHitboxAnchorPos(this.position);
    }

    public AnimationFrame(Vector position, BufferedImage pic) {
        this.position = position;
        this.sprite = new Sprite(position, pic);
        this.hitboxes = new ArrayList<RelativeHitbox>();

        RelativeHitbox hitbox = new RelativeHitbox(this.position, Vector.VECTOR_ZERO.clone(),
                this.sprite.getWidth(), this.sprite.getHeight());
        this.hitboxes.add(hitbox);
    }

    @Override
    public void draw(Graphics graphics) {
        this.sprite.draw(graphics);
    }
    
    @Override
    public void drawDebugInfo(Graphics graphics) {
        for (Hitbox hitbox: this.hitboxes) {
            hitbox.draw(graphics);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        for (Hitbox hitbox: this.hitboxes) {
            if (hitbox.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean intersects(Hitbox other) {
        for (Hitbox hitbox: this.hitboxes) {
            if (hitbox.intersects(other)) {
                return true;
            }
        }
        
        return false;
    }

    public boolean intersects(AnimationFrame otherFrame) {
        for (Hitbox hitbox: this.hitboxes) {
            if (otherFrame.intersects(hitbox)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method moves the position of the sprites and the hitboxes to the
     * new position. The hiboxes maintain their relative positions to the position
     * of the anchor/animation frame position.
     * @param newPos The new position.
     */
    public void setPos(Vector newPos) {
        this.sprite.setPos(newPos);
        this.setHitboxAnchorPos(newPos);
        this.position = newPos;
    }

    public void setHitboxes(ArrayList<RelativeHitbox> hitboxes) {
        this.hitboxes = hitboxes;
    }

    private void setHitboxAnchorPos(Vector newAnchorPos) {
        for (RelativeHitbox hitbox: this.hitboxes) {
            hitbox.setAnchorPos(newAnchorPos);
        }
    }

    public void reflectHorizontally(int xLine) {
        this.sprite.reflectHorizontally(xLine);
        for (RelativeHitbox hitbox: this.hitboxes) {
            hitbox.reflectHorizontally(xLine);
        }
    }
}

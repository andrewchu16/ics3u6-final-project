import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/*
 * This class represents a single frame in the animation cycle of an entity. It
 * contains both the sprite and the hitboxes associated with it.
 */
public class AnimationFrame implements Drawable, Debuggable {
    private Vector position;
    private Sprite sprite;
    private ArrayList<Hitbox> hitboxes;

    /**
     * This constructs an AnimationFrame object with a single hitbox around the image.
     * @param x The x-coordinate of the AnimationFrame.
     * @param y The y-coordinate of the AnimationFrame.
     * @param pic The image to use for the sprite.
     */
    public AnimationFrame(int x, int y, BufferedImage pic) {
        this.position = new Vector(x, y);
        this.sprite = new Sprite(x, y, pic);
        this.hitboxes = new ArrayList<Hitbox>();

        Hitbox hitbox = new Hitbox(this.position, this.sprite.getWidth(), 
                this.sprite.getHeight());
        this.hitboxes.add(hitbox);
    }

    /**
     * This constructs an AnimationFrame object with preset hitboxes.
     * @param x The x-coordinate of the AnimationFrame.
     * @param y The y-coordinate of the AnimationFrame.
     * @param pic The image to use for the sprite.
     * @param hitboxes The hitboxes for the frame, with the position relative to the sprite.
     */
    public AnimationFrame(int x, int y, BufferedImage pic, ArrayList<Hitbox> hitboxes) {
        this.position = new Vector(x, y);
        this.sprite = new Sprite(x, y, pic);
        this.hitboxes = hitboxes;
        this.setRelHitboxPos(this.position);
    }

    public AnimationFrame(Vector position, BufferedImage pic) {
        this.position = position;
        this.sprite = new Sprite(position, pic);
        this.hitboxes = new ArrayList<Hitbox>();

        Hitbox hitbox = new Hitbox(this.position, this.sprite.getWidth(), 
                this.sprite.getHeight());
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

    /**
     * This method moves the position of the sprites and the hitboxes to the
     * new position. The hiboxes maintain their relative positions to the position
     * of the 
     * @param newPos The new position.
     */
    public void setPos(Vector newPos) {
        this.sprite.setPos(newPos);
        this.setRelHitboxPos(newPos);
        this.position = newPos;
    }

    private void setRelHitboxPos(Vector newRelPos) {
        for (Hitbox hitbox: this.hitboxes) {
            Vector newHitboxPos = hitbox.getPos().clone();
            newHitboxPos.sub(this.position);
            newHitboxPos.add(newRelPos);
            hitbox.setPos(newHitboxPos);
        }
    }
}

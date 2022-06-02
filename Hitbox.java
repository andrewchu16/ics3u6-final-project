import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public class Hitbox implements Drawable, Debuggable {
    private Vector position;
    private Rectangle rect;

    /**
     * This constructs a Hitbox object at a the given position, with a width and
     * height.
     * @param x The x-coordinate of the hitbox.
     * @param y The y-coordinate of the hitbox.
     * @param width The width of the hitbox.
     * @param height The height of the hitbox.
     */
    public Hitbox(int x, int y, int width, int height) {
        this.position = new Vector(x, y);
        this.rect = new Rectangle(x, y, width, height);
    }

    /**
     * This constructs a Hitbox object at a the given position, with a width and
     * height.
     * @param position The position of the hitbox.
     * @param width The width of the hitbox.
     * @param height The height of the hitbox.
     */
    public Hitbox(Vector position, int width, int height) {
        this.position = position;
        this.rect = new Rectangle((int) position.getX(), (int) position.getY(), width, height);
    }

    public Rectangle getRect() {
        return this.rect;
    }

    public int getX() {
        return (int) this.rect.getX();
    }

    public int getY() {
        return (int) this.rect.getY();
    }

    public int getWidth() {
        return (int) this.rect.getWidth();
    }

    public int getHeight() {
        return (int) this.rect.getHeight();
    }

    public Vector getPos() {
        return this.position;
    }

    public void setWidth(int newWidth) {
        this.rect.setSize(newWidth, this.getHeight());
    }

    public void setHeight(int newHeight) {
        this.rect.setSize(this.getWidth(), newHeight);
    }

    public void setPos(Vector newPos) {
        this.rect.setLocation((int) newPos.getX(), (int) newPos.getY());
        this.position = newPos;
    }
    
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);
        ((Graphics2D) graphics).draw(this.rect);
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.draw(graphics);
    }
    
    public boolean contains(int x, int y) {
        return this.rect.contains(x, y);
    }

    public boolean intersects(Hitbox other) {
        return this.rect.intersects(other.getRect());
    }


    public static boolean intersects(Hitbox box1, Hitbox box2) {
        Rectangle rect1 = box1.getRect();
        Rectangle rect2 = box2.getRect();

        return rect1.intersects(rect2);
    }
}

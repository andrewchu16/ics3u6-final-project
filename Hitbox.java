import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.BasicStroke;

/**
 * This class represents a hitbox for determining the collision of objects.
 */
public class Hitbox implements Drawable, Debuggable, Collidable {
    private Vector position;
    private Rectangle rect;

    /**
     * This constructs a {@code Hitbox} object at a the given position, with 
     * a width and height.
     * @param x The top-left x-coordinate of this {@code Hitbox}.
     * @param y The top-left y-coordinate of this {@code Hitbox}.
     * @param width The width of this {@code Hitbox}.
     * @param height The height of this {@code Hitbox}.
     */
    public Hitbox(int x, int y, int width, int height) {
        this.position = new Vector(x, y);
        this.rect = new Rectangle(x, y, width, height);
    }

    /**
     * This constructs a {@code Hitbox} object at a the given position, with a 
     * width and height. It does NOT store a reference to the position of this 
     * {@code Hitbox}.
     * @param position The position of this {@code Hitbox}.
     * @param width The width of this {@code Hitbox}.
     * @param height The height of this {@code Hitbox}.
     */
    public Hitbox(Vector position, int width, int height) {
        this.position = position.clone();
        this.rect = new Rectangle((int) position.getX(), (int) position.getY(), 
                width, height);
    }

    /**
     * This method gets a reference to the internal {@code Rectangle} of this 
     * {@code Hitbox}.
     * @return The {@code Rectangle} in this {@code Hitbox}.
     * @see Rectangle
     */
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

    /**
     * This method gets a copy of the position of this {@code Hitbox}.
     * @return A copy of this {@code Hitbox}'s position.
     */
    public Vector getPos() {
        return this.position.clone();
    }

    /**
     * This method gets a reference of the position of this {@code Hitbox}.
     * @return A reference of this {@code Hitbox}'s position.
     */
    public Vector getRefPos() {
        return this.position;
    }

    public void setWidth(int newWidth) {
        this.rect.setSize(newWidth, this.getHeight());
    }

    public void setHeight(int newHeight) {
        this.rect.setSize(this.getWidth(), newHeight);
    }

    /**
     * This method sets the position of this {@code Hitbox}. It does NOT store 
     * a reference to the new position.
     * @param newPos The new postion of this {@code Hitbox}.
     */
    public void setPos(Vector newPos) {
        this.rect.setLocation((int) newPos.getX(), (int) newPos.getY());
        this.position = newPos.clone();
    }
    
    /**
     * This method draws the outline of this {@code Hitbox} in red.
     * @see java.awt.Color#RED
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);
        ((Graphics2D) graphics).setStroke(new BasicStroke(2));
        ((Graphics2D) graphics).draw(this.rect);
    }

    /**
     * This method does the same thing as the draw method. It draws the outline
     * of this {@code Hitbox} in red.
     * @see java.awt.Color#RED
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.draw(graphics);
    }
    
    /**
     * This method checks if a point is inside this {@code Hitbox}.
     * @param x The x-coordinate of the point to check.
     * @param y The y-cooridnate of the point to check.
     * @return {@code true} if the point is inside this {@code Hitbox}, {@code false} otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.rect.contains(x, y);
    }

    /**
     * This method checks if this {@code Hitbox} overlaps with another {@code Hitbox}.
     * @param other The other {@code Hitbox} to check.
     * @return {@code true} if the hitboxes overlap, {@code false} otherwise.
     */
    @Override
    public boolean intersects(Hitbox other) {
        return this.rect.intersects(other.getRect());
    }

    /**
     * This method checks if two hitboxes overlap with each other.
     * @param box1 The first {@code Hitbox} object to check.
     * @param box2 The second {@code Hitbox} object to check.
     * @return {@code true} if the hitboxes overlap, {@code false} otherwise.
     */
    public static boolean intersects(Hitbox box1, Hitbox box2) {
        Rectangle rect1 = box1.getRect();
        Rectangle rect2 = box2.getRect();

        return rect1.intersects(rect2);
    }
}

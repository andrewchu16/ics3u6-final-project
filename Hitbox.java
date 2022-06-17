import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.BasicStroke;

/**
 * This class represents a hitbox for determining the collision of objects.
 */
public class Hitbox implements Drawable, Debuggable, Collidable {
    private Rectangle rect;
    private Color color;

    private Vector position;
    private int width;
    private int height;

    /**
     * This constructs a {@code Hitbox} object at a the given position, with 
     * a width and height.
     * @param x The top-left x-coordinate of this {@code Hitbox}.
     * @param y The top-left y-coordinate of this {@code Hitbox}.
     * @param width The width of this {@code Hitbox}.
     * @param height The height of this {@code Hitbox}.
     */
    public Hitbox(int x, int y, int width, int height) {
        this.rect = new Rectangle(x, y, width, height);
        this.color = Const.RED;
        this.position = new Vector(x, y);
        this.width = width;
        this.height = height;
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
        this.rect = new Rectangle((int) position.getX(), (int) position.getY(), 
                width, height);
        this.color = Const.RED;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * This method gets a reference to the internal {@code Rectangle} of this 
     * {@code Hitbox}.
     * @return The {@code Rectangle} in this {@code Hitbox}.
     * @see Rectangle
     */
    public Rectangle getRect() {
        if (this.rect == null) {
            this.rect = new Rectangle(this.getX(), this.getY(), this.width, this.height);
        }

        return this.rect;
    }

    public int getX() {
        return (int) this.position.getX();
    }

    public int getY() {
        return (int) this.position.getY();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    /**
     * This method gets a copy of the position of this {@code Hitbox}.
     * @return A copy of this {@code Hitbox}'s position.
     */
    public Vector getPos() {
        return this.position.clone();
    }

    public Color getColor() {
        return this.color;
    }

    public void setWidth(int newWidth) {
        this.width = newWidth;
        this.rect = null;
    }

    public void setHeight(int newHeight) {
        this.height = newHeight;
        this.rect = null;
    }

    /**
     * This method sets the position of this {@code Hitbox}. It does NOT store 
     * a reference to the new position.
     * @param newPos The new postion of this {@code Hitbox}.
     */
    public void setPos(Vector newPos) {
        this.position = newPos;
        this.rect = null;
    }

    public void setColor(Color newColor) {
        this.color = newColor;
    }
    
    /**
     * This method draws the outline of this {@code Hitbox}.
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(this.color);
        ((Graphics2D) graphics).setStroke(new BasicStroke(1));
        ((Graphics2D) graphics).drawRect(this.getX(), this.getY(), this.width, this.height);
    }

    /**
     * This method does the same thing as the draw method. It draws the outline
     * of this {@code Hitbox}.
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
        return (this.getX() <= x && x <= this.getX() + this.getWidth()) &&
            (this.getY() <= y && y <= this.getY() + this.getHeight());
    }

    /**
     * This method checks if this {@code Hitbox} overlaps with another {@code Hitbox}.
     * @param other The other {@code Hitbox} to check.
     * @return {@code true} if the hitboxes overlap, {@code false} otherwise.
     */
    @Override
    public boolean intersects(Hitbox other) {
        int left1 = this.getX();
        int up1 = this.getY();
        int right1 = this.getX() + this.getWidth();
        int down1 = this.getY() + this.getHeight();

        int left2 = other.getX();
        int up2 = other.getY();
        int right2 = other.getX() + other.getWidth();
        int down2 = other.getY() + other.getHeight();
        
        boolean xOverlap = (left2 <= left1 && left1 <= right2) ||
                (left1 <= left2 && left2 <= right1);
        boolean yOverlap = (up1 <= up2 && up2 <= down1) || 
                (up2 <= up1 && up1 <= down2);

        return xOverlap && yOverlap;
    }

    /**
     * This method checks if two hitboxes overlap with each other.
     * @param box1 The first {@code Hitbox} object to check.
     * @param box2 The second {@code Hitbox} object to check.
     * @return {@code true} if the hitboxes overlap, {@code false} otherwise.
     */
    public static boolean intersects(Hitbox box1, Hitbox box2) {
        return box1.intersects(box2);
    }

    @Override
    public Hitbox clone() {
        return new Hitbox(this.position.clone(), this.getWidth(), this.getHeight());
    }
}

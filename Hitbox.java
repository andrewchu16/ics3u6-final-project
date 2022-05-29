import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public class Hitbox {
    private Rectangle rect;

    public Hitbox(int x, int y, int width, int height) {
        this.rect = new Rectangle(x, y, width, height);
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

    public void setWidth(int newWidth) {
        this.rect.setSize(newWidth, this.getHeight());
    }

    public void setHeight(int newHeight) {
        this.rect.setSize(this.getWidth(), newHeight);
    }

    public boolean contains(int x, int y) {
        return this.rect.contains(x, y);
    }

    public boolean intersects(Hitbox other) {
        return this.rect.intersects(other.getRect());
    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);
        ((Graphics2D) graphics).draw(this.rect);
    }

    public static boolean intersects(Hitbox box1, Hitbox box2) {
        Rectangle rect1 = box1.getRect();
        Rectangle rect2 = box2.getRect();

        return rect1.intersects(rect2);
    }
}

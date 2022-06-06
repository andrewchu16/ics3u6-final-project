import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.BasicStroke;

/**
 * This class represents a button on a {@code Screen}. Instead of being a rectangle,
 * it is in the shape of an isosceles triangle.
 * @see Button
 */
public class ArrowButton extends Button {
    private int orientation;
    private Polygon arrowShape;

    /**
     * This constructs a {@code ArrowButton} object.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param width The width of this {@code ArrowButton}.
     * @param height THe height of this {@code ArrowButton}.
     * @param orientation The direction this {@code ArrowButton} points to.
     * @param name The name of this {@code ArrowButton}.
     * @param hoverColor The colour of this {@code ArrowButton} when a mouse is over it.
     * @param pressedColor The default colour of this {@code ArrowButton}.
     * @see Const#UP
     * @see Const#LEFT
     * @see Const#DOWN
     * @see Const#RIGHT
     */
    public ArrowButton(int x, int y, int width, int height, int orientation, String name, 
            Color hoverColor, Color pressedColor) {
        super(x, y, width, height, name, "", Const.buttonFont, hoverColor, pressedColor);

        this.orientation = orientation;
        this.createArrow();
    }

    /**
     * This method creates the triangle shape of this {@code ArrowButton} in the set orientation.
     */
    private void createArrow() {
        final int NUM_POINTS = 3;
        int[] xPoints = new int[NUM_POINTS];
        int[] yPoints = new int[NUM_POINTS];

        // Determine the coordinates of each point in the triangle.
        switch (this.orientation) {
            case Const.UP:
                //   2
                //
                // 0   1
                xPoints[0] = this.getX();
                xPoints[1] = this.getX() + this.getWidth() / 2;
                xPoints[2] = this.getX() + this.getWidth();
                yPoints[0] = this.getY() + this.getHeight();
                yPoints[1] = this.getY() + this.getHeight();
                yPoints[2] = this.getY();
                break;
            case Const.LEFT:
                //     2
                // 0
                //     1
                xPoints[0] = this.getX();
                xPoints[1] = this.getX() + this.getWidth();
                xPoints[2] = this.getX() + this.getWidth();
                yPoints[0] = this.getY() + this.getHeight() / 2;
                yPoints[1] = this.getY() + this.getHeight();
                yPoints[2] = this.getY();
                break;
            case Const.DOWN:
                // 0   2
                //
                //   1
                xPoints[0] = this.getX();
                xPoints[1] = this.getX() + this.getWidth() / 2;
                xPoints[2] = this.getX() + this.getWidth();
                yPoints[0] = this.getY();
                yPoints[1] = this.getY() + this.getHeight();
                yPoints[2] = this.getY();
                break;
            case Const.RIGHT:
                // 0
                //     2
                // 1
                xPoints[0] = this.getX();
                xPoints[1] = this.getX();
                xPoints[2] = this.getX() + this.getWidth();
                yPoints[0] = this.getY();
                yPoints[1] = this.getY() + this.getHeight();
                yPoints[2] = this.getY() + this.getHeight() / 2;
                break;
        }

        this.arrowShape = new Polygon(xPoints, yPoints, NUM_POINTS);
    }

    @Override
    public boolean contains(int x, int y) {
        return this.arrowShape.contains(x, y);
    }

    /**
     * This method draws this {@code ArrowButton} onto a surface.
     */
    @Override 
    public void draw(Graphics graphics) {
        // Draw button body.
        graphics.setColor(this.getActiveColor());
        ((Graphics2D) graphics).fill(this.arrowShape);
        ((Graphics2D) graphics).setStroke(new BasicStroke(1));
        graphics.setColor(Const.BLACK);
        ((Graphics2D) graphics).draw(this.arrowShape);
    }
    
}
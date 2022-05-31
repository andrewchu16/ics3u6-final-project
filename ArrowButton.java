import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.BasicStroke;

public class ArrowButton extends Button {
    private int orientation;
    private Polygon arrowShape;

    public ArrowButton(int x, int y, int width, int height, int orientation, String name, 
            Color hoverColor, Color pressedColor) {
        super(x, y, width, height, name, "", Const.buttonFont, hoverColor, pressedColor);

        this.orientation = orientation;
        this.createArrow();
    }

    private void createArrow() {
        final int NUM_POINTS = 3;
        int[] xPoints = new int[NUM_POINTS];
        int[] yPoints = new int[NUM_POINTS];

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
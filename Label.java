import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

/**
 * This class represents a label that can be drawn onto a surface. Specifically,
 * a {@code Label} is meant to be drawn on a Screen. A {@code Label} consists of
 * {@code Text} centered in a {@code Rectangle}.
 * @see Screen
 * @see Text
 */
public class Label implements Drawable {
    private Rectangle rect;
    private Text text;
    private Color bodyColor;

    /**
     * This constructs a {@code Label} object.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param width The width of the {@code Label}.
     * @param height The height of the {@code Label}.
     * @param text The text on the {@code Label}.
     * @param font The font of the text of the {@code Label}.
     * @param bodyColor The colour of the {@code Label}.
     */
    public Label(int x, int y, int width, int height, String text, Font font,
            Color bodyColor) {
        this.rect = new Rectangle(x, y, width, height);
        this.text = new Text(text, font, (int) this.rect.getCenterX(), 
                (int) this.rect.getCenterY());
        this.bodyColor = bodyColor;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    /**
     * This method draws the label onto a surface.
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(this.bodyColor);
        ((Graphics2D) graphics).fill(this.rect);

        this.text.draw(graphics);
    }
}

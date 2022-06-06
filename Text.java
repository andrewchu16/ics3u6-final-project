import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.Color;

/**
 * This class represents text on a surface. It has a font and color.
 */
public class Text implements Drawable {
    private Vector position;
    private Vector center;

    private String text;
    private Font font;
    private Color color;

    /**
     * This constructs a {@code Text} object with a black color.
     * @param text The string text.
     * @param font The font of the text.
     * @param centerX The center of this {@code Text}.
     * @param centerY The center of this {@code Text}.
     */
    public Text(String text, Font font, int centerX, int centerY) {
        this.text = text;
        this.font = font;
        this.color = Const.BLACK;

        this.center = new Vector(centerX, centerY);
        this.position = new Vector();
        this.calculateCoord();
    }

    /**
     * This method calculates the top-left coordinate based off of the center coordinates.
     */
    private void calculateCoord() {
        this.position.setX(this.center.getX() - (this.getWidth() / 2));
        this.position.setY(this.center.getY() + (this.getHeight() / 2));
    }

    public int getWidth() {
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affineTransform, true, true);     
        int textWidth = (int) (this.font.getStringBounds(text, frc).getWidth());
        return textWidth;
    }

    public int getHeight() {
        return this.font.getSize() / 2;
    }

    public String getText() {
        return this.text;
    }

    public void setCenterX(int newCenterX) {
        this.center.setX(newCenterX);
        this.calculateCoord();
    }

    public void setCenterY(int newCenterY) {
        this.center.setY(newCenterY);
        this.calculateCoord();
    }

    public void setText(String text) {
        this.text = text;
        this.calculateCoord();
    }

    public void setFont(Font font) {
        this.font = font;
        this.calculateCoord();
    }

    /**
     * This method calculates the width of a string of text rendered in a given font.
     * @param text The string.
     * @param font The font of the text.
     * @return The width of the text in the font.
     */
    public static int getTextWidth(String text, Font font) {
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affineTransform, true, true);     
        int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
        return textWidth;
    }

    /**
     * This method calculates the height of a font.
     * @param font The font.
     * @return
     */
    public static int getTextHeight(Font font) {
        return font.getSize() / 2;
    }

    /**
     * This method draws the text onto a surface with anti-aliasing. 
     */
    @Override
    public void draw(Graphics graphics) {
        ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);


        graphics.setColor(this.color);
        graphics.setFont(this.font);
        graphics.drawString(this.text, (int) this.position.getX(), (int) this.position.getY());
    }
}

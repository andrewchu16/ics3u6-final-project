import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Text {
    private int x;
    private int y;
    private int centerX;
    private int centerY;
    
    private String text;
    private Font font;
    private Color color;

    public Text(String text, Font font, int centerX, int centerY) {
        this.text = text;
        this.font = font;
        this.color = Const.BLACK;

        this.centerX = centerX;
        this.centerY = centerY;
        this.calculateCoord();
    }

    private void calculateCoord() {
        this.x = this.centerX - (this.getWidth() / 2);
        this.y = this.centerY + (this.getHeight() / 2);
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
        this.centerX = newCenterX;
        this.calculateCoord();
    }

    public void setCenterY(int newCenterY) {
        this.centerY = newCenterY;
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

    public void draw(Graphics graphics) {
        ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);


        graphics.setColor(this.color);
        graphics.setFont(this.font);
        graphics.drawString(this.text, this.x, this.y);
    }
}

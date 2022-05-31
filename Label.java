import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class Label {
    private Rectangle rect;
    private Text text;
    private Color bodyColor;

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

    public void draw(Graphics graphics) {
        graphics.setColor(this.bodyColor);
        ((Graphics2D) graphics).fill(this.rect);

        this.text.draw(graphics);
    }
}

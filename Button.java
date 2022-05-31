import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;

public class Button {
    private Hitbox hitbox;
    private String name;
    private ArrayList<ButtonHandler> handlers;
    private Text text;

    private Color hoverColor;
    private Color unpressedColor;
    private Color activeColor;


    public Button(int x, int y, int width, int height, String name, String text,
                Font font, Color hoverColor, Color unpressedColor) {
        this.hitbox = new Hitbox(x, y, width, height);
        this.name = name;
        this.handlers = new ArrayList<ButtonHandler>();
        this.text = new Text(text, font, x + (width / 2), y + (height / 2));

        this.hoverColor = hoverColor;
        this.unpressedColor = unpressedColor;
        this.activeColor = unpressedColor;
    }

    public void draw(Graphics graphics) {
        // Draw button body.
        graphics.setColor(this.activeColor);
        ((Graphics2D) graphics).fill(this.hitbox.getRect());
        ((Graphics2D) graphics).setStroke(new BasicStroke(1));
        graphics.setColor(Const.BLACK);
        ((Graphics2D) graphics).draw(this.hitbox.getRect());

        // Draw text in button.
        if (this.text != null) {
            this.text.draw(graphics);
        }
    }

    public boolean contains(int x, int y) {
        return this.hitbox.contains(x, y);
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }
    
    public int getX() {
        return this.hitbox.getX();
    }

    public int getY() {
        return this.hitbox.getY();
    }

    public int getWidth() {
        return this.hitbox.getWidth();
    }

    public int getHeight() {
        return this.hitbox.getHeight();
    }

    public String getName() {
        return this.name;
    }

    public String getText() {
        return this.text.getText();
    }

    public Color getHoverColor() {
        return this.hoverColor;
    }

    public Color getUnpressedColor() {
        return this.unpressedColor;
    }

    public Color getActiveColor() {
        return this.activeColor;
    }

    public void setWidth(int newWidth) {
        this.hitbox.setWidth(newWidth);
        this.text.setCenterX(this.getX() + this.getWidth() / 2);
    }

    public void setHeight(int newHeight) {
        this.hitbox.setHeight(newHeight);
        this.text.setCenterY(this.getY() + this.getHeight() / 2);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setFont(Font font) {
        this.text.setFont(font);
    }

    public void setHoverColor(Color newHoverColor) {
        this.hoverColor = newHoverColor;
    }

    public void setUnpressedColor(Color newUnpressedColor) {
        this.unpressedColor = newUnpressedColor;
    }

    public void setActiveColor(Color newActiveColor) {
        this.activeColor = newActiveColor;
    }
    
    public void addHandler(ButtonHandler handler) {
        this.handlers.add(handler);
    }

    public class ButtonMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();

            if (contains(mouseX, mouseY)) {
                for (ButtonHandler handler: handlers) {
                    handler.handlePress();
                }
            }
        }
        
        public void mousePressed(MouseEvent event) {}

        public void mouseReleased(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();

            if (contains(mouseX, mouseY)) {
                for (ButtonHandler handler: handlers) {
                    handler.handleUnpress();
                }
                activeColor = unpressedColor;
            }
        }

        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
    }

    public class ButtonMouseMotionListener implements MouseMotionListener {
        public void mouseMoved(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();

            if (contains(mouseX, mouseY)) {
                // Handle user hovering.
                activeColor = hoverColor;
                for (ButtonHandler handler: handlers) {
                    handler.handleHover();
                }
            } else {
                activeColor = unpressedColor;
            }
        }

        public void mouseDragged(MouseEvent event) {}
    }
    
    public interface ButtonHandler {
        public void handlePress();
        public void handleHover();
        public void handleUnpress();
    }
}

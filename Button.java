import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Button {
    private Hitbox hitbox;
    private String name;
    private ButtonHandler handler;
    private Text text;

    private Color hoverColor;
    private Color pressedColor;
    private Color unpressedColor;
    private Color activeColor;

    public Button(int x, int y, int width, int height, String name, String text, 
                Font font, Color hoverColor, Color pressedColor, Color unpressedColor) {
        this(x, y, width, height, name, text, font, hoverColor, unpressedColor);
        this.pressedColor = pressedColor;
    }

    public Button(int x, int y, int width, int height, String name, String text,
                Font font, Color hoverColor, Color unpressedColor) {
        this.hitbox = new Hitbox(x, y, width, height);
        this.name = name;
        this.handler = null;
        this.text = new Text(text, font, x + (width / 2), y + (height / 2));

        this.hoverColor = hoverColor;
        this.pressedColor = unpressedColor;
        this.unpressedColor = unpressedColor;
        this.activeColor = unpressedColor;
    }

    public void draw(Graphics graphics) {
        graphics.setColor(this.activeColor);
        ((Graphics2D) graphics).fill(this.hitbox.getRect());

        if (this.text != null) {
            this.text.draw(graphics);
        }
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

    public Color getPressedColor() {
        return this.pressedColor;
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

    public void setPressedColor(Color newPressedColor) {
        this.pressedColor = newPressedColor;
    }

    public void setUnpressedColor(Color newUnpressedColor) {
        this.unpressedColor = newUnpressedColor;
    }

    public void setActiveColor(Color newActiveColor) {
        this.activeColor = newActiveColor;
    }
    
    public void setHandler(ButtonHandler handler) {
        this.handler = handler;
    }

    public class ButtonMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();

            if (hitbox.contains(mouseX, mouseY)) {
                if (handler != null) {
                    handler.handlePress();
                }
            }
        }

        public void mousePressed(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();

            if (hitbox.contains(mouseX, mouseY)) {
                activeColor = pressedColor;
            }
        }

        public void mouseReleased(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();

            if (hitbox.contains(mouseX, mouseY)) {
                activeColor = hoverColor;
            } else {
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

            if (hitbox.contains(mouseX, mouseY)) {
                activeColor = hoverColor;
                if (handler != null) {
                    handler.handleHover();
                }
            } else {
                activeColor = unpressedColor;
                if (handler != null) {
                    handler.handleUnpress();
                }
            }
        }

        public void mouseDragged(MouseEvent event) {}
    }
    
    abstract public static class ButtonHandler {
        public void handlePress() {}
        public void handleHover() {}
        public void handleUnpress() {}
    }

    public static class MenuButton extends Button {
        public MenuButton(int x, int y, String name, String text) {
            super(x, y, 200, 70, name, text, Const.buttonFont, Const.DARK_BLUE, 
                    Const.LIGHT_BLUE);
            
            // Resize the button if it is too small for the text.
            Text tmpText = new Text(text, Const.buttonFont, 0, 0);
            int newWidth = Math.max(this.getWidth(), tmpText.getWidth() + 20);
            int newHeight = Math.max(this.getHeight(), tmpText.getHeight());

            this.setWidth(newWidth);
            this.setHeight(newHeight);
        }
    }
}

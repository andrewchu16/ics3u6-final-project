import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.ArrayList;

/**
 * This class represents a button on a {@code Screen}. It is in the shape of a 
 * rectangle. It is capable of changing color on hover and performing an action 
 * on press. It can have {@code Text}.
 * @see Screen
 * @see Text
 */
public class Button implements Drawable {
    private Hitbox hitbox;
    private String name;
    private ArrayList<ButtonHandler> handlers;
    private Text text;

    private Color hoverColor;
    private Color unpressedColor;
    private Color activeColor;

    /**
     * This constructs a {@code Button} object.
     * @param x The top-left x-coordinate of this {@code Button}.
     * @param y The top-left y-coordinate of this {@code Button}.
     * @param width The width of this {@code Button}.
     * @param height The height of this {@code Button}.
     * @param name The name of this {@code Button}.
     * @param text The text string on this {@code Button}.
     * @param font The font of the text on this {@code Button}.
     * @param hoverColor The colour this {@code Button} when a mouse is over it.
     * @param unpressedColor The default colour of this {@code Button}.
     */
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

    /**
     * This method draws this {@code Button} including its text if it has any. 
     */
    @Override
    public void draw(Graphics graphics) {
        // Draw button body.
        graphics.setColor(this.activeColor);
        ((Graphics2D) graphics).fill(this.hitbox.getRect());
        ((Graphics2D) graphics).setStroke(new BasicStroke(1));

        // Draw this {@code Button} border.
        graphics.setColor(Const.BLACK);
        ((Graphics2D) graphics).draw(this.hitbox.getRect());

        // Draw text in button.
        if (this.text != null) {
            this.text.draw(graphics);
        }
    }

    /**
     * This method checks if a poin is contained within this {@code Button}.
     * @param x The x-coordinate of the point to check.
     * @param y The y-coordinate of the point to check.
     * @return True if the point is contained in this {@code Button}, false otherwise.
     */
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

    /**
     * This class represents a {@code MouseListener} for triggering this 
     * {@code Button}'s behaviour. It is necessary to allow this {@code Button} to 
     * respond to presses. It should added to the {@code Screen} this {@code Button} 
     * is placed on.
     * @see Screen#addButton
     */
    public class ButtonMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();

            if (contains(mouseX, mouseY)) {
                // Handle user pressing.
                for (ButtonHandler handler: handlers) {
                    handler.handlePress();
                }
            }
        }
        
        public void mouseReleased(MouseEvent event) {
            int mouseX = event.getX();
            int mouseY = event.getY();
            
            if (contains(mouseX, mouseY)) {
                // Handle user unpress.
                for (ButtonHandler handler: handlers) {
                    handler.handleUnpress();
                }
                activeColor = unpressedColor;
            }
        }
        
        public void mousePressed(MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
    }

    /**
     * This class represents a MouseMotionListener for triggering this {@code Button}'s 
     * behaviour. It is necessary to allow this {@code Button} to respond to hovering. 
     * It should be added to the {@code Screen} this {@code Button} is placed on.
     * @see Screen#addButton
     */
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
                // Change to unpressed state.
                activeColor = unpressedColor;
            }
        }

        public void mouseDragged(MouseEvent event) {}
    }
    
    /**
     * This interface contains the types of behaviours that a {@code Button} 
     * can respond to. The behaviours are pressing, hovering, and unpressing.
     */
    public interface ButtonHandler {
        /**
         * This method performs the expected behaviour when the {@code Button} gets pressed.
         */
        public void handlePress();

        /**
         * This method performs the expected behaviour when the {@code Button} gets hovered.
         */
        public void handleHover();

        /**
         * This method performs the expected behaviour when the {@code Button} is unpressed.
         */
        public void handleUnpress();
    }
}

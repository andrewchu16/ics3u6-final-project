import javax.swing.JPanel;
import java.awt.Graphics;

import java.awt.image.BufferedImage;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.util.ArrayList;

/**
 * This class represents a {@code Screen} to be used in a {@code Window}. A {@code Screen} 
 * can have a background and {@code Button}s.
 * @see Window
 * @see Button
 */
abstract public class Screen extends JPanel {
    private Sprite background;
    private ArrayList<Button> buttons;
    
    private String screenName;

    /**
     * This constructs a new Screen object with no background, buttons, or name.
     */
    public Screen() {
        this.background = null;
        this.buttons = new ArrayList<Button>();
        this.screenName = "Unnamed Screen ";
        
        this.setFocusable(true);
        this.addComponentListener(this.FOCUS_WHEN_SHOWN);
    }

    /**
     * This constructs a new Screen object with a background image.
     * @param backgroundPicName The name of the background image file.
     * @see Sprite
     */
    public Screen(String backgroundPicName) {
        this();
        this.background = new Sprite(0, 0, backgroundPicName);
    }

    /**
     * This constructs a new Screen ojbect with a background image.
     * @param backgroundImage The background image object.
     * @see BufferedImage
     * @see Sprite
     */
    public Screen(BufferedImage backgroundImage) {
        this();
        this.background = new Sprite(0, 0, backgroundImage);
    }

    /**
     * This method gets a button on the screen with a given name.
     * @param buttonName The name of the button to get.
     * @return The button object if it exists, null otherwise.
     * @see Button
     */
    public Button getButton(String buttonName) {
        // Search every button on the screen until the button is found.
        for (Button button: this.buttons) {
            if (button.getName().equals(buttonName)) {
                return button;
            }
        }

        return null;
    }

    public String getName() {
        return this.screenName;
    }

    public void setName(String newScreenName) {
        this.screenName = newScreenName;
    }

    /**
     * This method adds a button to the screen. It also adds its associated 
     * input listeners so the button is able to perform its functions.
     * @param button The button to be added.
     * @see Button
     * @see Button.ButtonHandler
     * @see TextField
     * @see TextField.TextFieldHandler
     */
    public void addButton(Button button) {
        this.buttons.add(button);

        if (button instanceof TextField) {
            TextField textField = (TextField) button;
            this.addMouseListener(textField.new TextFieldMouseListener());
            this.addMouseMotionListener(textField.new ButtonMouseMotionListener());
            this.addKeyListener(textField.new TextFieldKeyListener());
        } else {
            this.addMouseListener(button.new ButtonMouseListener());
            this.addMouseMotionListener(button.new ButtonMouseMotionListener());
        }
    }

    /**
     * This method draws the background of the screen. Default behaviour does not 
     * draw the buttons.
     * @see JPanel
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.background != null) {
            this.background.draw(graphics);
        }
    }

    /**
     * This method draws the buttons.
     * @param graphics
     */
    public void drawButtons(Graphics graphics) {
        for (Button button: this.buttons) {
            button.draw(graphics);
        }
    }

    public final ComponentAdapter FOCUS_WHEN_SHOWN = new ComponentAdapter() {
        public void componentShown(ComponentEvent event) {
            requestFocusInWindow();
        }
    };
}

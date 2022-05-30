import javax.swing.JPanel;
import java.awt.Graphics;

import java.awt.image.BufferedImage;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.util.ArrayList;

abstract public class Screen extends JPanel {
    private Sprite background;
    private ArrayList<Button> buttons;
    
    private String screenName;
    private static int numScreens = 0;

    public Screen() {
        this.background = null;
        this.buttons = new ArrayList<Button>();
        this.screenName = "Unnamed Screen " + numScreens;
        numScreens++;
        
        this.setFocusable(true);
        this.addComponentListener(this.FOCUS_WHEN_SHOWN);
    }

    public Screen(String backgroundPicName) {
        this();
        this.background = new Sprite(0, 0, 1, backgroundPicName);
    }

    public Screen(BufferedImage backgroundImage) {
        this();
        this.background = new Sprite(0, 0, 1, backgroundImage);
    }

    public Button getButton(String buttonName) {
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

    public void addButton(Button button) {
        this.buttons.add(button);

        if (button instanceof TextField) {
            TextField textField = (TextField) button;
            this.addMouseListener(textField.new TextFieldMouseListener());
            this.addKeyListener(textField.new TextFieldKeyListener());
        } else {
            this.addMouseListener(button.new ButtonMouseListener());
            this.addMouseMotionListener(button.new ButtonMouseMotionListener());
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (this.background != null) {
            this.background.draw(graphics);
        }
    }

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

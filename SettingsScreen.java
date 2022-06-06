import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * This class represents a {@code Screen} for changing the {@code Window} settings. 
 * It allows the player to enter debug mode or change the FPS of the window.
 */
public class SettingsScreen extends Screen {
    private Label titleLabel;
    private Label fpsLabel;
    private Label debugLabel;

    private Rectangle centerRect;

    /**
     * This constructs a {@code SettingsScreen} object.
     * @param window The {@code Window} this {@code SettingsScreen} is a part of.
     * @param game The {@code Game} object.
     */
    public SettingsScreen(Window window, Game game) {
        super(Const.dayScreenBackground);

        this.setName(Const.SETTINGS_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "Settings", 
                Const.subtitleFont, Const.WHITE2);

        // Instantiate settings center background rectangle.
        this.centerRect = new Rectangle(Const.WIDTH / 2 - 320, 160, 640, 500);
        
        // Instantiate button labels.
        this.fpsLabel = new Label(Const.WIDTH / 2 - 180, 300, 120, 60, "FPS", 
                Const.buttonFont, Const.WHITE);
        this.debugLabel = new Label(Const.WIDTH / 2 - 180, 400, 120, 60, "Debug",
                Const.buttonFont, Const.WHITE);
                
        // Instantiate buttons and text field.
        Button goBackButton = new BackButton(30, 50, window);
        TextField fpsTextField = new TextField(Const.WIDTH / 2 + 100, 300, 100, 60, 
                "fps text field", Integer.toString(window.getFPS()), Const.buttonFont, 
                Const.LIGHT_GRAY2, Const.WHITE, TextField.INCLUDE_DIGITS, 3);
        Button debugButton = this.createDebugButton(game);

        // Add button press effects.
        goBackButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                if (fpsTextField.valid(fpsTextField.getText())) {
                    window.setFPS(Integer.parseInt(fpsTextField.getText()));
                }
            }

            public void handleHover() {}
            public void handleUnpress() {}
        });
        fpsTextField.addTextFieldHandler(new TextField.TextFieldHandler() {
            public void handleSubmit() {
                if (fpsTextField.valid(fpsTextField.getText())) {
                    window.setFPS(Integer.parseInt(fpsTextField.getText()));
                }
            }
        });

        // Add buttons to screen.
        this.addButton(goBackButton);
        this.addButton((Button) fpsTextField);
        this.addButton(debugButton);
    }

    /**
     * This method creates the button for enabling debugging. Instead of just 
     * changing colour on hover, it toggles between colours.
     * @param game The {@code Game} object.
     * @return The created debug button.
     */
    private Button createDebugButton(Game game) {
        String startDebugText;
        Color startDebugUnpressedColor;
        Color startDebugHoverColor;

        // Determine the starting state of the debug button.
        if (game.checkDebugging()) {
            startDebugText = "On";
            startDebugUnpressedColor = Const.GREEN;
            startDebugHoverColor = Const.GREEN2;
        } else {
            startDebugText = "Off";
            startDebugUnpressedColor = Const.WHITE;
            startDebugHoverColor = Const.LIGHT_GRAY;
        }

        Button debugButton = new Button(Const.WIDTH / 2 + 100, 400, 100, 60, "debug button", 
        startDebugText, Const.buttonFont, startDebugHoverColor, startDebugUnpressedColor);

        // Add the debug button handler.
        debugButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                if (game.checkDebugging()) {
                    // Turn off debug mode.
                    game.setDebugging(false);
                    debugButton.setText("Off");
                    debugButton.setUnpressedColor(Const.WHITE);
                    debugButton.setHoverColor(Const.LIGHT_GRAY);
                    debugButton.setActiveColor(Const.LIGHT_GRAY);
                } else {
                    // Turn on debug mode.
                    game.setDebugging(true);
                    debugButton.setText("On");
                    debugButton.setUnpressedColor(Const.GREEN);
                    debugButton.setHoverColor(Const.GREEN2);
                    debugButton.setActiveColor(Const.GREEN2);
                }
            }

            public void handleHover() {}
            public void handleUnpress() {}
        });

        return debugButton;
    }

    /**
     * This method draws the {@code SettingsScreen}.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw title.
        this.titleLabel.draw(graphics);

        // Draw center background rectangle.
        graphics.setColor(Const.LIGHT_BLUE1);
        ((Graphics2D) graphics).fill(this.centerRect);
        graphics.setColor(Const.BLACK);
        ((Graphics2D) graphics).setStroke(new BasicStroke(4));
        ((Graphics2D) graphics).draw(this.centerRect);

        // Draw button labels.
        this.fpsLabel.draw(graphics);
        this.debugLabel.draw(graphics);

        // Draw buttons.
        this.drawButtons(graphics);
    }
}
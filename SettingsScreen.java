import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.Color;

public class SettingsScreen extends Screen {
    private Rectangle centerRect;
    private Rectangle fpsRect;
    private Text fpsText;
    private Rectangle debugRect;
    private Text debugText;

    public SettingsScreen(Window window, Game game) {
        super(Const.dayScreenBackground);

        this.setName(Const.SETTINGS_SCREEN_NAME);

        // Instantiate settings center background rectangle.
        this.centerRect = new Rectangle(Const.WIDTH / 2 - 320, 160, 640, 500);
        
        // Instantiate button labels.
        this.fpsRect = new Rectangle(Const.WIDTH / 2 - 180, 300, 120, 60);
        this.fpsText = new Text("FPS", Const.buttonFont, Const.WIDTH / 2 - 120, 330);
        this.debugRect = new Rectangle(Const.WIDTH / 2 - 180, 400, 120, 60);
        this.debugText = new Text("Debug", Const.buttonFont, Const.WIDTH / 2 - 120, 430);

        // Instantiate buttons and text field.
        Button goBackButton = new Button.BackButton(30, 50);
        TextField fpsTextField = new TextField(Const.WIDTH / 2 + 100, 300, 100, 60, 
                "fps text field", Integer.toString(window.getFPS()), Const.buttonFont, 
                Const.LIGHT_GRAY2, Const.WHITE, TextField.INCLUDE_DIGITS, 3);
        Button debugButton = this.createDebugButton(game);

        // Add button press effects.
        goBackButton.addHandler(window.new ButtonScreenSwapper(window.getPrevScreenName()));
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

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw center background rectangle.
        graphics.setColor(Const.LIGHT_BLUE2);
        ((Graphics2D) graphics).fill(this.centerRect);
        graphics.setColor(Const.BLACK);
        ((Graphics2D) graphics).setStroke(new BasicStroke(4));
        ((Graphics2D) graphics).draw(this.centerRect);

        // Draw button labels.
        graphics.setColor(Const.WHITE);
        ((Graphics2D) graphics).fill(this.fpsRect);
        ((Graphics2D) graphics).fill(this.debugRect);

        this.fpsText.draw(graphics);
        this.debugText.draw(graphics);

        // Draw buttons.
        this.drawButtons(graphics);
    }
}
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Rectangle;

/**
 * This class represents a screen for pausing the {@code Game}. It also allows 
 * the player to change the game settings.
 * @see SettingsScreen
 */
public class PauseScreen extends Screen {
    private Label titleLabel;

    private Rectangle centerRect;

    /**
     * This constructs a {@code PauseScreen} object.
     * @param window The {@code Window} this {@code PauseScreen} is a part of.
     * @param game The {@code Game} object.
     */
    public PauseScreen(Window window, Game game) {
        super(Const.DAY_SCREEN_BACKGROUND);
        
        this.setName(Const.PAUSE_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "Paused", 
                Const.SUBTITLE_FONT, Const.WHITE2);

        // Instantiate pause center background rectangle.
        this.centerRect = new Rectangle(Const.WIDTH / 2 - 320, 160, 640, 500);
        
        // Instantiate buttons.
        Button goBackButton = new BackButton(30, 50, window);
        Button resumeButton = new MenuButton(Const.WIDTH / 2 - 100, 200, "resume button", "Resume");
        Button settingsButton = new MenuButton(Const.WIDTH / 2 - 100, 300, "settings button", "Settings");

        // Add button press effects.
        resumeButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                game.run();
            }

            public void handleHover() {}
            public void handleUnpress() {}
        });
        resumeButton.addHandler(window.new ScreenSwapperButtonHandler(Const.GAME_SCREEN_NAME));
        settingsButton.addHandler(window.new ScreenSwapperButtonHandler(Const.SETTINGS_SCREEN_NAME));

        // Add buttons to screen.
        this.addButton(goBackButton);
        this.addButton(resumeButton);
        this.addButton(settingsButton);
    }

    /**
     * This method draws this {@code PauseScreen}.
     */
    @Override 
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.titleLabel.draw(graphics);

        // Draw center background rectangle.
        graphics.setColor(Const.LIGHT_BLUE1);
        ((Graphics2D) graphics).fill(this.centerRect);
        graphics.setColor(Const.BLACK);
        ((Graphics2D) graphics).setStroke(new BasicStroke(4));
        ((Graphics2D) graphics).draw(this.centerRect);

        this.drawButtons(graphics);
    }
}

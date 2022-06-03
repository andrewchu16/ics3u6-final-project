import java.awt.Graphics;
import java.awt.Rectangle;

public class PauseScreen extends Screen {
    private Label titleLabel;

    private Rectangle centerRect;

    public PauseScreen(Window window) {
        super(Const.dayScreenBackground);
        
        this.setName(Const.PAUSE_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "Paused", 
                Const.subtitleFont, Const.WHITE2);

        // Instantiate pause center background rectangle.
        this.centerRect = new Rectangle(Const.WIDTH / 2 - 320, 160, 640, 500);
        
        // Instantiate buttons.
        Button goBackButton = new BackButton(30, 50, window);
        Button settingsButton = new MenuButton(Const.WIDTH / 2 - 100, 200, "settings button", "Settings");

        // Add button press effects.
        settingsButton.addHandler(window.new ScreenSwapperButtonHandler(Const.SETTINGS_SCREEN_NAME));
        
        // Add buttons to screen.
        this.addButton(goBackButton);
        this.addButton(settingsButton);
    }

    @Override 
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.titleLabel.draw(graphics);

        this.drawButtons(graphics);
    }
}

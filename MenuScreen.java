import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MenuScreen extends Screen {
    private Text titleText;
    private Rectangle titleRect;

    public MenuScreen(Window window) {
        super(Const.dayScreenBackground);

        this.setName(Const.MENU_SCREEN_NAME);

        // Instantiate title.
        this.titleRect = new Rectangle(Const.WIDTH / 2 - 170, 120, 380, 140);
        this.titleText = new Text("Across The Sands", Const.titleFont, 
                (int) this.titleRect.getCenterX(), (int) this.titleRect.getCenterY());

        // Instantiate buttons.
        Button continueButton = new Button.MenuButton(50, 400, "continue button", "Continue");
        Button newGameButton = new Button.MenuButton(50, 480, "new game button", "New Game");
        Button settingsButton = new Button.MenuButton(50, 560, "settings button", "Settings");
        Button howToPlayButton = new Button.MenuButton(50, 640, "how to play button", "How To Play");

        // Add button press effects.
        continueButton.addHandler(window.new ButtonScreenSwapper(Const.GAME_SCREEN_NAME));
        newGameButton.addHandler(window.new ButtonScreenSwapper(Const.SELECT_SCREEN_NAME));
        settingsButton.addHandler(window.new ButtonScreenSwapper(Const.SETTINGS_SCREEN_NAME));
        howToPlayButton.addHandler(window.new ButtonScreenSwapper(Const.HOW_TO_PLAY_SCREEN_NAME));
        
        // Add buttons to screen.
        this.addButton(continueButton);
        this.addButton(newGameButton);
        this.addButton(settingsButton);
        this.addButton(howToPlayButton);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw title.
        graphics.setColor(Const.WHITE2);
        ((Graphics2D) graphics).fill(this.titleRect);

        graphics.setColor(Const.BLACK);
        this.titleText.draw(graphics);

        this.drawButtons(graphics);
    }
}

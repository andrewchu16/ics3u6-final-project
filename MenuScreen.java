import java.awt.Graphics;

public class MenuScreen extends Screen {
    private Label titleLabel;

    public MenuScreen(Window window) {
        super(Const.dayScreenBackground);

        this.setName(Const.MENU_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 170, 120, 380, 140, "Across The Sands",
                Const.titleFont, Const.WHITE2);

        // Instantiate buttons.
        Button continueButton = new MenuButton(50, 400, "continue button", "Continue");
        Button newGameButton = new MenuButton(50, 480, "new game button", "New Game");
        Button settingsButton = new MenuButton(50, 560, "settings button", "Settings");
        Button howToPlayButton = new MenuButton(50, 640, "how to play button", "How To Play");

        // Add button press effects.
        continueButton.addHandler(window.new ScreenSwapperButton(Const.GAME_SCREEN_NAME));
        newGameButton.addHandler(window.new ScreenSwapperButton(Const.SELECT_SCREEN_NAME));
        settingsButton.addHandler(window.new ScreenSwapperButton(Const.SETTINGS_SCREEN_NAME));
        howToPlayButton.addHandler(window.new ScreenSwapperButton(Const.HOW_TO_PLAY_SCREEN_NAME));
        
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
        this.titleLabel.draw(graphics);

        // Draw button.
        this.drawButtons(graphics);
    }
}

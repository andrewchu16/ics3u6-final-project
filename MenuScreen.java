import java.awt.Graphics;

/**
 * This class represents a {@code Screen} for displaying the main title screen. 
 * It has {@code Button}s that lead to the other subscreens.
 * @see GameScreen
 * @see SelectScreen
 * @see SettingsScreen
 * @see HowToPlayScreen
 */
public class MenuScreen extends Screen {
    private Label titleLabel;

    /**
     * This constructs a {@code MenuScreen} object.
     * @param window The {@code Window} that this {@code MenuScreen} is a part of.
     * @param game The {@code Game} object.
     */
    public MenuScreen(Window window, Game game) {
        super(Const.DAY_SCREEN_BACKGROUND);

        this.setName(Const.MENU_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 170, 120, 380, 140, "Across The Sands",
                Const.TITLE_FONT, Const.WHITE2);

        // Instantiate buttons.
        Button continueButton = new MenuButton(50, 400, "continue button", "Continue");
        Button newGameButton = new MenuButton(50, 480, "new game button", "New Game");
        Button settingsButton = new MenuButton(50, 560, "settings button", "Settings");
        Button howToPlayButton = new MenuButton(50, 640, "how to play button", "How To Play");

        // Add button press effects.
        continueButton.addHandler(window.new ScreenSwapperButtonHandler(Const.GAME_SCREEN_NAME));
        continueButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                game.run();
            }

            public void handleHover() {}
            public void handleUnpress() {}
        });
        newGameButton.addHandler(window.new ScreenSwapperButtonHandler(Const.SELECT_SCREEN_NAME));
        newGameButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                
            }

            public void handleHover() {}
            public void handleUnpress() {}
        });
        settingsButton.addHandler(window.new ScreenSwapperButtonHandler(Const.SETTINGS_SCREEN_NAME));
        howToPlayButton.addHandler(window.new ScreenSwapperButtonHandler(Const.HOW_TO_PLAY_SCREEN_NAME));
        
        // Add buttons to screen.
        this.addButton(continueButton);
        this.addButton(newGameButton);
        this.addButton(settingsButton);
        this.addButton(howToPlayButton);
    }

    /**
     * This method draws this {@code MenuScreen}.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw title.
        this.titleLabel.draw(graphics);

        // Draw button.
        this.drawButtons(graphics);
    }
}

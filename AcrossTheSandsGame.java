/**
 * This class represents the entry point and containing class for the Across The
 * Sands game. 
 * @author Andrew Chu
 * @version June 2022
 */
public class AcrossTheSandsGame {
    private Window window;
    private Game game;

    // The different window screens.
    private Screen menuScreen;
    private Screen settingsScreen;
    private Screen howToPlayScreen;
    private Screen selectScreen;
    private Screen gameScreen;
    private Screen gameOverScreen;
    private Screen pauseScreen;

    /**
     * This constructs an AcrossTheSandsGame object.
     */
    public AcrossTheSandsGame() {
        // Load assets into memory.
        Const.loadImages();
        Const.loadFonts();

        this.game = new Game();
        this.initializeWindow();
    }

    /**
     * This method initializes the window and different window screens. 
     * The screens in the window are:
     * <ol>
     * <li> The main menu screen.
     * <li> The settings screen.
     * <li> The how to play screen.
     * <li> The select screen.
     * <li> The game screen.
     * <li> The game over screen.
     * <li> The pause screen.
     * </ol>
     * @see Window
     * @see Screen
     */
    private void initializeWindow() {
        // Initialize the window object.
        this.window = new Window("Across The Sands", Const.WIDTH, Const.HEIGHT);

        // Initialize the window screens.
        this.menuScreen = new MenuScreen(this.window, this.game);
        this.settingsScreen = new SettingsScreen(this.window, this.game);
        this.howToPlayScreen = new HowToPlayScreen(this.window);
        this.selectScreen = new SelectScreen(this.window, this.game);
        this.gameScreen = new GameScreen(this.window, this.game);
        this.gameOverScreen = new GameOverScreen(this.window);
        this.pauseScreen = new PauseScreen(this.window, this.game);
        
        // Add screens to the window.
        this.window.addScreen(this.menuScreen);
        this.window.addScreen(this.settingsScreen);
        this.window.addScreen(this.howToPlayScreen);
        this.window.addScreen(this.selectScreen);
        this.window.addScreen(this.gameScreen);
        this.window.addScreen(this.gameOverScreen);
        this.window.addScreen(this.pauseScreen);
    }

    /**
     * This method opens the window and starts the game.
     */
    public void start() {
        this.window.start();
        this.window.switchToScreen(Const.MENU_SCREEN_NAME);
    }

    public static void main(String[] args) {
        AcrossTheSandsGame game = new AcrossTheSandsGame();
        game.start();
    }
}
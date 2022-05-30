public class AcrossTheSandsGame {
    private Window window;
    private Game game;

    // The different game screens.
    private Screen menuScreen;
    private Screen settingsScreen;
    private Screen howToPlayScreen;
    private Screen selectScreen;
    private Screen gameScreen;
    private Screen gameOverScreen;
    private Screen pauseScreen;

    public AcrossTheSandsGame() {
        // Load resources into memory.
        Const.loadImages();
        Const.loadFonts();

        this.game = new Game();
        this.initializeWindow();
    }

    private void initializeWindow() {
        this.window = new Window("Across The Sands", Const.WIDTH, Const.HEIGHT);

        this.menuScreen = new MenuScreen(this.window);
        this.settingsScreen = new SettingsScreen(this.window, this.game);
        
        this.window.addScreen(menuScreen);
        this.window.addScreen(settingsScreen);
    }

    public void start() {
        System.out.println("Starting window");
        this.window.start();
        this.window.switchToScreen(Const.MENU_SCREEN_NAME);
    }

    public static void main(String[] args) {
        AcrossTheSandsGame game = new AcrossTheSandsGame();
        game.start();
    }
}
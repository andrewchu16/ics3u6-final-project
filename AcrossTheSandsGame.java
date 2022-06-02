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

        this.menuScreen = new MenuScreen(this.window, this.game);
        this.settingsScreen = new SettingsScreen(this.window, this.game);
        this.howToPlayScreen = new HowToPlayScreen(this.window);
        this.selectScreen = new SelectScreen(this.window, this.game);
        this.gameScreen = new GameScreen(this.window, this.game);
        this.gameOverScreen = new GameOverScreen(this.window);
        this.pauseScreen = new PauseScreen(this.window);
        
        this.window.addScreen(this.menuScreen);
        this.window.addScreen(this.settingsScreen);
        this.window.addScreen(this.howToPlayScreen);
        this.window.addScreen(this.selectScreen);
        this.window.addScreen(this.gameScreen);
        this.window.addScreen(this.gameOverScreen);
        this.window.addScreen(this.pauseScreen);
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
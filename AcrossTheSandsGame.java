import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    
    private Timer drawLoop;

    public AcrossTheSandsGame() {
        // Load resources into memory.
        Const.loadImages();
        Const.loadFonts();

        this.initializeWindow();
        this.game = new Game();

        this.drawLoop = new Timer(Const.FRAME_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                window.draw();
            }
        });
    }

    private void initializeWindow() {
        this.window = new Window("Across The Sands", Const.WIDTH, Const.HEIGHT);

        this.menuScreen = new MenuScreen(this.window);
        this.settingsScreen = new SettingsScreen(this.window);
        
        this.window.add(menuScreen);
        this.window.add(settingsScreen, Const.SETTINGS_SCREEN_NAME);
    }

    public void start() {
        System.out.println("Starting window");
        this.window.start();
        this.window.switchToScreen(Const.MENU_SCREEN_NAME);

        this.drawLoop.start();
    }

    public static void main(String[] args) {
        AcrossTheSandsGame game = new AcrossTheSandsGame();
        game.start();
    }
}
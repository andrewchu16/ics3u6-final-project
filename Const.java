import java.awt.image.BufferedImage;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import java.awt.event.KeyEvent;

import java.io.IOException;
import java.awt.FontFormatException;

public class Const {
    // Dimensions of the window.
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;

    // Update rates.
    public static final int MS_PER_S = 1000;

    // The duration between window redraws in milliseconds.
    public static final int DEFAULT_FRAME_PERIOD = MS_PER_S / 60;
    // The duration between game updates in milliseconds.
    public static final int UPDATE_PERIOD = MS_PER_S / 45;
    // The duration betweeen each frame in an animation in milliseconds.
    public static final int ANIMATE_PERIOD = MS_PER_S / 6;

    // Labels for the different screens.
    public static final String MENU_SCREEN_NAME = "main menu screen";
    public static final String SETTINGS_SCREEN_NAME = "setting screen";
    public static final String HOW_TO_PLAY_SCREEN_NAME = "how to play screen";
    public static final String SELECT_SCREEN_NAME = "select screen";
    public static final String GAME_SCREEN_NAME = "game screen";
    public static final String GAME_OVER_SCREEN_NAME = "game over screen";
    public static final String PAUSE_SCREEN_NAME = "pause screen";
    
    // Color palette.
    // d03d35,ff582e,f6ad41,ffda54,7dba84,4d9357,a7cedd,5ca4c1,53a1be,0c4197,d8dbe2,f3f5f7,c6cfd2,afbdc0,394748,0e262f
    public static final Color RED = new Color(208, 61, 53);
    public static final Color ORANGE = new Color(255, 88, 46);
    public static final Color YELLOW = new Color(246, 173, 65);
    public static final Color YELLOW2 = new Color(255, 218, 84);
    public static final Color GREEN = new Color(125, 186, 132);
    public static final Color GREEN2 = new Color(77, 147, 86);
    public static final Color LIGHT_BLUE1 = new Color(166, 206, 221);
    public static final Color LIGHT_BLUE2 = new Color(92, 165, 193);
    public static final Color BLUE = new Color(83, 162, 190);
    public static final Color DARK_BLUE = new Color(12, 65, 151);
    public static final Color WHITE = new Color(216, 219, 226);
    public static final Color WHITE2 = new Color(243, 245, 247);
    public static final Color LIGHT_GRAY = new Color(198, 208, 210);
    public static final Color LIGHT_GRAY2 = new Color(175, 189, 192);
    public static final Color GRAY = new Color(57, 70, 72);
    public static final Color BLACK = new Color(14, 38, 47);
    
    // Animation cycle data files.

    // Directions.
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    
    // Game keys.
    public static final int K_ESC = KeyEvent.VK_ESCAPE;
    public static final int K_UP = KeyEvent.VK_W;
    public static final int K_LEFT = KeyEvent.VK_A;
    public static final int K_DOWN = KeyEvent.VK_S;
    public static final int K_RIGHT = KeyEvent.VK_D;
    public static final int K_USE = KeyEvent.VK_E;
    public static final int K_RELOAD = KeyEvent.VK_R;
    
    // Map data file.
    public static final String MAP_FILE_NAME = "assets/map/map.txt";

    // Menu images.
    public static BufferedImage dayScreenBackground;
    public static BufferedImage nightScreenBackground;
    public static BufferedImage howToPlayScreenText;

    // Player animation images.
    public static BufferedImage playerIdleSpriteSheet;
    public static BufferedImage playerWalkSpriteSheet;
    public static BufferedImage playerAttackSpriteSheet;
    public static BufferedImage playerHurtSpriteSheet;

    // Player animation files.
    public static final String PLAYER_IDLE_FILE_NAME = "assets/animation/player/mummy_idle.txt";
    public static final String PLAYER_WALK_FILE_NAME = "assets/animation/player/mummy_walk.txt";
    public static final String PLAYER_ATTACK_FILE_NAME = "assets/animation/player/mummy_attack.txt";
    public static final String PLAYER_HURT_FILE_NAME = "assets/animation/player/mummy_hurt.txt";

    // Audio.

    // Fonts.
    private static final int TITLE_FONT_SIZE = 64;
    private static final int SUBTITLE_FONT_SIZE = 50;
    private static final int MENU_BUTTON_FONT_SIZE = 30;
    private static final int SMALL_BUTTON_FONT_SIZE = 24;
    private static final int DEBUG_FONT_SIZE = 12;

    public static Font titleFont;
    public static Font subtitleFont;
    public static Font buttonFont;
    public static Font smallButtonFont;
    public static Font debugFont;
    

    // Tile and map sprites.
    public static final Sprite SAND_TILE_SPRITE = new Sprite(0, 0, "assets/images/tiles/sand_tile.png");
    public static final Sprite ROCK_TILE_SPRITE = new Sprite(0, 0, "assets/images/tiles/rock_tile.png");
    
    /**
     * This method loads all the images used in the game.
     */
    public static void loadImages() {
        // Load menu screen images.
        dayScreenBackground = Sprite.tryLoadImage("assets/images/menu/day_background.png");
        nightScreenBackground = Sprite.tryLoadImage("assets/images/menu/night_background.png");
        howToPlayScreenText = Sprite.tryLoadImage("assets/images/menu/how_to_play.png");

        // Load player images.
        playerIdleSpriteSheet = Sprite.tryLoadImage("assets/images/player/mummy_idle.png");
        playerWalkSpriteSheet = Sprite.tryLoadImage("assets/images/player/mummy_walk.png");
        playerAttackSpriteSheet = Sprite.tryLoadImage("assets/images/player/mummy_attack.png");
        playerHurtSpriteSheet = Sprite.tryLoadImage("assets/images/player/mummy_hurt.png");
    }

    /**
     * This method loads all the text fonts used in the game. If a font cannot be read
     * from memory, a default font will be used.
     */
    public static void loadFonts() {
        try {
            // Load title and subtitle font.
            Font amaticSCBold = Font.createFont(Font.TRUETYPE_FONT, 
                    new File("assets/fonts/AmaticSC-Bold.ttf"));
            
            titleFont = amaticSCBold.deriveFont((float) TITLE_FONT_SIZE);
            subtitleFont = amaticSCBold.deriveFont((float) SUBTITLE_FONT_SIZE);
        } catch (IOException ex) {
            System.out.println("Error: Could not read font files.");
            titleFont = new Font("Calibri", Font.BOLD, TITLE_FONT_SIZE);
            subtitleFont = new Font("Calibri", Font.BOLD, SUBTITLE_FONT_SIZE);
        } catch (FontFormatException ex) {
            System.out.println("Error: Invalid font files.");
            titleFont = new Font("Calibri", Font.BOLD, TITLE_FONT_SIZE);
            subtitleFont = new Font("Calibri", Font.BOLD, SUBTITLE_FONT_SIZE);
        }

        // Load button and debug fonts.
        buttonFont = new Font("Sans-Serif", Font.PLAIN, MENU_BUTTON_FONT_SIZE);
        smallButtonFont = new Font("Sans-Serif", Font.PLAIN, SMALL_BUTTON_FONT_SIZE);
        debugFont = new Font("Sans-Serif", Font.PLAIN, DEBUG_FONT_SIZE);
    }

    private Const() {}
}

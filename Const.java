import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

import java.awt.event.KeyEvent;

import java.io.IOException;
import java.awt.FontFormatException;

public final class Const {
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
    public static final int ANIMATE_PERIOD = MS_PER_S / 12;

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

    // Minimap constants.
    public static final Vector MINIMAP_POS = new Vector(40, 40);
    public static final int MINIMAP_WIDTH = 180;
    public static final int MINIMAP_HEIGHT = 180;
    public static final double MINIMAP_SCALE = 6;

    // Menu images.
    public static final BufferedImage DAY_SCREEN_BACKGROUND = Sprite.tryLoadImage("assets/images/menu/day_background.png");;
    public static final BufferedImage NIGHT_SCREEN_BACKGROUND = Sprite.tryLoadImage("assets/images/menu/night_background.png");;
    public static final BufferedImage HOW_TO_PlAY_SCREEN_TEXT = Sprite.tryLoadImage("assets/images/menu/how_to_play.png");

    // Player animation images.
    public static final BufferedImage PLAYER_IDLE_SPRITE_SHEET = Sprite.tryLoadImage("assets/images/player/mummy_idle.png");
    public static final BufferedImage PLAYER_WALK_SPRITE_SHEET = Sprite.tryLoadImage("assets/images/player/mummy_walk.png");
    public static final BufferedImage PLAYER_ATTACK_SPRITE_SHEET = Sprite.tryLoadImage("assets/images/player/mummy_attack.png");
    public static final BufferedImage PLAYER_HURT_SPRITE_SHEET = Sprite.tryLoadImage("assets/images/player/mummy_hurt.png");

    // Player animation files.
    public static final String PLAYER_IDLE_FILE_NAME = "assets/animation/player/mummy_idle.txt";
    public static final String PLAYER_WALK_FILE_NAME = "assets/animation/player/mummy_walk.txt";
    public static final String PLAYER_ATTACK_FILE_NAME = "assets/animation/player/mummy_attack.txt";
    public static final String PLAYER_HURT_FILE_NAME = "assets/animation/player/mummy_hurt.txt";

    // Audio.

    // Fonts.
    private static final String AMATIC_SC_BOLD_FILE_NAME = "assets/fonts/AmaticSC-Bold.ttf";

    public static final Font TITLE_FONT = tryLoadLocalFont(AMATIC_SC_BOLD_FILE_NAME, 
            Font.TRUETYPE_FONT, Font.PLAIN, 64);
    public static final Font SUBTITLE_FONT = tryLoadLocalFont(AMATIC_SC_BOLD_FILE_NAME, 
            Font.TRUETYPE_FONT, Font.PLAIN, 50);
    public static final Font BUTTON_FONT = new Font("Sans-Serif", Font.PLAIN, 30);
    public static final Font SMALL_BUTTON_FONT = new Font("Sans-Serif", Font.PLAIN, 24);
    public static final Font DEBUG_FONT = new Font("Sans-Serif", Font.PLAIN, 12);
    
    // Tile and map sprites.
    public static final Sprite SAND_TILE_SPRITE = new Sprite(0, 0, "assets/images/tiles/sand_tile.png");
    public static final Sprite ROCK_TILE_SPRITE = new Sprite(0, 0, "assets/images/tiles/rock_tile.png");

    public static Font tryLoadLocalFont(String baseFontName, int fontType, int fontStyle, int fontSize) {
        Font fallbackFont = new Font("Calibri", fontStyle, fontSize);
        Font font;

        // Try to load the font.
        try {
            Font baseFont = Font.createFont(fontType, new File(baseFontName));
            font = baseFont.deriveFont((float) fontSize);
        } catch (IOException ex) {
            System.out.println("Error: Could not read font file. [" + baseFontName + "]");
            font = fallbackFont;
        } catch (FontFormatException ex) {
            System.out.println("Error: Invalid font file. [" + baseFontName + "]");
            font = fallbackFont;
        }

        return font;
    }

    private Const() {}
}

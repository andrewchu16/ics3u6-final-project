import java.awt.Color;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

import javax.imageio.ImageIO;

import java.awt.Font;
import java.io.File;

import java.io.IOException;
import java.awt.FontFormatException;

public class Const {
    // Dimensions of the window.
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;

    // Update rates.
    public static final int MS_PER_S = 1000;

    public static final int FRAME_PERIOD = MS_PER_S / 60;
    public static final int UPDATE_PERIOD = MS_PER_S / 45;

    // Labels for the different screens.
    public static final String MENU_SCREEN_NAME = "main menu screen";
    public static final String SETTINGS_SCREEN_NAME = "setting screen";
    public static final String HOW_TO_PLAY_SCREEN_NAME = "how to play screen";
    public static final String SELECT_SCREEN_NAME = "select screen";
    public static final String GAME_SCREEN_NAME = "game screen";
    public static final String GAME_OVER_SCREEN_NAME = "game over screen";
    public static final String PAUSE_SCREEN_NAME = "pause screen";
    
    // Color palette.
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

    // Difficulty levels.
    public static final String EASY_DIFFICULTY = "Easy";
    public static final String MEDIUM_DIFFICULTY = "Medium";
    public static final String HARD_DIFFICULTY = "Hard";

    // Images.
    public static BufferedImage dayScreenBackground;
    public static BufferedImage nightScreenBackground;
    public static BufferedImage howToPlayScreenText;

    // Audio.

    // Fonts.
    private static final int MENU_BUTTON_FONT_SIZE = 30;
    private static final int BACK_BUTTON_FONT_SIZE = 20;
    private static final int TITLE_FONT_SIZE = 64;
    private static final int SUBTITLE_FONT_SIZE = 50;

    public static Font buttonFont;
    public static Font backButtonFont;
    public static Font titleFont;
    public static Font subtitleFont;

    /**
     * This method loads all the images used in the game.
     */
    public static void loadImages() {
        // Create the day screen background.
        dayScreenBackground = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) dayScreenBackground.getGraphics();
        // Draw the sky.
        graphics.setColor(LIGHT_BLUE1);
        graphics.fillRect(0, 0, WIDTH, 500);
        // Draw the sand.
        GradientPaint yellowToOrange = new GradientPaint(WIDTH / 2, 500, YELLOW, 
                WIDTH / 2, 950, ORANGE);
        graphics.setPaint(yellowToOrange);
        graphics.fillRect(0, 500, WIDTH, HEIGHT - 500);
        // Draw the sun.
        graphics.setColor(YELLOW2);
        graphics.fillOval(850, 60, 70, 70);

        // Load how to play screen background.
        try {
            howToPlayScreenText = ImageIO.read(new File("images/howToPlay.png"));
            nightScreenBackground = ImageIO.read(new File("images/nightScreenBackground.png"));
        } catch (IOException ex) {
            System.out.println("Error: Could not load how to play screen image.");
            howToPlayScreenText = dayScreenBackground;
            nightScreenBackground = dayScreenBackground;
        }
    }

    /**
     * This method loads all the text fonts used in the game. If a font cannot be read
     * from memory, a default font will be used.
     */
    public static void loadFonts() {
        buttonFont = new Font("Sans-Serif", Font.PLAIN, MENU_BUTTON_FONT_SIZE);
        backButtonFont = new Font("Sans-Serif", Font.PLAIN, BACK_BUTTON_FONT_SIZE);

        // Load title font.
        try {
            Font amaticSCBold = Font.createFont(Font.TRUETYPE_FONT, 
                    new File("fonts/AmaticSC-Bold.ttf"));
                                            
            titleFont = amaticSCBold.deriveFont((float) TITLE_FONT_SIZE);
            subtitleFont = amaticSCBold.deriveFont((float) SUBTITLE_FONT_SIZE);
        } catch (IOException ex) {
            System.out.println("Error: Could not read title font.");
            titleFont = new Font("Calibri", Font.BOLD, TITLE_FONT_SIZE);
            subtitleFont = new Font("Calibri", Font.BOLD, SUBTITLE_FONT_SIZE);
        } catch (FontFormatException ex) {
            System.out.println("Error: Invalid title font file.");
            titleFont = new Font("Calibri", Font.BOLD, TITLE_FONT_SIZE);
            subtitleFont = new Font("Calibri", Font.BOLD, SUBTITLE_FONT_SIZE);
        }
    }

    private Const() {}
}
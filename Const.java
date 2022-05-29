import java.awt.Color;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

import java.awt.Font;
import java.io.File;

import java.io.IOException;
import java.awt.FontFormatException;

public class Const {
    // Dimensions of the window.
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;

    public static final int FRAME_PERIOD = 20;
    public static final int UPDATE_PERIOD = 30;

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
    public static final Color LIGHT_BLUE = new Color(83, 162, 190);
    public static final Color LIGHT_BLUE2 = new Color(166, 206, 221);
    public static final Color DARK_BLUE = new Color(12, 65, 151);
    public static final Color BLACK = new Color(14, 38, 47);
    public static final Color GRAY = new Color(57, 70, 72);
    public static final Color WHITE = new Color(216, 219, 226);
    public static final Color WHITE2 = new Color(243, 245, 247);

    // Images.
    public static BufferedImage dayScreenBackground;

    // Audio.

    // Fonts.
    private static final int BUTTON_FONT_SIZE = 30;
    private static final int TITLE_FONT_SIZE = 64;

    public static Font buttonFont;
    public static Font titleFont;

    public static void loadImages() {
        // Create the day screen background.
        dayScreenBackground = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) dayScreenBackground.getGraphics();
        graphics.setColor(LIGHT_BLUE2);
        graphics.fillRect(0, 0, WIDTH, 500);
        GradientPaint yellowToOrange = new GradientPaint(WIDTH / 2, 500, YELLOW, WIDTH / 2, 950, ORANGE);
        graphics.setPaint(yellowToOrange);
        graphics.fillRect(0, 500, WIDTH, HEIGHT - 500);
    }

    public static void loadFonts() {
        buttonFont = new Font("Sans-Serif", Font.PLAIN, BUTTON_FONT_SIZE);

        // Load title font.
        try {
            Font amaticSCBold = Font.createFont(Font.TRUETYPE_FONT, 
                                            new File("fonts/AmaticSC-Bold.ttf"));
                                            
            titleFont = amaticSCBold.deriveFont((float) TITLE_FONT_SIZE);
        } catch (IOException ex) {
            System.out.println("Error: Could not read title font.");
            titleFont = new Font("Calibri", Font.BOLD, TITLE_FONT_SIZE);
        } catch (FontFormatException ex) {
            System.out.println("Error: Invalid title font file.");
            titleFont = new Font("Calibri", Font.BOLD, TITLE_FONT_SIZE);
        }
    }

    private Const() {}
}

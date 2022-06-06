import java.awt.Graphics;

/**
 * This class represents a {@code Screen} for displaying information on how to 
 * play the game.
 */
public class HowToPlayScreen extends Screen {
    private Label titleLabel;
    private Sprite textSprite;

    /**
     * This constructs a {@code HowToPlayScreen} object.
     * @param window The window this {@code HowToPlayScreen} object is a part of.
     */
    public HowToPlayScreen(Window window) {
        super(Const.nightScreenBackground);

        this.setName(Const.HOW_TO_PLAY_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "How To Play", 
                Const.subtitleFont, Const.WHITE2);

        // Instantiate how to play text image.
        this.textSprite = new Sprite(0, 0, Const.howToPlayScreenText);

        // Instantiate buttons.
        Button goBackButton = new BackButton(30, 50, window);

        // Add buttons to screen.
        this.addButton(goBackButton);
    }

    /**
     * This method draws the {@code HowToPlayScreen}.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw title.
        this.titleLabel.draw(graphics);

        // Draw the text.
        this.textSprite.draw(graphics);

        // Draw buttons.
        this.drawButtons(graphics);
    }
}

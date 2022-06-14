import java.awt.Graphics;

/**
 * This class represents the {@code Screen} the player sees when the game ends.
 */
public class GameOverScreen extends Screen {
    private Label titleLabel;

    /**
     * This constructs a {@code GameOverScreen} object.
     * @param window The {@code Window} this {@code GameOverScreen} is a part of.
     */
    public GameOverScreen(Window window) {
        super(Const.NIGHT_SCREEN_BACKGROUND);
        
        this.setName(Const.GAME_OVER_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "Game Over", 
                Const.SUBTITLE_FONT, Const.WHITE2);
    }

    /**
     * This method draws the {@code GameOverScreen}.
     */
    @Override 
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.titleLabel.draw(graphics);
    }
}

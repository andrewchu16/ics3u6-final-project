import java.awt.Graphics;

/**
 * This class represents the canvas that the main game is drawn onto.
 */
public class GameScreen extends Screen {
    private Game game;

    /**
     * This constructs a {@code GameScreen} object. It has listeners for the various
     * game components.
     * @param window The {@code Window} this {@code GameScreen} is a part of.
     * @param game The {@code Game} object to display.
     */
    public GameScreen(Window window, Game game) {
        super();

        this.game = game;

        this.setName(Const.GAME_SCREEN_NAME);

        // Add game listeners.
        this.addKeyListener(game.new GameKeyListener());
        this.addMouseListener(game.new GameMouseListener());
        this.addMouseMotionListener(game.new GameMouseMotionListener());
    }

    /**
     * This method draws the game onto this {@code GameScreen}.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        this.game.draw(graphics);
    }
}

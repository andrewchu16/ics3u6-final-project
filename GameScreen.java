import java.awt.Graphics;

public class GameScreen extends Screen {
    private Game game;

    public GameScreen(Window window, Game game) {
        super();

        this.game = game;

        this.setName(Const.GAME_SCREEN_NAME);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        this.game.draw(graphics);
    }
}

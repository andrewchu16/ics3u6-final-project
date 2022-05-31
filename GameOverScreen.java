import java.awt.Graphics;

public class GameOverScreen extends Screen {
    private Label titleLabel;

    public GameOverScreen(Window window) {
        super(Const.nightScreenBackground);
        
        this.setName(Const.GAME_OVER_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "Game Over", 
                Const.subtitleFont, Const.WHITE2);
    }

    @Override 
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.titleLabel.draw(graphics);
    }
}

import java.awt.Graphics;

public class PauseScreen extends Screen {
    private Label titleLabel;

    public PauseScreen(Window window) {
        super(Const.dayScreenBackground);
        
        this.setName(Const.PAUSE_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "Paused", 
                Const.subtitleFont, Const.WHITE2);

        
        // Instantiate buttons.
        Button goBackButton = new BackButton(30, 50, window);

        // Add buttons to screen.
        this.addButton(goBackButton);
    }

    @Override 
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.titleLabel.draw(graphics);
    }
}

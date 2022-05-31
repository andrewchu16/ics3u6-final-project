import java.awt.Graphics;

public class HowToPlayScreen extends Screen {
    private Label titleLabel;
    private Sprite textSprite;

    public HowToPlayScreen(Window window) {
        super(Const.nightScreenBackground);

        this.setName(Const.HOW_TO_PLAY_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "How To Play", 
                Const.subtitleFont, Const.WHITE2);

        // Instantiate how to play text image.
        this.textSprite = new Sprite(0, 0, 1, Const.howToPlayScreenText);

        // Instantiate buttons.
        Button goBackButton = new BackButton(30, 50, window);

        // Add buttons to screen.
        this.addButton(goBackButton);
    }

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

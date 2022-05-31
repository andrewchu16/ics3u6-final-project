import java.awt.Graphics;

public class HowToPlayScreen extends Screen {
    private Sprite textSprite;

    public HowToPlayScreen(Window window) {
        super(Const.nightScreenBackground);

        this.setName(Const.HOW_TO_PLAY_SCREEN_NAME);

        this.textSprite = new Sprite(0, 0, 1, Const.howToPlayScreenText);

        // Instantiate buttons.
        Button goBackButton = new Button.BackButton(30, 50, window);

        // Add buttons to screen.
        this.addButton(goBackButton);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw the text.
        this.textSprite.draw(graphics);

        // Draw buttons.
        this.drawButtons(graphics);
    }
}

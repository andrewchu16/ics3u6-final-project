import java.awt.Graphics;

public class HowToPlayScreen extends Screen {
    public HowToPlayScreen(Window window) {
        super(Const.howToPlayScreenBackground);

        this.setName(Const.HOW_TO_PLAY_SCREEN_NAME);

        // Instantiate buttons.
        Button goBackButton = new Button.BackButton(30, 50, window);

        // Add buttons to screen.
        this.addButton(goBackButton);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw buttons.
        this.drawButtons(graphics);
    }
}

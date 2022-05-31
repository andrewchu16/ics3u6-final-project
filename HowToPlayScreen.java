import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class HowToPlayScreen extends Screen {
    private Text titleText;
    private Rectangle titleRect;
    private Sprite textSprite;

    public HowToPlayScreen(Window window) {
        super(Const.nightScreenBackground);

        this.setName(Const.HOW_TO_PLAY_SCREEN_NAME);

        // Instantiate title.
        this.titleRect = new Rectangle(Const.WIDTH / 2 - 100, 60, 200, 80);
        this.titleText = new Text("How To Play", Const.subtitleFont, (int) this.titleRect.getCenterX(),
                (int) this.titleRect.getCenterY());

        // Instantiate how to play text image.
        this.textSprite = new Sprite(0, 0, 1, Const.howToPlayScreenText);

        // Instantiate buttons.
        Button goBackButton = new Button.BackButton(30, 50, window);

        // Add buttons to screen.
        this.addButton(goBackButton);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw title.
        graphics.setColor(Const.WHITE2);
        ((Graphics2D) graphics).fill(this.titleRect);
        
        graphics.setColor(Const.BLACK);
        this.titleText.draw(graphics);

        // Draw the text.
        this.textSprite.draw(graphics);

        // Draw buttons.
        this.drawButtons(graphics);
    }
}

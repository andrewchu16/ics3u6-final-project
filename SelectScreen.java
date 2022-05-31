import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SelectScreen extends Screen {
    private Text titleText;
    private Rectangle titleRect;

    public SelectScreen(Window window) {
        super(Const.dayScreenBackground);

        this.setName(Const.SELECT_SCREEN_NAME);

        // Instantiate title.
        this.titleRect = new Rectangle(Const.WIDTH / 2 - 100, 60, 200, 80);
        this.titleText = new Text("New Game", Const.subtitleFont, (int) this.titleRect.getCenterX(),
                (int) this.titleRect.getCenterY());

        // Instantiate buttons and text field.
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

        // Draw buttons.
        this.drawButtons(graphics);
    }
}

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.awt.BasicStroke;

public class Minimap implements Drawable {
    private Vector position;
    private double scale;
    private BufferedImage surf;
    
    private Map map;
    private Player player;

    public Minimap(Vector position, int width, int height, double scale, Map map, Player player) {
        this.position = position;
        this.scale = scale;
        this.surf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        this.map = map;
        this.player = player;
    }

    public void update() {
        Graphics2D graphics = this.surf.createGraphics();

        // Draw the minimap background.
        graphics.setColor(Const.WHITE);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Center and scale the player in the minimap.
        AffineTransform saveAT = graphics.getTransform();
        AffineTransform translateCenterPlayer = AffineTransform.getTranslateInstance(
                this.getWidth() * this.scale / 2 - this.player.getCenterX(),
                this.getHeight() * this.scale / 2 - this.player.getCenterY());
        AffineTransform scaleDown = AffineTransform.getScaleInstance(1 / this.scale, 1 / this.scale);
        
        graphics.transform(scaleDown);
        graphics.transform(translateCenterPlayer);

        // Draw the map.
        this.map.draw(graphics);

        // Draw the player.
        graphics.setColor(Const.RED);
        graphics.fillOval((int) this.player.getCenterX() - 10, 
                (int) this.player.getCenterY() - 10, 20, 20);

        // Reset the graphics.
        graphics.setTransform(saveAT);
    }

    public int getWidth() {
        return this.surf.getWidth();
    }

    public int getHeight() {
        return this.surf.getHeight();
    }

    public int getX() {
        return (int) this.position.getX();
    }

    public int getY() {
        return (int) this.position.getY();
    }

    @Override
    public void draw(Graphics graphics) {
        // Transform the minimap to the top left.
        AffineTransform saveAT = ((Graphics2D) graphics).getTransform(); 
        AffineTransform setOriginTopLeft = AffineTransform.getTranslateInstance(0, 0);
        ((Graphics2D) graphics).setTransform(setOriginTopLeft);

        // Draw the minimap surface.
        ((Graphics2D) graphics).drawImage(this.surf, (int) position.getX(), (int) position.getY(), null);

        // Draw the minimap border.
        ((Graphics2D) graphics).setStroke(new BasicStroke(3));
        ((Graphics2D) graphics).setColor(Const.GRAY);
        ((Graphics2D) graphics).drawRect((int) position.getX() - 1, (int) position.getY() - 1, this.getWidth(), this.getHeight());

        // Reset the graphics.
        ((Graphics2D) graphics).setTransform(saveAT);
    }

    public Vector calculateMinimapPosition(Vector realPosition) {
        Vector minimapPosition = realPosition.clone();
        minimapPosition.div(this.scale);
        return minimapPosition;
    }
}

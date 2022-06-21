import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;

import java.util.ArrayList;

/**
 * This method represents a minimap of the real game map. The minimap can be a different size
 * magnification, and position than the actual game map.
 */
public class Minimap implements Drawable {
    private Vector position;
    private double scale;
    private BufferedImage surf;
    
    private Map map;
    private Player player;
    private ArrayList<Enemy> enemies;

    /**
     * This constructs a {@code Minimap} object with a position, size, and magnification.
     * @param position The top-left coordinate.
     * @param width The width of this {@code Minimap}.
     * @param height The height of this {@code Minimap}.
     * @param scale The magnification of this {@code Minimap}.
     * @param map The map this {@code Minimap} is based on.
     * @param player The {@code Player} object.
     * @param enemies The list of all the {@code Enemy} objects.
     */
    public Minimap(Vector position, int width, int height, double scale, Map map, 
            Player player, ArrayList<Enemy> enemies) {
        this.position = position;
        this.scale = scale;
        this.surf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        this.map = map;
        this.player = player;
        this.enemies = enemies;
    }

    /**
     * This method updates the rendering of this {@code Minimap}. The entities are
     * drawn as small circles on this {@code Minimap}. The player is centered.
     */
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

        // Draw the enemies.
        for (Enemy enemy: this.enemies) {
            graphics.setColor(Const.RED);
            graphics.fillOval((int) enemy.getCenterX() - 10, 
                    (int) enemy.getCenterY() - 10, 20, 20);
        }

        // Draw the player.
        graphics.setColor(Const.DARK_BLUE);
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

    /**
     * This method draws this {@code Minimap} onto a surface. It has a gray border.
     * @see Const#GRAY
     */
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
}

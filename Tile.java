import java.awt.Graphics;

public class Tile implements Drawable, Debuggable, Collidable {
    // Width and height of a tile.
    public static final int LENGTH = 50;

    // Tile types.
    public static final char SAND = '.';
    public static final char ROCK = 'R';
    public static final char UNKNOWN = '?';

    private Vector position;
    private char type;
    private boolean solid;

    private Sprite sprite;
    private Hitbox hitbox;
    
    public Tile(int x, int y, Sprite sprite) {
        this.position = new Vector(x, y);
        this.type = UNKNOWN;
        this.solid = false;
        this.sprite = sprite;
        this.hitbox = new Hitbox(position, LENGTH, LENGTH);
    }

    public Tile(Vector position, Sprite sprite) {
        this.position = position;
        this.type = UNKNOWN;
        this.solid = false;
        this.sprite = sprite;
        this.hitbox = new Hitbox(position, LENGTH, LENGTH);
    }

    public Tile(Vector position, char type) {
        this.position = position;
        this.type = type;

        switch (type) {
            case SAND:
                this.solid = false;
                this.sprite = Const.SAND_TILE_SPRITE;
                this.solid = false;
                break;
            case ROCK:
                this.solid = true;
                this.sprite = Const.ROCK_TILE_SPRITE;
                this.solid = true;
                break;
        }

        this.hitbox = new Hitbox(position, LENGTH, LENGTH);
    }
    
    /**
     * This method draws this {@code Tile}.
     */
    @Override
    public void draw(Graphics graphics) {
        this.sprite.draw(graphics, this.position);
    }

    /**
     * This method draws the outline of the hitbox of this {@code Tile}.
     * @param graphics
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.hitbox.drawDebugInfo(graphics);
    }

    @Override
    public boolean contains(int x, int y) {
        return this.hitbox.contains(x, y);
    }

    @Override
    public boolean intersects(Hitbox other) {
        return this.hitbox.intersects(other);
    }

    @Override
    public String toString() {
        return Character.toString(this.type);
    }
}

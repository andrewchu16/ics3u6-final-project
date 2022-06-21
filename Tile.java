import java.awt.Graphics;

/**
 * This class represents a tile in a {@code Chunk} in a {@code Map}. It has a sprite,
 * position and type.
 */
public class Tile implements Drawable, Debuggable, Collidable {
    // Width and height of a tile.
    public static final int LENGTH = 50;

    // Tile types.
    public static final char SAND = '.';
    public static final char ROCK = 'R';
    public static final char UNKNOWN = '?';

    private Vector position;
    private char type;
    // Whether the tile should be collidable with entities or not.
    private boolean solid;

    private Sprite sprite;
    private Hitbox hitbox;
    
    /**
     * This constructs a {@code Tile} object at a postion with a {@code Sprite}.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param sprite The sprite to use.
     */
    public Tile(int x, int y, Sprite sprite) {
        this.position = new Vector(x, y);
        this.type = UNKNOWN;
        this.solid = false;
        this.sprite = sprite;
        this.hitbox = new Hitbox(position, LENGTH, LENGTH);
    }

    /**
     * This constructs a {@code Tile} object at a position with a {@code Sprite}.
     * @param position The top-left coordinate.
     * @param sprite The sprite to use.
     */
    public Tile(Vector position, Sprite sprite) {
        this.position = position;
        this.type = UNKNOWN;
        this.solid = false;
        this.sprite = sprite;
        this.hitbox = new Hitbox(position, LENGTH, LENGTH);
    }

    /**
     * This constructs a {@code Tile} object at a position based off of a type. The
     * type determines whether the tile is collidable and what sprite to use.
     * @param position The top-left coordinate.
     * @param type The type of this {@code Tile}.
     */
    public Tile(Vector position, char type) {
        this.position = position;
        this.type = type;
        this.hitbox = new Hitbox(position, LENGTH, LENGTH);

        // Determine the sprite and whether it is solid or not.
        switch (type) {
            case SAND:
                this.sprite = Const.SAND_TILE_SPRITE;
                this.solid = false;
                break;
            case ROCK:
                this.sprite = Const.ROCK_TILE_SPRITE;
                this.solid = true;
                this.hitbox.setColor(Const.BLUE);
                break;
        }
    }

    public void setSolidState(boolean isSolid) {
        this.solid = isSolid;
    }

    /**
     * This method checks whether this {@code Tile} is solid or not.
     * @return {@code true} if it is solid, {@code false} otherwise.
     */
    public boolean checkSolid() {
        return this.solid;
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
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.hitbox.drawDebugInfo(graphics);
    }

    /**
     * This method determines whether a coordinate falls within this {@code Tile}.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.hitbox.contains(x, y);
    }

    /**
     * This method determines whether a hitbox intersects with this {@code Tile}.
     */
    @Override
    public boolean intersects(Hitbox other) {
        return this.hitbox.intersects(other);
    }

    /**
     * This method returns a string representing the type of this {@code Tile}.
     */
    @Override
    public String toString() {
        return Character.toString(this.type);
    }
}

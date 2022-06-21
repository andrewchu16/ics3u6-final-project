/**
 * This base class represents the base for all non-map objects in the game.
 * All entities have a name, position, and some sort of dimension.
 */
abstract public class Entity implements Drawable, Debuggable {
    private Vector position;
    private String name;

    /**
     * This constructs an {@code Entity} object at a position with a default name. 
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     */
    public Entity(int x, int y) {
        this.position = new Vector(x, y);
        this.name = "Unnamed Entity";
    }

    /**
     * This constructs an {@code Entity} object at a position with a default name.
     * @param position The top-left coordinate.
     */
    public Entity(Vector position) {
        this.position = position;
    }

    /**
     * This constructs an {@code Entity} object at a position with a name.
     * @param position The top-left coordinate.
     * @param name The name of this {@code Entity}.
     */
    public Entity(Vector position, String name) {
        this.position = position;
        this.name = name;
    }

    /**
     * This constructs an {@code Entity} object at a position with a name.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param name The name of this {@code Entity}.
     */
    public Entity(int x, int y, String name) {
        this.position = new Vector(x, y);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public double getX() {
        return this.position.getX();
    }
    
    public double getY() {
        return this.position.getY();
    }

    public Vector getPos() {
        return this.position.clone();
    }

    public Vector getRefPos() {
        return this.position;
    }

    abstract public int getWidth();
    abstract public int getHeight();
    abstract public int getCenterX();
    abstract public int getCenterY();
    abstract public Vector getCenter();

    /**
     * This method returns the general hitbox of this {@code Entity}. It should
     * be related to the width, height, and position of this {@code Entity}.
     * @return
     */
    abstract public Hitbox getGeneralHitbox();

    public void setName(String newName) {
        this.name = newName;
    }

    public void setX(double newX) {
        this.position.setX(newX);
    }

    public void setY(double newY) {
        this.position.setY(newY);
    }

    public void setPos(Vector newPos) {
        this.position = newPos;
    }

    /**
     * This method returns a string representation of this {@code Entity} in the format
     * "name (x, y)".
     */
    public String toString() {
        return this.name + " " + this.position;
    }
}

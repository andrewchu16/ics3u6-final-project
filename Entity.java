abstract public class Entity implements Drawable, Debuggable {
    private Vector position;
    private String name;

    public Entity(int x, int y) {
        this.position = new Vector(x, y);
        this.name = "Unnamed Entity";
    }

    public Entity(Vector position) {
        this.position = position;
    }

    public Entity(Vector position, String name) {
        this.position = position;
        this.name = name;
    }

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
        return this.position;
    }

    abstract public int getWidth();
    abstract public int getHeight();

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

    abstract public void setWidth(int newWidth);
    abstract public void setHeight(int newHeight);
}

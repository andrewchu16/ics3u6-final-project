abstract public class Entity implements Drawable, Debuggable {
    private Vector position;
    private AnimationCycle animationCycle;

    public Entity() {
        this.position = new Vector(0, 0);
    }

    public Entity(int x, int y) {
        this.position = new Vector(x, y);
    }

    public void setX(double newX) {
        this.position.setX(newX);
    }

    public void setY(double newY) {
        this.position.setY(newY);
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }
}

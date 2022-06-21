import java.awt.Graphics;

/**
 * This class represents a health bar that can be drawn on a surface. The health
 * bar looks like a red rectangle that is partially green, representing the
 * percentage of the current health that is left.
 */
public class HealthBar implements Drawable {
    private int maxPoints;
    private int curPoints;

    private Vector position;
    private int width;
    private int height;

    /**
     * This constructs a health bar at a position with a maximum number of hit 
     * points and dimension.
     * @param position The top-left coordinate.
     * @param maxPoints The maximum number of hit points.
     * @param width The width of this {@code HealthBar}.
     * @param height The height of this {@code HealthBar}.
     */
    public HealthBar(Vector position, int maxPoints, int width, int height) {
        this.curPoints = maxPoints;
        this.maxPoints = maxPoints;
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * This method draws the full health bar as a red rectangle and the current 
     * health as a green section of the full health bar.
     */
    @Override
    public void draw(Graphics graphics) {
        // Draw the full bar.
        graphics.setColor(Const.RED);
        graphics.fillRect((int) this.position.getX(), (int) this.position.getY(), width, height);

        // Draw the current health bar.
        int curWidth = 0;
        if (this.maxPoints != 0) {
            curWidth = width * curPoints / maxPoints;
        }
        graphics.setColor(Const.GREEN);
        graphics.fillRect((int) this.position.getX(), (int) this.position.getY(), 
                curWidth, height);
    }

    /**
     * This method sets the current health to the maximum hit points.
     */
    public void reset() {
        this.curPoints = maxPoints;
    }

    /**
     * This method sets the current health to 0.
     */
    public void empty() {
        this.curPoints = 0;
    }

    public void setPos(Vector pos) {
        this.position = pos;
    }

    /**
     * This method sets the current health to a new value. If the points
     * do not fit between 0 and the maximum number of hit points, nothing happens.
     * @param points The amount of hit points to set the current health to.
     */
    public void setHealth(int points) {
        if (0 <= points && points <= this.maxPoints) {
            this.curPoints = points;
        }
    }

    /**
     * This method sets the maximum number of hit points. If the new maximum is 
     * negative, nothing happens. The current health points get lowered if the new 
     * maximum health is lower than the current health.
     * @param maxPoints
     */
    public void setMaxPoints(int maxPoints) {
        if (maxPoints >= 0) {
            this.maxPoints = maxPoints;
            this.curPoints = Math.min(this.curPoints, maxPoints);
        }
    }

    public int getHealth() {
        return this.curPoints;
    }

    /**
     * This method lowers the current health by an amount. The current health 
     * will not go lower than 0.
     * @param damagePoints The amount of damage to deal.
     */
    public void takeDamage(int damagePoints) {
        this.curPoints = Math.max(0, this.curPoints - damagePoints);
    }

    /**
     * This method raises the current health by an amount. The current health
     * will not go higher than the maximum hit points.
     * @param healPoints The amount of health to heal.
     */
    public void heal(int healPoints) {
        this.curPoints = Math.min(this.maxPoints, this.curPoints + healPoints);
    }
}

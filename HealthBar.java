import java.awt.Graphics;

public class HealthBar implements Drawable {
    private int maxPoints;
    private int curPoints;

    private Vector position;
    private int width;
    private int height;

    public HealthBar(Vector position, int maxPoints, int width, int height) {
        this.curPoints = maxPoints;
        this.maxPoints = maxPoints;
        this.position = position;
        this.width = width;
        this.height = height;
    }

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

    public void reset() {
        this.curPoints = maxPoints;
    }

    public void empty() {
        this.curPoints = 0;
    }

    public void setPos(Vector pos) {
        this.position = pos;
    }

    public void setHealth(int points) {
        if (points < 0 || points > this.maxPoints) {
            return;
        }

        this.curPoints = points;
    }

    public void setMaxPoints(int maxPoints) {
        if (maxPoints >= 0) {
            this.maxPoints = maxPoints;
        }
    }

    public int getHealth() {
        return this.curPoints;
    }

    public void takeDamage(int damagePoints) {
        this.curPoints = Math.max(0, this.curPoints - damagePoints);
    }

    public void heal(int healPoints) {
        this.curPoints = Math.min(this.maxPoints, this.curPoints + healPoints);
    }
}

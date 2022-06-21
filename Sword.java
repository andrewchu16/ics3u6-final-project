import java.awt.Graphics;

import java.util.ArrayList;

/**
 * This class represents a melee sword weapon in the game. When swung, it has an
 * animation and hitbox.
 */
public class Sword extends Entity implements Collidable {
    private ArrayList<AnimationCycle> cycles;
    private AnimationCycle activeCycle;
    private AnimationCycle attackCycle;
    private AnimationCycle idleCycle;

    private int direction;
    private int damagePoints;
    
    /**
     * This constructs a {@code Sword} object with a position and damage points.
     * @param position The position of this {@code Sword}.
     * @param swordDamagePoints The damage points this {@code Sword} does.
     */
    public Sword(Vector position, int swordDamagePoints) {
        super(position, "Unnamed Sword");

        this.idleCycle = new AnimationCycle(this.getPos(), Const.SWORD_IDLE_SPRITE_SHEET, Const.SWORD_IDLE_FILE_NAME);
        this.attackCycle = new AnimationCycle(this.getPos(), Const.SWORD_ATTACK_SPRITE_SHEET, Const.SWORD_ATTACK_FILE_NAME);

        this.cycles = new ArrayList<AnimationCycle>();
        this.cycles.add(this.idleCycle);
        this.cycles.add(this.attackCycle);

        this.activeCycle = this.idleCycle;

        this.direction = Const.LEFT;
        this.damagePoints = swordDamagePoints;
    }

    /**
     * This constructs a {@code Sword} object with a position, damage points, and a name.
     * @param position The position of this {@code Sword}.
     * @param swordDamagePoints The damage points this {@code Sword} does.
     * @param name The name of this {@code Sword}.
     */
    public Sword(Vector position, int swordDamagePoints, String name) {
        this(position, swordDamagePoints);
        this.setName(name);
    }

    /**
     * This method draws this {@code Sword} onto a surface.
     */
    @Override
    public void draw(Graphics graphics) {
        this.activeCycle.draw(graphics);
    }

    /**
     * This method draws the hitboxes of this {@code Sword} onto a surface.
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeCycle.drawDebugInfo(graphics);
    }

    /**
     * This method handles the animations of this {@code Sword}. It updates animation
     * frames and switches to different animation cycles when the current cycle ends.
     */
    public void animate() {
        this.activeCycle.loadNextFrame();
        
        if (this.activeCycle.checkDone()) {
            this.activeCycle.reset();
            this.attackCycle.reset();
            this.activeCycle = idleCycle;
            this.activeCycle.setPos(this.getPos());
        }
    }

    /**
     * This method determines whether a coordinate is contained in this {@code Sword}.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.activeCycle.contains(x, y);
    }

    /**
     * This method determines whether a hitbox intersects with this {@code Sword}.
     */
    @Override
    public boolean intersects(Hitbox other) {
        return this.activeCycle.intersects(other);
    }

    /**
     * This method determines whether an {@code AnimationCycle} intersects with this {@code Sword}.
     * @param otherCycle The {@code AnimationCycle} to check.
     * @return {@code true} if they do intersect, {@code false} otherwise.
     */
    public boolean intersects(AnimationCycle otherCycle) {
        return this.activeCycle.intersects(otherCycle);
    }

    @Override
    public int getWidth() {
        return this.activeCycle.getFrameWidth();
    }

    @Override
    public int getHeight() {
        return this.activeCycle.getFrameHeight();
    }

    @Override
    public int getCenterX() {
        RelativeHitbox generalHitbox = (RelativeHitbox) this.getGeneralHitbox();
        return generalHitbox.getX() + generalHitbox.getWidth() / 2;
    }

    @Override
    public int getCenterY() {
        RelativeHitbox generalHitbox = (RelativeHitbox) this.getGeneralHitbox();
        return generalHitbox.getY() + generalHitbox.getHeight() / 2;
    }

    @Override
    public Vector getCenter() {
        return new Vector(this.getCenterX(), this.getCenterY());
    }

    @Override
    public Hitbox getGeneralHitbox() {
        return this.activeCycle.getGeneralHitbox().clone();
    }

    public int getDamage() {
        return this.damagePoints;
    }

    public void setDamage(int newSwordDamage) {
        this.damagePoints = newSwordDamage;
    }

    @Override
    public void setPos(Vector newPos) {
        super.setPos(newPos);
        this.activeCycle.setPos(newPos);
    }

    /**
     * This method checks whether this {@code Sword} is in the middle of an attack cycle.
     * @return {@code true} if it is, {@code false} otherwise.
     */
    public boolean checkAttacking() {
        return this.activeCycle == this.attackCycle;
    }

    /**
     * This method starts an attack cycle if one is not already occuring.
     */
    public void attack() {
        if (!this.checkAttacking()) {
        this.activeCycle = this.attackCycle;
        }
    }

    /**
     * This method resets the attack cycle.
     */
    public void resetAttack() {
        this.attackCycle.reset();
        this.activeCycle = this.idleCycle;
    }

    /**
     * This method reflects this {@code Sword} so it is facing left.
     */
    public void turnLeft() {
        if (this.direction == Const.RIGHT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
        }
        this.direction = Const.LEFT;
    }

    /**
     * This method reflects this {@code Sword so ti is facing right}.
     */
    public void turnRight() {
        if (this.direction == Const.LEFT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
        }
        this.direction = Const.RIGHT;
    }
}

import java.awt.Graphics;

import java.util.ArrayList;

public class Sword extends Entity implements Collidable {
    private ArrayList<AnimationCycle> cycles;
    private AnimationCycle activeCycle;
    private AnimationCycle attackCycle;
    private AnimationCycle idleCycle;

    private int direction;
    private int damagePoints;
    
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

    public Sword(Vector position, int swordDamagePoints, String name) {
        this(position, swordDamagePoints);
        this.setName(name);
    }

    @Override
    public void draw(Graphics graphics) {
        this.activeCycle.draw(graphics);
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeCycle.drawDebugInfo(graphics);
    }

    public void animate() {
        this.activeCycle.loadNextFrame();
        
        if (this.activeCycle.checkDone()) {
            this.activeCycle.reset();
            this.attackCycle.reset();
            this.activeCycle = idleCycle;
            this.activeCycle.setPos(this.getPos());
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return this.activeCycle.contains(x, y);
    }

    @Override
    public boolean intersects(Hitbox other) {
        return this.activeCycle.intersects(other);
    }

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

    public boolean checkAttacking() {
        return this.activeCycle == this.attackCycle;
    }

    public void setDamage(int newSwordDamage) {
        this.damagePoints = newSwordDamage;
    }

    @Override
    public void setPos(Vector newPos) {
        super.setPos(newPos);
        this.activeCycle.setPos(newPos);
    }

    public void attack() {
        this.activeCycle = this.attackCycle;
    }

    public void turnLeft() {
        if (this.direction == Const.RIGHT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
        }
        this.direction = Const.LEFT;
    }

    public void turnRight() {
        if (this.direction == Const.LEFT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
        }
        this.direction = Const.RIGHT;
    }
}

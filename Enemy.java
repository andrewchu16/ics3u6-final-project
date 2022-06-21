import java.awt.Graphics;

import java.util.ArrayList;

/**
 * This class represents an enemy in the game. Like the player, they have swords
 * and have the same hitbox. However, they are not affected by tile collisions.
 */
public class Enemy extends Entity implements Moveable, Collidable {
    private static final int WALK_SPEED = 4;
    private static final int ATTACK_RANGE = 20;
    private static int numEnemies = 0;

    private ArrayList<AnimationCycle> cycles;
    private AnimationCycle activeCycle;
    private AnimationCycle idleCycle;
    private AnimationCycle attackCycle;
    private AnimationCycle hurtCycle;
    private AnimationCycle walkCycle;
    private AnimationCycle deathCycle;

    private int direction;
    private Vector speed;
    private Vector targetPos;
    private Player player;
    private Sword sword;
    private HealthBar healthBar;
    private boolean deadState;

    /**
     * This constructs an {@code Enemy} object.
     * @param position The position of the {@code Enemy}.
     * @param player The player.
     * @param maxHealthPoints The max health of the {@code Enemy}.
     * @param swordDamagePoints The sword damage of the {@code Enemy}.
     */
    public Enemy(Vector position, Player player, int maxHealthPoints, int swordDamagePoints) {
        super(position, "Unnamed Enemy " + numEnemies);
        numEnemies++;

        // Initialize the animation cycles.
        this.idleCycle = new AnimationCycle(this.getPos(), Const.ENEMY_IDLE_SPRITE_SHEET, Const.ENEMY_IDLE_FILE_NAME);
        this.walkCycle = new AnimationCycle(this.getPos(), Const.ENEMY_WALK_SPRITE_SHEET, Const.ENEMY_WALK_FILE_NAME);
        this.attackCycle = new AnimationCycle(this.getPos(), Const.ENEMY_ATTACK_SPRITE_SHEET, Const.ENEMY_ATTACK_FILE_NAME);
        this.hurtCycle = new AnimationCycle(this.getPos(), Const.ENEMY_HURT_SPRITE_SHEET, Const.ENEMY_HURT_FILE_NAME);
        this.deathCycle = new AnimationCycle(this.getPos(), Const.ENEMY_DEATH_SPRITE_SHEET, Const.ENEMY_DEATH_FILE_NAME);
        
        this.cycles = new ArrayList<AnimationCycle>();
        this.cycles.add(this.idleCycle);
        this.cycles.add(this.walkCycle);
        this.cycles.add(this.attackCycle);
        this.cycles.add(this.hurtCycle);
        this.cycles.add(this.deathCycle);
        
        this.activeCycle = this.idleCycle;

        this.direction = Const.LEFT;
        this.speed = Vector.VECTOR_ZERO.clone();
        this.targetPos = position.clone();
        this.player = player;
        this.deadState = false;

        this.sword = new Sword(position, swordDamagePoints);
        this.healthBar = new HealthBar(Vector.sum(this.getCenter(), new Vector(-this.getWidth() / 2 + 20, -53)), 
                maxHealthPoints, this.getWidth() - 40, 7);
    }

    /**
     * This method draws the enemy animation and health bar.
     */
    @Override
    public void draw(Graphics graphics) {
        this.activeCycle.draw(graphics);
        this.healthBar.draw(graphics);
        this.sword.draw(graphics);
    }

    /**
     * This method draws the hitboxes, the target, and other debug info for the enemy.
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeCycle.drawDebugInfo(graphics);
        this.sword.drawDebugInfo(graphics);
        
        // Draw the coordinates of the enemy.
        String info = this.getName() + "(" + (Math.round(this.getCenterX() * 10) / 10.0) + 
                ", " + (Math.round(this.getY() * 10) / 10.0) + ")";
        Text text = new Text(info, Const.DEBUG_FONT, (int) this.getCenterX(), (int) this.getY());
        text.draw(graphics);

        // Draw the enemy target.
        graphics.setColor(Const.GRAY);
        graphics.fillOval((int) this.targetPos.getX() - 3, (int) this.targetPos.getY() - 3, 6, 6);
    }

    /**
     * This method updates the position of the enemy and its current behaviour.
     */
    public void update() {
        // Update the speed.
        this.speed = Vector.difference(this.targetPos, this.getCenter());
        this.speed.setLength(Math.min(WALK_SPEED, 
                (int) (Vector.getEuclideanDistanceFrom(this.getPos(), this.targetPos))));

        if (this.intersects(this.player.getGeneralHitbox())) {
            this.speed = Vector.VECTOR_ZERO.clone();
        }

        // Update the position.
        if (this.checkCanMove()) {
            Vector newPos = this.getPos();
            newPos.add(this.speed);
            this.setPos(newPos);
        }

        // Attack or set a new target position.
        if (Vector.compareDistance(this.getCenter(), player.getCenter(), ATTACK_RANGE) <= 0) {
            this.attack();
        } else if (this.checkAtTarget()) {
            this.setTargetPos(this.player.getCenter());
        }
    }

    /**
     * This method animates the {@code Enemy} on each frame.
     */
    public void animate() {
        this.activeCycle.loadNextFrame();
        
        if (this.activeCycle.checkDone()) {
            // The player is completely dead once the death animation ends.
            if (this.activeCycle == this.deathCycle) {
                this.deadState = true;
            }
            this.activeCycle.reset();
            this.attackCycle.reset();
            this.activeCycle.setPos(this.getPos());
        }
        
        // Walk towards the target.
        if (this.checkAtTarget()) {
            this.attack();
        } else {
            this.activeCycle = this.walkCycle;
        }

        this.sword.animate();
    }

    /**
     * This method checks if the enemy is alive.
     * @return {@code true} if the enemy is alive, {@code false} otherwise.
     */
    public boolean checkAlive() {
        return this.healthBar.getHealth() > 0;
    }

    /**
     * This method checks if enemy has died and completed the death animation cycle.
     * @return {@code true} if the enemy is completely dead, {@code false} otherwise.
     */
    public boolean checkFullyDead() {
        return this.deadState;
    }

    /**
     * This method checks if the enemy is within a certain distance threshold from its target.
     * @return
     */
    public boolean checkAtTarget() {
        return Vector.compareDistance(this.getCenter(), this.targetPos, Tile.LENGTH * 2) <= 0;
    }

    /**
     * This method checks if the enemy is currently in an attack cycle.
     * @return {@code true} if they are, {@code false} otherwise.
     */
    public boolean checkAttacking() {
        return this.sword.checkAttacking();
    }

    /**
     * This method checks if the enemy can currently move. The only time the
     * enemy cannot move is when they are being hit.
     * @return {@code true} if they can move, {@code false} otherwise.
     */
    public boolean checkCanMove() {
        return this.activeCycle != this.hurtCycle;
    }

    /**
     * This method checks if the enemy can attack or not. The enemy must
     * be able to move and not be already attacking.
     * @return {@code true} if the enemy can attack, {@code false} otherwise.
     */
    public boolean checkCanAttack() {
        return !this.sword.checkAttacking() && this.checkCanMove();
    }

    @Override
    public void moveUp() {
        this.activeCycle = walkCycle;
        this.speed.add(0, -WALK_SPEED);
        this.speed.setLength(WALK_SPEED);
    }

    @Override
    public void moveLeft() {
        this.activeCycle = walkCycle;
        this.speed.add(-WALK_SPEED, 0);
        this.speed.setLength(WALK_SPEED);
    }

    @Override
    public void moveDown() {
        this.activeCycle = walkCycle;
        this.speed.add(0, WALK_SPEED);
        this.speed.setLength(WALK_SPEED);
    }

    @Override
    public void moveRight() {
        this.activeCycle = walkCycle;
        this.speed.add(WALK_SPEED, 0);
        this.speed.setLength(WALK_SPEED);
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

    public AnimationCycle getActiveCycle() {
        return this.activeCycle;
    }

    public Sword getSword() {
        return this.sword;
    }

    public Vector getSpeed() {
        return this.speed;
    }

    public void setSpeed(Vector newspeed) {
        this.speed = newspeed;
    }

    /**
     * This method sets the target position for the {@code Enemy}. The enemy
     * will try to walk towards its target.
     * @param targetPos The target position.
     */
    public void setTargetPos(Vector targetPos) {
        this.targetPos = targetPos;

        // Turn towards target.
        if (Double.compare(this.targetPos.getX(), this.getCenterX()) <= 0) {
            this.turnLeft();
        } else {
            this.turnRight();
        }
    }

    @Override
    public void setX(double newX) {
        super.setX(newX);
        this.setPos(this.getPos());
    }

    @Override
    public void setY(double newY) {
        super.setY(newY);
        this.setPos(this.getPos());
    }

    @Override
    public void setPos(Vector newPos) {
        super.setPos(newPos);
        this.activeCycle.setPos(newPos);
        this.healthBar.setPos(Vector.sum(this.getCenter(), new Vector(-this.getWidth() / 2 + 20, -53)));
        this.sword.setPos(newPos);
    }

    /**
     * This method sets a new maximum health points for the enemy. It also
     * refills the enemy's current health to the new maximum.
     * @param newMaxHealthPoints The new maximum health points of the enemy.
     */
    public void setMaxHealthPoints(int newMaxHealthPoints) {
        this.healthBar.setMaxPoints(newMaxHealthPoints);
        this.healthBar.setHealth(newMaxHealthPoints);
    }

    /**
     * This method checks if a coordinate is contained within the hitboxes of the
     * enemy.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.activeCycle.contains(x, y);
    }

    /**
     * This method checks if a hitbox intersects with the hitboxes of the enemy.
     */
    @Override
    public boolean intersects(Hitbox other) {
        return this.activeCycle.intersects(other);
    }

    /**
     * This method begins the attack cycle.
     */
    public void attack() {
        if (this.checkCanAttack()) {
            this.activeCycle = this.attackCycle;
            this.sword.attack();
        }
    }

    /**
     * This method inflicts damage onto the enemy. It also displays a hurt
     * animation cycle.
     * @param damagePoints The amount of damage to deal to the enemy.
     */
    public void takeDamage(int damagePoints) {
        if (this.checkAlive()) {
            this.healthBar.takeDamage(damagePoints);
            this.activeCycle = this.hurtCycle;
        }
    }

    /**
     * This method begins the death animation cycle. Once that is complete,
     * the enemy is considered completely dead.
     */
    public void die() {
        this.activeCycle = deathCycle;
    }

    /**
     * This method makes the enemy face left if they are not already.
     */
    private void turnLeft() {
        if (this.direction == Const.RIGHT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
            this.sword.turnLeft();
        }
        this.direction = Const.LEFT;
    }

    /**
     * This method makes the enemy face right if they are not already.
     */
    private void turnRight() {
        if (this.direction == Const.LEFT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
            this.sword.turnRight();
        }
        this.direction = Const.RIGHT;
    }
}

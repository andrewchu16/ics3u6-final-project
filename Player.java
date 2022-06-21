import java.awt.Graphics;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * This class represents the player in the game. The player is capable of
 * moving around, colliding with tiles and attacking.
 */
public class Player extends Entity implements Moveable, Collidable {
    private static final int WALK_SPEED = 16;

    private ArrayList<AnimationCycle> cycles;
    private AnimationCycle activeCycle;
    private AnimationCycle idleCycle;
    private AnimationCycle attackCycle;
    private AnimationCycle hurtCycle;
    private AnimationCycle walkCycle;

    private int direction;
    private Vector moveSpeed;
    private Vector realSpeed;
    private Map map;
    private Sword sword;
    private HealthBar healthBar;

    /**
     * This constructs a {@code Player} object.
     * @param maxHealthPoints The player's max hit points.
     * @param swordDamagePoints The player's sword damage.
     * @param map The map the player interacts with.
     */
    public Player(int maxHealthPoints, int swordDamagePoints, Map map) {
        super(0, 0, "Player");

        // Initialize the animation cycles.
        this.idleCycle = new AnimationCycle(this.getPos(), Const.PLAYER_IDLE_SPRITE_SHEET, Const.PLAYER_IDLE_FILE_NAME);
        this.walkCycle = new AnimationCycle(this.getPos(), Const.PLAYER_WALK_SPRITE_SHEET, Const.PLAYER_WALK_FILE_NAME);
        this.attackCycle = new AnimationCycle(this.getPos(), Const.PLAYER_ATTACK_SPRITE_SHEET, Const.PLAYER_ATTACK_FILE_NAME);
        this.hurtCycle = new AnimationCycle(this.getPos(), Const.PLAYER_HURT_SPRITE_SHEET, Const.PLAYER_HURT_FILE_NAME);
        
        this.cycles = new ArrayList<AnimationCycle>();
        this.cycles.add(this.idleCycle);
        this.cycles.add(this.walkCycle);
        this.cycles.add(this.attackCycle);
        this.cycles.add(this.hurtCycle);
        
        this.activeCycle = this.idleCycle;

        this.direction = Const.LEFT;
        this.moveSpeed = Vector.VECTOR_ZERO.clone();
        this.map = map;
        this.sword = new Sword(this.getPos(), swordDamagePoints, this.getName() + "'s Sword");
        this.healthBar = new HealthBar(Vector.sum(this.getCenter(), new Vector(-this.getWidth() / 2, -60)), 
                maxHealthPoints, this.getWidth(), 10);
    }

    /**
     * This method draws the player and their healthbar onto a surface.
     */
    @Override
    public void draw(Graphics graphics) {
        this.activeCycle.draw(graphics);
        this.healthBar.draw(graphics);
        this.sword.draw(graphics);
    }

    /**
     * This method draws the player hitboxes and debug info onto a surface.
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeCycle.drawDebugInfo(graphics);
        this.sword.drawDebugInfo(graphics);
        
        // Draw the coordinates of the player.
        String info = "(" + (Math.round(this.getCenterX() * 10) / 10.0) + 
                ", " + (Math.round(this.getY() * 10) / 10.0) + ")";
        Text text = new Text(info, Const.DEBUG_FONT, (int) this.getCenterX(), (int) this.getY());
        text.draw(graphics);
    }

    /**
     * This method updates the player's position and handles tile collisions.
     */
    public void update() {
        // Handle collisions.
        this.realSpeed = this.moveSpeed.clone();
        this.handleTileCollisions();

        // Update the position.
        Vector newPos = this.getPos();
        newPos.add(this.realSpeed);
        this.setPos(newPos);

        // Regenerate health randomly.
        if ((int) (Math.random() * 5) == 0) {
            this.healthBar.heal(Const.PLAYER_REGEN);
        }
    }

    /**
     * This method animates the player on each frame.
     */
    public void animate() {
        this.activeCycle.loadNextFrame();
        
        if (this.activeCycle.checkDone()) {
            this.activeCycle.reset();
            this.attackCycle.reset();

            // Switch to an idle animation when stationary or walking when moving.
            if (this.moveSpeed.equals(Vector.VECTOR_ZERO)) {
                this.activeCycle = this.idleCycle;
            } else {
                this.activeCycle = this.walkCycle;
            }
            this.activeCycle.setPos(this.getPos());
        }

        this.sword.animate();
    }

    /**
     * This method alters the player speed in order to prevent solid tile collisions.
     */
    private void handleTileCollisions() {
        RelativeHitbox shiftedHitbox = (RelativeHitbox) this.getGeneralHitbox();

        Vector newRealSpeed = this.realSpeed.getVectorX();
        double speedPercentage = 1.0;

        // Reduce the speed until it can move horizontally.
        shiftedHitbox.setAnchorPos(Vector.sum(this.getPos(), newRealSpeed));
        while (this.map.intersectsWithActiveSolid(shiftedHitbox) && 
                Double.compare(speedPercentage, 0.1) >= 0) {
            speedPercentage -= 0.1;
            speedPercentage = Math.round(speedPercentage * 10) / 10.0;
            newRealSpeed.setX(this.realSpeed.getX() * speedPercentage);
            shiftedHitbox.setAnchorPos(Vector.sum(this.getPos(), newRealSpeed));
        }

        // Reduce the speed until it can move vertically.
        newRealSpeed.setY(this.realSpeed.getY());
        speedPercentage = 1.0;
        shiftedHitbox.setAnchorPos(Vector.sum(this.getPos(), newRealSpeed));
        while (this.map.intersectsWithActiveSolid(shiftedHitbox) && 
                Double.compare(speedPercentage, 0.1) >= 0) {
            speedPercentage -= 0.1;
            speedPercentage = Math.round(speedPercentage * 10) / 10;
            newRealSpeed.setY(this.realSpeed.getY() * speedPercentage);
            shiftedHitbox.setAnchorPos(Vector.sum(this.getPos(), newRealSpeed));
        }

        this.realSpeed = newRealSpeed;
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
        this.healthBar.setPos(Vector.sum(this.getCenter(), new Vector(-this.getWidth() / 2, -60)));
        this.sword.setPos(newPos);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * This method sets a new maximum health points for the player. It also
     * refills the player's current health to the new maximum.
     * @param newMaxHealthPoints The new maximum health points of the player.
     */
    public void setMaxHealthPoints(int newMaxHealthPoints) {
        this.healthBar.setMaxPoints(newMaxHealthPoints);
        this.healthBar.reset();
    }

    public void setSwordDamage(int newSwordDamage) {
        this.sword.setDamage(newSwordDamage);
    }

    /**
     * THis method checks if the player is currently attacking or not.
     * @return {@code true} if the player is attacking, {@code false} otherwise.
     */
    public boolean checkAttacking() {
        return this.sword.checkAttacking();
    }

    /**
     * This method checks if the player is alive or not.
     * @return {@code true} if the player is alive, {@code false} otherwise.
     */
    public boolean checkAlive() {
        return this.healthBar.getHealth() > 0;
    }

    @Override
    public void moveUp() {
        this.resetAttack();
        this.activeCycle = walkCycle;
        this.moveSpeed.setY(-WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
    }

    @Override
    public void moveLeft() {
        this.resetAttack();
        this.activeCycle = walkCycle;
        this.moveSpeed.setX(-WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
    }

    @Override
    public void moveDown() {
        this.resetAttack();
        this.activeCycle = walkCycle;
        this.moveSpeed.setY(WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
    }

    @Override
    public void moveRight() {
        this.resetAttack();
        this.activeCycle = walkCycle;
        this.moveSpeed.setX(WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
    }

    /**
     * This method begins the attack cycle.
     */
    public void attack() {
        if (!this.checkAttacking()) {
            this.activeCycle = this.attackCycle;
            this.resetAttack();
            this.sword.attack();
            this.activeCycle.setPos(getPos());
        }
    }

    /**
     * This method resets the attack cycle.
     */
    public void resetAttack() {
        this.attackCycle.reset();
        this.sword.resetAttack();
    }

    /**
     * This method inflicts damage onto the player. It also displays a hurt
     * animation cycle.
     * @param damagePoints The amount of damage to deal to the player.
     */
    public void takeDamage(int damagePoints) {
        this.healthBar.takeDamage(damagePoints);
        this.activeCycle = this.hurtCycle;
    }

    /**
     * This method checks if a coordinate is contained within the hitboxes of the
     * player.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.activeCycle.contains(x, y);
    }

    /**
     * This method checks if a hitbox intersects with the hitboxes of the player.
     */
    @Override
    public boolean intersects(Hitbox other) {
        return this.activeCycle.intersects(other);
    }

    /**
     * This method checks if another {@code AnimationCycle} intersects with the player.
     * @param otherCycle
     * @return
     */
    public boolean intersects(AnimationCycle otherCycle) {
        return this.activeCycle.intersects(otherCycle);
    }

    /**
     * This method makes the player face left if they are not already.
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
     * This method makes the player face right if they are not already.
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

    /**
     * This class is used to get player keyboard input.
     */
    public class PlayerKeyListener implements KeyListener {
        private boolean[] pressedKeys;

        /**
         * This constructs a {@code PlayerKeyListener}.
         */
        public PlayerKeyListener() {
            this.pressedKeys = new boolean[KeyEvent.KEY_LAST + 1];
            Arrays.fill(this.pressedKeys, false);
        }

        private boolean checkKeyValid(int keyCode) {
            return 0 <= keyCode && keyCode < this.pressedKeys.length;
        }

        public void keyTyped(KeyEvent event) {}

        /**
         * This method handles key pressed events.
         */
        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();

            if (!checkKeyValid(keyCode) || this.pressedKeys[keyCode]) {
                return;
            }

            this.pressedKeys[keyCode] = true;
            
            // Handle movement input.
            if (keyCode == Const.K_UP) {
                moveUp();
            } 
            if (keyCode == Const.K_LEFT) {
                moveLeft();
            } 
            if (keyCode == Const.K_DOWN) {
                moveDown();
            } 
            if (keyCode == Const.K_RIGHT) {
                moveRight();
            }
        }

        /**
         * This method handles key released events.
         */
        @Override
        public void keyReleased(KeyEvent event) {
            int keyCode = event.getKeyCode();

            if (!checkKeyValid(keyCode)) {
                return;
            }

            this.pressedKeys[keyCode] = false;
            
            if (keyCode == Const.K_UP) {
                if (!this.pressedKeys[Const.K_DOWN]) {
                    moveSpeed.setY(0);
                } else {
                    moveDown();
                }
            } 
            if (keyCode == Const.K_LEFT) {
                if (!this.pressedKeys[Const.K_RIGHT]) {
                    moveSpeed.setX(0);
                } else {
                    moveRight();
                }
            } 
            if (keyCode == Const.K_DOWN) {
                if (!this.pressedKeys[Const.K_UP]) {
                    moveSpeed.setY(0);
                } else {
                    moveUp();
                }
            } 
            if (keyCode == Const.K_RIGHT) {
                if (!this.pressedKeys[Const.K_LEFT]) {
                    moveSpeed.setX(0);
                } else {
                    moveLeft();
                }
            }

            if (moveSpeed.equals(Vector.VECTOR_ZERO)) {
                activeCycle = idleCycle;
                activeCycle.setPos(getPos());
            } else {
                moveSpeed.setLength(WALK_SPEED);
            }
        }
    };

    /**
     * This class is used to get player mouse button inputs.
     */
    public class PlayerMouseListener implements MouseListener {
        public void mousePressed(MouseEvent event) {
            attack();
        }

        public void mouseReleased(MouseEvent event) {}
        public void mouseClicked(MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
    }

    /**
     * This class is used to get player mouse inputs.
     */
    public class PlayerMouseMotionListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent event) {}

        @Override
        public void mouseMoved(MouseEvent event) {
            if (checkAttacking()) {
                return;
            }

            // Turn the player to face the mouse.
            int x = event.getX() + (int) getCenterX() - Const.WIDTH / 2;
            if (x < getCenterX()) {
                turnLeft();
            } else {
                turnRight();
            }
        }
    }
}

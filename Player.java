import java.awt.Graphics;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.Arrays;
import java.util.ArrayList;

public class Player extends Entity implements Moveable {
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

    public Player() {
        super(0, 0, "Player");

        this.idleCycle = new AnimationCycle(this.getPos(), Const.PLAYER_IDLE_SPRITE_SHEET, Const.PLAYER_IDLE_FILE_NAME);
        this.walkCycle = new AnimationCycle(this.getPos(), Const.PLAYER_WALK_SPRITE_SHEET, Const.PLAYER_WALK_FILE_NAME);
        this.attackCycle = new AnimationCycle(this.getPos(), Const.PLAYER_ATTACK_SPRITE_SHEET, Const.PLAYER_ATTACK_FILE_NAME);
        this.hurtCycle = new AnimationCycle(this.getPos(), Const.PLAYER_HURT_SPRITE_SHEET, Const.PLAYER_HURT_FILE_NAME);
        
        this.cycles = new ArrayList<AnimationCycle>();
        this.cycles.add(idleCycle);
        this.cycles.add(walkCycle);
        this.cycles.add(attackCycle);
        this.cycles.add(hurtCycle);
        
        this.activeCycle = idleCycle;

        this.direction = Const.LEFT;
        this.moveSpeed = Vector.VECTOR_ZERO.clone();
        this.map = null;
    }

    @Override
    public void draw(Graphics graphics) {
        this.activeCycle.draw(graphics);
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeCycle.drawDebugInfo(graphics);
        
        // Draw the coordinates of the player.
        String info = "(" + (Math.round(this.getX() * 10) / 10.0) + ", " + (Math.round(this.getY() * 10) / 10.0) + ")";
        Text text = new Text(info, Const.DEBUG_FONT, (int) this.getX(), (int) this.getY());
        text.draw(graphics);
    }

    public void update() {
        // Handle collisions.
        this.realSpeed = this.moveSpeed.clone();
        this.handleTileCollisions();

        // Update the position.
        Vector newPos = this.getPos();
        newPos.add(this.realSpeed);
        this.setPos(newPos);
    }

    public void animate() {
        this.activeCycle.loadNextFrame();
        
        if (this.activeCycle.checkDone()) {
            this.activeCycle.reset();
            this.activeCycle = this.idleCycle;
            this.activeCycle.setPos(this.getPos());
        }
    }

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

    @Override
    public void setX(double newX) {
        super.setX(newX);
        this.activeCycle.setPos(this.getPos());
    }

    @Override
    public void setY(double newY) {
        super.setY(newY);
        this.activeCycle.setPos(this.getPos());
    }

    @Override
    public void setPos(Vector newPos) {
        super.setPos(newPos);
        this.activeCycle.setPos(newPos);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public class PlayerKeyListener implements KeyListener {
        private boolean[] pressedKeys;

        public PlayerKeyListener() {
            this.pressedKeys = new boolean[KeyEvent.KEY_LAST + 1];
            Arrays.fill(this.pressedKeys, false);
        }

        private boolean checkKeyValid(int keyCode) {
            return 0 <= keyCode && keyCode < this.pressedKeys.length;
        }

        public void keyTyped(KeyEvent event) {}

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

    public class PlayerMouseListener implements MouseListener {
        public void mousePressed(MouseEvent event) {
            int x = event.getX() + (int) getCenterX() - Const.WIDTH / 2;
            int y = event.getY() + (int) getCenterY() - Const.HEIGHT / 2;

            if (activeCycle.contains(x, y)) {
                takeDamage();
            } else {
                attack();
            }

            activeCycle.setPos(getPos());
        }

        public void mouseReleased(MouseEvent event) {}
        public void mouseClicked(MouseEvent event) {}
        public void mouseEntered(MouseEvent event) {}
        public void mouseExited(MouseEvent event) {}
    }

    @Override
    public void moveUp() {
        this.activeCycle = walkCycle;
        this.moveSpeed.setY(-WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
    }

    @Override
    public void moveLeft() {
        this.activeCycle = walkCycle;
        this.moveSpeed.setX(-WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
        if (this.direction == Const.RIGHT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
        }
        this.direction = Const.LEFT;
    }

    @Override
    public void moveDown() {
        this.activeCycle = walkCycle;
        this.moveSpeed.setY(WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
    }

    @Override
    public void moveRight() {
        this.activeCycle = walkCycle;
        this.moveSpeed.setX(WALK_SPEED);
        this.moveSpeed.setLength(WALK_SPEED);
        if (this.direction == Const.LEFT) {
            for (AnimationCycle cycle: this.cycles) {
                cycle.reflectHorizontally();
            }
        }
        this.direction = Const.RIGHT;
    }

    public void attack() {
        activeCycle = attackCycle;
    }

    public void takeDamage() {
        activeCycle = hurtCycle;
    }
}

import java.awt.Graphics;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.Arrays;

public class Player extends Entity implements Moveable {
    private static final int WALK_SPEED = 16;

    private AnimationCycle activeCycle;
    private AnimationCycle idleCycle;
    private AnimationCycle attackCycle;
    private AnimationCycle hurtCycle;
    private AnimationCycle walkCycle;

    private Vector moveSpeed;
    private Vector realSpeed;
    private Map map;

    public Player() {
        super(0, 0, "Player");

        this.idleCycle = new AnimationCycle(this.getPos(), Const.playerIdleSpriteSheet, Const.PLAYER_IDLE_FILE_NAME);
        this.walkCycle = new AnimationCycle(this.getPos(), Const.playerWalkSpriteSheet, Const.PLAYER_WALK_FILE_NAME);
        this.attackCycle = new AnimationCycle(this.getPos(), Const.playerAttackSpriteSheet, Const.PLAYER_ATTACK_FILE_NAME);
        this.hurtCycle = new AnimationCycle(this.getPos(), Const.playerHurtSpriteSheet, Const.PLAYER_HURT_FILE_NAME);
        
        this.activeCycle = idleCycle;

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
        Text text = new Text(info, Const.debugFont, (int) this.getX(), (int) this.getY());
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
        RelativeHitbox shiftedHitbox = (RelativeHitbox) this.activeCycle.getGeneralHitbox().clone();

        Vector newRealSpeed = this.realSpeed.getVectorX();
        double speedPercentage = 1.0;

        // Reduce the speed until it can move horizontally.
        shiftedHitbox.setAnchorPos(Vector.sum(this.getPos(), newRealSpeed));
        while (this.map.intersectsWithActiveSolid(shiftedHitbox) && 
                Double.compare(speedPercentage, 0) >= 0.1) {
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
                Double.compare(speedPercentage, 0) >= 0.1) {
            speedPercentage -= 0.1;
            speedPercentage = Math.round(speedPercentage * 10) / 10;
            newRealSpeed.setY(this.realSpeed.getY() * speedPercentage);
            shiftedHitbox.setAnchorPos(Vector.sum(this.getPos(), newRealSpeed));
        }

        this.realSpeed = newRealSpeed;
    }

    private void handleObstacleCollisions() {

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

        public void keyTyped(KeyEvent event) {}

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();

            if (this.pressedKeys[keyCode]) {
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

            this.pressedKeys[keyCode] = false;
            
            if (keyCode == Const.K_UP) {
                if (!this.pressedKeys[Const.K_DOWN]) {
                    moveSpeed.setY(0);
                }
            } 
            if (keyCode == Const.K_LEFT) {
                if (!this.pressedKeys[Const.K_RIGHT]) {
                    moveSpeed.setX(0);
                }
            } 
            if (keyCode == Const.K_DOWN) {
                if (!this.pressedKeys[Const.K_UP]) {
                    moveSpeed.setY(0);
                }
            } 
            if (keyCode == Const.K_RIGHT) {
                if (!this.pressedKeys[Const.K_LEFT]) {
                    moveSpeed.setX(0);
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
            int x = event.getX() + (int) getX() - Const.WIDTH / 2;
            int y = event.getY() + (int) getY() - Const.HEIGHT / 2;

            if (activeCycle.contains(x, y)) {
                activeCycle = hurtCycle;
            } else {
                activeCycle = attackCycle;
            }

            activeCycle.setPos(getPos());
        }

        public void mouseReleased(MouseEvent event) {

        }
        
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
    }
}

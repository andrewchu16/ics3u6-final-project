import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.Arrays;

public class Player extends Entity implements Moveable {
    private static final int WALK_SPEED = 5;

    private AnimationCycle activeCycle;
    private AnimationCycle idleCycle;
    private AnimationCycle attackCycle;
    private AnimationCycle hurtCycle;
    private AnimationCycle walkCycle;

    private Vector speed;

    public Player() {
        super(0, 0, "Leto");
        this.idleCycle = new AnimationCycle(this.getPos(), Const.playerIdleSpriteSheet, 4, true);
        this.walkCycle = new AnimationCycle(this.getPos(), Const.playerWalkSpriteSheet, 6, true);
        this.attackCycle = new AnimationCycle(this.getPos(), Const.playerAttackSpriteSheet, 6, false);
        this.hurtCycle = new AnimationCycle(this.getPos(), Const.playerHurtSpriteSheet, 2, false);

        this.idleCycle.setLooping(true);
        this.walkCycle.setLooping(true);

        this.speed = Vector.VECTOR_ZERO.clone();

        this.activeCycle = idleCycle;
    }

    @Override
    public void draw(Graphics graphics) {
        this.activeCycle.draw(graphics);
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeCycle.drawDebugInfo(graphics);
        
        // Draw the coordinates of the player.
        String info = "(" + (Math.round(this.getX() * 100) / 100) + ", " + (Math.round(this.getY() * 100) / 100) + ")";
        Text text = new Text(info, Const.debugFont, (int) this.getX(), (int) this.getY());
        text.draw(graphics);
    }

    public void update() {
        Vector newPos = this.getPos();
        newPos.add(this.speed);
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
                speed.setY(0);
            } 
            if (keyCode == Const.K_LEFT) {
                speed.setX(0);
            } 
            if (keyCode == Const.K_DOWN) {
                speed.setY(0);
            } 
            if (keyCode == Const.K_RIGHT) {
                speed.setX(0);
            }

            if (speed.equals(Vector.VECTOR_ZERO)) {
                activeCycle = idleCycle;
                activeCycle.setPos(getPos());
            } else {
                speed.setLength(WALK_SPEED);
            }
        }
    };

    public class PlayerMouseListener implements MouseListener {
        public void mousePressed(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();

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
        this.speed.setY(-WALK_SPEED);
        this.speed.setLength(WALK_SPEED);
    }

    @Override
    public void moveLeft() {
        this.activeCycle = walkCycle;
        this.speed.setX(-WALK_SPEED);
        this.speed.setLength(WALK_SPEED);
    }

    @Override
    public void moveDown() {
        this.activeCycle = walkCycle;
        this.speed.setY(WALK_SPEED);
        this.speed.setLength(WALK_SPEED);
    }

    @Override
    public void moveRight() {
        this.activeCycle = walkCycle;
        this.speed.setX(WALK_SPEED);
        this.speed.setLength(WALK_SPEED);
    }
}

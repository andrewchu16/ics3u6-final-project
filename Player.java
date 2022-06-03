import java.awt.Graphics;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Player extends Entity implements Moveable {
    private static final int WALK_SPEED = 3;

    private AnimationCycle activeCycle;
    private AnimationCycle idleCycle;
    private AnimationCycle attackCycle;
    private AnimationCycle hurtCycle;
    private AnimationCycle walkCycle;

    public Player() {
        super(0, 0, "Leto");
        this.idleCycle = new AnimationCycle(this.getPos(), Const.playerIdleSpriteSheet, 4, true);
        this.walkCycle = new AnimationCycle(this.getPos(), Const.playerWalkSpriteSheet, 6, true);
        this.activeCycle = idleCycle;
    }

    @Override
    public void draw(Graphics graphics) {
        this.activeCycle.draw(graphics);
    }

    public void update() {

    }

    public void animate() {
        this.activeCycle.loadNextFrame();
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeCycle.drawDebugInfo(graphics);
    }

    @Override
    public void moveHorizontal(double units) {
        this.setX(this.getX() + units);
        activeCycle = walkCycle;

        this.activeCycle.setPos(this.getPos());
    }

    @Override
    public void moveVertical(double units) {
        this.setY(this.getY() + units);
        activeCycle = walkCycle;
        
        this.activeCycle.setPos(this.getPos());
    }

    @Override
    public int getWidth() {
        return this.activeCycle.getFrameWidth();
    }

    @Override
    public int getHeight() {
        return this.activeCycle.getFrameHeight();
    }

    public class PlayerKeyListener implements KeyListener {
        private Window window;

        public PlayerKeyListener(Window window) {
            this.window = window;
        }

        public void keyTyped(KeyEvent event) {}

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            
            switch (keyCode) {
                case Const.K_ESC:
                    window.switchToScreen(Const.PAUSE_SCREEN_NAME);
                    break;
                case Const.K_UP:
                    moveVertical(-WALK_SPEED);
                    break;
                case Const.K_LEFT:
                    moveHorizontal(-WALK_SPEED);
                    break;
                case Const.K_DOWN:
                    moveVertical(WALK_SPEED);
                    break;
                case Const.K_RIGHT:
                    moveHorizontal(WALK_SPEED);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
            int keyCode = event.getKeyCode();
            
            switch (keyCode) {
                case Const.K_UP:
                    activeCycle = idleCycle;
                    break;
                case Const.K_LEFT:
                    activeCycle = idleCycle;
                    break;
                case Const.K_DOWN:
                    activeCycle = idleCycle;
                    break;
                case Const.K_RIGHT:
                    activeCycle = idleCycle;
                    break;
            }

            System.out.println(getPos());
        }
    };
}

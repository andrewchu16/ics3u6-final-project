import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Game implements Drawable, Debuggable {
    // Difficulty levels.
    public static final String[] DIFFICULTY_STRINGS = {"Easy", "Medium", "Hard"};
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    private boolean debugMode;
    private int difficulty;

    private Player player;
    private Map map;

    private Timer updateLoop;
    private Timer animateLoop;
    
    public Game() {
        this.debugMode = false;
        this.difficulty = MEDIUM;

        this.player = new Player();
        this.map = new Map(Const.MAP_FILE_NAME);
        this.map.loadFromFile();
        this.map.updateRendering(this.player.getPos());

        this.updateLoop = new Timer(Const.UPDATE_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                update();
            }
        });

        this.animateLoop = new Timer(Const.ANIMATE_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                animate();
            }
        });
    }

    public void run() {
        this.updateLoop.start();
        this.animateLoop.start();
    }

    public void pause() {
        this.updateLoop.stop();
        this.animateLoop.stop();
    }

    private void update() {
        Vector prevPlayerMapPosition = Map.calculateMapPosition(this.player.getPos());
        
        this.player.update();

        Vector curPlayerMapPosition = Map.calculateMapPosition(this.player.getPos());
        if (!prevPlayerMapPosition.equals(curPlayerMapPosition)) {
            this.map.updateRendering(this.player.getPos());
        }
    }

    private void animate() {
        this.player.animate();
    }

    @Override
    public void draw(Graphics graphics) {
        // Center the player in the window.
        AffineTransform saveAT = ((Graphics2D) graphics).getTransform();
        AffineTransform translateCenterPlayer = AffineTransform.getTranslateInstance(
                Const.WIDTH / 2 - this.player.getX(), Const.HEIGHT / 2 - this.player.getY());
        ((Graphics2D) graphics).transform(translateCenterPlayer);

        this.map.draw(graphics);
        this.player.draw(graphics);
        
        if (this.checkDebugging()) {
            this.drawDebugInfo(graphics);
        }

        // Reset the graphics.
        ((Graphics2D) graphics).setTransform(saveAT);
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.map.drawDebugInfo(graphics);
        this.player.drawDebugInfo(graphics);

        // Draw a circle at (0, 0)
        graphics.setColor(Const.RED);
        graphics.fillOval(0, 0, 5, 5);
    }

    public boolean checkDebugging() {
        return this.debugMode;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDebugging(boolean isDebugging) {
        this.debugMode = isDebugging;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setUpdatePeriod(int updatePeriod) {
        this.updateLoop.setDelay(updatePeriod);
    }

    public class GameKeyListener implements KeyListener {
        private Window window;

        public GameKeyListener(Window window) {
            this.window = window;
        }

        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();

            if (keyCode == Const.K_ESC) {
                pause();
                this.window.switchToScreen(Const.PAUSE_SCREEN_NAME);
            }
        }
        public void keyTyped(KeyEvent event) {}
        public void keyReleased(KeyEvent event) {}
    }
}

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.Timer;

import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

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
    private Minimap minimap;
    private ArrayList<Enemy> enemies;

    private Timer updateLoop;
    private Timer animateLoop;
    private Timer enemySpawnLoop;
    
    public Game() {
        this.debugMode = true;
        
        this.player = new Player();
        this.map = new Map(Const.MAP_FILE_NAME);
        this.map.loadFromFile();
        this.map.updateRendering(this.player.getPos());
        this.player.setMap(map);
        this.minimap = new Minimap(Const.MINIMAP_POS, Const.MINIMAP_WIDTH, 
        Const.MINIMAP_HEIGHT, Const.MINIMAP_SCALE, this.map, this.player);
        
        this.enemies = new ArrayList<Enemy>();
        
        for (int i = 0; i < Const.NUM_START_ENEMIES; i++) {
            this.spawnEnemy();
        }
        
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
        
        this.enemySpawnLoop = new Timer(Const.MEDIUM_SPAWN_SPEED, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                spawnEnemy();
            }
        });
        
        this.setDifficulty(MEDIUM);
    }

    public void run() {
        this.updateLoop.start();
        this.animateLoop.start();
        this.enemySpawnLoop.start();
    }

    public void pause() {
        this.updateLoop.stop();
        this.animateLoop.stop();
        this.enemySpawnLoop.stop();
    }

    private void update() {
        Vector prevPlayerMapPosition = Map.calculateMapPosition(this.player.getPos());
        
        this.player.update();

        for (Enemy enemy: this.enemies) {
            enemy.update();

            if (Vector.compareDistance(enemy.getCenter(), player.getCenter(), 50) <= 0) {
                enemy.attack();
            } else if (enemy.checkAtTarget()) {
                enemy.setTargetPos(this.player.getCenter());
            }
        }

        // Update map rendering if player moves to a new chunk.
        Vector curPlayerMapPosition = Map.calculateMapPosition(this.player.getPos());
        if (!prevPlayerMapPosition.equals(curPlayerMapPosition)) {
            this.map.updateRendering(this.player.getPos());
        }

        this.minimap.update();
    }

    private void animate() {
        this.player.animate();

        for (Enemy enemy: this.enemies) {
            enemy.animate();
        }
    }

    private void spawnEnemy() {
        Vector randomPos;
        do {
            randomPos = Vector.getRandomInstance(this.player.getCenterX() - 400, 
                    this.player.getCenterX() + 400, this.player.getCenterY() - 400, 
                    this.player.getCenterY() + 400);
        } while (Vector.compareDistance(randomPos, this.player.getCenter(), 180) <= 0);

        Enemy newEnemy = new Enemy(randomPos);
        this.enemies.add(newEnemy);
    }

    @Override
    public void draw(Graphics graphics) {
        // Center the player in the window.
        AffineTransform saveAT = ((Graphics2D) graphics).getTransform();
        AffineTransform translateCenterPlayer = AffineTransform.getTranslateInstance(
                Const.WIDTH / 2 - this.player.getCenterX(), Const.HEIGHT / 2 - this.player.getCenterY());
        ((Graphics2D) graphics).setTransform(translateCenterPlayer);

        this.map.draw(graphics);
        this.player.draw(graphics);

        for (Enemy enemy: this.enemies) {
            enemy.draw(graphics);
        }
        
        // Reset the graphics.
        ((Graphics2D) graphics).setTransform(saveAT);
        
        if (this.checkDebugging()) {
            this.drawDebugInfo(graphics);
        }
        
        this.minimap.draw(graphics);
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        // Center the player in the window.
        AffineTransform saveAT = ((Graphics2D) graphics).getTransform();
        AffineTransform translateCenterPlayer = AffineTransform.getTranslateInstance(
                Const.WIDTH / 2 - this.player.getCenterX(), Const.HEIGHT / 2 - this.player.getCenterY());
        ((Graphics2D) graphics).setTransform(translateCenterPlayer);

        this.map.drawDebugInfo(graphics);
        this.player.drawDebugInfo(graphics);

        for (Enemy enemy: this.enemies) {
            enemy.drawDebugInfo(graphics);
        }

        // Draw a circle at (0, 0)
        graphics.setColor(Const.RED);
        graphics.fillOval(-2, -2, 5, 5);

        // Reset the graphics.
        ((Graphics2D) graphics).setTransform(saveAT);
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
        if (difficulty != EASY && difficulty != MEDIUM && difficulty != HARD) {
            return;
        }

        this.difficulty = difficulty;

        switch (difficulty) {
            case EASY:
                this.enemySpawnLoop.setDelay(Const.EASY_SPAWN_SPEED);
                break;
            case MEDIUM:
                this.enemySpawnLoop.setDelay(Const.MEDIUM_SPAWN_SPEED);
                break;
            case HARD: 
                this.enemySpawnLoop.setDelay(Const.HARD_SPAWN_SPEED);
                break;
        }
    }

    public void setUpdatePeriod(int updatePeriod) {
        this.updateLoop.setDelay(updatePeriod);
    }

    public class GameKeyListener implements KeyListener {
        private Window window;
        private ArrayList<KeyListener> keyListeners;

        public GameKeyListener(Window window) {
            this.window = window;
            this.keyListeners = new ArrayList<KeyListener>();
            keyListeners.add(player.new PlayerKeyListener());
        }

        public void keyPressed(KeyEvent event) {
            for (KeyListener keyListener: this.keyListeners) {
                keyListener.keyPressed(event);
            }
            int keyCode = event.getKeyCode();

            if (keyCode == Const.K_ESC) {
                pause();
                this.window.switchToScreen(Const.PAUSE_SCREEN_NAME);
            }
        }
        public void keyTyped(KeyEvent event) {
            for (KeyListener keyListener: this.keyListeners) {
                keyListener.keyTyped(event);
            }
        }

        public void keyReleased(KeyEvent event) {
            for (KeyListener keyListener: this.keyListeners) {
                keyListener.keyReleased(event);
            }
        }
    }

    public class GameMouseListener implements MouseListener {
        private ArrayList<MouseListener> mouseListeners;

        public GameMouseListener() {
            this.mouseListeners = new ArrayList<MouseListener>();
            this.mouseListeners.add(player.new PlayerMouseListener());
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            for (MouseListener mouseListener: this.mouseListeners) {
                mouseListener.mouseClicked(event);
            }
        }

        @Override
        public void mousePressed(MouseEvent event) {
            for (MouseListener mouseListener: this.mouseListeners) {
                mouseListener.mousePressed(event);
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            for (MouseListener mouseListener: this.mouseListeners) {
                mouseListener.mouseReleased(event);
            }
        }

        @Override
        public void mouseEntered(MouseEvent event) {
            for (MouseListener mouseListener: this.mouseListeners) {
                mouseListener.mouseEntered(event);
            }
        }

        @Override
        public void mouseExited(MouseEvent event) {
            for (MouseListener mouseListener: this.mouseListeners) {
                mouseListener.mouseExited(event);
            }
        }
    }
}

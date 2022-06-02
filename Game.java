import java.awt.Graphics;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Game implements Drawable, Debuggable {
    // Difficulty levels.
    public static final String[] DIFFICULTY_STRINGS = {"Easy", "Medium", "Hard"};
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    private boolean debugMode;
    private Player player;
    private int difficulty;

    private Timer updateLoop;
    
    public Game() {
        this.debugMode = false;
        this.difficulty = MEDIUM;

        player = new Player();

        this.updateLoop = new Timer(Const.UPDATE_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                update();
            }
        });
    }

    public void run() {
        System.out.println("Starting game");
        this.updateLoop.start();
    }

    private void update() {
        this.player.update();
    }

    @Override
    public void draw(Graphics graphics) {
        this.player.draw(graphics);

        if (this.checkDebugging()) {
            this.drawDebugInfo(graphics);
        }
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.player.drawDebugInfo(graphics);
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
}

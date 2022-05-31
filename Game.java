import java.awt.Graphics;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Game {
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

    }

    private void update() {

    }

    public void draw(Graphics graphics) {

        if (this.debugMode) {
            this.debugDraw(graphics);
        }
    }

    public void debugDraw(Graphics graphics) {
        
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

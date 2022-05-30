import java.awt.Graphics;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Game {
    private boolean debugMode;

    private Timer updateLoop;

    public Game() {
        this.debugMode = false;

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

    public void setDebugging(boolean isDebugging) {
        this.debugMode = isDebugging;
    }

    public void setUpdatePeriod(int updatePeriod) {
        this.updateLoop.setDelay(updatePeriod);
    }
}

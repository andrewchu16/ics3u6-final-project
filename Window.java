import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

import java.awt.CardLayout;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Window {
    private JFrame frame;
    private JPanel cards;

    private ArrayList<String> screenStack;

    private Timer drawLoop;

    public Window(String title, int width, int height) {
        // Create the window.
        this.frame = new JFrame(title);
        this.frame.getContentPane().setPreferredSize(new Dimension(width, height));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.cards = new JPanel(new CardLayout());

        this.screenStack = new ArrayList<String>();

        this.drawLoop = new Timer(Const.DEFAULT_FRAME_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                draw();
            }
        });
    }

    public void start() {
        this.frame.add(cards);
        this.frame.setVisible(true);
        this.frame.pack();
        this.drawLoop.start();
    }

    public void draw() {
        this.frame.repaint();
    }

    public void addScreen(Screen screen, String screenName) {
        this.cards.add(screen, screenName);
    }

    public void addScreen(Screen screen) {
        this.cards.add(screen, screen.getName());
    }

    public void switchToScreen(String screenName) {
        CardLayout layout = (CardLayout) this.cards.getLayout();

        String prevScreenName = "";
        if (!this.screenStack.isEmpty()) {
            prevScreenName = this.screenStack.get(this.screenStack.size() - 1);
        }

        if (this.screenStack.isEmpty() || !prevScreenName.equals(screenName)) {
            this.screenStack.add(screenName);
        }

        // Switch screens.
        layout.show(this.cards, screenName);
        System.out.println("Switching: " + prevScreenName + " --> " + screenName);
    }

    public int getFPS() {
        return 1000 / this.drawLoop.getDelay();
    }

    public void setFPS(int fps) {
        this.drawLoop.setDelay(Const.MS_PER_S / fps);
    }
    
    public class ScreenSwapperButtonHandler implements Button.ButtonHandler {
        private String swapScreenName;

        public ScreenSwapperButtonHandler(String swapScreenName) {
            this.swapScreenName = swapScreenName;
        }

        public void handlePress() {
            switchToScreen(swapScreenName);
        }

        public void handleHover() {}
        public void handleUnpress() {}
    }

    public class BackButtonHandler implements Button.ButtonHandler {
        public void handlePress() {
            String screenName = screenStack.get(screenStack.size() - 2);
            screenStack.remove(screenStack.size() - 1);
            switchToScreen(screenName);
        }

        public void handleHover() {}
        public void handleUnpress() {}
    }
}
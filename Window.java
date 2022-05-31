import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    private JFrame frame;
    private JPanel cards;

    private String prevScreenName;

    private Timer drawLoop;

    public Window(String title, int width, int height) {
        this.frame = new JFrame(title);
        this.frame.getContentPane().setPreferredSize(new Dimension(width, height));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.cards = new JPanel(new CardLayout());

        this.prevScreenName = Const.MENU_SCREEN_NAME;

        this.drawLoop = new Timer(Const.FRAME_PERIOD, new ActionListener() {
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

        // Record the previously shown screen.
        Screen prevScreen = getShownScreen();
        this.prevScreenName = prevScreen.getName();

        // Switch screens.
        layout.show(this.cards, screenName);
        System.out.println("Switching: " + this.prevScreenName + " --> " + screenName);
    }

    public String getPrevScreenName() {
        return this.prevScreenName;
    }

    private Screen getShownScreen() {
        for (Component comp: this.cards.getComponents()) {
            if (comp.isShowing()) {
                return (Screen) comp;
            }
        }
        return null;
    }

    public int getFPS() {
        return 1000 / this.drawLoop.getDelay();
    }

    public void setFPS(int fps) {
        this.drawLoop.setDelay(Const.MS_PER_S / fps);
    }
    
    public class ScreenSwapperButton implements Button.ButtonHandler {
        private String swapScreenName;

        public ScreenSwapperButton(String swapScreenName) {
            this.swapScreenName = swapScreenName;
        }

        public void handlePress() {
            switchToScreen(swapScreenName);
        }

        public void handleHover() {}
        public void handleUnpress() {}
    }
}
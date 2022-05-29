import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

import java.awt.CardLayout;

public class Window {
    private JFrame frame;
    private JPanel cards;

    public Window(String title, int width, int height) {
        this.frame = new JFrame(title);
        this.frame.getContentPane().setPreferredSize(new Dimension(width, height));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.cards = new JPanel(new CardLayout());
    }

    public void start() {
        this.frame.add(cards);
        this.frame.setVisible(true);
        this.frame.pack();
    }

    public void draw() {
        this.frame.repaint();
    }

    public void add(Screen screen, String screenName) {
        this.cards.add(screen, screenName);
    }

    public void add(Screen screen) {
        this.cards.add(screen, screen.getName());
    }

    public void switchToScreen(String screenName) {
        CardLayout layout = (CardLayout) this.cards.getLayout();
        layout.show(this.cards, screenName);
        System.out.println("Switching to " + screenName);
    }

    public class ButtonScreenSwapper extends Button.ButtonHandler {
        private String swapScreenName;

        public ButtonScreenSwapper(String swapScreenName) {
            this.swapScreenName = swapScreenName;
        }

        public void press() {
            switchToScreen(swapScreenName);
        }
    }
}
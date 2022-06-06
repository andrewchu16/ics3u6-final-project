import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

/**
 * This class manages a running window application. It can store the states of multiple
 * screens and switch between them. One screen is always visible.
 * @see Screen 
 */
public class Window {
    private JFrame frame;
    private JPanel cards;

    private ArrayList<String> screenStack;
    private Timer drawLoop;

    /**
     * This constructs a Window object with a title, width, and height.
     * @param title The title of the window typically located on the top border of the window.
     * @param width The width of the usuable area of the window.
     * @param height The height of the usuable area of the window.
     */
    public Window(String title, int width, int height) {
        // Set up the basic attributes of the window.
        this.frame = new JFrame(title);
        this.frame.getContentPane().setPreferredSize(new Dimension(width, height));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        // Initialize the cards/screen manager.
        this.cards = new JPanel(new CardLayout());
        this.frame.add(cards);


        this.screenStack = new ArrayList<String>();

        this.drawLoop = new Timer(Const.DEFAULT_FRAME_PERIOD, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                draw();
            }
        });
    }

    /**
     * This method opens the window and sets it to be visible.
     */
    public void start() {
        this.frame.setVisible(true);
        this.frame.pack();
        this.drawLoop.start();
    }

    /**
     * This method redraws the entire window. This redraw occurs regularly by a Timer
     * at a fixed rate based on a constant.
     * @see javax.swing.Timer
     * @see Const
     */
    public void draw() {
        this.frame.repaint();
    }

    /**
     * THis method adds a screen to the window.
     * @param screen The screen object to add.
     */
    public void addScreen(Screen screen) {
        this.cards.add(screen, screen.getName());
    }

    /**
     * This method attempts to switch the active screen. If the screen does not
     * exist, it does nothing.
     * @param screenName The name of the screen.
     */
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

    /**
     * This method gets the frames-per-second set for the window.
     * @return The integer-rounded FPS of the window.
     */
    public int getFPS() {
        return 1000 / this.drawLoop.getDelay();
    }

    /**
     * This method set the frames-per-second for the window. Due to integer rounding
     * and computer performance, the actual FPS may differ.
     * @param fps The FPS the window should be set to.
     */
    public void setFPS(int fps) {
        this.drawLoop.setDelay(Const.MS_PER_S / fps);
    }
    
    /**
     * This class represents a handler for a button to switch to a specified screen when
     * pressed.
     * @see Button
     */
    public class ScreenSwapperButtonHandler implements Button.ButtonHandler {
        private String swapScreenName;

        /**
         * This constructs a new ScreenSwapperButtonHandler instance.
         * @param swapScreenName The screen to swap to on press.
         */
        public ScreenSwapperButtonHandler(String swapScreenName) {
            this.swapScreenName = swapScreenName;
        }

        public void handlePress() {
            switchToScreen(swapScreenName);
        }

        public void handleHover() {}
        public void handleUnpress() {}
    }

    /**
     * This class represents a handler for back button meant to switch to the previously
     * displayed screen in the window.
     * @see Button
     */
    public class BackButtonHandler implements Button.ButtonHandler {
        public void handlePress() {
            screenStack.remove(screenStack.size() - 1);
            String screenName = screenStack.get(screenStack.size() - 1);
            switchToScreen(screenName);
        }

        public void handleHover() {}
        public void handleUnpress() {}
    }
}
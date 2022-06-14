/**
 * This class represents a shared {@code Button} for going to the previous shown
 * screen. It has a particular size, font, and colour. These styles are constants.
 * @see Const
 */
public class BackButton extends Button {
    /**
     * This constructs a {@code BackButton} object.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param window The window this {@code BackButton} is placed in.
     */
    public BackButton(int x, int y, Window window) {
        super(x, y, 150, 60, "go back button", "Go Back", Const.BUTTON_FONT, 
                Const.DARK_BLUE, Const.BLUE);

        this.addHandler(window.new BackButtonHandler());
    }
}
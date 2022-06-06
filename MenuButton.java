/**
 * This class represents a shared {@code Button} for menus. It has a particular 
 * size, font, and colour. These styles are constants.
 * @see Const
 * @see Button
 * @see MenuScreen
 */
public class MenuButton extends Button {
    /**
     * This constructs a {@code MenuButton} object.
     * @param x The top-left x-coordinate.
     * @param y The top-left y-coordinate.
     * @param name The name of this {@code MenuButton}.
     * @param text The text string on this {@code MenuButton}.
     */
    public MenuButton(int x, int y, String name, String text) {
        super(x, y, 200, 70, name, text, Const.buttonFont, Const.DARK_BLUE, 
                Const.BLUE);
        
        // Resize the button if it is too small for the text.
        Text tmpText = new Text(text, Const.buttonFont, 0, 0);
        int newWidth = Math.max(this.getWidth(), tmpText.getWidth() + 20);
        int newHeight = Math.max(this.getHeight(), tmpText.getHeight());

        this.setWidth(newWidth);
        this.setHeight(newHeight);
    }
}
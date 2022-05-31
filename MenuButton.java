
public class MenuButton extends Button {
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
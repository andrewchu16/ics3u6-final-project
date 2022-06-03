
public class BackButton extends Button {
    public BackButton(int x, int y, Window window) {
        super(x, y, 150, 60, "go back button", "Go Back", Const.buttonFont, 
                Const.DARK_BLUE, Const.BLUE);

        this.addHandler(window.new BackButtonHandler());
    }

    public BackButton(int x, int y) {
        super(x, y, 150, 60, "go back button", "Go Back", Const.buttonFont,
                Const.DARK_BLUE, Const.BLUE);
    }
}
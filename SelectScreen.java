import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;

public class SelectScreen extends Screen {
    private Label titleLabel;

    private Rectangle centerRect;
    private Label nameLabel;
    private Label difficultyLabel;
    private Label currentDifficultyLabel;

    public SelectScreen(Window window, Game game) {
        super(Const.dayScreenBackground);

        this.setName(Const.SELECT_SCREEN_NAME);

        // Instantiate title.
        this.titleLabel = new Label(Const.WIDTH / 2 - 100, 45, 200, 80, "New Game", 
                Const.subtitleFont, Const.WHITE2);

        // Instantiate select center background rectangle.
        this.centerRect = new Rectangle(Const.WIDTH / 2 - 320, 160, 640, 500);

        // Instantiate button labels.
        this.nameLabel = new Label(Const.WIDTH / 2 - 260, 300, 200, 56, "Player Name", 
                Const.smallButtonFont, Const.WHITE);
        this.difficultyLabel = new Label(Const.WIDTH / 2 - 260, 420, 200, 56, "Difficulty", 
                Const.smallButtonFont, Const.WHITE);
        this.currentDifficultyLabel = new Label(Const.WIDTH / 2 + 100, 420, 100, 56, 
                Game.DIFFICULTY_STRINGS[game.getDifficulty()], Const.smallButtonFont, Const.WHITE);

        // Instantiate buttons and text field.
        Button goBackButton = new BackButton(30, 50, window);
        TextField nameTextField = new TextField(Const.WIDTH / 2 + 20, 300, 260, 56, 
                "name text field", game.getPlayer().getName(), Const.smallButtonFont, 
                Const.LIGHT_GRAY2, Const.WHITE, TextField.INCLUDE_ALPHA | TextField.INCLUDE_SPACE, 12);
        ArrowButton lowerDifficultyButton = new ArrowButton(Const.WIDTH / 2 + 40, 430,
                40, 36, Const.LEFT, "lower difficulty button", Const.GREEN2, Const.GREEN);
        ArrowButton raiseDifficultyButton = new ArrowButton(Const.WIDTH / 2 + 220, 430,
                40, 36, Const.RIGHT, "raise difficulty button", Const.GREEN2, Const.GREEN);
        Button startButton = new MenuButton(Const.WIDTH / 2 - 20, 570, "start button", "Start Game");


        // Add button press effects.
        nameTextField.addTextFieldHandler(new TextField.TextFieldHandler() {
            public void handleSubmit() {
                String name = nameTextField.getText();
                game.getPlayer().setName(name);
            }
        });
        lowerDifficultyButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                int newDifficulty = (game.getDifficulty() + Game.DIFFICULTY_STRINGS.length - 1) 
                        % Game.DIFFICULTY_STRINGS.length;
                        currentDifficultyLabel.setText(Game.DIFFICULTY_STRINGS[newDifficulty]);
                        game.setDifficulty(newDifficulty);
                    }
                    
            public void handleHover() {}
            public void handleUnpress() {}
        });
        raiseDifficultyButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                int newDifficulty = (game.getDifficulty() + 1) % Game.DIFFICULTY_STRINGS.length;
                currentDifficultyLabel.setText(Game.DIFFICULTY_STRINGS[newDifficulty]);
                game.setDifficulty(newDifficulty);
            }

            public void handleHover() {}
            public void handleUnpress() {}
        });
        startButton.addHandler(new Button.ButtonHandler() {
            public void handlePress() {
                System.out.println("Pressing start button");
            }

            public void handleHover() {}
            public void handleUnpress() {}
        });


        // Add buttons to screen.
        this.addButton(goBackButton);
        this.addButton(nameTextField);
        this.addButton(lowerDifficultyButton);
        this.addButton(raiseDifficultyButton);
        this.addButton(startButton);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        // Draw title.
        this.titleLabel.draw(graphics);

        // Draw center background rectangle.
        graphics.setColor(Const.LIGHT_BLUE1);
        ((Graphics2D) graphics).fill(this.centerRect);
        graphics.setColor(Const.BLACK);
        ((Graphics2D) graphics).setStroke(new BasicStroke(4));
        ((Graphics2D) graphics).draw(this.centerRect);

        // Draw button labels.
        this.nameLabel.draw(graphics);
        this.difficultyLabel.draw(graphics);
        this.currentDifficultyLabel.draw(graphics);

        // Draw buttons.
        this.drawButtons(graphics);
    }
}

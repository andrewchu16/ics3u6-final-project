import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

/**
 * This class represents a field of text on a {@code Screen}. When focused, it can
 * add or delete stored characters. The {@code Text} inside of it is centered. 
 * @see Screen
 */
public class TextField extends Button {
    private int filter;
    private boolean active;
    private int maxChars;

    private Color focusedColor;
    private Color unfocusedColor;

    private ArrayList<TextFieldHandler> textFieldHandlers;

    // Filters for valid input of the text field.
    public static final int INCLUDE_DIGITS = 0b0001;
    public static final int INCLUDE_UPPER  = 0b0010;
    public static final int INCLUDE_LOWER  = 0b0100;
    public static final int INCLUDE_SPACE  = 0b1000;
    public static final int INCLUDE_ALPHA  = 0b0110;
    public static final int INCLUDE_ALL    = 0b1111; 

    public static final int NO_MAX_CHARS = -1;

    /**
     * This constructs a {@code TextField} object.
     * @param x The top-left x-coordinate of this {@code TextField}.
     * @param y The top-left y-coordinate of this {@code TextField}.
     * @param width The width of this {@code TextField}.
     * @param height The height of this {@code TextField}.
     * @param name The name of this {@code TextField}.
     * @param startText The starting {@code Text} of this {@code TextField}.
     * @param font The font of the {@code Text} of this {@code TextField}.
     * @param focusedColor The color of this {@code TextField} when it is focused.
     * @param unfocusedColor The color of this {@code TextField} when it is unfocused.
     * @param filter The filter on inputted characters.
     */
    public TextField(int x, int y, int width, int height, String name, String startText, 
                Font font, Color focusedColor, Color unfocusedColor, int filter) {
        super(x, y, width, height, name, startText, font, focusedColor, unfocusedColor);

        this.focusedColor = focusedColor;
        this.unfocusedColor = unfocusedColor;
        this.filter = filter;
        this.active = false;
        this.maxChars = NO_MAX_CHARS;

        this.textFieldHandlers = new ArrayList<TextFieldHandler>();
    }

    /**
     * This constructs a {@code TextField} object.
     * @param x The top-left x-coordinate of this {@code TextField}.
     * @param y The top-left y-coordinate of this {@code TextField}.
     * @param width The width of this {@code TextField}.
     * @param height The height of this {@code TextField}.
     * @param name The name of this {@code TextField}.
     * @param startText The starting {@code Text} of this {@code TextField}.
     * @param font The font of the {@code Text} of this {@code TextField}.
     * @param focusedColor The color of this {@code TextField} when it is focused.
     * @param unfocusedColor The color of this {@code TextField} when it is unfocused.
     * @param filter The filter on inputted characters.
     * @param maxChars The number of characters that can be entered into this {@code TextField} at once.
     */
    public TextField(int x, int y, int width, int height, String name, String startText, 
                Font font, Color focusedColor, Color unfocusedColor, int filter,
                int maxChars) {
        this(x, y, width, height, name, startText, font, focusedColor, unfocusedColor, filter);

        this.maxChars = Math.max(NO_MAX_CHARS, maxChars);
    }

    /**
     * This method adds a character to the end of this {@code TextField}, if it 
     * passes the set filters and there is space for it to be added.
     * @param chr The character to be added.
     */
    public void addChar(char chr) {
        // Check if the character to be added is valid.
        boolean valid = this.valid(Character.toString(chr));

        // Check if there is a limit to the text length.
        if (maxChars >= 0) {
            valid = (valid && this.getText().length() < maxChars);
        }

        if (valid) {
            String newText = this.getText() + chr;
            this.setText(newText);
        }
    }
    
    /**
     * THis method deletes a character from the end of this {@code TextField}. If
     * there are no characters to delete, it does nothing.
     */
    public void deleteChar() {
        String newText = this.getText();
        if (newText.length() > 0) {
            newText = newText.substring(0, newText.length() - 1);
            this.setText(newText);
        }
    }

    /**
     * This method checks if a string passes the filters for this {@code TextField}.
     * @param str The string to check.
     * @return {@code true} if it is valid, {@code false} otherwise.
     */
    public boolean valid(String str) {
        boolean strValid = true;

        // Check if each character is an allowed input.
        for (int i = 0; i < str.length() && strValid; i++) {
            boolean charValid = (this.filter == INCLUDE_ALL);

            char chr = str.charAt(i);
            if ((this.filter & INCLUDE_DIGITS) != 0) {
                charValid = (charValid || Character.isDigit(chr));
            } 
            if ((this.filter & INCLUDE_UPPER) != 0) {
                charValid = (charValid || Character.isUpperCase(chr));
            }
            if ((this.filter & INCLUDE_LOWER) != 0) {
                charValid = (charValid || Character.isLowerCase(chr));
            }
            if ((this.filter & INCLUDE_SPACE) != 0) {
                charValid = (charValid || Character.isSpaceChar(chr));
            }

            strValid = (strValid && charValid);
        }

        return strValid;
    }

    /**
     * This method adds a handler to the {@code TextField}.
     * @param handler The {@code TextFieldHandler} to be added.
     * @see TextField.TextFieldHandler
     */
    public void addTextFieldHandler(TextFieldHandler handler) {
        this.textFieldHandlers.add(handler);
    }

    /**
     * This class represents a {@code MouseListener} for triggering this
     * {@code TextField}'s behaviour. It is necessary to allow this {@code TextField}
     * to focus and accept input. It should be added to the {@code Screen} this
     * {@code TextField} is placed on.
     * @see Button.ButtonMouseListener
     * @see Screen#addButton
     */
    public class TextFieldMouseListener extends ButtonMouseListener {
        public void mouseClicked(MouseEvent event) {
            super.mouseClicked(event);

            int mouseX = event.getX();
            int mouseY = event.getY();

            if (getHitbox().contains(mouseX, mouseY)) {
                setUnpressedColor(focusedColor);
                setActiveColor(focusedColor);
                active = true;
            } else {
                setUnpressedColor(unfocusedColor);
                setActiveColor(unfocusedColor);
                active = false;
            }
        }
    }

    /**
     * This class represents a {@code KeyListener} for entering text into this
     * {@code TextField}. It should be added the {@code Screen} this {@code TextField}
     * is placed on.
     * @see Screen#addButton(Button)
     */
    public class TextFieldKeyListener implements KeyListener {
        public void keyPressed(KeyEvent event) {
            if (!active) {
                return;
            }

            int keyCode = event.getKeyCode();

            if (keyCode == KeyEvent.VK_BACK_SPACE) {
                deleteChar();
            } else if (keyCode == KeyEvent.VK_ENTER) {
                for (TextFieldHandler textFieldHandler: textFieldHandlers) {
                    textFieldHandler.handleSubmit();
                }
            }
        }

        public void keyTyped(KeyEvent event) {
            if (!active) {
                return;
            }

            char chr = event.getKeyChar();
            addChar(chr);
        }

        public void keyReleased(KeyEvent evnet) {}
    }

    /**
     * This interface contains the behaviour that a {@code TextField}
     * can respond to. The behaviour is submitting the text stored inside it.
     */
    public interface TextFieldHandler {
        public void handleSubmit();
    }
}

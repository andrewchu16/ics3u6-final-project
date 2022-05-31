import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

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

    public TextField(int x, int y, int width, int height, String name, String startText, 
                Font font, Color focusedColor, Color unfocusedColor, int filter,
                int maxChars) {
        this(x, y, width, height, name, startText, font, focusedColor, unfocusedColor, filter);

        this.maxChars = Math.max(NO_MAX_CHARS, maxChars);
    }

    public void addChar(char chr) {
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

    public void deleteChar() {
        String newText = this.getText();
        if (newText.length() > 0) {
            newText = newText.substring(0, newText.length() - 1);
            this.setText(newText);
        }
    }

    public void addTextFieldHandler(TextFieldHandler handler) {
        this.textFieldHandlers.add(handler);
    }

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

    public interface TextFieldHandler {
        public void handleSubmit();
    }
}

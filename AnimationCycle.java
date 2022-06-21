import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import java.io.FileReader;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is used for easily combining animations with hitboxes. Animations
 * can be read from a custom animation file. An {@code AnimationCycle} is made 
 * of {@code AnimationFrame}s and a general hitbox that describes the general
 * area that hitboxes in the {@code AnimationFrame}s will fall in.
 * @see AnimationFrame
 */
public class AnimationCycle implements Drawable, Debuggable, Collidable {
    // Different looping types
    public static final int NO_LOOPING = 0;
    public static final int LOOP_TO_START = 1;
    public static final int LOOP_BACKWARDS = 2;
    private static final int FIRST_LOOP_TYPE = 0;
    private static final int LAST_LOOP_TYPE = 2;
    
    private AnimationFrame[] frames;
    private AnimationFrame activeFrame;
    private int loopType;

    private int indexDir;
    private int curIndex;
    private int numFrames;
    private Vector position;
    private int frameWidth;
    private int frameHeight;
    private RelativeHitbox generalHitbox;

    /**
     * This constructs an {@code AnimationCycle} object out of of a sprite sheet.
     * @param position The top-left coordinate of the {@code AnimationCycle} object.
     * @param picSheet The sprite sheet to use. Each frame should be stacked vertically up to down.
     * @param numFrames The number of frames in the sprite sheet.
     * @param loopType The way to handle the cycle once it is finished. The cycle can stop, loop to start, or loop backwards.
     */
    public AnimationCycle(Vector position, BufferedImage picSheet, int numFrames, int loopType) {
        this.position = position;
        this.frames = new AnimationFrame[numFrames];

        // Calculate the dimensions of the frames.
        this.numFrames = numFrames;
        this.frameWidth = picSheet.getWidth();
        this.frameHeight = picSheet.getHeight() / numFrames;

        // Create the individual {@code AnimationFrame} objects.
        for (int i = 0; i < numFrames; i++) {
            BufferedImage subImage = picSheet.getSubimage(0, i * frameHeight, 
                    frameWidth, frameHeight);
            this.frames[i] = new AnimationFrame(position, subImage);
        }

        this.setLooping(loopType);
        this.indexDir = 1;
        this.setActiveFrame(0);

        this.generalHitbox = new RelativeHitbox(position, Vector.VECTOR_ZERO.clone(), 
                this.frameWidth, this.frameHeight);
    }

    /**
     * This constructs an {@code AnimationCycle} object out of a sprite sheet.
     * @param position The top-left coordinate of the {@code AnimationCycle} object.
     * @param picSheet The sprite sheet to use. Each frame should be stacked vertically up to down.
     * @param frameWidth The width of each frame.
     * @param frameHeight The height of each frame.
     * @param loopType The way to handle the cycle once it is finished. The cycle can stop, loop to start, or loop backwards.
     */
    public AnimationCycle(Vector position, BufferedImage picSheet, int frameWidth, int frameHeight,
            int loopType) {
        this.position = position;
        this.frames = new AnimationFrame[this.numFrames];
        
        // Calculate the number of frames.
        this.numFrames = picSheet.getHeight() / frameHeight;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        // Create the individual {@code AnimationFrame} objects.
        for (int i = 0; i < numFrames; i++) {
            BufferedImage subImage = picSheet.getSubimage(0, i * frameHeight, 
                    frameWidth, frameHeight);
            this.frames[i] = new AnimationFrame(position, subImage);
        }

        this.setLooping(loopType);
        this.indexDir = 1;
        this.setActiveFrame(0);

        this.generalHitbox = new RelativeHitbox(position, Vector.VECTOR_ZERO.clone(), 
                this.frameWidth, this.frameHeight);
    }

    /**
     * This constructs an {@code AnimationCycle} object from file. This includes 
     * information relating to the {@code Hitbox}es of each frame.
     * @param position The top-left anchor coordinate of this {@code AnimationCycle}.
     * @param picSheet The image sheet with each frame in the animation.
     * @param animationFileName The file containing the information for this {@code AnimationCycle}.
     * @see AnimationCycle#loadFromFile
     */
    public AnimationCycle(Vector position, BufferedImage picSheet, String animationFileName) {
        this.position = position;
        this.loadFromFile(picSheet, animationFileName);

        this.indexDir = 1;
        this.setActiveFrame(0);
        this.generalHitbox.setColor(Const.GREEN);
    }

    /**
     * This method loads the animation cycle from a file.
     * <ul>
     * <li> Frame numbers start from {@code 0}.
     * <li> All labels should be kept.</li>
     * <li> Spacing should be kept.</li>
     * <li> {@code hitboxName} can be changed with the name of any hitbox as long as it has no spaces.</li>
     * <li> {@code x} and {@code y} can be of type {@code double}.</li>
     * <li> {@code n}, {@code width}, and {@code height} should be of type {@code int}.</li>
     * </ul>
     * <p>The file should be formatted as follows with values filled in.</p>
     * <pre>{@code
     *loopType: NO_LOOPING/LOOP_TO_START/LOOP_BACKWARDS
     *generalHitbox: x y width height
     *numFrames: n
     *FRAME0
     *numHitboxes: n
     *hitboxName: x y width height
     *hitboxName: x y width height
     *hitboxName: x y width height
     *FRAME1
     *numHitboxes: n
     *hitboxName: x y width height
     *hitboxName: x y width height
     *FRAME2
     *numHitboxes: n
     *hitboxName: x y width height
     *hitboxName: x y width height
     * }</pre>
     * @param picSheet
     * @param animationFileName
     */
    public void loadFromFile(BufferedImage picSheet, String animationFileName) {
        BufferedReader input;
        // Open animation cycle file.
        try {
            FileReader animationFile = new FileReader(animationFileName);
            input = new BufferedReader(animationFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Error: Animation file not found. [" + animationFileName + "]");
            this.numFrames = 0;
            return;
        }

        // Load general animation cycle information.
        try {
            // Get the looping type.
            String loopTypeString = input.readLine().split(" ")[1];
            if (loopTypeString.equals("NO_LOOPING")) {
                this.setLooping(NO_LOOPING);
            } else if (loopTypeString.equals("LOOP_TO_START")) {
                this.setLooping(LOOP_TO_START);
            } else if (loopTypeString.equals("LOOP_BACKWARDS")) {
                this.setLooping(LOOP_BACKWARDS);
            } else {
                System.out.println("Invalid loop type: [" + loopTypeString + "]");
                this.setLooping(NO_LOOPING);
            }

            // Get the general hitbox.
            String[] generalHitboxData = input.readLine().split(" ");
            Vector relativePosition = new Vector(Double.parseDouble(generalHitboxData[1]), 
                    Double.parseDouble(generalHitboxData[2]));
            int width = Integer.parseInt(generalHitboxData[3]);
            int height = Integer.parseInt(generalHitboxData[4]);
            this.generalHitbox = new RelativeHitbox(this.position, relativePosition, width, height);

            this.numFrames = Integer.parseInt(input.readLine().split(" ")[1]);
        } catch (IOException ex) {
            System.out.println("Error: Could not read animation file (general information).");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Incorrect animation file general information format (expected numerical value).");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Error: Incorrect animation file general information format (incorrect number of values).");
        }

        // Calculate the dimensions of the animation frames.
        this.frames = new AnimationFrame[this.numFrames];
        this.frameWidth = picSheet.getWidth();
        this.frameHeight = picSheet.getHeight() / this.numFrames;
        
        // Create the animation frames.
        try {
            for (int i = 0; i < this.numFrames; i++) {
                int frameIndex = Integer.parseInt(input.readLine().substring("FRAME".length()));
                int numHitboxes = Integer.parseInt(input.readLine().split(" ")[1]);
                
                // Load the current frame's hitboxes.
                ArrayList<RelativeHitbox> hitboxes = new ArrayList<RelativeHitbox>();
                for (int j = 0; j < numHitboxes; j++) {
                    String[] hitboxData = input.readLine().split(" ");
                    Vector relativePosition = new Vector(Double.parseDouble(hitboxData[1]), 
                            Double.parseDouble(hitboxData[2]));
                    int width = Integer.parseInt(hitboxData[3]);
                    int height = Integer.parseInt(hitboxData[4]);
                    RelativeHitbox hitbox = new RelativeHitbox(this.position, relativePosition, width, height);
                    hitboxes.add(hitbox);
                }

                this.frames[frameIndex] = new AnimationFrame(this.position, 
                        picSheet.getSubimage(0, frameIndex * frameHeight, frameWidth, 
                        frameHeight), hitboxes);
            }
        } catch (IOException ex) {
            System.out.println("Error: Could not read animation file (animation frames).");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Incorrect animation file frames (expected numerical value).");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Error: Incorrect animation file frames (incorrect number of values).");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Error: Incorrect animation file frames (incorrect frame number label).");
        }

        // Check if all frames were properly loaded.
        boolean allFramesLoaded = true;
        for (int i = 0; i < this.numFrames; i++) {
            if (this.frames[i] == null) {
                System.out.println("Error: frame " + i + " not loaded.");
                allFramesLoaded = false;
            }
        }

        if (!allFramesLoaded) {
            System.out.println("Error: Incomplete animation file. [" + animationFileName + "]");
        }

        // Close animation cycle file.
        try {
            input.close();
        } catch (IOException ex) {
            System.out.println("Error: Animation file cannot be closed.");
        }
    }

    /**
     * This method restarts this {@code AnimationCycle} from the beginning. If the 
     * loop was playing backwards, it resets to playing forwards.
     */
    public void reset() {
        this.setActiveFrame(0);
        this.indexDir = 1;
    }

    /**
     * This method checks if this {@code AnimationCycle} has reached the last frame.
     * @return {@code true} if it reached the last frame, {@code false} otherwise.
     * If this cycle is looping, this method will always return {@code false}.
     */
    public boolean checkDone() {
        return (this.loopType == NO_LOOPING && this.curIndex == this.numFrames - 1);
    }
    
    public Vector getPos() {
        return this.position.clone();
    }
    
    public int getFrameWidth() {
        return this.frameWidth;
    }
    
    public int getFrameHeight() {
        return this.frameHeight;
    }

    /**
     * This method returns a reference to the general hitbox of this {@code AnimationCycle}.
     * @return A {@code RelativeHitbox} object.
     */
    public Hitbox getGeneralHitbox() {
        return this.generalHitbox;
    }

    /**
     * This method returns a reference to the active frame of this {@code AnimationCycle} 
     * when this method is called.
     * @return A reference to a {@code AnimationFrame} object.
     */
    public AnimationFrame getActiveFrame() {
        return this.activeFrame;
    }
    
    public void setPos(Vector newPos) {
        this.position = newPos;
        for (AnimationFrame frame: this.frames) {
            frame.setPos(newPos);
        }
        this.generalHitbox.setAnchorPos(newPos);
    }

    /**
     * THis method sets the looping behaviour of this {@code AnimationCycle}. If
     * the new loop type is invalid, it defaults to no looping.
     * @param newLoopType The integer representing the new looping type.
     */
    public void setLooping(int newLoopType) {
        if (FIRST_LOOP_TYPE <= newLoopType && newLoopType <= LAST_LOOP_TYPE) {
            this.loopType = newLoopType;
        } else {
            this.loopType = NO_LOOPING;
        }
    }
    
    /**
     * This method sets the active or visible frame based on its index in the cycle. 
     * The modulo of the given index by the number of frames is used if the index 
     * is too large.
     * @param index The index of the frame to be set as active.
     */
    public void setActiveFrame(int index) {
        this.curIndex = index % this.numFrames;
        this.activeFrame = this.frames[this.curIndex];
    }

    /**
     * This method loads the next active frame. This follows its looping type.
     */
    public void loadNextFrame() {
        if (this.checkDone()) {
            return;
        }
        
        this.curIndex += this.indexDir;
        this.setActiveFrame(this.curIndex);

        if (this.loopType == LOOP_BACKWARDS) {
            if (this.curIndex == 0) {
                this.indexDir = 1;
            } else if (this.curIndex == this.numFrames - 1) {
                this.indexDir = -1;
            }
        }
    }

    /**
     * This method determines whether a coordinate is within the hitboxes of the
     * current active {@code AnimationFrame}.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.activeFrame.contains(x, y);
    }

    /**
     * THis method determines whether a hitbox intersects with the hitboxes of the
     * current active {@code AnimationFrame}.
     */
    @Override
    public boolean intersects(Hitbox other) {
        return this.activeFrame.intersects(other);
    }

    /**
     * This method determines whether another {@code AnimationCycle} intersects with
     * the current active {@code AnimationFrame}.
     * @param otherCycle The other {@code AnimationCycle} to check.
     * @return
     */
    public boolean intersects(AnimationCycle otherCycle) {
        return this.activeFrame.intersects(otherCycle.getActiveFrame());
    }
    
    /**
     * This method draws the active frame onto a surface.
     */
    @Override
    public void draw(Graphics graphics) {
        this.activeFrame.draw(graphics);
    }

    /**
     * This method draws the hitboxes of the active frame onto a surface.
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.generalHitbox.drawDebugInfo(graphics);
        this.activeFrame.drawDebugInfo(graphics);
    }

    /**
     * This method reflects the sprites and hitboxes of this {@code AnimationCycle}
     * over the middle of the general hitbox.
     */
    public void reflectHorizontally() {
        int xLine = this.generalHitbox.getX() + this.generalHitbox.getWidth() / 2;
        for (AnimationFrame frame: this.frames) {
            frame.reflectHorizontally(xLine);
        }
    }
}

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AnimationCycle implements Drawable, Debuggable {
    private AnimationFrame[] frames;
    private AnimationFrame activeFrame;
    private boolean loopBackwards;
    private boolean isLooping;

    private int indexDir;
    private int curIndex;
    private int numFrames;
    private Vector position;
    private int frameWidth;
    private int frameHeight;

    public AnimationCycle(Vector position, BufferedImage picSheet, int numFrames, boolean loopBackwards) {
        this.position = position;
        this.frames = new AnimationFrame[numFrames];

        this.numFrames = numFrames;
        this.frameWidth = picSheet.getWidth() / numFrames;
        this.frameHeight = picSheet.getHeight();

        for (int i = 0; i < numFrames; i++) {
            BufferedImage subImage = picSheet.getSubimage(i*frameWidth, 0, 
                    frameWidth, frameHeight);
            this.frames[i] = new AnimationFrame(position, subImage);
        }

        this.loopBackwards = loopBackwards;
        this.isLooping = false;
        this.indexDir = 1;
        this.setActiveFrame(0);
    }

    public AnimationCycle(Vector position, BufferedImage picSheet, int frameWidth, int frameHeight,
            boolean loopBackwards) {
        this.position = position;
        this.frames = new AnimationFrame[this.numFrames];
        
        this.numFrames = picSheet.getWidth() / frameWidth;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        for (int i = 0; i < numFrames; i++) {
            BufferedImage subImage = picSheet.getSubimage(i*frameWidth, 0, 
                    frameWidth, frameHeight);
            this.frames[i] = new AnimationFrame(position, subImage);
        }

        this.loopBackwards = loopBackwards;
        this.isLooping = false;
        this.indexDir = 1;
        this.setActiveFrame(0);
    }

    public void reset() {
        this.setActiveFrame(0);
        this.indexDir = 1;
    }

    public boolean checkDone() {
        return (!this.isLooping && this.curIndex == this.numFrames - 1);
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
    
    public void setPos(Vector newPos) {
        this.position = newPos;
        for (AnimationFrame frame: this.frames) {
            frame.setPos(newPos);
        }
    }

    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }
    
    public void setActiveFrame(int index) {
        this.curIndex = index % this.numFrames;
        this.activeFrame = this.frames[this.curIndex];
    }

    public void loadNextFrame() {
        if (!this.isLooping && this.curIndex == this.numFrames - 1) {
            return;
        }
        
        this.curIndex += this.indexDir;
        this.setActiveFrame(this.curIndex);

        if (this.loopBackwards) {
            if (this.curIndex == 0) {
                this.indexDir = 1;
            } else if (this.curIndex == this.numFrames - 1) {
                this.indexDir = -1;
            }
        }
    }
    
    @Override
    public void draw(Graphics graphics) {
        this.activeFrame.draw(graphics);
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.activeFrame.drawDebugInfo(graphics);
    }
}

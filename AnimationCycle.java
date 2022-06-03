import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AnimationCycle implements Drawable, Debuggable {
    private AnimationFrame[] frames;
    private AnimationFrame activeFrame;

    private int curIndex;
    private int numFrames;
    private Vector position;
    private int frameWidth;
    private int frameHeight;

    public AnimationCycle(Vector position, BufferedImage picSheet, int numFrames) {
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

        this.setActiveFrame(0);
    }

    public AnimationCycle(Vector position, BufferedImage picSheet, int frameWidth, int frameHeight) {
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

        this.setActiveFrame(0);
    }

    public Vector getPos() {
        return this.position;
    }

    public void setPos(Vector newPos) {
        this.position = newPos;
        for (AnimationFrame frame: this.frames) {
            frame.setPos(newPos);
        }
    }

    public void setActiveFrame(int index) {
        this.curIndex = index % this.numFrames;
        this.activeFrame = this.frames[this.curIndex];
    }

    public void loadNextFrame() {
        this.setActiveFrame(this.curIndex + 1);
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

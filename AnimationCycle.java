import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AnimationCycle implements Drawable, Debuggable {
    private AnimationFrame[] frames;
    private AnimationFrame activeFrame;

    private int curIndex;
    private int numFrames;

    public AnimationCycle(BufferedImage picSheet, int numFrames) {
        this.frames = new AnimationFrame[numFrames];

        int frameWidth = picSheet.getWidth();
        int frameHeight = picSheet.getHeight() / numFrames;

        for (int i = 0; i < numFrames; i++) {
            BufferedImage subImage = picSheet.getSubimage(0, i * frameHeight, 
                    frameWidth, frameHeight);
            this.frames[i] = new AnimationFrame(0, 0, subImage);
        }

        this.numFrames = numFrames;
        this.setActiveFrame(0);
    }

    public void setPos(Vector newPos) {
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

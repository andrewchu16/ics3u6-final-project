import java.awt.Graphics;

public class Player extends Entity implements Moveable {
    private AnimationCycle animationCycle;
    private int width;
    private int height;

    public Player() {
        super(0, 0, "Leto");
        this.animationCycle = new AnimationCycle(this.getPos(), Const.playerSpriteSheet, 4);
    }

    @Override
    public void draw(Graphics graphics) {
        this.animationCycle.draw(graphics);
    }

    public void update() {

    }

    public void animate() {
        this.animationCycle.loadNextFrame();
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        this.animationCycle.drawDebugInfo(graphics);
    }

    @Override
    public void moveHorizontal(double units) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveVertical(double units) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setWidth(int newWidth) {

    }

    @Override
    public void setHeight(int newHeight) {
        // TODO Auto-generated method stub
        
    }
}

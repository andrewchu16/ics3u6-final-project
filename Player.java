import java.awt.Graphics;

public class Player extends Entity implements Moveable {
    private AnimationCycle animationCycle;

    public Player() {
        super();
        this.animationCycle = new AnimationCycle(Const.playerSpriteSheet, 32);
    }

    public String getName() {
        return "Leto";
    }

    public void setName(String name) {

    }

    @Override
    public void draw(Graphics graphics) {
        this.animationCycle.draw(graphics);
    }

    public void update() {
        this.animationCycle.loadNextFrame();
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {

    }

    @Override
    public void moveHorizontal(double units) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveVertical(double units) {
        // TODO Auto-generated method stub
        
    }
}

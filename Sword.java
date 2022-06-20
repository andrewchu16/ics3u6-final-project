import java.awt.Graphics;

public class Sword extends Entity implements Collidable {

    public Sword(Vector position) {
        super(position);
    }

    @Override
    public void draw(Graphics graphics) {
        return;
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean contains(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean intersects(Hitbox other) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCenterX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCenterY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector getCenter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Hitbox getGeneralHitbox() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

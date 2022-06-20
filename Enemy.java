import java.awt.Graphics;

public class Enemy extends Entity implements Moveable, Collidable {
    public Enemy(Vector position) {
        super(position);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void draw(Graphics graphics) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveUp() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveLeft() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveDown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void moveRight() {
        // TODO Auto-generated method stub
        
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
    
}

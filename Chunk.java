import java.awt.Graphics;

import java.io.FileReader;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

/**
 * This class represents a small part of the {@code Map}. A {@code Chunk} consists
 * of a 2-dimensional grid of tiles. This class allows for easier organization and
 * collision detection. A {@code Chunk} can be read from a custom chunk file.
 * @see Map
 * @see Tile
 */
public class Chunk implements Drawable, Debuggable, Collidable {
    // Number of tiles horizontally and vertically. (Eg, 16x16)
    public static final int LENGTH = 16;

    private String fileName;
    private Hitbox hitbox;
    private Tile[][] tiles;
    private ArrayList<Tile> solidTiles;
    private Vector mapPosition;

    /**
     * This constructs a {@code Chunk} object without loading the tiles or position.
     */
    public Chunk(String chunkFileName) {
        this.fileName = chunkFileName;
        this.hitbox = null;
        this.tiles = new Tile[LENGTH][LENGTH];
        this.mapPosition = null;
    }

    /**
     * This method loads the chunk data for this {@code Chunk} from a file. 
     * <ul>
     * <li> {@code x} and {@code y} are of type {@code int}.</li>
     * <li> The grid must be the correct dimensions.</li>
     * <li> The grid must be filled in using correct tile types.</li>
     * </ul>
     * <p>This is an example file filled in.</p>
     * <pre>{@code
     * -1
     * 4
     * ......
     * ...R..
     * ..RRR.
     * .RR...
     * .RR...
     * ......
     * }</pre>
     * @see Tile
     */
    public void loadFromFile() {
        // Open chunk file.
        BufferedReader input;
        try {
            FileReader chunkFile = new FileReader(this.fileName);
            input = new BufferedReader(chunkFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Error: Chunk file not found. [" + this.fileName + "]");
            return;
        }

        // Load chunk.
        try {
            this.mapPosition = new Vector(Integer.parseInt(input.readLine()), 
                    Integer.parseInt(input.readLine()));
            this.hitbox = new Hitbox(this.getPos(), LENGTH * Tile.LENGTH, LENGTH * Tile.LENGTH);
            this.hitbox.setColor(Const.GREEN);
            Vector offset = Map.calculateRealPosition(this.mapPosition);
            
            for (int y = 0; y < LENGTH; y++) {
                String line = input.readLine();
                for (int x = 0; x < LENGTH; x++) {
                    Vector tilePosition = new Vector(offset.getX() + x * Tile.LENGTH,
                            offset.getY() + y * Tile.LENGTH);
                    char tileType = line.charAt(x);
                    this.tiles[y][x] = new Tile(tilePosition, tileType);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: Could not read chunk file.");
        }

        // Close chunk file.
        try {
            input.close();
        } catch (IOException ex) {
            System.out.println("Error: Chunk file cannot be closed.");
        }
    }

    public int getX() {
        return (int) this.mapPosition.getX() * LENGTH;
    }

    public int getY() {
        return (int) this.mapPosition.getY() * LENGTH;
    }

    /**
     * This method creates a copy of the real position of this {@code Chunk}, 
     * as opposed to the map position. 
     * @return The top-left coordinate of this {@code Chunk}.
     * @see Chunk#getMapPos()
     */
    public Vector getPos() {
        return new Vector(this.mapPosition.getX() * LENGTH * Tile.LENGTH, 
                this.mapPosition.getY() * LENGTH * Tile.LENGTH);
    }

    /**
     * This method creates a copy of the map position of this {@code Chunk}.
     * @return A copy of the map position of this {@code Chunk}.
     * @see Map#calculateMapPosition(Vector)
     */
    public Vector getMapPos() {
        return this.mapPosition.clone();
    }

    /**
     * This method gets all the solid {@code Tile} objects in this {@code Chunk}.
     * @return An {@code ArrayList} object containg all the solid {@code Tile}s.
     */
    public ArrayList<Tile> getSolidTiles() {
        // Check if the solid tiles have already been calculated.
        if (this.solidTiles != null) {
            return this.solidTiles;
        }

        this.solidTiles = new ArrayList<Tile>();

        // Check if each tile is solid or not.
        for (int y = 0; y < LENGTH; y++) {
            for (int x = 0; x < LENGTH; x++) {
                if (this.tiles[y][x].checkSolid()) {
                    this.solidTiles.add(this.tiles[y][x]);
                }
            }
        }

        return this.solidTiles;
    }

    /**
     * This method determines whether a hitbox intersects with a solid tile in this
     * {@code Chunk}.
     * @param other The hitbox to check.
     * @return {@code true} if they intersect, {@code false} otherwise.
     */
    public boolean intersectsWithSolid(Hitbox other) {
        this.getSolidTiles();

        for (Tile tile: this.solidTiles) {
            if (tile.intersects(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method deteremines whether a coordinate is contained within this {@code Chunk}.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.hitbox.contains(x, y);
    }

    /**
     * This method determines whether a hitbox intersects with this {@code Chunk}.
     */
    @Override
    public boolean intersects(Hitbox other) {
        return this.hitbox.intersects(other);
    }
    
    /**
     * This method draws the tiles onto a surface.
     */
    @Override
    public void draw(Graphics graphics) {
        for (Tile[] tileRow: this.tiles) {
            for (Tile tile: tileRow) {
                tile.draw(graphics);
            }
        }
    }

    /**
     * This method draws the hitboxes of solid tiles within this {@code Chunk} onto a surface.
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        for (Tile[] tileRow: this.tiles) {
            for (Tile tile: tileRow) {
                if (tile.checkSolid()) {
                    tile.drawDebugInfo(graphics);
                }
            }
        }
        this.hitbox.drawDebugInfo(graphics);
    }

    /**
     * This method returns a string representation of this {@code Chunk} in the 
     * format "Chunk (x, y)" where (x, y) is this {@code Chunk}'s map position.
     */
    @Override
    public String toString() {
        return "Chunk " + this.mapPosition; 
    }
}
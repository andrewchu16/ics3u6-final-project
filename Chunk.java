import java.awt.Graphics;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Chunk implements Drawable, Debuggable, Collidable {
    // Number of tiles horizontally and vertically. (Eg, 16x16)
    public static final int LENGTH = 16;

    private String fileName;
    private Hitbox hitbox;
    private Tile[][] tiles;
    private Vector mapPosition;

    public Chunk() {
        this.fileName = "";
        this.hitbox = null;
        this.tiles = new Tile[LENGTH][LENGTH];
        this.mapPosition = null;
    }

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
            int ofsX = (int) this.mapPosition.getX() * LENGTH * Tile.LENGTH;
            int ofsY = (int) this.mapPosition.getY() * LENGTH * Tile.LENGTH;
            
            for (int y = 0; y < LENGTH; y++) {
                String line = input.readLine();
                for (int x = 0; x < LENGTH; x++) {
                    Vector tilePosition = new Vector(ofsX + x * Tile.LENGTH,
                            ofsY + y * Tile.LENGTH);
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

    public Vector getPos() {
        return new Vector(this.mapPosition.getX() * LENGTH, 
                this.mapPosition.getY() * LENGTH);
    }

    public Vector getMapPos() {
        return this.mapPosition.clone();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getMapDistanceFromReal(Vector realPosition) {
        Vector mapPosition = Map.calculateMapPosition(realPosition);

        return this.getMapDistanceFrom(mapPosition);
    }

    public int getMapDistanceFrom(Vector mapPosition) {
        int deltaX = (int) Math.abs(mapPosition.getX() - this.mapPosition.getX());
        int deltaY = (int) Math.abs(mapPosition.getY() - this.mapPosition.getY());

        int distance = deltaX + deltaY;
        return distance;
    }

    @Override
    public boolean contains(int x, int y) {
        return this.hitbox.contains(x, y);
    }

    @Override
    public boolean intersects(Hitbox other) {
        return this.hitbox.intersects(other);
    }
    
    @Override
    public void draw(Graphics graphics) {
        for (Tile[] tileRow: this.tiles) {
            for (Tile tile: tileRow) {
                tile.draw(graphics);
            }
        }
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        for (Tile[] tileRow: this.tiles) {
            for (Tile tile: tileRow) {
                tile.drawDebugInfo(graphics);
            }
        }
    }
}

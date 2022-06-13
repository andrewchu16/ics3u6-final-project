import java.awt.Graphics;

import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.Iterator;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Map implements Drawable, Debuggable {
    public static final int RENDER_DISTANCE = 2;

    private String fileName;
    private ArrayList<Chunk> unactiveChunks;
    private ArrayList<Chunk> activeChunks;

    public Map(String mapFileName) {
        this.fileName = mapFileName;
        this.unactiveChunks = new ArrayList<Chunk>();
        this.activeChunks = new ArrayList<Chunk>();
    }

    public void loadFromFile() {
        // Open map file.
        BufferedReader input;
        try {
            FileReader mapFile = new FileReader(this.fileName);
            input = new BufferedReader(mapFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Error: Map file not found. [" + this.fileName + "]");
            return;
        }
        
        // Load chunks.
        try {
            int numChunks = Integer.parseInt(input.readLine());

            for (int i = 0; i < numChunks; i++) {
                String chunkFileName = input.readLine();
                Chunk newChunk = new Chunk();
                newChunk.setFileName(chunkFileName);
                newChunk.loadFromFile();
                this.unactiveChunks.add(newChunk);
            }
        } catch (IOException ex) {
            System.out.println("Error: Could not read map file.");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Incorrect map file format.");
        }

        // Close map file.
        try {
            input.close();
        } catch (IOException ex) {
            System.out.println("Error: Map file cannot be closed.");
        }
    }

    public void updateRendering(Vector cameraRealPosition) {
        Vector cameraMapPosition = Map.calculateMapPosition(cameraRealPosition);
        // Remove chunks that are now outside render distance.
        for (Iterator<Chunk> it = this.activeChunks.iterator(); it.hasNext(); ) {
            Chunk chunk = it.next();
            if (Vector.checkDistance(chunk.getMapPos(), cameraMapPosition, Map.RENDER_DISTANCE) > 0) {
                this.unactiveChunks.add(chunk);
                it.remove();
            }
        }

        // Add chunks that are now inside render distance.
        for (Iterator<Chunk> it = this.unactiveChunks.iterator(); it.hasNext(); ) {
            Chunk chunk = it.next();
            if (Vector.checkDistance(chunk.getMapPos(), cameraMapPosition, Map.RENDER_DISTANCE) <= 0) {
                this.activeChunks.add(chunk);
                it.remove();
            }
        }
    }

    public Chunk getChunkContaining(int x, int y) {
        // Search in active chunks.
        for (Chunk chunk: this.activeChunks) {
            if (chunk.contains(x, y)) {
                return chunk;
            }
        }

        // Search in unactive chunks.
        for (Chunk chunk: this.unactiveChunks) {
            if (chunk.contains(x, y)) {
                return chunk;
            }
        }

        return null;
    }

    public ArrayList<Chunk> getActiveChunksIntersecting(Hitbox other) {
        ArrayList<Chunk> chunks = new ArrayList<Chunk>();
        
        // Search in active chunks.
        for (Chunk chunk: this.activeChunks) {
            if (chunk.intersects(other)) {
                chunks.add(chunk);
            }
        }

        return chunks;
    }

    public boolean intersectsWithActiveSolid(Hitbox other) {
        ArrayList<Chunk> intersectingChunks = this.getActiveChunksIntersecting(other);

        for (Chunk chunk: intersectingChunks) {
            if (chunk.intersectsWithSolid(other)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics graphics) {
        for (Chunk chunk: this.activeChunks) {
            chunk.draw(graphics);
        }
    }

    @Override
    public void drawDebugInfo(Graphics graphics) {
        for (Chunk chunk: this.activeChunks) {
            chunk.drawDebugInfo(graphics);
        }
    }

    public static Vector calculateMapPosition(Vector realPosition) {
        Vector mapPosition = realPosition.clone();
        mapPosition.div(Chunk.LENGTH * Tile.LENGTH);
        mapPosition.setX((int) mapPosition.getX());
        mapPosition.setY((int) mapPosition.getY());
        return mapPosition;
    }

    public static Vector calculateRealPosition(Vector mapPosition) {
        Vector realPosition = mapPosition.clone();
        realPosition.mult(Chunk.LENGTH * Tile.LENGTH);
        return realPosition;
    }
}

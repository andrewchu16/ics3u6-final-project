import java.awt.Graphics;

import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.Iterator;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * This class represents the game map storing the position of all tiles and chunks
 * in the game.
 */
public class Map implements Drawable, Debuggable {
    // The number of chunks away from the player to render.
    public static final int RENDER_DISTANCE = 2;

    private String fileName;
    private ArrayList<Chunk> unactiveChunks;
    private ArrayList<Chunk> activeChunks;

    /**
     * This constructs a {@code Map} object using data from a file.
     * @param mapFileName The name of the map file.
     * @see Map#loadFromFile()
     */
    public Map(String mapFileName) {
        this.fileName = mapFileName;
        this.unactiveChunks = new ArrayList<Chunk>();
        this.activeChunks = new ArrayList<Chunk>();
    }

    /**
     * This method loads the map and chunk data from a file. The map file is formatted 
     * as follows with values substituted in:
     * <pre>{@code
     * numChunks
     * chunkFilePath1
     * chunkFilePath2
     * chunkFilePath3
     * ...
     * chunkFilePathN
     * }</pre>
     * If an error occurs while reading the file, a relevant error message will be provided.
     * @see Chunk#loadFromFile()
     */
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
                Chunk newChunk = new Chunk(chunkFileName);
                newChunk.loadFromFile();
                this.unactiveChunks.add(newChunk);
            }
        } catch (IOException ex) {
            System.out.println("Error: Could not read map file.");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Incorrect map file number format.");
        }

        // Close map file.
        try {
            input.close();
        } catch (IOException ex) {
            System.out.println("Error: Map file cannot be closed.");
        }
    }

    /**
     * This method loads and unloads chunks based on their proximity to the camera.
     * @param cameraRealPosition The real position of the camera.
     * @see Map#calculateRealPosition(Vector)
     */
    public void updateRendering(Vector cameraRealPosition) {
        Vector cameraMapPosition = Map.calculateMapPosition(cameraRealPosition);
        // Remove chunks that are now outside render distance.
        for (Iterator<Chunk> it = this.activeChunks.iterator(); it.hasNext(); ) {
            Chunk chunk = it.next();
            if (Vector.compareDistance(chunk.getMapPos(), cameraMapPosition, Map.RENDER_DISTANCE) > 0) {
                this.unactiveChunks.add(chunk);
                it.remove();
            }
        }

        // Add chunks that are now inside render distance.
        for (Iterator<Chunk> it = this.unactiveChunks.iterator(); it.hasNext(); ) {
            Chunk chunk = it.next();
            if (Vector.compareDistance(chunk.getMapPos(), cameraMapPosition, Map.RENDER_DISTANCE) <= 0) {
                this.activeChunks.add(chunk);
                it.remove();
            }
        }
    }

    /**
     * This method gets the {@code Chunk} that a coordinate falls into.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The {@code Chunk} containing the coordinate, {@code null} if no chunk 
     * contains the coordinate.
     */
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

        // The chunk cannot be found on the map.
        return null;
    }

    public ArrayList<Chunk> getActiveChunks() {
        return this.activeChunks;
    }

    public ArrayList<Chunk> getUnactiveChunks() {
        return this.unactiveChunks;
    }

    /**
     * This method determines the chunks that intersect with a hitbox. It 
     * only checks the active chunks for intersections.
     * @param other The hitbox to check.
     * @return An {@code ArrayList} object containing all the active intersecting chunks.
     */
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

    /**
     * This method determines whether a hitbox intersects with an active solid tile.
     * It only checks the active chunks for solid tiles.
     * @param other The hitbox to check.
     * @return {@code true} if the hitbox does intersect, {@code false} otherwise.
     */
    public boolean intersectsWithActiveSolid(Hitbox other) {
        ArrayList<Chunk> intersectingChunks = this.getActiveChunksIntersecting(other);

        for (Chunk chunk: intersectingChunks) {
            if (chunk.intersectsWithSolid(other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method draws all the active chunks.
     */
    @Override
    public void draw(Graphics graphics) {
        for (Chunk chunk: this.activeChunks) {
            chunk.draw(graphics);
        }
    }

    /**
     * This method draws the debug information of all the active chunks.
     */
    @Override
    public void drawDebugInfo(Graphics graphics) {
        for (Chunk chunk: this.activeChunks) {
            chunk.drawDebugInfo(graphics);
        }
    }

    /**
     * This method calculates the map position from a real position. The map
     * position represents a position such that if each chunk represents one 
     * unit, the map position would be the coordinate of the chunk the real
     * position falls into. Map position is floored to the lowest integer.
     * @param realPosition The real position.
     * @return The map position based on the real position.
     */
    public static Vector calculateMapPosition(Vector realPosition) {
        Vector mapPosition = realPosition.clone();
        mapPosition.div(Chunk.LENGTH * Tile.LENGTH);
        mapPosition.setX(Math.floor(mapPosition.getX()));
        mapPosition.setY(Math.floor(mapPosition.getY()));
        return mapPosition;
    }

    /**
     * This method calculates the real position from a map position. Since map
     * positions are floored to the lowest integer, precision is lost when converting
     * between map and real position.
     * @param mapPosition The map position.
     * @return The approximate real position based on the map position.
     * @see Map#calculateMapPosition(Vector)
     */
    public static Vector calculateRealPosition(Vector mapPosition) {
        Vector realPosition = mapPosition.clone();
        realPosition.mult(Chunk.LENGTH * Tile.LENGTH);
        return realPosition;
    }
}

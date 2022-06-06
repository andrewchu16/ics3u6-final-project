public interface Collidable {
    /**
     * This method determines whether a coordinate is inside the object.
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return {@code true} if the coordinate is inside the object, {@code false} otherwise.
     */
    public boolean contains(int x, int y);
    /**
     * This method determines whether a {@code Hitbox} intersects with the object.
     * @param hitbox The {@code Hitbox} to check.
     * @return {@code true} if the {@code Hitbox} intersects with the object, {@code false} otherwise.
     */
    public boolean intersects(Hitbox other);
}

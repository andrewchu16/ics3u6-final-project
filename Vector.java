/**
 * This class represents a 2-dimensional vector with double precision.
 */
public class Vector {
    public static final Vector VECTOR_ZERO = new Vector(0, 0);

    private double x;
    private double y;

    /**
     * This constructs a new {@code Vector} object at (0, 0).
     */
    public Vector() {
        this.x = 0.0;
        this.y = 0.0;
    }

    /**
     * This constructs a new {@code Vector} object at the specified coordinate.
     * @param x The x-coordinate of the {@code Vector}.
     * @param y The y-coordinate of the {@code Vector}.
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method adds the x and y parts of a {@code Vector} to this {@code Vector}. 
     * For example, if the first {@code Vector} is at {@code (a, b)} and the second 
     * {@code Vector} s at {@code (c, d)}, This method will set the first {@code Vector} 
     * to {@code (a+c, b+d)}.
     * @param other The {@code Vector} to add.
     */
    public void add(Vector other) {
        this.x += other.getX();
        this.y += other.getY();
    }

    /**
     * This method subtracts the x and y parts of a {@code Vector} to this 
     * {@code Vector}. For example, if the first {@code Vector} is at {@code (a, b)} 
     * and the second {@code Vector} is at {@code (c, d)}, This method will set the 
     * first {@code Vector} to {@code (a-c, b-d)}.
     * @param other The {@code Vector} to subtract.
     */
    public void sub(Vector other) {
        this.x -= other.getX();
        this.y -= other.getY();
    }

    /**
     * This method divides the x and y-coordinates of this {@code Vector} by a scalar.
     * @param scalar The number to divide this {@code Vector} by.
     */
    public void div(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
    }
    /**
     * This method multiplies the x and y-coordinates of this {@code Vector} by a scalar.
     * @param scalar The number to multiply the {@code Vector} by.
     */
    public void mult(double scalar) {
        this.scale(scalar);
    }

    /**
     * This method calculates the length of this {@code Vector}. It will always 
     * return a positive value for all valid {@code Vector}s.
     * @return The length or magnitude of the {@code Vector}.
     */
    public double getMagnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    /**
     * This method gets the dot product of this and another {@code Vector}.
     * @param other The other {@code Vector}.
     * @return The dot product of the {@code Vector}s, a scalar.
     */
    public double dot(Vector other) {
        return this.x * other.getX() + this.y * other.getY();
    }
    
    /**
     * This method sets the length of this {@code Vector} to 1.
     */
    public void normalize() {
        this.scale(1 / this.getMagnitude());
    }

    /**
     * This method scales the length of this {@code Vector}.
     * @param scalar The length to scale this {@code Vector} by.
     */
    public void scale(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    /**
     * This method rotates this {@code Vector} in user coordinates.
     * @param angleDegrees The degree to rotate the vector in user coordinates.
     */
    public void rotate(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);

        double cosAngle = Math.cos(angleRadians);
        double sinAngle = Math.sin(angleRadians);

        double x2 = cosAngle * this.x - sinAngle * this.y;
        double y2 = sinAngle * this.x + cosAngle * this.y;

        this.x = x2;
        this.y = y2;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * This method sets this {@code Vector} to the specified length.
     * @param length The new length of this {@code Vector}.
     */
    public void setLength(double length) {
        this.scale(length / this.getMagnitude());
    }

    /**
     * This method returns a copy of this {@code Vector}.
     * @return The copied {@code Vector}.
     */
    @Override
    public Vector clone() {
        return new Vector(this.getX(), this.getY());
    }

    /**
     * This method determines whether another object is the same as it.
     * @return {@code true} if they are the same, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            Vector vector = (Vector) obj;
            return Double.compare(this.x, vector.getX()) == 0 && Double.compare(this.y, vector.getY()) == 0;
        }

        return false;
    }
    /**
     * This method returns a string representation of this {@code Vector} in the form, 
     * Vec(x, y).
     */
    @Override
    public String toString() {
        return "Vec(" + x + ", " + y + ")";
    }
}
